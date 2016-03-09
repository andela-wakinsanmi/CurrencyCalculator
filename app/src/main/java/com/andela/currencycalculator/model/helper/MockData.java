package com.andela.currencycalculator.model.helper;

import com.andela.currencycalculator.model.currency.Currency;

import java.util.ArrayList;

/**
 * Created by Spykins on 08/03/16.
 */
public class MockData {
    private ArrayList<Currency>arrayList;
    ;
    public MockData(){
        arrayList = new ArrayList<>();
        setUp();
    }
    /*
            "AED": 3.672976,
    "AFN": 68.610001,
    "ALL": 125.2531,
    "AMD": 490.690003,
    "ANG": 1.7887,
    "AOA": 158.753334,
    "ARS": 15.36896,
    "AUD": 1.345022,
    "AWG": 1.793333,

    "BGN": 1.775247,
    "BHD": 0.377007,
    "BIF": 1551.2725,
    "BMD": 1,
    "BND": 1.380596,
    "BOB": 6.82777,
    "BRL": 3.779357,
    "BSD": 1,
    "BTC": 0.002433930945,
    "BTN": 67.306233,
    "BWP": 11.118488,
    "BYR": 21171.35,

    "CNY": 6.50941,
    "COP": 3138.765023,
    "CRC": 534.190399,
    "CUC": 1,
    "CUP": 0.998438,
    "CVE": 100.233666,
    "CZK": 24.57101,
    "DJF": 177.740001,
    "DKK": 6.776354,
    "DOP": 45.70344,
    "DZD": 108.65634,
    "EEK": 14.21845,
    "EGP": 7.830646,
    "ERN": 15.0015,
    "ETB": 21.287,
    "EUR": 0.907547,
    "FJD": 2.097367,
    "FKP": 0.702394,
    "GBP": 0.702394,
    "GEL": 2.4622,
    "GGP": 0.702394,
    "GIP": 0.702394,
    "GMD": 39.6648,
    "GNF": 7613.577598,
    "GTQ": 7.672425,
    "GYD": 206.409836,
    "HKD": 7.766104,
    "HNL": 22.56661,
    "HRK": 6.885186,
    "HTG": 61.705613,
    "HUF": 282.068101,
    "IDR": 13143.533333,
    "ILS": 3.910741,
    "IMP": 0.702394,
    "INR": 67.35394,
    "IQD": 1088.549988,
    "IRR": 30210.5,
    "ISK": 128.316801,
    "JEP": 0.702394,
    "JMD": 121.191299,
    "JOD": 0.708738,
    "JPY": 113.113001,
    "KES": 101.53759,
    "KGS": 72.871649,
    "KHR": 3986.947549,
    "KMF": 443.750974,
    "KPW": 899.91,
    "KRW": 1205.694982,
    "KYD": 0.8242,
    "KZT": 344.021588,
    "LAK": 8168.6825,
    "LBP": 1510.353317,
    "LKR": 144.404801,
    "LRD": 84.66847,
    "LSL": 15.418163,
    "LTL": 3.092985,
    "LVL": 0.632622,
    "MAD": 9.827939,
    "MDL": 19.87969,
    "MGA": 3205.111667,
    "MKD": 55.90101,
    "MMK": 1231.182512,
    "MNT": 2041.166667,
    "MOP": 7.988682,
    "MRO": 342.205167,
    "MTL": 0.683738,
    "MUR": 35.770912,
    "MVR": 15.22,
    "MWK": 720.615776,
    "MXN": 17.82857,
    "MYR": 4.10726,
    "MZN": 49.169999,
    "NAD": 15.39093,
    "NIO": 28.1938,
    "NOK": 8.536419,
    "NPR": 107.540101,
    "NZD": 1.479612,
    "OMR": 0.384934,
    "PAB": 1,
    "PEN": 3.449804,
    "PGK": 3.055,
    "PHP": 46.90651,
    "PKR": 104.5623,
    "PLN": 3.934551,
    "PYG": 5677.603333,
    "QAR": 3.641089,
    "RON": 4.053101,
    "RSD": 111.96684,
    "RUB": 71.63326,
    "RWF": 763.111757,
    "SAR": 3.749918,
    "SBD": 7.961492,
    "SCR": 13.571117,
    "SDG": 6.08919,
    "SEK": 8.480975,
    "SGD": 1.382891,
    "SLL": 4077,
    "SOS": 611.070381,
    "SRD": 3.9925,
    "STD": 22277,
    "SVC": 8.72852,
    "SYP": 219.826998,
    "SZL": 15.39043,
    "THB": 35.42024,
    "TJS": 7.8696,
    "TMT": 3.501367,
    "TND": 2.035416,
    "TOP": 2.260344,
    "TRY": 2.919418,
    "TTD": 6.52561,
    "TWD": 32.7557,
    "TZS": 2183.041634,
    "UAH": 26.14729,
    "UGX": 3371.526667,
    "USD": 1,
    "UYU": 32.22199,
    "UZS": 2861.319947,
    "VEF": 6.31701,
    "VND": 22288.75,
    "VUV": 112.543749,
    "WST": 2.556908,
    "XAF": 595.933367,
    "XAG": 0.063909,
    "XAU": 0.000782,
    "XCD": 2.70102,
    "XDR": 0.7198,
    "XOF": 597.496407,
    "XPD": 0.00173,

     */

    private void setUp() {

        Currency currency = new Currency("USD",125.2531,"ALL");
        arrayList.add(currency);
        Currency currency2 = new Currency("USD", 1, "USD");
        arrayList.add(currency2);
        Currency currency3 = new Currency("USD",198.5125,"NGN");
        arrayList.add(currency3);
        Currency currency4 = new Currency("USD",1.991525,"BZD");
        arrayList.add(currency4);
        Currency currency5 = new Currency("USD",1.333562,"CAD");
        arrayList.add(currency5);
        Currency currency6 = new Currency("USD",928.5,"CDF");
        arrayList.add(currency6);
        Currency currency7 = new Currency("USD",0.992645,"CHF");
        arrayList.add(currency7);
        Currency currency8 = new Currency("USD",678.255196,"CLP");
        arrayList.add(currency8);

        Currency currency9 = new Currency("USD",108.309575,"XPF");
        arrayList.add(currency9);
        Currency currency10 = new Currency("USD",0.000999,"XPT");
        arrayList.add(currency10);
        Currency currency11 = new Currency("USD",15.40644,"ZAR");
        arrayList.add(currency11);
        Currency currency12 = new Currency("USD", 214.88,"YER");
        arrayList.add(currency12);

        Currency currency13 = new Currency("USD",5253.075255,"ZMK");
        arrayList.add(currency13);
        Currency currency14 = new Currency("USD",11.382988,"ZMW");
        arrayList.add(currency14);
        Currency currency15 = new Currency("USD",322.387247,"ZWL");
        arrayList.add(currency15);
        Currency currency16 = new Currency("USD",0.702394,"SHP");
        arrayList.add(currency16);

        Currency currency17 = new Currency("USD",0.30056,"KWD");
        arrayList.add(currency17);
        Currency currency18 = new Currency("USD",198.5125,"NGN");
        arrayList.add(currency18);

        Currency currency19 = new Currency("USD",1.385324,"LYD");
        arrayList.add(currency19);

        Currency currency20 = new Currency("USD",3.860641,"GHS");
        arrayList.add(currency20);
    }

    public ArrayList<Currency> getArrayList(){
        return arrayList;
    }


}
