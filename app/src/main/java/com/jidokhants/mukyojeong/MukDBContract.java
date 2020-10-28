package com.jidokhants.mukyojeong;

public class MukDBContract {
    private MukDBContract() {
    }
    // food table
    public static final String TABLE_FOODS = "foods";
    public static final String FOOD_COL_ID = "food_id";
    public static final String FOOD_COL_DB_GROUP = "food_dbgroup";
    public static final String FOOD_COL_COMM = "food_comm";
    public static final String FOOD_COL_NAME = "food_name";
    public static final String FOOD_COL_FROM = "food_from";
    public static final String FOOD_COL_SUB = "food_subcategory";
    public static final String FOOD_COL_SERVING_SIZE = "food_servingsize";
    public static final String FOOD_COL_UNIT = "food_unit";
    public static final String FOOD_COL_TOTAL_GRAM = "food_total_g";
    public static final String FOOD_COL_TOTAL_ML = "food_total_ml";
    public static final String FOOD_COL_CALORIE = "food_kcal";
    public static final String FOOD_COL_MOISTURE = "food_moisture";
    public static final String FOOD_COL_PROTEIN = "food_protein";
    public static final String FOOD_COL_FAT = "food_fat";
    public static final String FOOD_COL_CARBO = "food_carbo";
    public static final String FOOD_COL_SUGARS = "food_sugars";
    public static final String FOOD_COL_FIBER = "food_fiber";
    public static final String FOOD_COL_CALCIUM = "food_calcium";
    public static final String FOOD_COL_FE = "food_fe";
    public static final String FOOD_COL_MAGNESIUM = "food_magnesium";
    public static final String FOOD_COL_PHO = "food_pho";
    public static final String FOOD_COL_POTA = "food_pota";
    public static final String FOOD_COL_SALT = "food_salt";
    public static final String FOOD_COL_ZINC = "food_zinc";
    public static final String FOOD_COL_COPPER = "food_copper";
    public static final String FOOD_COL_MANGAN = "food_mangan";
    public static final String FOOD_COL_SELENIUM = "food_selenium";
    public static final String FOOD_COL_IODINE = "food_iodine";
    public static final String FOOD_COL_CHLORINE = "food_chlorine";
    public static final String FOOD_COL_VITA_A = "food_vitaA";
    public static final String FOOD_COL_VITA_ARE = "food_vitaARE";
    public static final String FOOD_COL_RETINOL = "food_retinol";
    public static final String FOOD_COL_BETA = "food_beta";
    public static final String FOOD_COL_VITA_B = "food_vitaB";
    public static final String FOOD_COL_VITA_D = "food_vitaD";
    public static final String FOOD_COL_PANTO = "food_panto";
    public static final String FOOD_COL_VITA_B6 = "food_vitaB6";
    public static final String FOOD_COL_BIOTIN = "food_biotin";
    public static final String FOOD_COL_VITA_C = "food_vitaC";
    public static final String FOOD_COL_OMEGA3_FATTY_ACIDS = "food_omega3_fatty_acids";
    public static final String FOOD_COL_OMEGA6_FATTY_ACIDS = "food_omega6_fatty_acids";

    public static final String SQL_FOOD_SELECT = "SELECT * FROM "+ TABLE_FOODS+" ORDER BY "+ FOOD_COL_NAME;
    public static final String SQL_FOOD_SELECT_KEYWORDS = "SELECT " + FOOD_COL_ID+", " +FOOD_COL_COMM+", "+FOOD_COL_NAME+", "+FOOD_COL_FROM+" FROM " + TABLE_FOODS;

    public static final String TABLE_RECORDS = "records";
    public static final String RECORD_COL_ID = "rcd_id";
    public static final String RECORD_COL_DATE = "rcd_date";
    public static final String RECORD_COL_MEAL = "rcd_meal";
    public static final String RECORD_COL_FOOD_ID = "rcd_food_id";
    public static final String RECORD_COL_AMOUNT_RATIO = "rcd_amount_ratio";

    public static final String SQL_RECORD_SELECT = "SELECT * FROM " + TABLE_RECORDS +", "+ TABLE_FOODS+" WHERE "+FOOD_COL_ID+" = "+RECORD_COL_FOOD_ID+" AND ";
    public static final String SQL_RECORD_INSERT = "INSERT OR REPLACE INTO "+ TABLE_RECORDS +"("+ RECORD_COL_DATE+", "+RECORD_COL_MEAL+", "
        +RECORD_COL_FOOD_ID+", "+RECORD_COL_AMOUNT_RATIO+") VALUES ";

    public static final String SQL_RECORD_DELETE = "DELETE FROM "+ TABLE_RECORDS +" WHERE "+RECORD_COL_ID +" = ";
    public static final String SQL_RECORD_UPDATE = "UPDATE "+ TABLE_RECORDS +" SET "+ RECORD_COL_AMOUNT_RATIO+" = ";
    public static final String SQL_RECORD_UPDATE_WHERE = " WHERE "+RECORD_COL_ID +" = ";

    public static final String SQL_RECORD_WEEK_SELECT = "SELECT count(rcd_date), AVG(food_kcal) as food_kcal , AVG(food_moisture) as food_moisture , AVG(food_protein) as food_protein , AVG(food_fat) as food_fat , AVG(food_carbo) as food_carbo , AVG(food_sugars) as food_sugars , AVG(food_fiber) as food_fiber , AVG(food_calcium) as food_calcium , AVG(food_fe) as food_fe , AVG(food_magnesium) as food_magnesium , AVG(food_pho) as food_pho , AVG(food_pota) as food_pota , AVG(food_salt) as food_salt , AVG(food_zinc) as food_zinc , AVG(food_copper) as food_copper , AVG(food_mangan) as food_mangan , AVG(food_selenium) as food_selenium , AVG(food_iodine) as food_iodine , AVG(food_chlorine) as food_chlorine , AVG(food_vitaA) as food_vitaA , AVG(food_vitaARE) as food_vitaARE , AVG(food_retinol) as food_retinol , AVG(food_beta) as food_beta , AVG(food_vitaB) as food_vitaB , AVG(food_vitaD) as food_vitaD , AVG(food_panto) as food_panto , AVG(food_vitaB6) as food_vitaB6 , AVG(food_biotin) as food_biotin , AVG(food_vitaC) as food_vitaC , AVG(food_omega3_fatty_acids) as food_omega3_fatty_acids , AVG(food_omega6_fatty_acids) as food_omega6_fatty_acids FROM(SELECT rcd_date, sum(rcd_amount_ratio *food_kcal) as food_kcal , sum(rcd_amount_ratio *food_moisture) as food_moisture , sum(rcd_amount_ratio *food_protein) as food_protein , sum(rcd_amount_ratio *food_fat) as food_fat , sum(rcd_amount_ratio *food_carbo) as food_carbo , sum(rcd_amount_ratio *food_sugars) as food_sugars , sum(rcd_amount_ratio *food_fiber) as food_fiber , sum(rcd_amount_ratio *food_calcium) as food_calcium , sum(rcd_amount_ratio *food_fe) as food_fe , sum(rcd_amount_ratio *food_magnesium) as food_magnesium , sum(rcd_amount_ratio *food_pho) as food_pho , sum(rcd_amount_ratio *food_pota) as food_pota , sum(rcd_amount_ratio *food_salt) as food_salt , sum(rcd_amount_ratio *food_zinc) as food_zinc , sum(rcd_amount_ratio *food_copper) as food_copper , sum(rcd_amount_ratio *food_mangan) as food_mangan , sum(rcd_amount_ratio *food_selenium) as food_selenium , sum(rcd_amount_ratio *food_iodine) as food_iodine , sum(rcd_amount_ratio *food_chlorine) as food_chlorine , sum(rcd_amount_ratio *food_vitaA) as food_vitaA , sum(rcd_amount_ratio *food_vitaARE) as food_vitaARE , sum(rcd_amount_ratio *food_retinol) as food_retinol , sum(rcd_amount_ratio *food_beta) as food_beta , sum(rcd_amount_ratio *food_vitaB) as food_vitaB , sum(rcd_amount_ratio *food_vitaD) as food_vitaD , sum(rcd_amount_ratio *food_panto) as food_panto , sum(rcd_amount_ratio *food_vitaB6) as food_vitaB6 , sum(rcd_amount_ratio *food_biotin) as food_biotin , sum(rcd_amount_ratio *food_vitaC) as food_vitaC , sum(rcd_amount_ratio *food_omega3_fatty_acids) as food_omega3_fatty_acids , sum(rcd_amount_ratio *food_omega6_fatty_acids) as food_omega6_fatty_acids\n" +
            " FROM records, foods WHERE rcd_food_id = food_id AND rcd_date BETWEEN ";
}
