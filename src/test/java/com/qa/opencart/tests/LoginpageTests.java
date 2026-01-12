package com.qa.opencart.tests;

import com.qa.opencart.base.BaseClass;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.pages.LoginPage;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginpageTests extends BaseClass {

    LoginPage loginPage;

    @BeforeClass
    public void navigateToLoginPage(){
        loginPage = homepage.navigateToLoginPage();
    }

    @Test
    public void verifyLogin(){
        loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
        String actualTitle = loginPage.getLoginPageTitle();
        Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE);
    }
}
