package com.parabank.utils;

import com.microsoft.playwright.Page;

public class LocatorUtil {
    public static Page.GetByRoleOptions name(String name){
        return new Page.GetByRoleOptions().setName(name);
    }
}
