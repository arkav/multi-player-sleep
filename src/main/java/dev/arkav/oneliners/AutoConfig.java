package dev.arkav.oneliners;

/*
  This file is licensed under CC0, use as you wish <3
 */

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;

public class AutoConfig<T> {
    private T object;
    private Class<T> objectClass;
    private String path;
    public AutoConfig(Class<T> c, String path) {
        this.path = path;
        try {
             objectClass = c;
             object = objectClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Gson gson = new Gson();
            object = gson.fromJson(new FileReader(this.path), objectClass);
        } catch (IOException e) {
            System.err.printf("Error while reading config for %s.class from file %s using defaults\n", objectClass.getSimpleName(), this.path);
        }
    }
    public T get() {
        return object;
    }

    public void save() {
        Gson gson =  new Gson();
        try {
            gson.toJson(object, new FileWriter(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public AutoConfig<T> addSaveShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::save));
        return this;
    }
}
