/*
 * Copyright (c) 2014 Movilok. All Rights Reserved
 *
 * This software is the confidential and proprietary information of
 * Movilok ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Movilok.
 */

package sidelance.com.stormclouds.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Tools for loading and saving values into persistence.
 *
 * * * Usages * * *
 *
 *
 * * * Loading values * * *
 * Persistence.with(context).getString(key,defaultValue);
 * Persistence.with(context).getInt(key,defaultValue);
 * Persistence.with(context).get...
 *
 * * * Saving values * * *
 * Persistence.with(context).save(key,value)
 *
 * * * Removing values * * *
 * Persistence.with(context).remove(key)
 *
 *
 * @author <a href="mailto:info@movilok.com">Movilok Interactividad Movil S.L.</a>
 */
public class Persistence {

    static Persistence singleton = null;

    static SharedPreferences preferences;

    static SharedPreferences.Editor editor;

    Persistence() {} // Avoiding to instantiate objects

    Persistence(Context context) {
        preferences = context.getSharedPreferences(AppConstants.STORE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static Persistence with(Context context) {
        if (singleton == null) {
            singleton = new Builder(context, null, -1).build();
        }
        return singleton;
    }

    public void save(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public void save(String key, String value) {
        editor.putString(key, value).apply();
    }

    public void save(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public void save(String key, float value) {
        editor.putFloat(key, value).apply();
    }

    public void save(String key, long value) {
        editor.putLong(key, value).apply();
    }

    public void save(String key, Set<String> value) {
        editor.putStringSet(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defValue) {
        return preferences.getStringSet(key, defValue);
    }

    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    public void remove(String key) {
        editor.remove(key).apply();
    }

    private static class Builder {

        private final Context context;

        public Builder(Context context, String name, int mode) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        /**
         * Method that creates an instance of Prefs
         *
         * @return an instance of Prefs
         */
        public Persistence build() {

            return new Persistence(context);

        }
    }

}
