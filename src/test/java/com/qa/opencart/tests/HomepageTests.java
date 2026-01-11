package com.qa.opencart.tests;

import com.microsoft.playwright.Page;
import com.qa.opencart.base.BaseClass;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.pages.Homepage;
import com.qa.opencart.playwrightfactory.PlaywrightFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomepageTests extends BaseClass {




    @Test
    public void validatePageTitle(){
        String actualTitle = homepage.getHomepageTitle();
        Assert.assertEquals(actualTitle, AppConstants.HOME_PAGE_TITLE);
    }

    @DataProvider
    public Object[][] provideProductData(){
        return new Object[][]{
                {"Macbook"},
                {"imac"},
                {"samsung"}
        };
    }

    @Test (dataProvider = "provideProductData")
    public void validateSearchProduct(String productName){
        String actualHeader = homepage.doSearch(productName);
        Assert.assertEquals(actualHeader, "Search - "+productName);
    }


}
