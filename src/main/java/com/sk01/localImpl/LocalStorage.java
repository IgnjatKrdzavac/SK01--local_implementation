package com.sk01.localImpl;

import com.google.gson.Gson;
import com.sk01.storage.Storage;
import com.sk01.utils.Config;
import com.sk01.utils.StorageInfo;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalStorage extends Storage {
    @Override
    public File getConfig(String path) throws Exception {
        return FileUtils.getFile(path + "/config.json");
    }

    @Override
    public void editConfig(String path, String maxSize, String maxNumOfFiles, List<String> unsupportedFiles) throws Exception {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put("path", path);
        configMap.put("maxSize", (maxSize != null) ? maxSize : StorageInfo.getInstance().getConfig().getMaxSize());
        configMap.put("numberOfFiles", (maxNumOfFiles != null) ? maxNumOfFiles : StorageInfo.getInstance().getConfig().getNumberOfFiles());
        configMap.put("unsupportedFiles", (unsupportedFiles != null) ? unsupportedFiles : StorageInfo.getInstance().getConfig().getUnsuportedFiles());

        try {
            Writer writer = new FileWriter(path + "/config.json");
            new Gson().toJson(configMap, writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createStorage(String path, String storageName) throws Exception {

        File storageDir = FileUtils.getFile(path + "/" + storageName);  //uzmemo skldiste
        if (!storageDir.exists()) {
            try {
                FileUtils.forceMkdir(storageDir);  //napravimo ga

                File configFile = new File(path + "/" + storageName + "/config.json");  //kreiramo config.json


                initConfig(configFile, path + "/" + storageName);

                FileUtils.touch(configFile);  //napravimo config.json u skladistu

                readConfig(getConfig(path + "/" + storageName));

            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void initConfig(File configFile, String path) {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put("path", path);
        configMap.put("maxSize", "pvr");
        configMap.put("numberOfFiles", "pvr");
        configMap.put("unsupportedFiles", null);

        try {
            Writer writer = new FileWriter(configFile);
            new Gson().toJson(configMap, writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void readConfig(File configFile) throws Exception{
        Gson gson = new Gson();

        try {
            Reader reader = new FileReader(configFile);
            Config config = gson.fromJson(reader, Config.class);

            StorageInfo.getInstance().setConfig(config);
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
