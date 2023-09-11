package org.company.project.common.wrraper;

import org.apache.log4j.Logger;
import org.company.project.common.exception.NotMatchRecordVersionException;
import org.company.project.common.exception.UserNotFoundException;

import java.io.IOException;
import java.sql.SQLException;

public class ErrorHandler {
    private ErrorHandler(){}
    private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class);
    public static String getError (Exception e){
        e.printStackTrace();
        String msg;
        if (e instanceof ArithmeticException){
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getClass());
            msg = "101 : " + "Arithmetic Exception";
        } else if (e instanceof SQLException) {
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getClass());
            msg = "102 : " + e.getMessage();
        } else if (e instanceof IOException) {
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getClass());
            msg = "103 : " + e.getMessage();
        } else if (e instanceof NotMatchRecordVersionException) {
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getClass());
            msg = "104 : " + "NotMatchRecordVersionException";
        } else if (e instanceof UserNotFoundException) {
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getClass());
            msg = "105 : " + "UserNotFoundException";
        }else {
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getClass());
            msg = "1000 : " + e.getMessage();
        }
        return msg;
    }
}
