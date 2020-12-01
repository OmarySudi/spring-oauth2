package com.carpool.auth.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    public User() {
    }

    public User(User user){

        this.username = user.getUsername();
//        this.email = user.getEmail();
        this.accountNonExpired = user.isAccountNonExpired();
        this.enabled = user.isEnabled();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
        this.accountNonlocked = user.isAccountNonlocked();
        this.password = user.getPassword();
        this.roles = user.getRoles();

        this.userID = user.getUserID();
        this.fullName = user.getFullName();
        this.phoneNumber = user.getPhoneNumber();
        this.profilePicture = user.getProfilePicture();
        this.identityCard = user.getIdentityCard();
        this.drivingLicence = user.getDrivingLicence();
        this.certificateOfGoodConduct = user.getCertificateOfGoodConduct();
        this.carID = user.getCarID();

        this.driverStatus = user.getDriverStatus();
        this.carApproval = user.isCarApproval();
        this.emailConfirmation = user.isEmailConfirmation();
        this.adminDocumentVerification = user.isAdminDocumentVerification();
        this.accountNotification = user.isAccountNotification();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

//    @Column(name = "email")
//    private String email;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "accountNonExpired")
    private boolean accountNonExpired;

    @Column(name = "credentialsNonExpired")
    private boolean credentialsNonExpired;

    @Column(name = "accountNonlocked")
    private boolean accountNonlocked;

    @Column(name = "userID")
    private String userID;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "identinty_card")
    private String identityCard;

    @Column(name = " driving_licence ")
    private String drivingLicence;

    @Column(name = "certificate_of_good_conduct")
    private String certificateOfGoodConduct;

    @Column(name = "carID")
    private String carID;

    @Column(name = "driver_status")
    private String driverStatus;

    @Column(name = "car_approval")
    private boolean carApproval;

    @Column(name = "email_confirmation")
    private boolean emailConfirmation;

    @Column(name = "admin_document_verification")
    private boolean adminDocumentVerification;

    @Column(name = "account_activation")
    private boolean accountNotification;

    @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "role_user", joinColumns = {
                @JoinColumn(name="user_id",referencedColumnName = "id")},inverseJoinColumns = {
                @JoinColumn(name="role_id",referencedColumnName = "id")
        })
    private List<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isAccountNonlocked() {
        return accountNonlocked;
    }

    public void setAccountNonlocked(boolean accountNonlocked) {
        this.accountNonlocked = accountNonlocked;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getDrivingLicence() {
        return drivingLicence;
    }

    public void setDrivingLicence(String drivingLicence) {
        this.drivingLicence = drivingLicence;
    }

    public String getCertificateOfGoodConduct() {
        return certificateOfGoodConduct;
    }

    public void setCertificateOfGoodConduct(String certificateOfGoodConduct) {
        this.certificateOfGoodConduct = certificateOfGoodConduct;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public boolean isCarApproval() {
        return carApproval;
    }

    public void setCarApproval(boolean carApproval) {
        this.carApproval = carApproval;
    }

    public boolean isEmailConfirmation() {
        return emailConfirmation;
    }

    public void setEmailConfirmation(boolean emailConfirmation) {
        this.emailConfirmation = emailConfirmation;
    }

    public boolean isAdminDocumentVerification() {
        return adminDocumentVerification;
    }

    public void setAdminDocumentVerification(boolean adminDocumentVerification) {
        this.adminDocumentVerification = adminDocumentVerification;
    }

    public boolean isAccountNotification() {
        return accountNotification;
    }

    public void setAccountNotification(boolean accountNotification) {
        this.accountNotification = accountNotification;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
