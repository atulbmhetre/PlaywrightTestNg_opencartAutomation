package com.qa.opencart.base;

import com.microsoft.playwright.Page;
import com.qa.opencart.pages.Homepage;
import com.qa.opencart.playwrightfactory.PlaywrightFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.util.Properties;

public class BaseClass {

    PlaywrightFactory pf;
    Page page;
    protected Homepage homepage;
    protected Properties prop;

    @BeforeClass
    public void setUp(){
        pf = new PlaywrightFactory();
        prop = pf.intiProperties();
        page = pf.intiBrowser(prop);
        homepage = new Homepage(page);
    }

    @AfterClass
    public void tearDown(){
        //page.context().browser().close();
        PlaywrightFactory.getBrowser().close();
        PlaywrightFactory.getPlaywright().close();
    }
}
