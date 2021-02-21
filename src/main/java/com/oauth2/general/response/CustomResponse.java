package com.oauth2.general.response;

import java.util.ArrayList;
import java.util.List;

public class CustomResponse<T>{

    private String message;
    private String details;
    private int generalErrorCode;
    private List<String> errors = new ArrayList<>();
    private List<T> objects = new ArrayList<>();
    private int errorCount;

    public CustomResponse(String message) {
        this.message = message;
        this.generalErrorCode = -1;
        this.errorCount = 0;

    }

    public CustomResponse(String message, String details, int generalErrorCode) {
        this.message = message;
        this.details = details;
        this.generalErrorCode = generalErrorCode;
    }

    public CustomResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getGeneralErrorCode() {
        return generalErrorCode;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    public void addErrorToList(String error){
        this.errors.add(error);
        this.errorCount++;
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    public void setGeneralErrorCode(int generalErrorCode) {
        this.generalErrorCode = generalErrorCode;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
}
