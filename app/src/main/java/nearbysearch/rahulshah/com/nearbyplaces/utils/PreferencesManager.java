package nearbysearch.rahulshah.com.nearbyplaces.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import nearbysearch.rahulshah.com.nearbyplaces.AppController;

public final class PreferencesManager {
    private SharedPreferences preferences;
    private static PreferencesManager preferencesManager = null;

    public static PreferencesManager getInstance(Context c) {
        if (preferencesManager == null) {
            preferencesManager = new PreferencesManager(c);
        }
        return preferencesManager;
    }

    public static PreferencesManager getInstance() {
        if (preferencesManager == null) {
            preferencesManager = new PreferencesManager(AppController.getInstance());
        }
        return preferencesManager;
    }

    private PreferencesManager(Context c) {
        preferences = PreferenceManager.getDefaultSharedPreferences(c);
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void clear() {
        preferences.edit().clear().apply();
    }


    public void clear(String key){
        preferences.edit().remove(key).apply();
    }


    public int getInt(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }



    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public long getLong(String key, long defaultValue) {
        return getPreferences().getLong(key, defaultValue);
    }

    public void setLong(String key, long value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public float getFloat(String key, float defaultValue) {
        return getPreferences().getFloat(key, defaultValue);
    }

    public void setFloat(String key, float value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
