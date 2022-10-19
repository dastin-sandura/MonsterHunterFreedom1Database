package sandura.mhdatabase.logging;

import java.io.IOError;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Logger {

    public Logger(LoggingLevel loggingLevel) {
        this.loggingLevel = loggingLevel;
    }

    private LoggingLevel loggingLevel;

    public void logDebug(Map<String, String> items) {
        if (LoggingLevel.DEBUG == loggingLevel) {
            System.out.println(items);
        }
    }

    public enum LoggingLevel {
        DEBUG, INFO, ERROR;
        int level;
    }

    public void logDebug(String debugMessage) {
        if (LoggingLevel.DEBUG == loggingLevel) {
            System.out.println(debugMessage);
        }
    }

    public void logDebug(boolean b) {
        logDebug(String.valueOf(b));
    }

    public void logInfo(String infoMessage) {
        if (LoggingLevel.INFO == loggingLevel) {
            System.out.println(infoMessage);
        }
    }

    public void logInfo(List<String> infoMessages) {
        if (LoggingLevel.INFO == loggingLevel) {
            System.out.println(infoMessages);
        }
    }

    public void logError(String errorMessage) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement stackTraceElement = stackTrace[0];
        StackTraceElement stackTraceElement1 = stackTrace[1];
        StackTraceElement stackTraceElement2 = stackTrace[2];
        if (LoggingLevel.ERROR == loggingLevel || LoggingLevel.INFO == loggingLevel || LoggingLevel.DEBUG == loggingLevel) {
            System.err.println(errorMessage);
        }
    }

    public void logError(IOException ioe) {
        logError(ioe.toString());
    }
}
