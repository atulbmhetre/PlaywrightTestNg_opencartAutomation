package com.qa.opencart.pages;

import com.microsoft.playwright.Page;

public class Homepage {

    private Page page;
    private String field_search = "//input[@name='search']";
    private String btn_search = "//div[@id='search']//button";
    private String txt_prodHeader = "//div[@id='content']/h1";
    private String link_myAccount = "//span[text()='My Account']";
    private String link_login = "//a[text()='Login']";

    public Homepage(Page page){
        this.page = page;
    }

    public String getHomepageTitle(){
        System.out.println("Home Page title = " + page.title());
        return page.title();
    }

    public String doSearch(String productName){
        page.fill(field_search,productName);
        page.click(btn_search);
        System.out.println("Search product Header = "+ page.textContent(txt_prodHeader));
        return page.textContent(txt_prodHeader);
    }

    public LoginPage navigateToLoginPage(){
        page.click(link_myAccount);
        page.click(link_login);
        System.out.println("Login page opened.");
        return new LoginPage(page);
    }

}
