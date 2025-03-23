package com.anmory.platform.DatabaseService.Dao;


/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-23 下午2:44
 */

public class Herb {
    private int herbId; // 数据库列名为 herb_id
    private String herbName; // 数据库列名为 herb_name
    private String herbTibetanName; // 数据库列名为 herb_tibetan_name
    private String herbAlias; // 数据库列名为 herb_alias
    private String herbDescription; // 数据库列名为 herb_description
    private String partUsed; // 数据库列名为 part_used
    private String herbFeatures; // 数据库列名为 herb_features
    private String flavorTropism; // 数据库列名为 flavor_tropism
    private String functionIndication; // 数据库列名为 function_indication
    private String clinicalApplication; // 数据库列名为 clinical_application
    private String pharmacologicalEffect; // 数据库列名为 pharmacological_effect
    private String research; // 数据库列名为 research
    private String notes; // 数据库列名为 notes

    public int getHerbId() {
        return herbId;
    }

    public Herb(int herbId, String herbName, String herbTibetanName, String herbAlias, String herbDescription, String partUsed, String herbFeatures, String flavorTropism, String functionIndication, String clinicalApplication, String pharmacologicalEffect, String research, String notes) {
        this.herbId = herbId;
        this.herbName = herbName;
        this.herbTibetanName = herbTibetanName;
        this.herbAlias = herbAlias;
        this.herbDescription = herbDescription;
        this.partUsed = partUsed;
        this.herbFeatures = herbFeatures;
        this.flavorTropism = flavorTropism;
        this.functionIndication = functionIndication;
        this.clinicalApplication = clinicalApplication;
        this.pharmacologicalEffect = pharmacologicalEffect;
        this.research = research;
        this.notes = notes;
    }

    public void setHerbId(int herbId) {
        this.herbId = herbId;
    }

    public String getHerbName() {
        return herbName;
    }

    public void setHerbName(String herbName) {
        this.herbName = herbName;
    }

    public String getHerbTibetanName() {
        return herbTibetanName;
    }

    public void setHerbTibetanName(String herbTibetanName) {
        this.herbTibetanName = herbTibetanName;
    }

    public String getHerbAlias() {
        return herbAlias;
    }

    public void setHerbAlias(String herbAlias) {
        this.herbAlias = herbAlias;
    }

    public String getHerbDescription() {
        return herbDescription;
    }

    public void setHerbDescription(String herbDescription) {
        this.herbDescription = herbDescription;
    }

    public String getPartUsed() {
        return partUsed;
    }

    public void setPartUsed(String partUsed) {
        this.partUsed = partUsed;
    }

    public String getHerbFeatures() {
        return herbFeatures;
    }

    public void setHerbFeatures(String herbFeatures) {
        this.herbFeatures = herbFeatures;
    }

    public String getFlavorTropism() {
        return flavorTropism;
    }

    public void setFlavorTropism(String flavorTropism) {
        this.flavorTropism = flavorTropism;
    }

    public String getFunctionIndication() {
        return functionIndication;
    }

    public void setFunctionIndication(String functionIndication) {
        this.functionIndication = functionIndication;
    }

    public String getClinicalApplication() {
        return clinicalApplication;
    }

    public void setClinicalApplication(String clinicalApplication) {
        this.clinicalApplication = clinicalApplication;
    }

    public String getPharmacologicalEffect() {
        return pharmacologicalEffect;
    }

    public void setPharmacologicalEffect(String pharmacologicalEffect) {
        this.pharmacologicalEffect = pharmacologicalEffect;
    }

    public String getResearch() {
        return research;
    }

    public void setResearch(String research) {
        this.research = research;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Herb{" +
                "herbId=" + herbId +
                ", herbName='" + herbName + '\'' +
                ", herbTibetanName='" + herbTibetanName + '\'' +
                ", herbAlias='" + herbAlias + '\'' +
                ", herbDescription='" + herbDescription + '\'' +
                ", partUsed='" + partUsed + '\'' +
                ", herbFeatures='" + herbFeatures + '\'' +
                ", flavorTropism='" + flavorTropism + '\'' +
                ", functionIndication='" + functionIndication + '\'' +
                ", clinicalApplication='" + clinicalApplication + '\'' +
                ", pharmacologicalEffect='" + pharmacologicalEffect + '\'' +
                ", research='" + research + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
