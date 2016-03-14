package com.andela.currencycalculator.model.dbparser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.andela.currencycalculator.model.currency.Currency;

import java.util.ArrayList;

/**
 * Created by Spykins on 20/02/2016.
 */
public class DbHandler extends SQLiteOpenHelper {

    public DbHandler(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context, DbConfig.DATABASE_NAME.getRealName(), factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DbConfig.TABLE_NAME.getRealName() + " (" +
                DbConfig.COLUMN_ID.getRealName() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbConfig.COLUMN_BASE_CURRENCY.getRealName() + " TEXT, " +
                DbConfig.COLUMN_CURRENCY.getRealName() + " TEXT , " +
                DbConfig.COLUMN_EXCHANGE_RATE.getRealName() + " DOUBLE, " +
                DbConfig.COLUMN_DATE.getRealName() + " DATE " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  " + DbConfig.TABLE_NAME.getRealName());
        onCreate(db);
    }

    public void insertCurrencyInDatabase(Currency currency) {
        ContentValues values = new ContentValues();
        values.put(DbConfig.COLUMN_BASE_CURRENCY.getRealName(), currency.getBaseCurrency());
        values.put( DbConfig.COLUMN_CURRENCY.getRealName(), currency.getCurrency());
        values.put(DbConfig.COLUMN_EXCHANGE_RATE.getRealName(), currency.getExchangeRate());
        values.put(DbConfig.COLUMN_DATE.getRealName(), currency.getDateCreated());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(DbConfig.TABLE_NAME.getRealName(), null, values);
        db.close();

    }

    public ArrayList<Currency> readCurrencyFromDatabase() {
        ArrayList<Currency> allCurrencyInDataBase = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + DbConfig.TABLE_NAME.getRealName();
        Cursor cursorHandle = db.rawQuery(query, null);
        cursorHandle.moveToFirst();
        while (cursorHandle.moveToNext()) {
            String baseCurrency = cursorHandle.getString(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_BASE_CURRENCY.getRealName()));
            String currency = cursorHandle.getString(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_CURRENCY.getRealName()));
            double exchangeRate = cursorHandle.getDouble(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_EXCHANGE_RATE.getRealName()));
            allCurrencyInDataBase.add(new Currency(baseCurrency, exchangeRate, currency));
        }
        cursorHandle.close();
        db.close();
        return allCurrencyInDataBase;
    }

    public boolean hasDataBase(Context context) {
        SQLiteDatabase checkDB = null;
        try {
            String db_full_path = context.getDatabasePath(
                    DbConfig.DATABASE_NAME.getRealName()).getPath();

            checkDB = SQLiteDatabase.openDatabase(db_full_path, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
        }
        return checkDB != null;
    }

    public void updateDatabase(String currencyCode, Double newValue) {
        SQLiteDatabase sq = getWritableDatabase();
        String query = "UPDATE " + DbConfig.TABLE_NAME.getRealName() + " " + "SET " +
                DbConfig.COLUMN_EXCHANGE_RATE.getRealName() + " = " +
                newValue + " WHERE " + DbConfig.COLUMN_ID.getRealName() + " = " + 0;
        sq.execSQL(query);
    }

}
