package com.anmory.platform.DatabaseService.Dao;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-02-25 上午3:56
 */

public class Medicine {
    private String name;
    private String category;
    private String efficacyScore;
    private String distribution;
    private String description;
    private String usage;

    public Medicine() {
    }

    public Medicine(String name, String category, String efficacyScore, String distribution, String description, String usage) {
        this.name = name;
        this.category = category;
        this.efficacyScore = efficacyScore;
        this.distribution = distribution;
        this.description = description;
        this.usage = usage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEfficacyScore() {
        return efficacyScore;
    }

    public void setEfficacyScore(String efficacyScore) {
        this.efficacyScore = efficacyScore;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
