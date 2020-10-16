package com.jidokhants.mukyojeong;

public class MukDBContract {
    private MukDBContract() {
    }
    // food table
    public static final String TABLE_FOODS = "foods";
    public static final String COL_DB_GROUP = "food_dbgroup";
    public static final String COL_COMM = "food_comm";
    public static final String COL_NAME = "food_name";
    public static final String COL_FROM = "food_from";
    public static final String COL_SUB = "food_subcategory";
    public static final String COL_SERVING_SIZE = "food_servingsize";
    public static final String COL_UNIT = "food_unit";
    public static final String COL_TOTAL_GRAM = "food_total_g";
    public static final String COL_TOTAL_ML = "food_total_ml";
    public static final String COL_CALORIE = "food_kcal";
    public static final String COL_MOISTURE = "food_moisture";
    public static final String COL_PROTEIN = "food_protein";
    public static final String COL_FAT = "food_fat";
    public static final String COL_CARBO = "food_carbo";
    public static final String COL_SUGARS = "food_sugars";
    public static final String COL_FIBER = "food_fiber";
    public static final String COL_CALCIUM = "food_calcium";
    public static final String COL_FE = "food_fe";
    public static final String COL_MAGNESIUM = "food_magnesium";
    public static final String COL_PHO = "food_pho";
    public static final String COL_POTA = "food_pota";
    public static final String COL_SALT = "food_salt";
    public static final String COL_ZINC = "food_zinc";
    public static final String COL_COPPER = "food_copper";
    public static final String COL_MANGAN = "food_mangan";
    public static final String COL_SELENIUM = "food_selenium";
    public static final String COL_IODINE = "food_iodine";
    public static final String COL_CHLORINE = "food_chlorine";
    public static final String COL_VITA_A = "food_vitaA";
    public static final String COL_VITA_ARE = "food_vitaARE";
    public static final String COL_RETINOL = "food_retinol";
    public static final String COL_BETA = "food_beta";
    public static final String COL_VITA_B = "food_vitaB";
    public static final String COL_VITA_D = "food_vitaD";
    public static final String COL_PANTO = "food_panto";
    public static final String COL_VITA_B6 = "food_vitaB6";
    public static final String COL_BIOTIN = "food_biotin";
    public static final String COL_VITA_C = "food_vitaC";
    public static final String COL_OMEGA3_FATTY_ACIDS = "food_omega3_fatty_acids";
    public static final String COL_OMEGA6_FATTY_ACIDS = "food_omega6_fatty_acids";

    public static final String SQL_FOOD_SELECT = "SELECT * FROM "+ TABLE_FOODS;
}
