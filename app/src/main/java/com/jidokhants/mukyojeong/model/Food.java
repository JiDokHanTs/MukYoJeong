package com.jidokhants.mukyojeong.model;

public class Food {
    private int id;
    private String dBGroup;
    private String commercial;
    private String name;
    private String from;
    private String subCategory;
    private Double servingSize;
    private String unit;
    private double totalGram;
    private double totalML;
    private double calorie;
    private double moisture;
    private double protein;
    private double fat;
    private double carbohydrate;
    private double sugars;
    private double fiber;
    private double calcium;
    private double fe;
    private double magnesium;
    private double phosphorus;
    private double potassium;
    private double salt;
    private double zinc;
    private double copper;
    private double manganese;
    private double selenium;
    private double iodine;
    private double chlorine;
    private double vitaminA;
    private double vitaminARE;
    private double retinol;
    private double betaCarotene;
    private double vitaminD;
    private double vitaminK;
    private double panto;
    private double vitaminB6;
    private double biotin;
    private double vitaminC;
    private double omega3FattyAcids;
    private double omega6FattyAcids;

    public Food() {
    }

    public Food(int id) {
        this.id = id;
    }

    public Food(int id, double calorie, double moisture, double protein, double fat, double carbohydrate, double sugars, double fiber, double calcium, double fe, double magnesium, double phosphorus, double potassium, double salt, double zinc, double copper, double manganese, double selenium, double iodine, double chlorine, double vitaminA, double vitaminARE, double retinol, double betaCarotene, double vitaminD, double vitaminK, double panto, double vitaminB6, double biotin, double vitaminC, double omega3FattyAcids, double omega6FattyAcids) {
        this.id = id;
        this.calorie = calorie;
        this.moisture = moisture;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.sugars = sugars;
        this.fiber = fiber;
        this.calcium = calcium;
        this.fe = fe;
        this.magnesium = magnesium;
        this.phosphorus = phosphorus;
        this.potassium = potassium;
        this.salt = salt;
        this.zinc = zinc;
        this.copper = copper;
        this.manganese = manganese;
        this.selenium = selenium;
        this.iodine = iodine;
        this.chlorine = chlorine;
        this.vitaminA = vitaminA;
        this.vitaminARE = vitaminARE;
        this.retinol = retinol;
        this.betaCarotene = betaCarotene;
        this.vitaminD = vitaminD;
        this.vitaminK = vitaminK;
        this.panto = panto;
        this.vitaminB6 = vitaminB6;
        this.biotin = biotin;
        this.vitaminC = vitaminC;
        this.omega3FattyAcids = omega3FattyAcids;
        this.omega6FattyAcids = omega6FattyAcids;
    }

    public Food(int id, String dBGroup, String commercial, String name, String from, String subCategory, double servingSize, String unit, double totalGram, double totalML, double calorie, double moisture, double protein, double fat, double carbohydrate, double sugars, double fiber, double calcium, double fe, double magnesium, double phosphorus, double potassium, double salt, double zinc, double copper, double manganese, double selenium, double iodine, double chlorine, double vitaminA, double vitaminARE, double retinol, double betaCarotene, double vitaminD, double vitaminK, double panto, double vitaminB6, double biotin, double vitaminC, double omega3FattyAcids, double omega6FattyAcids) {
        this.id = id;
        this.dBGroup = dBGroup;
        this.commercial = commercial;
        this.name = name;
        this.from = from;
        this.subCategory = subCategory;
        this.servingSize = servingSize;
        this.unit = unit;
        this.totalGram = totalGram;
        this.totalML = totalML;
        this.calorie = calorie;
        this.moisture = moisture;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.sugars = sugars;
        this.fiber = fiber;
        this.calcium = calcium;
        this.fe = fe;
        this.magnesium = magnesium;
        this.phosphorus = phosphorus;
        this.potassium = potassium;
        this.salt = salt;
        this.zinc = zinc;
        this.copper = copper;
        this.manganese = manganese;
        this.selenium = selenium;
        this.iodine = iodine;
        this.chlorine = chlorine;
        this.vitaminA = vitaminA;
        this.vitaminARE = vitaminARE;
        this.retinol = retinol;
        this.betaCarotene = betaCarotene;
        this.vitaminD = vitaminD;
        this.vitaminK = vitaminK;
        this.panto = panto;
        this.vitaminB6 = vitaminB6;
        this.biotin = biotin;
        this.vitaminC = vitaminC;
        this.omega3FattyAcids = omega3FattyAcids;
        this.omega6FattyAcids = omega6FattyAcids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getdBGroup() {
        return dBGroup;
    }

    public void setdBGroup(String dBGroup) {
        this.dBGroup = dBGroup;
    }

    public String getCommercial() {
        return commercial;
    }

    public void setCommercial(String commercial) {
        this.commercial = commercial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public double getServingSize() {
        return servingSize;
    }

    public void setServingSize(double servingSize) {
        this.servingSize = servingSize;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getTotalGram() {
        return totalGram;
    }

    public void setTotalGram(double totalGram) {
        this.totalGram = totalGram;
    }

    public double getTotalML() {
        return totalML;
    }

    public void setTotalML(double totalML) {
        this.totalML = totalML;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public double getMoisture() {
        return moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getSugars() {
        return sugars;
    }

    public void setSugars(double sugars) {
        this.sugars = sugars;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public double getCalcium() {
        return calcium;
    }

    public void setCalcium(double calcium) {
        this.calcium = calcium;
    }

    public double getFe() {
        return fe;
    }

    public void setFe(double fe) {
        this.fe = fe;
    }

    public double getMagnesium() {
        return magnesium;
    }

    public void setMagnesium(double magnesium) {
        this.magnesium = magnesium;
    }

    public double getPhosphorus() {
        return phosphorus;
    }

    public void setPhosphorus(double phosphorus) {
        this.phosphorus = phosphorus;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getSalt() {
        return salt;
    }

    public void setSalt(double salt) {
        this.salt = salt;
    }

    public double getZinc() {
        return zinc;
    }

    public void setZinc(double zinc) {
        this.zinc = zinc;
    }

    public double getCopper() {
        return copper;
    }

    public void setCopper(double copper) {
        this.copper = copper;
    }

    public double getManganese() {
        return manganese;
    }

    public void setManganese(double manganese) {
        this.manganese = manganese;
    }

    public double getSelenium() {
        return selenium;
    }

    public void setSelenium(double selenium) {
        this.selenium = selenium;
    }

    public double getIodine() {
        return iodine;
    }

    public void setIodine(double iodine) {
        this.iodine = iodine;
    }

    public double getChlorine() {
        return chlorine;
    }

    public void setChlorine(double chlorine) {
        this.chlorine = chlorine;
    }

    public double getVitaminA() {
        return vitaminA;
    }

    public void setVitaminA(double vitaminA) {
        this.vitaminA = vitaminA;
    }

    public double getVitaminARE() {
        return vitaminARE;
    }

    public void setVitaminARE(double vitaminARE) {
        this.vitaminARE = vitaminARE;
    }

    public double getRetinol() {
        return retinol;
    }

    public void setRetinol(double retinol) {
        this.retinol = retinol;
    }

    public double getBetaCarotene() {
        return betaCarotene;
    }

    public void setBetaCarotene(double betaCarotene) {
        this.betaCarotene = betaCarotene;
    }

    public double getVitaminD() {
        return vitaminD;
    }

    public void setVitaminD(double vitaminD) {
        this.vitaminD = vitaminD;
    }

    public double getVitaminK() {
        return vitaminK;
    }

    public void setVitaminK(double vitaminK) {
        this.vitaminK = vitaminK;
    }

    public double getPanto() {
        return panto;
    }

    public void setPanto(double panto) {
        this.panto = panto;
    }

    public double getVitaminB6() {
        return vitaminB6;
    }

    public void setVitaminB6(double vitaminB6) {
        this.vitaminB6 = vitaminB6;
    }

    public double getBiotin() {
        return biotin;
    }

    public void setBiotin(double biotin) {
        this.biotin = biotin;
    }

    public double getVitaminC() {
        return vitaminC;
    }

    public void setVitaminC(double vitaminC) {
        this.vitaminC = vitaminC;
    }

    public double getOmega3FattyAcids() {
        return omega3FattyAcids;
    }

    public void setOmega3FattyAcids(double omega3FattyAcids) {
        this.omega3FattyAcids = omega3FattyAcids;
    }

    public double getOmega6FattyAcids() {
        return omega6FattyAcids;
    }

    public void setOmega6FattyAcids(double omega6FattyAcids) {
        this.omega6FattyAcids = omega6FattyAcids;
    }
}
