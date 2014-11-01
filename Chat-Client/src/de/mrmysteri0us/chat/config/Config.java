package de.mrmysteri0us.chat.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by robin on 16/10/2014
 */
public class Config {
    private Properties              config;
    private File                    configFile;
    private HashMap<String, String> configDefaults;

    public Config(File configFile) {
        this.config = new Properties();
        this.configFile = configFile;
        this.configDefaults = new HashMap<String, String>();
    }

    public Config(String configPath) {
        this(new File(configPath));
    }

    public void load() throws IOException {
        if(!configFile.exists()) {
            System.out.println(configFile.getName() + " not found. Creating file using defaults.");
            FileWriter fileWriter = new FileWriter(configFile);
            config.putAll(configDefaults);
            config.store(fileWriter, null);
            fileWriter.close();
        } else {
            FileReader fileReader = new FileReader(configFile);
            config.load(fileReader);
            fileReader.close();
        }
    }

    public void save() throws IOException {
        FileWriter fileWriter = new FileWriter(configFile);
        config.store(fileWriter, null);
        fileWriter.close();
    }

    public void addDefault(String key, String value) {
        configDefaults.put(key, value);
    }

    public byte getByte(String key) {
        return Byte.parseByte(getProperty(key));
    }

    public short getShort(String key) {
        return Short.parseShort(getProperty(key));
    }

    public int getInt(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public long getLong(String key) {
        return Long.parseLong(getProperty(key));
    }

    public float getFloat(String key) {
        return Float.parseFloat(getProperty(key));
    }

    public double getDouble(String key) {
        return Double.parseDouble(getProperty(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    public char getChar(String key) {
        return getProperty(key).charAt(0);
    }

    public String getProperty(String key) {
        return config.getProperty(key);
    }

    public void setProperty(String key, String value) {
        config.setProperty(key, value);
    }
}
