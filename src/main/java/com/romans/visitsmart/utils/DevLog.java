package com.romans.visitsmart.utils;
import android.util.Log;

/**
 * Logger helper class to improve android application logging
 * strategies. Adds application name, class name, line number to
 * standard log message output
 *
 * @author TestDevLab
 * @version 1.0
 */
public class DevLog
{
    private static boolean sDebug = true;
    // application name which may be used later as filter
    private static String sAppName;

    /**
     * Initializer, must call once to init Logger
     * @param appName String
     * @param debug boolean
     */
    public static void init(String appName, boolean debug)
    {
        sDebug = debug;
        sAppName = appName;
    }

    /**
     * Use to output debug level log messages
     * Will output only in debug mode
     * @param msg Object
     */
    public static void d(Object... msg)
    {
        if (sDebug)
        {
            StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
            StackTraceElement stackTraceElement = stackTraces[3];

            String out = getOutput(stackTraceElement, msg);
            Log.d(sAppName, out.substring(0, out.length() - 2));
        }
    }

    /**
     * Use to output info level log messages
     * Will output only in debug mode
     * @param msg Object
     */
    public static void i(Object... msg)
    {
        if (sDebug)
        {
            StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
            StackTraceElement stackTraceElement = stackTraces[3];

            String out = getOutput(stackTraceElement, msg);
            Log.i(sAppName, out.substring(0, out.length() - 2));
        }
    }

    /**
     * Use to output error level log messages
     * Will output only in debug mode
     * @param msg Object
     */
    public static void e(Object... msg)
    {
        if (sDebug)
        {
            StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
            StackTraceElement stackTraceElement = stackTraces[3];

            String out = getOutput(stackTraceElement, msg);
            Log.e(sAppName, out.substring(0, out.length() - 2));
        }
    }

    /**
     * Use to output error level log messages
     * Will output only in debug mode
     * @param msg Object
     */
    public static void w(Object... msg)
    {
        if (sDebug)
        {
            StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
            StackTraceElement stackTraceElement = stackTraces[3];

            String out = getOutput(stackTraceElement, msg);
            Log.w(sAppName, out.substring(0, out.length() - 2));
        }
    }

    /**
     * Gets the stack-trace, line number of class and class name.
     * Iterates trough passed objects to create log message
     * @param msg Object...
     * @return String of prepared log message
     */
    private static String getOutput(StackTraceElement stackTraceElement, Object... msg)
    {

        String lineNumber = String.format(" (%d):  ", stackTraceElement.getLineNumber());
        String className = getClassName(stackTraceElement.getClassName());

        String out = className + lineNumber;

        for (Object o : msg)
        {
            if (o == null)
            {
                out += "NULL, ";
            }
            else
            {
                out += o.toString() + ", ";
            }
        }

        return out;
    }

    /**
     * Gets class name of stacktrace element
     * @param className String class name
     * @return String prepared class name
     */
    private static String getClassName(String className)
    {
        String[] parts = className.split("\\.");
        return parts[parts.length - 1];
    }

}
