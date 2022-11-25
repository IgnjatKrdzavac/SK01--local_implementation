package com.sk01.localImpl;

import com.sk01.exceptions.ConfigException;
import com.sk01.storage.Create;
import com.sk01.utils.StorageInfo;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class LocalCreate extends Create {

    static int indexDir = 1;
    static int indexFile = 1;

    @Override
    public void createDir(String path) throws Exception {
            String name = "Directory" + indexDir;
            indexDir++;

            path =  StorageInfo.getInstance().getConfig().getPath() + path;

            makeDir(path,name);
    }

    @Override
    public void createDir(String path, String name) throws Exception {
            path =  StorageInfo.getInstance().getConfig().getPath() + path;

            makeDir(path,name);
    }

    @Override
    public void createDirs(String path, int velicinaListe) throws Exception {

        String name = "Directory";
        path =  StorageInfo.getInstance().getConfig().getPath() + path;

        while(velicinaListe > 0){
            name = name.concat(String.valueOf(indexDir));
            indexDir++;

            makeDir(path,name);

            velicinaListe--;
        }

    }

    @Override
    public void createFiles(String path) throws Exception {
        String name = "File" + indexFile;
        indexFile++;

        path =  StorageInfo.getInstance().getConfig().getPath() + path;

        makeFile(path,name);

    }

    @Override
    public void createFiles(String path, String name) throws Exception {
        String extension = name.split("\\.")[1];
        path =  StorageInfo.getInstance().getConfig().getPath() + path;
        if (StorageInfo.getInstance().getConfig().getUnsuportedFiles().contains(extension)) {
            throw new ConfigException("Extension not supported");
        }

        if (!checkNumOfFiles()) {
            throw new ConfigException("File number exceeded");
        }

        makeFile(path,name);
    }

    @Override
    public void createFiles(String path, int velicinaListe) throws Exception {

        String name = "File";
        path =  StorageInfo.getInstance().getConfig().getPath() + path;

        while(velicinaListe > 0){
            name = name.concat(String.valueOf(indexFile));
            indexFile++;

            makeFile(path,name);

            velicinaListe--;
        }

    }

    private void makeDir(String path, String name){

        File dir = FileUtils.getFile(path + name);
        if (!dir.exists()) {
            try {
                FileUtils.forceMkdir(dir);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void makeFile(String path, String name){

        try {
            FileUtils.touch(new File(path + name));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkNumOfFiles() {

        return countFiles(StorageInfo.getInstance().getConfig().getPath(), 0) + 1 <= Integer.parseInt(StorageInfo.getInstance().getConfig().getNumberOfFiles());
    }

    private int countFiles(String rootPath, int counter) {
        File dir = new File(rootPath);

        for (File file: dir.listFiles()) {
            if (file.isHidden()) {
                continue;
            }

            if (file.isDirectory()) {  //rekurzivno racunamo broj fajlova
                counter = countFiles(file.getPath(), counter);
            }
            else {
                counter++;
            }
        }

        return counter;
    }
}
