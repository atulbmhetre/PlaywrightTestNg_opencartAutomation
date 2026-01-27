package com.qa.opencart.playwrightfactory;

import com.microsoft.playwright.*;
import com.qa.opencart.config.ConfigManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

public class PlaywrightFactory {

//    Playwright playwright;
    BrowserType browserType;
//    Browser browser;
//    BrowserContext browserContext;
//    Page page;

    private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlPage = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return tlPlaywright.get();
    }

    public static Browser getBrowser() {
        return tlBrowser.get();
    }

    public static BrowserContext getBrowserContext() {
        return tlBrowserContext.get();
    }

    public static Page getPage() {
        return tlPage.get();
    }


    public Page intiBrowser(String browserName){
        //playwright = Playwright.create();
        tlPlaywright.set(Playwright.create());
        switch (browserName.toLowerCase()){
            case "chromium":
                //browserType = playwright.chromium();
                browserType = tlPlaywright.get().chromium();
                break;
            case "firefox":
                //browserType = playwright.firefox();
                browserType = tlPlaywright.get().firefox();
                break;
            case "safari":
                //browserType = playwright.webkit();
                browserType = tlPlaywright.get().webkit();
                break;
        }
//        browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(prop.getProperty("isHeadless"))));
//        browserContext = browser.newContext();
//        page = browserContext.newPage();
        tlBrowser.set(browserType.launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(ConfigManager.get("isHeadless")))));
        tlBrowserContext.set(tlBrowser.get().newContext());
        tlPage.set(tlBrowserContext.get().newPage());
//        page.navigate(prop.getProperty("url"));
        tlPage.get().navigate(ConfigManager.get("url"));

        return getPage();
    }

//    public Properties intiProperties() throws IOException {
//        String env = System.getProperty("env", "dev");
//
//        String fileName = "config/" + env + ".config.properties";
//
//        InputStream is = getClass()
//                .getClassLoader()
//                .getResourceAsStream(fileName);
//
//        if (is == null) {
//            throw new IllegalStateException("Environment config file not found: " + fileName);
//        }
//
//        Properties prop = new Properties();
//        prop.load(is);
//        return prop;
//
//
//    }


}
