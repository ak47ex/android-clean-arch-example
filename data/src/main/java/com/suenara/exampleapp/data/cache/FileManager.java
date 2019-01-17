package com.suenara.exampleapp.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FileManager {

    @Inject
    FileManager() {}

    void writeToFile(File file, String content) {
        if (!file.exists()) {
            try {
                final FileWriter writer = new FileWriter(file);
                writer.write(content);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    String readFileToString(File file) {
        final StringBuilder fileDataBuilder = new StringBuilder();
        if (file.exists()) {
            String line;
            try {
                final FileReader fileReader = new FileReader(file);
                final BufferedReader bufferedReader = new BufferedReader(fileReader);
                while ((line = bufferedReader.readLine()) != null) {
                    fileDataBuilder.append(line).append('\n');
                }
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileDataBuilder.toString();
    }

    boolean exists(File file) {
        return file.exists();
    }

    boolean clearDirectory(File dir) {
        boolean result = false;
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                result = file.delete();
            }
        }
        return result;
    }

    void writeToPreferences(Context context, String prefName, String key, long value) {
        final SharedPreferences prefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value).apply();
    }

    long getFromPreferences(Context context, String prefName, String key) {
        final SharedPreferences prefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return prefs.getLong(key, 0);
    }
}
