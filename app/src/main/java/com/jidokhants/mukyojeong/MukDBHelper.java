package com.jidokhants.mukyojeong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jidokhants.mukyojeong.model.Food;
import com.jidokhants.mukyojeong.model.FoodItem;
import com.jidokhants.mukyojeong.model.Record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MukDBHelper extends SQLiteOpenHelper {
    private static volatile MukDBHelper instance;
    private static final String DB_NAME = "mukyojeong.db";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase db = null;

    private MukDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (db == null)
            db = getWritableDatabase();
    }

    public static MukDBHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (MukDBHelper.class) {
                if (instance == null) {


                    instance = new MukDBHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.disableWriteAheadLogging();
    }

    public ArrayList<FoodItem> getSearchAllFood() {
        ArrayList<FoodItem> foodItems = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery(MukDBContract.SQL_FOOD_SELECT_KEYWORDS, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String commercial = cursor.getString(1);
            String name = cursor.getString(2);
            String from = cursor.getString(3);
            FoodItem foodItem = new FoodItem(id, commercial, name, from);
            foodItems.add(foodItem);
        }
        return foodItems;
    }

    public ArrayList<Food> getAllFood() {
        ArrayList<Food> foodList = new ArrayList<Food>();
        Cursor cursor;

        cursor = db.rawQuery(MukDBContract.SQL_FOOD_SELECT + " ORDER BY " + MukDBContract.FOOD_COL_NAME, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String dBGroup = cursor.getString(1);
            String commercial = cursor.getString(2);
            String name = cursor.getString(3);
            String from = cursor.getString(4);
            String subCategory = cursor.getString(5);
            double servingSize = cursor.getDouble(6);
            String unit = cursor.getString(7);
            double totalGram = cursor.getDouble(8);
            double totalML = cursor.getDouble(9);
            double calorie = cursor.getDouble(10);
            double moisture = cursor.getDouble(11);
            double protein = cursor.getDouble(12);
            double fat = cursor.getDouble(13);
            double carbohydrate = cursor.getDouble(14);
            double sugars = cursor.getDouble(15);
            double fiber = cursor.getDouble(16);
            double calcium = cursor.getDouble(17);
            double fe = cursor.getDouble(18);
            double magnesium = cursor.getDouble(19);
            double phosphorus = cursor.getDouble(20);
            double potassium = cursor.getDouble(21);
            double salt = cursor.getDouble(22);
            double zinc = cursor.getDouble(23);
            double copper = cursor.getDouble(24);
            double manganese = cursor.getDouble(25);
            double selenium = cursor.getDouble(26);
            double iodine = cursor.getDouble(27);
            double chlorine = cursor.getDouble(28);
            double vitaminA = cursor.getDouble(29);
            double vitaminARE = cursor.getDouble(30);
            double retinol = cursor.getDouble(31);
            double betaCarotene = cursor.getDouble(32);
            double vitaminB = cursor.getDouble(33);
            double vitaminD = cursor.getDouble(34);
            double panto = cursor.getDouble(35);
            double vitaminB6 = cursor.getDouble(36);
            double biotin = cursor.getDouble(37);
            double vitaminC = cursor.getDouble(38);
            double omega3FattyAcids = cursor.getDouble(39);
            double omega6FattyAcids = cursor.getDouble(40);

            Food food = new Food(id, dBGroup, commercial, name, from, subCategory, servingSize, unit, totalGram, totalML,
                    calorie, moisture, protein, fat, carbohydrate, sugars, fiber, calcium, fe, magnesium, phosphorus, potassium,
                    salt, zinc, copper, manganese, selenium, iodine, chlorine, vitaminA, vitaminARE, retinol, betaCarotene, vitaminB,
                    vitaminD, panto, vitaminB6, biotin, vitaminC, omega3FattyAcids, omega6FattyAcids);
            foodList.add(food);
        }
        return foodList;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Record getRecordOne(long recordId) {
        String selection = MukDBContract.RECORD_COL_ID + " = " + recordId;
        Cursor cursor = db.rawQuery(MukDBContract.SQL_RECORD_SELECT + selection, null);
        cursor.moveToNext();
        int id = cursor.getInt(0);
        String tempDate = cursor.getString(1);
        int tempMeal = cursor.getInt(2);
        double tempRatio = cursor.getDouble(4);
        int fid = cursor.getInt(5);
        String dBGroup = cursor.getString(6);
        String commercial = cursor.getString(7);
        String name = cursor.getString(8);
        String from = cursor.getString(9);
        String subCategory = cursor.getString(10);
        double servingSize = cursor.getDouble(11);
        String unit = cursor.getString(12);
        double totalGram = cursor.getDouble(13);
        double totalML = cursor.getDouble(14);
        double calorie = cursor.getDouble(15);
        double moisture = cursor.getDouble(16);
        double protein = cursor.getDouble(17);
        double fat = cursor.getDouble(18);
        double carbohydrate = cursor.getDouble(19);
        double sugars = cursor.getDouble(20);
        double fiber = cursor.getDouble(21);
        double calcium = cursor.getDouble(22);
        double fe = cursor.getDouble(23);
        double magnesium = cursor.getDouble(24);
        double phosphorus = cursor.getDouble(25);
        double potassium = cursor.getDouble(26);
        double salt = cursor.getDouble(27);
        double zinc = cursor.getDouble(28);
        double copper = cursor.getDouble(29);
        double manganese = cursor.getDouble(30);
        double selenium = cursor.getDouble(31);
        double iodine = cursor.getDouble(32);
        double chlorine = cursor.getDouble(33);
        double vitaminA = cursor.getDouble(34);
        double vitaminARE = cursor.getDouble(35);
        double retinol = cursor.getDouble(36);
        double betaCarotene = cursor.getDouble(37);
        double vitaminB = cursor.getDouble(38);
        double vitaminD = cursor.getDouble(39);
        double panto = cursor.getDouble(40);
        double vitaminB6 = cursor.getDouble(41);
        double biotin = cursor.getDouble(42);
        double vitaminC = cursor.getDouble(43);
        double omega3FattyAcids = cursor.getDouble(44);
        double omega6FattyAcids = cursor.getDouble(45);

        Food food = new Food(fid, dBGroup, commercial, name, from, subCategory, servingSize, unit, totalGram, totalML,
                calorie, moisture, protein, fat, carbohydrate, sugars, fiber, calcium, fe, magnesium, phosphorus, potassium,
                salt, zinc, copper, manganese, selenium, iodine, chlorine, vitaminA, vitaminARE, retinol, betaCarotene, vitaminB,
                vitaminD, panto, vitaminB6, biotin, vitaminC, omega3FattyAcids, omega6FattyAcids);
        return (new Record(id, tempDate, tempMeal, tempRatio, food));
    }

    public ArrayList<Record> getRecord(String date, int meal) {

        ArrayList<Record> records = new ArrayList<>();

        String selection = MukDBContract.RECORD_COL_DATE + " = '" + date + "' AND " + MukDBContract.RECORD_COL_MEAL + " = " + meal;

        Cursor cursor = db.rawQuery(MukDBContract.SQL_RECORD_SELECT + selection, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tempDate = cursor.getString(1);
            int tempMeal = cursor.getInt(2);
            double tempRatio = cursor.getDouble(4);
            int fid = cursor.getInt(5);
            String dBGroup = cursor.getString(6);
            String commercial = cursor.getString(7);
            String name = cursor.getString(8);
            String from = cursor.getString(9);
            String subCategory = cursor.getString(10);
            double servingSize = cursor.getDouble(11);
            String unit = cursor.getString(12);
            double totalGram = cursor.getDouble(13);
            double totalML = cursor.getDouble(14);
            double calorie = cursor.getDouble(15);
            double moisture = cursor.getDouble(16);
            double protein = cursor.getDouble(17);
            double fat = cursor.getDouble(18);
            double carbohydrate = cursor.getDouble(19);
            double sugars = cursor.getDouble(20);
            double fiber = cursor.getDouble(21);
            double calcium = cursor.getDouble(22);
            double fe = cursor.getDouble(23);
            double magnesium = cursor.getDouble(24);
            double phosphorus = cursor.getDouble(25);
            double potassium = cursor.getDouble(26);
            double salt = cursor.getDouble(27);
            double zinc = cursor.getDouble(28);
            double copper = cursor.getDouble(29);
            double manganese = cursor.getDouble(30);
            double selenium = cursor.getDouble(31);
            double iodine = cursor.getDouble(32);
            double chlorine = cursor.getDouble(33);
            double vitaminA = cursor.getDouble(34);
            double vitaminARE = cursor.getDouble(35);
            double retinol = cursor.getDouble(36);
            double betaCarotene = cursor.getDouble(37);
            double vitaminB = cursor.getDouble(38);
            double vitaminD = cursor.getDouble(39);
            double panto = cursor.getDouble(40);
            double vitaminB6 = cursor.getDouble(41);
            double biotin = cursor.getDouble(42);
            double vitaminC = cursor.getDouble(43);
            double omega3FattyAcids = cursor.getDouble(44);
            double omega6FattyAcids = cursor.getDouble(45);

            Food food = new Food(fid, dBGroup, commercial, name, from, subCategory, servingSize, unit, totalGram, totalML,
                    calorie, moisture, protein, fat, carbohydrate, sugars, fiber, calcium, fe, magnesium, phosphorus, potassium,
                    salt, zinc, copper, manganese, selenium, iodine, chlorine, vitaminA, vitaminARE, retinol, betaCarotene, vitaminB,
                    vitaminD, panto, vitaminB6, biotin, vitaminC, omega3FattyAcids, omega6FattyAcids);
            records.add(new Record(id, tempDate, tempMeal, tempRatio, food));
            ;
        }
        return records;
    }

    public Food weekRecord(String date_from, String date_to) {

        String selection = "'" + date_from + "' AND '" + date_to + "' GROUP BY rcd_date)";
        Cursor cursor = db.rawQuery(MukDBContract.SQL_RECORD_WEEK_SELECT + selection, null);

        cursor.moveToNext();
        int fid = cursor.getInt(0);
        double calorie = cursor.getDouble(1);
        double moisture = cursor.getDouble(2);
        double protein = cursor.getDouble(3);
        double fat = cursor.getDouble(4);
        double carbohydrate = cursor.getDouble(5);
        double sugars = cursor.getDouble(6);
        double fiber = cursor.getDouble(7);
        double calcium = cursor.getDouble(8);
        double fe = cursor.getDouble(9);
        double magnesium = cursor.getDouble(10);
        double phosphorus = cursor.getDouble(11);
        double potassium = cursor.getDouble(12);
        double salt = cursor.getDouble(13);
        double zinc = cursor.getDouble(14);
        double copper = cursor.getDouble(15);
        double manganese = cursor.getDouble(16);
        double selenium = cursor.getDouble(17);
        double iodine = cursor.getDouble(18);
        double chlorine = cursor.getDouble(19);
        double vitaminA = cursor.getDouble(20);
        double vitaminARE = cursor.getDouble(21);
        double retinol = cursor.getDouble(22);
        double betaCarotene = cursor.getDouble(23);
        double vitaminB = cursor.getDouble(24);
        double vitaminD = cursor.getDouble(25);
        double panto = cursor.getDouble(26);
        double vitaminB6 = cursor.getDouble(27);
        double biotin = cursor.getDouble(28);
        double vitaminC = cursor.getDouble(29);
        double omega3FattyAcids = cursor.getDouble(30);
        double omega6FattyAcids = cursor.getDouble(31);
        Log.d("MUKDB", "count: " + fid);
        Log.d("MUKDB", "calorie: " + cursor.getDouble(1));
        Food food = new Food(fid, calorie, moisture, protein, fat, carbohydrate, sugars, fiber, calcium, fe, magnesium, phosphorus, potassium, salt, zinc, copper, manganese, selenium, iodine, chlorine, vitaminA, vitaminARE, retinol, betaCarotene, vitaminB, vitaminD, panto, vitaminB6, biotin, vitaminC, omega3FattyAcids, omega6FattyAcids);
        return food;
    }

    public ArrayList<Food> getRecommendFood(Food start, Food end) {
        ArrayList<Food> result = new ArrayList<>();
        String selection = " WHERE ";
        String orderBy = " ORDER BY ";

        if (!((int) (start.getCalorie()) == 0) || !((int) (end.getCalorie()) == 0)) {
            selection += MukDBContract.FOOD_COL_CALORIE + " >= " + start.getCalorie() + " AND " + MukDBContract.FOOD_COL_CALORIE + " < " + end.getCalorie();
        }
        orderBy += MukDBContract.FOOD_COL_CALORIE;
        if (!((int) (start.getCarbohydrate()) == 0) || !((int) (end.getCarbohydrate()) == 0)) {
            selection += " AND " + MukDBContract.FOOD_COL_CARBO + " >= " + start.getCarbohydrate() + " AND " + MukDBContract.FOOD_COL_CARBO + " < " + end.getCarbohydrate();
        }
        orderBy += ", " + MukDBContract.FOOD_COL_CARBO;
        if (!((int) (start.getProtein()) == 0) || !((int) (end.getProtein()) == 0)) {
            selection += " AND " + MukDBContract.FOOD_COL_PROTEIN + " >= " + start.getProtein() + " AND " + MukDBContract.FOOD_COL_PROTEIN + " < " + end.getProtein();
        }
        orderBy += ", " + MukDBContract.FOOD_COL_PROTEIN;
        if (!((int) (start.getFat()) == 0) || !((int) (end.getFat()) == 0)) {
            selection += " AND " + MukDBContract.FOOD_COL_FAT + " >= " + start.getFat() + " AND " + MukDBContract.FOOD_COL_FAT + " < " + end.getFat();
        }
        orderBy += ", " + MukDBContract.FOOD_COL_FAT;
        if (!((int) (start.getMoisture()) == 0) || !((int) (end.getMoisture()) == 0)) {
            selection += " AND " + MukDBContract.FOOD_COL_MOISTURE + " >= " + start.getMoisture() + " AND " + MukDBContract.FOOD_COL_MOISTURE + " < " + end.getMoisture();
        }
        orderBy += ", " + MukDBContract.FOOD_COL_MOISTURE;
        if (!((int) (start.getVitaminD()) == 0) || !((int) (end.getVitaminD()) == 0)) {
            selection += " AND " + MukDBContract.FOOD_COL_VITA_D + " >= " + start.getVitaminD() + " AND " + MukDBContract.FOOD_COL_VITA_D + " < " + end.getVitaminD();
        }
        orderBy += ", " + MukDBContract.FOOD_COL_VITA_D;
        if (!((int) (start.getVitaminC()) == 0) || !((int) (end.getVitaminC()) == 0)) {
            selection += " AND " + MukDBContract.FOOD_COL_VITA_C + " >= " + start.getVitaminC() + " AND " + MukDBContract.FOOD_COL_VITA_C + " < " + end.getVitaminC();
        }
        orderBy += ", " + MukDBContract.FOOD_COL_VITA_C;
        if (!((int) (start.getFiber()) == 0) || !((int) (end.getFiber()) == 0)) {
            selection += " AND " + MukDBContract.FOOD_COL_FIBER + " >= " + start.getFiber() + " AND " + MukDBContract.FOOD_COL_FIBER + " < " + end.getFiber();
        }
        orderBy += ", " + MukDBContract.FOOD_COL_FIBER;
        if (!((int) (start.getFe()) == 0) || !((int) (end.getFe()) == 0)) {
            selection += " AND " + MukDBContract.FOOD_COL_FE + " >= " + start.getFe() + " AND " + MukDBContract.FOOD_COL_FE + " < " + end.getFe();
        }
        orderBy += ", " + MukDBContract.FOOD_COL_FE;
        if (!((int) (start.getSalt()) == 0) || !((int) (end.getSalt()) == 0)) {
            selection += " AND " + MukDBContract.FOOD_COL_SALT + " >= " + start.getSalt() + " AND " + MukDBContract.FOOD_COL_SALT + " < " + end.getSalt();
        }
        orderBy += ", " + MukDBContract.FOOD_COL_SALT;
        if (selection.equals(" WHERE "))
            selection = "";
        else if (selection.contains("WHERE  AND"))
            selection = selection.replaceAll("WHERE  AND", "WHERE");

        Cursor cursor = db.rawQuery(MukDBContract.SQL_FOOD_SELECT + selection + orderBy + " LIMIT 3", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String dBGroup = cursor.getString(1);
            String commercial = cursor.getString(2);
            String name = cursor.getString(3);
            String from = cursor.getString(4);
            String subCategory = cursor.getString(5);
            double servingSize = cursor.getDouble(6);
            String unit = cursor.getString(7);
            double totalGram = cursor.getDouble(8);
            double totalML = cursor.getDouble(9);
            double calorie = cursor.getDouble(10);
            double moisture = cursor.getDouble(11);
            double protein = cursor.getDouble(12);
            double fat = cursor.getDouble(13);
            double carbohydrate = cursor.getDouble(14);
            double sugars = cursor.getDouble(15);
            double fiber = cursor.getDouble(16);
            double calcium = cursor.getDouble(17);
            double fe = cursor.getDouble(18);
            double magnesium = cursor.getDouble(19);
            double phosphorus = cursor.getDouble(20);
            double potassium = cursor.getDouble(21);
            double salt = cursor.getDouble(22);
            double zinc = cursor.getDouble(23);
            double copper = cursor.getDouble(24);
            double manganese = cursor.getDouble(25);
            double selenium = cursor.getDouble(26);
            double iodine = cursor.getDouble(27);
            double chlorine = cursor.getDouble(28);
            double vitaminA = cursor.getDouble(29);
            double vitaminARE = cursor.getDouble(30);
            double retinol = cursor.getDouble(31);
            double betaCarotene = cursor.getDouble(32);
            double vitaminB = cursor.getDouble(33);
            double vitaminD = cursor.getDouble(34);
            double panto = cursor.getDouble(35);
            double vitaminB6 = cursor.getDouble(36);
            double biotin = cursor.getDouble(37);
            double vitaminC = cursor.getDouble(38);
            double omega3FattyAcids = cursor.getDouble(39);
            double omega6FattyAcids = cursor.getDouble(40);

            Food food = new Food(id, dBGroup, commercial, name, from, subCategory, servingSize, unit, totalGram, totalML,
                    calorie, moisture, protein, fat, carbohydrate, sugars, fiber, calcium, fe, magnesium, phosphorus, potassium,
                    salt, zinc, copper, manganese, selenium, iodine, chlorine, vitaminA, vitaminARE, retinol, betaCarotene, vitaminB,
                    vitaminD, panto, vitaminB6, biotin, vitaminC, omega3FattyAcids, omega6FattyAcids);

            result.add(food);
        }
        return result;
    }

    public long insertRecord(Record record) {
        ContentValues values = new ContentValues();
        values.put(MukDBContract.RECORD_COL_DATE, record.getDate());
        values.put(MukDBContract.RECORD_COL_MEAL, record.getMeal());
        values.put(MukDBContract.RECORD_COL_FOOD_ID, record.getFood().getId());
        values.put(MukDBContract.RECORD_COL_AMOUNT_RATIO, record.getAmountRatio());
        return db.insert(MukDBContract.TABLE_RECORDS, null, values);

    }

    public void deleteRocord(Record record) {
        db.execSQL(MukDBContract.SQL_RECORD_DELETE + record.getId());
    }

    public void updateRecord(Record record) {
        db.execSQL(MukDBContract.SQL_RECORD_UPDATE + record.getAmountRatio() + MukDBContract.SQL_RECORD_UPDATE_WHERE + record.getId());
    }
}
