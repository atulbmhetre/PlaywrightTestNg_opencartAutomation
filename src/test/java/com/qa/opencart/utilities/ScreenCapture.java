package com.qa.opencart.utilities;

import com.microsoft.playwright.Page;
import com.qa.opencart.playwrightfactory.PlaywrightFactory;

import java.nio.file.Paths;
import java.util.Base64;

public class ScreenCapture {

    public static String capture(Page page) {

        if (page == null) {
            return null;
        }

        try {
            byte[] buffer = page.screenshot(
                    new Page.ScreenshotOptions().setFullPage(true)
            );
            return Base64.getEncoder().encodeToString(buffer);

        } catch (Exception e) {
            return null;   // Never break execution
        }
    }
}
