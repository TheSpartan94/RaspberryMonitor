package net.ddns.andrewnetwork.raspberrymonitor.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andry on 16/11/2017.
 */

public class MemoryManager {

    Context context;
    String file;

    public void saveList(List<?> List, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String jsonList = new Gson().toJson(List);
        editor.putString(key, jsonList);
        editor.commit();
    }

    public List<?> retrieveList(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(key))
            return new Gson().fromJson(key, new TypeToken<List<?>>(){}.getType());
        return new ArrayList<>();
    }

    public MemoryManager(Context context, String file)
    {
        this.context = context;
        this.file = file;
    }
}
