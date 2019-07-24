package com.huawei.rtc.reporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LoggingReporter logs.
 */
public class LoggingReporter implements Reporter {
    private final Logger logger;

    public LoggingReporter() {
        this(null);
    }

    public LoggingReporter(Logger logger) {
        if (logger == null) {
            logger = LoggerFactory.getLogger(this.getClass());
        }
        this.logger = logger;
    }

    public void report(String message) {
        logger.info("Message reported: {}", message);
    }

    public void close() {
        // nothing to do
    }
}
