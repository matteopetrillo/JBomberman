package it.petrillo.jbomberman.util;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;

public class JsonLoader {
    public static <T> T loadJson(String filePath, Class<T> type) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            Gson gson = new Gson();
            return gson.fromJson(reader, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
