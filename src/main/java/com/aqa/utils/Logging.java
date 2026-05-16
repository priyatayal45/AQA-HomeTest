package com.aqa.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Logging {

	private Logging() {}

    public static Logger get(Class<?> classes) {
        return LoggerFactory.getLogger(classes);
    }
}