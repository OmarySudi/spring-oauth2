package com.carpool.auth.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.carpool.auth.exeption.EntityNotFoundException;
import com.carpool.auth.exeption.InternalServerErrorException;
import com.carpool.auth.model.User;
import com.carpool.auth.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AmazonClient {

 @Autowired
 private UserDetailsRepository userDetailsRepository;

 private AmazonS3 s3client;

 @Value("${amazonProperties.endpointUrl}")
 private String endpointUrl;

 @Value("${amazonProperties.bucketName}")
 private String bucketName;

 @Value("${amazonProperties.accessKey}")
 private String accessKey;

 @Value("${amazonProperties.secretKey}")
 private String secretKey;

 @PostConstruct
 private void initializeAmazon() {
  AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
  this.s3client = new AmazonS3Client(credentials);
 }

 public User uploadFile(MultipartFile multipartFile,String userID) {

  Optional<User> optionalUser = userDetailsRepository.findByUserID(userID);

  if(optionalUser.isPresent()){

   User user = optionalUser.get();

   String fileUrl = "";

   try {

    File file = convertMultiPartToFile(multipartFile);

    String fileName = generateFileName(multipartFile);

    fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;

    uploadFileTos3bucket(fileName, file);

    file.delete();

   } catch (Exception e) {
    //e.printStackTrace();
    throw new InternalServerErrorException("There is server error when uploading a file, please try again");
   }
   user.setProfilePicture(fileUrl);

   userDetailsRepository.save(user);

   return user;
  }
  else
    throw new EntityNotFoundException("User with id "+userID+" does not exist");

 }

 public User uploadFiles(List<MultipartFile> files, String userID,String carID) {

  Optional<User> optionalUser = userDetailsRepository.findByUserID(userID);

  if(optionalUser.isPresent()){

   User user = optionalUser.get();

   files.forEach(multipartFile->{

    String fileUrl = "";

    try {

     File file = convertMultiPartToFile(multipartFile);

     String fileName = generateFileName(multipartFile);

     fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;

     uploadFileTos3bucket(fileName, file);

     file.delete();

    } catch (Exception e) {

     throw new InternalServerErrorException("There is server error when uploading a file, please try again");
    }

    switch(multipartFile.getName()){

     case "profile_picture":
      user.setProfilePicture(fileUrl);
      break;

     case "identity_card":
      user.setIdentityCard(fileUrl);
      break;

     case "driving_licence":
      user.setDrivingLicence(fileUrl);
      break;

     case "certificate_of_good_conduct":
      user.setCertificateOfGoodConduct(fileUrl);
      break;

    }

   });

   user.setCarID(carID);

   userDetailsRepository.save(user);

   return user;
  }
  else
   throw new EntityNotFoundException("User with id "+userID+" does not exist");

 }

 private File convertMultiPartToFile(MultipartFile file) throws IOException {

  File convFile = new File(file.getOriginalFilename());

  FileOutputStream fos = new FileOutputStream(convFile);

  fos.write(file.getBytes());

  fos.close();

  return convFile;
 }

 private String generateFileName(MultipartFile multiPart) {
  return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
 }

 private void uploadFileTos3bucket(String fileName, File file) {
  s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
          .withCannedAcl(CannedAccessControlList.PublicRead));
 }

 public String deleteFileFromS3Bucket(String fileUrl) {
  String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
  s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
  return "Successfully deleted";
 }

}
