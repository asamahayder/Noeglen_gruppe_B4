package com.example.noeglen.data;

public class LicenseDTO {

    private String license;
    private String UserID;
    private boolean used;

    LicenseDTO(){

    }

    public LicenseDTO(String license, String UserID, boolean used) {
        this.license = license;
        this.UserID = UserID;
        this.used = used;


    }

    public String getLicense() {
        return license;
    }

    public String getUserID() {
        return UserID;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
