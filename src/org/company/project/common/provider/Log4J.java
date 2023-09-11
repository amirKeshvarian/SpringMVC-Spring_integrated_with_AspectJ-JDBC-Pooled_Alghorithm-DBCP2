package org.company.project.common.provider;

import org.apache.log4j.Logger;

public class Log4J {
    private Log4J () {}
    private static Logger logger;
    public  static <T> Logger getLogger (Class<T> tClass){
        return Logger.getLogger(tClass);
    }
}
