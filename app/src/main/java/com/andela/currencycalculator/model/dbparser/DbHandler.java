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
public class DbHandler extends SQLiteOpenHelper implements DbConfig.FeedEntry {

    public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BASE_CURRENCY + " TEXT, " +
                COLUMN_CURRENCY + " TEXT , " +
                COLUMN_EXCHANGE_RATE + " DOUBLE, " +
                COLUMN_DATE + " DATE " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_NAME);
        onCreate(db);
    }

    public void insertCurrencyInDatabase(Currency currency) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BASE_CURRENCY, currency.getBaseCurrency());
        values.put(COLUMN_CURRENCY, currency.getCurrency());
        values.put(COLUMN_EXCHANGE_RATE, currency.getExchangeRate());
        values.put(COLUMN_DATE, currency.getDateCreated());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public ArrayList<Currency> readCurrencyFromDatabase() {
        ArrayList<Currency> allCurrencyInDataBase = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursorHandle = db.rawQuery(query, null);
        cursorHandle.moveToFirst();
        while (cursorHandle.moveToNext()) {
            String baseCurrency = cursorHandle.getString(cursorHandle.getColumnIndex(COLUMN_BASE_CURRENCY));
            String currency = cursorHandle.getString(cursorHandle.getColumnIndex(COLUMN_CURRENCY));
            double exchangeRate = cursorHandle.getDouble(cursorHandle.getColumnIndex(COLUMN_EXCHANGE_RATE));
            allCurrencyInDataBase.add(new Currency(baseCurrency, exchangeRate, currency));
        }
        cursorHandle.close();
        db.close();
        return allCurrencyInDataBase;
    }

    public boolean hasDataBase(Context context) {
        SQLiteDatabase checkDB = null;
        try {
            String db_full_path = context.getDatabasePath(DATABASE_NAME).getPath();
            checkDB = SQLiteDatabase.openDatabase(db_full_path, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
        }
        return checkDB != null;
    }

    public void updateDatabase(String currencyCode, Double newValue) {
        SQLiteDatabase sq = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " " +
                "SET " + COLUMN_EXCHANGE_RATE + " = " + newValue + " WHERE " + COLUMN_ID + " = " + 0;
        sq.execSQL(query);
    }

}
