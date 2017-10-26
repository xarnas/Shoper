package com.arnas.shoper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class dbSQL extends SQLiteOpenHelper {

    // The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.arnas.shoper/databases/";

    private static String DB_NAME = "sqlite";

    public SQLiteDatabase myDataBase;

    private final Context myContext;

    /**`enter code here`
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     *
     * @param context
     */
    public dbSQL(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     * */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            // do nothing - database already exist


            SQLiteDatabase db = this.getWritableDatabase();

            String UID="_id";
            String CREATE_TABLEMainMenu = "CREATE TABLE IF NOT EXISTS "+"MainMenu" +
                    "( "+UID+" INTEGER PRIMARY KEY AUTOINCREMENT , Name VARCHAR(225))";

            db.execSQL(CREATE_TABLEMainMenu);

            String CREATE_TABLEGroceries = "CREATE TABLE IF NOT EXISTS "+"Groceries" +
                    "( "+UID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Name VARCHAR(225) NOT NULL," +
                    "OpenId INTEGER NOT NULL," +
                    "Unit VARCHAR(225) NOT NULL," +
                    "Category VARCHAR(225) NOT NULL,"+
                    "activeStatus INTEGER,"+
                    "Price DECIMAL(18.2)," +
                    "PricerLink VARCHAR(225),"+
                    "Foto VARCHAR(225))";

            db.execSQL(CREATE_TABLEGroceries);

            String CREATE_TABLECat = "CREATE TABLE IF NOT EXISTS "+"Category" +
                    "( "+UID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Category VARCHAR(225) NOT NULL)";

            db.execSQL(CREATE_TABLECat);


            String CREATE_TABLEPricer = "CREATE TABLE IF NOT EXISTS "+"Pricer" +
                    "( "+UID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Picture VARCHAR(225)," +
                    "Brand VARCHAR(225)," +
                    "Product VARCHAR(225)," +
                    "Price VARCHAR(225),"+
                    "ShopLogo VARCHAR(225),"+
                    "ShopName DECIMAL(225)," +
                    "PriceDate VARCHAR(225))";

            db.execSQL(CREATE_TABLEPricer);

        } else {

            this.getReadableDatabase();

            this.close();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);

        } catch (SQLiteException e) {

            e.printStackTrace();

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

// Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        // Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertDataMainMenu(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        db.insertOrThrow("MainMenu", null , contentValues);
    }

    public void insertDataGroceries(String name, int openID, String unit,String cat ,float price, String priceLink, String foto)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("OpenId", openID);
        contentValues.put("Unit", unit);
        contentValues.put("Price", price);
        contentValues.put("PricerLink", "world");
        contentValues.put("Foto", "hello");
        contentValues.put("Category", cat);
        db.insertOrThrow("Groceries", null , contentValues);
    }

    public void insertPrice(String pic, String brand, String prod,String price ,String logo, String shopName, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Picture", pic);
        contentValues.put("Brand", brand);
        contentValues.put("Product", prod);
        contentValues.put("Price", price);
        contentValues.put("ShopLogo", logo);
        contentValues.put("ShopName", shopName);
        contentValues.put("PriceDate", date);
        db.insertOrThrow("Pricer", null , contentValues);
    }

    public void insertDataCategory(String cat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Category", cat);
        db.insertOrThrow("Category", null , contentValues);
    }
    public void createAllTables(){

        SQLiteDatabase db = this.getWritableDatabase();


        String UID="_id";
        String CREATE_TABLEMainMenu = "CREATE TABLE IF NOT EXISTS "+"MainMenu" +
                "( "+UID+" INTEGER PRIMARY KEY AUTOINCREMENT , Name VARCHAR(225))";

        db.execSQL(CREATE_TABLEMainMenu);

        String CREATE_TABLEGroceries = "CREATE TABLE IF NOT EXISTS "+"Groceries" +
                "( "+UID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name VARCHAR(225) NOT NULL," +
                "OpenId INTEGER NOT NULL," +
                "Unit VARCHAR(225) NOT NULL," +
                "Category VARCHAR(225) NOT NULL,"+
                "activeStatus INTEGER,"+
                "Price DECIMAL(18,4)," +
                "PricerLink VARCHAR(225),"+
                "Foto VARCHAR(225))";

        db.execSQL(CREATE_TABLEGroceries);

        String CREATE_TABLECat = "CREATE TABLE IF NOT EXISTS "+"Category" +
                "( "+UID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Category VARCHAR(225) NOT NULL)";

        db.execSQL(CREATE_TABLECat);


        String CREATE_TABLEPricer = "CREATE TABLE IF NOT EXISTS "+"Pricer" +
                "( "+UID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Picture VARCHAR(225)," +
                "Brand VARCHAR(225)," +
                "Product VARCHAR(225)," +
                "Price VARCHAR(225),"+
                "ShopLogo VARCHAR(225),"+
                "ShopName DECIMAL(225)," +
                "PriceDate VARCHAR(225))";

        db.execSQL(CREATE_TABLEPricer);
    }
    public void deleteSingleGroceries(int id, int openID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Groceries","_id=? and OpenId=?",new String[]{String.valueOf(id),String.valueOf(openID)});
    }
    public void deleteGros(int openID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Groceries","OpenId=?",new String[]{String.valueOf(openID)});
    }
    public void deleteSingleMainMenu(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("Groceries","_id=?",new String[]{String.valueOf(id)});
    }
    public void deleteSingleCat(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Category","_id=?",new String[]{String.valueOf(id)});
    }

    public void wipeTables(){
        SQLiteDatabase db = this.getWritableDatabase();
         db.execSQL("DELETE FROM MainMenu");
         db.execSQL("DELETE FROM Groceries");
         db.execSQL("DELETE FROM Category");

    }
    public void wipePricerList(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Pricer");

    }
    public void dropTables(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+table);

    }
    public void updateSingleGroceries(int id, int openId,String name,String unit,String cat,float price,String priceLink,String foto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("Name", name);
        data.put("OpenId", openId);
        data.put("Unit", unit);
        data.put("Price", price);
        data.put("PricerLink", priceLink);
        data.put("Foto", foto);
        data.put("Category", cat);
        db.update("Groceries", data, "_id="+id+" and OpenId="+openId, null);
    }
    public void updateSingleCat(int id, String catname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("Category", catname);
        db.update("Category", data, "_id="+id, null);
    }
    public void updateSingleGroceriesActiveId(int id, int openId,int activeStatus){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("activeStatus", activeStatus);
        db.update("Groceries", data, "_id="+id+" and OpenId="+openId, null);
    }

    public void updateSingleMainMenu(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("Name", name);
        db.update("MainMenu", data, "_id="+id, null);
    }

    public List<String> prepPriceList(){

        String selectQuery = "SELECT * FROM Pricer;";
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, null);
        }catch (Exception e){
            e.printStackTrace();
        }
        List<String> list = new ArrayList<String>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do{
                    list.add(cursor.getString(3)+" "+cursor.getString(6));
                }
                while (cursor.moveToNext());
            }

            cursor.close();
        }
        return list;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getLastOpenId(){

        String selectQuery = "SELECT _id FROM MainMenu ORDER BY _id DESC LIMIT 1";
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = null;
        int openIdvalue=0;
        try {
            cursor = db.rawQuery(selectQuery, null);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do{
                   openIdvalue=cursor.getInt(0);
                }
                while (cursor.moveToNext());
            }

            cursor.close();
        }
        return openIdvalue;
    }

    public List<String> getPricerListProduct (CharSequence name) {


        String selectQuery = "SELECT  * FROM Pricer WHERE Product LIKE "+"'%"+name+"%'" ;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, null);
        }catch (Exception e){
            e.printStackTrace();
        }
        List<String> list = new ArrayList<String>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do{
                    list.add(cursor.getString(3)+" "+cursor.getString(6));
                }
                while (cursor.moveToNext());
            }

            cursor.close();
        }
        return list;
    }

    public List<String> getPricerListShop (CharSequence name) {


        String selectQuery = "SELECT  * FROM Pricer WHERE ShopName LIKE "+"'%"+name+"%'" ;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, null);
        }catch (Exception e){
            e.printStackTrace();
        }
        List<String> list = new ArrayList<String>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do{
                    list.add(cursor.getString(3)+" "+cursor.getString(6));
                }
                while (cursor.moveToNext());
            }

            cursor.close();
        }
        return list;
    }


    public Pricer getPricerListShopFull (int itemid, int openId, String product,String shop) {

        String selectQuery = "SELECT  * FROM Pricer WHERE ShopName LIKE "+"'%"+shop+"%'"+" AND Product LIKE "+"'%"+product+"%'";
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = null;
        float priceValue=0;
        Pricer pc = null;
        try {
            cursor = db.rawQuery(selectQuery, null);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do{
                    cursor.getString(1);//Foto
                    cursor.getString(0);//pricelink aka id
                    cursor.getString(4);//price
                    priceValue=Float.parseFloat(cursor.getString(4).substring(1));
                    pc = new Pricer(openId,itemid,priceValue,cursor.getString(0),cursor.getString(1));
                }
                while (cursor.moveToNext());
            }

            cursor.close();
        }
        return pc;
    }


    public DyMealsList3 getSingleQueary (String name) {


        String selectQuery = "SELECT  * FROM MainMenu WHERE Name ="+"'"+name+"'" ;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, null);
        }catch (Exception e){
            e.printStackTrace();
        }
        DyMealsList3 data = null;
       if (cursor != null) {
           if (cursor.moveToFirst()) {
               do{
                   data = new DyMealsList3(cursor.getInt(0), cursor.getString(1));
               }
               while (cursor.moveToNext());
           }

           cursor.close();
       }
        return data;
    }


}