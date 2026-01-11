package com.qa.opencart.playwrightfactory;

import com.microsoft.playwright.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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


    public Page intiBrowser(Properties prop){
        String browserName = prop.getProperty("browser");
        System.out.println("Browser asked to launch = " + browserName);
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
        tlBrowser.set(browserType.launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(prop.getProperty("isHeadless")))));
        tlBrowserContext.set(tlBrowser.get().newContext());
        tlPage.set(tlBrowserContext.get().newPage());
        System.out.println("Browser " + browserName + " is launched.");
//        page.navigate(prop.getProperty("url"));
        tlPage.get().navigate(prop.getProperty("url"));
        System.out.println("Application is launched.");


        return getPage();
    }

    public Properties intiProperties(){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("./src/test/resources/config/config.properties");
            Properties prop = new Properties();
            prop.load(fis);
            return prop;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String takeScreenshot(){
        String path = System.getProperty("user.dir")
                + "/test-output/extent-reports/screenshots/"
                + "screenshot_" + System.currentTimeMillis() + ".png";
        byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setFullPage(true).setPath(Paths.get(path)));
        String base64Path = Base64.getEncoder().encodeToString(buffer);
        return base64Path;
    }

}
