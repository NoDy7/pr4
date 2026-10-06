package utils;

import java.time.Duration;

/** Centralised constants: no "magic" URLs / timeouts in tests and pages. */
public final class Config {
    private Config() {}

    public static final String BASE_URL = "https://www.saucedemo.com";
    public static final Duration TIMEOUT = Duration.ofSeconds(10);

    public static final String STANDARD_USER = "standard_user";
    public static final String LOCKED_OUT_USER = "locked_out_user";
    public static final String PASSWORD = "secret_sauce";
}
