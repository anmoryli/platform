package com.anmory.platform.DatabaseService.Dao;


/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-23 下午3:13
 */

public class Plant {
    private int plantId;
    private String plantName;
    private String plantLatin;
    private String plantAlias;
    private String plantTibetanName;
    private String plantFamilyName;
    private String plantGenusName;
    private String imgPath;
    private String plantFeatures;
    private String plantOrigin;
    private String plantProtectLevel;
    private String notes;

    @Override
    public String toString() {
        return "Plant{" +
                "plantId=" + plantId +
                ", plantName='" + plantName + '\'' +
                ", plantLatin='" + plantLatin + '\'' +
                ", plantAlias='" + plantAlias + '\'' +
                ", plantTibetanName='" + plantTibetanName + '\'' +
                ", plantFamilyName='" + plantFamilyName + '\'' +
                ", plantGenusName='" + plantGenusName + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", plantFeatures='" + plantFeatures + '\'' +
                ", plantOrigin='" + plantOrigin + '\'' +
                ", plantProtectLevel='" + plantProtectLevel + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantLatin() {
        return plantLatin;
    }

    public void setPlantLatin(String plantLatin) {
        this.plantLatin = plantLatin;
    }

    public String getPlantAlias() {
        return plantAlias;
    }

    public void setPlantAlias(String plantAlias) {
        this.plantAlias = plantAlias;
    }

    public String getPlantTibetanName() {
        return plantTibetanName;
    }

    public void setPlantTibetanName(String plantTibetanName) {
        this.plantTibetanName = plantTibetanName;
    }

    public String getPlantFamilyName() {
        return plantFamilyName;
    }

    public void setPlantFamilyName(String plantFamilyName) {
        this.plantFamilyName = plantFamilyName;
    }

    public String getPlantGenusName() {
        return plantGenusName;
    }

    public void setPlantGenusName(String plantGenusName) {
        this.plantGenusName = plantGenusName;
    }

    public String getPlantFeatures() {
        return plantFeatures;
    }

    public void setPlantFeatures(String plantFeatures) {
        this.plantFeatures = plantFeatures;
    }

    public String getPlantOrigin() {
        return plantOrigin;
    }

    public void setPlantOrigin(String plantOrigin) {
        this.plantOrigin = plantOrigin;
    }

    public String getPlantProtectLevel() {
        return plantProtectLevel;
    }

    public void setPlantProtectLevel(String plantProtectLevel) {
        this.plantProtectLevel = plantProtectLevel;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Plant(int plantId, String plantName, String plantLatin, String plantAlias, String plantTibetanName, String plantFamilyName, String plantGenusName, String plantFeatures, String plantOrigin, String plantProtectLevel, String notes) {
        this.plantId = plantId;
        this.plantName = plantName;
        this.plantLatin = plantLatin;
        this.plantAlias = plantAlias;
        this.plantTibetanName = plantTibetanName;
        this.plantFamilyName = plantFamilyName;
        this.plantGenusName = plantGenusName;
        this.plantFeatures = plantFeatures;
        this.plantOrigin = plantOrigin;
        this.plantProtectLevel = plantProtectLevel;
        this.notes = notes;
    }
}
