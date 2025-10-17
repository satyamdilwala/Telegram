package org.webrtc;

/**
 * Stub implementation of Logging for compilation purposes.
 * This is a placeholder - VoIP functionality will not work properly.
 */
public class Logging {
    
    public enum Severity {
        LS_VERBOSE,
        LS_INFO,
        LS_WARNING,
        LS_ERROR,
        LS_NONE
    }
    
    public static void enableLogToDebugOutput(Severity severity) {
        // Stub implementation
    }
    
    public static void log(Severity severity, String tag, String msg) {
        // Stub implementation
    }
    
    public static void d(String tag, String msg) {
        // Stub implementation
    }
}
