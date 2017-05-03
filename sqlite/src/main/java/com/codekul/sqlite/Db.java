package com.codekul.sqlite;

/**
 * Created by aniruddha on 3/5/17.
 */

public interface Db {

    int VERSION = 1;
    String NAME = "mobile.sqlite";

    class Mobile {

        public static String TAB_NAME = "mobile";

        public static String COL_IMEI = "imei";
        public static String COL_VERSION = "ver";
        public static String COL_OS = "os";

        public static String query() {
            return "create table " + TAB_NAME + " ( " + COL_IMEI + " text, " + COL_VERSION + " integer, " + COL_OS + " text)";
        }
    }
}
