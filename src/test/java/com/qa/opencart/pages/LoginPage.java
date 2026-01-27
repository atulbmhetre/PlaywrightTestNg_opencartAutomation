package com.qa.opencart.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    Page page;
    private String field_email = "//input[@name='email']";
    private String field_pwd = "//input[@name='password']";
    private String btn_login = "//input[@value='Login']";

    public LoginPage(Page page){
        this.page = page;
    }

    public void doLogin(String userName, String password){
        page.fill(field_email,userName);
        page.fill(field_pwd,password);
        System.out.println("User Creds Entered = " + userName + " : " + password);
        page.click(btn_login);
    }

    public String getLoginPageTitle(){

        System.out.println("Login Page title = " + page.title());
        return page.title();
    }

}
