package com.frezrik.androidstudy.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by Ming on 2018/4/16.
 */

public class SPHelper {
    private static final String FILE_NAME = "shared_data";
    private static SPHelper mSPHelper;
    private static SharedPreferences mSP;
    private static SharedPreferences.Editor mEditor;

    private SPHelper() {};

    public static SPHelper getInstance(Context context) {
        if(mSPHelper == null) {
            mSPHelper = new SPHelper();
        }
        mSP = context.getApplicationContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        mEditor = mSP.edit();
        return mSPHelper;
    }

    /**
     * 保存数据的方法，拿到数据保存数据的基本类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            mEditor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            mEditor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            mEditor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            mEditor.putLong(key, (Long) object);
        } else {
            mEditor.putString(key, object.toString());
        }
        mEditor.apply();
    }

    /**
     * 获取保存数据的方法，我们根据默认值的到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key           键的值
     * @param defaultObject 默认值
     * @return
     */

    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return mSP.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return mSP.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return mSP.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return mSP.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return mSP.getLong(key, (Long) defaultObject);
        } else {
            return mSP.getString(key, null);
        }

    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        mEditor.remove(key);
        mEditor.apply();
    }

    /**
     * 清除所有的数据
     */
    public void clear() {
        mEditor.clear();
        mEditor.apply();
    }

    /**
     * 查询某个key是否存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return mSP.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        return mSP.getAll();
    }
}
