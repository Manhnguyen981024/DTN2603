package com.dtn2603.constant;
import java.io.File;

public final class AppConstants {
    public static final String LOG_DIRECTORY = System.getProperty("user.dir") + File.separator + "logs";
    public static final String IMPORT_ACCOUNT_ERROR_LOG = LOG_DIRECTORY + File.separator + "insert_account_error_log.csv";
    public static final String IMPORT_DEPARTMENT_ERROR_LOG = LOG_DIRECTORY + File.separator + "insert_department_error_log.csv";
}
