package com.phonebook.mobile.config;

public final class MobileTimeouts {
    // Values can be overridden via system properties, e.g. -Dmobile.timeout.default=20
    public static final long SHORT = Long.parseLong(System.getProperty("mobile.timeout.short", "5"));
    public static final long DEFAULT = Long.parseLong(System.getProperty("mobile.timeout.default", "15"));
    public static final long CLICK = Long.parseLong(System.getProperty("mobile.timeout.click", "20"));
    public static final long LONG = Long.parseLong(System.getProperty("mobile.timeout.long", "30"));
    public static final long CI_LONG = Long.parseLong(System.getProperty("mobile.timeout.ci", "60"));

    private MobileTimeouts() {
        // utility
    }
}
