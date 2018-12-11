package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import model.Indicator;
import model.IndicatorPrice;
import model.UserData;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler databaseHandler;

    //Database configurations
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fids_db";

    private static final String USER_DATA = "user_data";
    private static final String PRICE_LIST = "price_list";


    //user_data columns
    private static final String USER_ID = "userId";
    private static final String USER_NAME = "userName";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String TOKEN_TYPE = "tokenType";
    private static final String EXPIRES_AT = "expiresAt";
    private static final String MARKET_ID = "marketId";
    private static final String MARKET_NAME = "marketName";
    private static final String MARKET_TYPE_ID = "marketTypeId"; //SLIMS and/or Main markets


    //Indicator Price Data
    private static final String INDICATOR_ID = "indicatorId";
    private static final String INDICATOR_NAME = "indicatorName";
    private static final String INDICATOR_PRICE = "indicatorPrice";


    public static synchronized DatabaseHandler getInstance(Context context) {

        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler(context.getApplicationContext());
        }
        return databaseHandler;
    }

    //Constructor
    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {

            //Create user_data
            String CREATE_USER_DATA = "CREATE TABLE " + USER_DATA
                    + "("
                    + USER_ID + " INTEGER PRIMARY KEY, "
                    + USER_NAME + " TEXT, "
                    + MARKET_ID + " TEXT, "
                    + MARKET_NAME + " TEXT, "
                    + MARKET_TYPE_ID + " TEXT, "
                    + ACCESS_TOKEN + " TEXT, "
                    + TOKEN_TYPE + " TEXT, "
                    + EXPIRES_AT + " TEXT "
                    + ")";


            String CREATE_PRICE_LIST = "CREATE TABLE " + PRICE_LIST
                    + "("
                    + INDICATOR_ID + " INTEGER PRIMARY KEY, "
                    + INDICATOR_NAME + " TEXT, "
                    + INDICATOR_PRICE + " TEXT "
                    + ")";

            sqLiteDatabase.execSQL(CREATE_USER_DATA);
            sqLiteDatabase.execSQL(CREATE_PRICE_LIST);

        } catch (Exception exception) {
            exception.printStackTrace();

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old_version, int new_version) {
        try {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_DATA);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PRICE_LIST);

            //Recreate tables
            onCreate(sqLiteDatabase);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }

    //Convenience methods - add User data
    public void addUserData(UserData userData) {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(USER_ID, userData.getUserId());
            contentValues.put(USER_NAME, userData.getUserName());
            contentValues.put(MARKET_NAME, userData.getMarketName());
            contentValues.put(MARKET_ID, userData.getMarketId());
            contentValues.put(MARKET_TYPE_ID, userData.getMarketTypeId());
            contentValues.put(ACCESS_TOKEN, userData.getAccessToken());
            contentValues.put(TOKEN_TYPE, userData.getTokenType());
            contentValues.put(EXPIRES_AT, userData.getExpiresAt());


            //Write changes to SQL Lite
            database.insert(USER_DATA, null, contentValues);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            database.close();
        }
    }


    //Get the current users data
    public UserData getUserData() {
        SQLiteDatabase database = this.getReadableDatabase();
        UserData userData = null;

        try {
            String sqlQuery = "SELECT * FROM " + USER_DATA;
            Cursor cursor = database.rawQuery(sqlQuery, null);
            if (cursor.moveToFirst()) {
                userData = new UserData(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                );

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            database.close();
        }
        return userData;
    }

    public void deleteUserData() {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            database.execSQL("DELETE FROM " + USER_DATA);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Add IndicatorPrice
    public void addIndicatorPrice(IndicatorPrice indicatorPrice) {
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(INDICATOR_ID, indicatorPrice.getIndicatorId());
            contentValues.put(INDICATOR_NAME, indicatorPrice.getIndicatorName());
            contentValues.put(INDICATOR_PRICE, indicatorPrice.getIndicatorPrice());
            //Write changes to SQL Lite
            database.insert(PRICE_LIST, null, contentValues);


        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            database.close();
        }
    }


    //GET INDICATORS' PRICE DATA LIST
    public ArrayList<IndicatorPrice> getPriceList() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<IndicatorPrice> indicatorList = new ArrayList<>();
        try {

            String query = "SELECT * FROM " + PRICE_LIST;
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {

                do {
                    IndicatorPrice indicatorPrice = new IndicatorPrice(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2)
                    );

                    indicatorList.add(indicatorPrice);

                } while (cursor.moveToNext());

            }

        } catch (Exception ex) {

        } finally {
            database.close();
        }

        return indicatorList;
    }


    //GET INDICATORS' PRICE DATA LIST
    public ArrayList<IndicatorPrice> getSavedPriceList() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<IndicatorPrice> indicatorList = new ArrayList<>();
        try {

            String query = "SELECT * FROM " + PRICE_LIST
                    + " WHERE " + INDICATOR_PRICE + " >" + "'0'";


            Cursor cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {

                do {
                    IndicatorPrice indicatorPrice = new IndicatorPrice(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2)
                    );

                    indicatorList.add(indicatorPrice);

                } while (cursor.moveToNext());

            }

        } catch (Exception ex) {

        } finally {
            database.close();
        }

        return indicatorList;
    }


    //Update Indicator data
    public int updateIndicatorPrice(String indicatorId, String indicatorPrice) {
        int updated = 0;
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(INDICATOR_PRICE, indicatorPrice);
            updated = database.update(
                    PRICE_LIST,
                    contentValues,
                    INDICATOR_ID + " =?",
                    new String[]{indicatorId}
            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return updated;

    }

    //Return price for a given Indicator
    public String getIndicatorPrice(Indicator indicator) {
        String price = "";
        SQLiteDatabase database = this.getReadableDatabase();
        try {
            String query = "SELECT * FROM " + PRICE_LIST
                    + " WHERE " + INDICATOR_ID + " =" + "'" + indicator.getIndicatorId() + "'";
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                IndicatorPrice fetchedIndicator = new IndicatorPrice(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );
                price = fetchedIndicator.getIndicatorPrice();
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return price;
    }

    public void deleteIndicatorPrice(String indicatorId) {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            database.execSQL("DELETE FROM " + PRICE_LIST
                    + " WHERE " + INDICATOR_ID + " =" + "'"
                    + indicatorId + "'"

            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
