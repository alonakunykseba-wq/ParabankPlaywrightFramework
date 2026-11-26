package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.SelectOption;
import com.parabank.exceptions.PageTimeoutException;

public abstract class BasePage {
    protected final Page page;

    protected BasePage(Page page){
        this.page = page;
    }

    protected String safeGetText(Locator locator){
        try{
            locator.waitFor();
            return locator.textContent();
        }catch (TimeoutError e){
            throw new PageTimeoutException("Timed out waiting to get text on the page" + getClass().getSimpleName(), e);
        }
    }

    protected void safeClick(Locator locator){
        try{
            locator.click();
        }catch (TimeoutError e){
            throw new PageTimeoutException("Timed out waiting to click on the page " + getClass().getSimpleName(), e);
        }
    }

    protected void safeSelectOption(Locator locator, SelectOption selectOption){
        try{
            locator.selectOption(selectOption);
        } catch  (TimeoutError e){
            throw new PageTimeoutException("Timed out waiting to select option " + selectOption + " on the page " + getClass().getSimpleName(), e);
        }
    }

    protected void safeSelectOption(Locator locator, String selectOption){
        try{
            locator.selectOption(selectOption);
        } catch  (TimeoutError e){
            throw new PageTimeoutException("Timed out waiting to select option " + selectOption + " on the page " + getClass().getSimpleName(), e);
        }
    }

    protected void safeFill(Locator locator, String text){
        try{
            locator.fill(text);
        } catch (TimeoutError e){
            throw new PageTimeoutException("Timed out waiting to fill the text on the page" + getClass().getSimpleName(), e);
        }
    }
}
