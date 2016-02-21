package com.andela.currencycalculator.model.dbparser;

/**
 * Created by Spykins on 21/02/2016.
 */
public final class DbConfig {

    public DbConfig() {

    }

    public interface FeedEntry {

        int DATABASE_VERSION = 1;
        String DATABASE_NAME = "exchangeRate.db";
        String TABLE_NAME = "currency_exchange";
        String COLUMN_ID = "_id";
        String COLUMN_BASE_CURRENCY = "baseCurrency";
        String COLUMN_CURRENCY = "currency";
        String COLUMN_EXCHANGE_RATE = "exchangeRate";
        String COLUMN_DATE = "dateCreated";

    }
}
