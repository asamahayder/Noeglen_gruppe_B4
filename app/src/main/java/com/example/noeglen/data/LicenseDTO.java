package com.example.noeglen.data;

/**
 * License objektet bliver brugt til at hente data fra databasen og gemmes i et objekt når man logger ind.
 */

public class LicenseDTO {

    /**
     * @variable license
     * Er selve licensen som bliver brugt til at tjekke efter når man logger ind
     * @variable userID
     * Bliver brugt til at tjekke om der er en bruger der ejer denne license
     * @variable used
     * Hvis licensen er brugt vil denne boolean være true, som vil sige at den ikke kan bruges mere
     *
     */

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

    /** GETTERS AND SETTERS */

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
