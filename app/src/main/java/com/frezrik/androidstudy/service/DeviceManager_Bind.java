package com.frezrik.androidstudy.service;

import com.frezrik.common.utils.ConvertUtilImpl;
import com.frezrik.core.api.ConvertUtil;
import com.frezrik.router.Router;

import java.lang.reflect.Field;

public class DeviceManager_Bind {
    String implName, ifName;
    String className = "", fieldType = "", feildName = "";

    public DeviceManager_Bind(Object obj) {
        DeviceManagerService dms = (DeviceManagerService) obj;
        try {
            Field field = dms.getClass().getField(feildName);
            field.setAccessible(true);
            field.set()
            //赋值
            Router.registerService(ConvertUtil.class, new ConvertUtilImpl());
            ConvertUtil convertUtil = Router.service(ConvertUtil.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
