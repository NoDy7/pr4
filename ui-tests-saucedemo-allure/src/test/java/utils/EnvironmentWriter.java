package utils;

import org.openqa.selenium.remote.BuildInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/** Writes the Allure "Environment" widget file and copies categories.json into allure-results. */
public final class EnvironmentWriter {
    private EnvironmentWriter() {}

    private static final Set<String> BROWSERS = ConcurrentHashMap.newKeySet();

    public static void registerBrowser(String nameAndVersion) {
        BROWSERS.add(nameAndVersion);
    }

    public static void write() {
        Path dir = Paths.get(System.getProperty("allure.results.directory", "target/allure-results"));
        try {
            Files.createDirectories(dir);

            Properties env = new Properties();
            env.setProperty("os.name", System.getProperty("os.name") + " " + System.getProperty("os.version"));
            env.setProperty("jdk.version", System.getProperty("java.version"));
            env.setProperty("selenium", seleniumVersion());
            env.setProperty("testng", testngVersion());
            env.setProperty("browsers", String.join("; ", new TreeSet<>(BROWSERS)));
            env.setProperty("driver.manager", "SeleniumManager");
            env.setProperty("base.url", Config.BASE_URL);
            try (OutputStream out = Files.newOutputStream(dir.resolve("environment.properties"))) {
                env.store(out, "Allure environment");
            }

            try (InputStream in = EnvironmentWriter.class.getResourceAsStream("/categories.json")) {
                if (in != null) {
                    Files.copy(in, dir.resolve("categories.json"), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            System.err.println("Could not write Allure environment: " + e.getMessage());
        }
    }

    private static String seleniumVersion() {
        try {
            return new BuildInfo().getReleaseLabel();
        } catch (Exception e) {
            return "4.x";
        }
    }

    private static String testngVersion() {
        try {
            return org.testng.internal.Version.getVersionString();
        } catch (Throwable t) {
            return "7.x";
        }
    }
}
