package com.frezrik.router.utils;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Log for apt
 */
public class Log {
    private Messager msg;
    private final String PREFIX_OF_LOG = "[Router Compiler]: ";

    public Log(Messager messager) {
        msg = messager;
    }

    public void i(CharSequence info) {
        if (isNotEmpty(info)) {
            msg.printMessage(Diagnostic.Kind.NOTE, PREFIX_OF_LOG + info);
        }
    }

    public void e(CharSequence error) {
        if (isNotEmpty(error)) {
            msg.printMessage(Diagnostic.Kind.ERROR, PREFIX_OF_LOG + "An exception is encountered, [" + error + "]");
        }
    }

    public void e(Throwable error) {
        if (null != error) {
            msg.printMessage(Diagnostic.Kind.ERROR, PREFIX_OF_LOG + "An exception is encountered, [" + error.getMessage() + "]" + "\n" + formatStackTrace(error.getStackTrace()));
        }
    }

    public void w(CharSequence warning) {
        if (isNotEmpty(warning)) {
            msg.printMessage(Diagnostic.Kind.WARNING, PREFIX_OF_LOG + warning);
        }
    }

    private String formatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append("    at ").append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    private  static  boolean isNotEmpty(final CharSequence cs) {
        boolean isEmpty =  cs == null || cs.length() == 0;
        return !isEmpty;
    }
}
