package com.sk01.localImpl;

import com.sk01.exceptions.ConfigException;
import com.sk01.exceptions.UnexistingPathException;
import com.sk01.storage.Operations;
import com.sk01.utils.StorageInfo;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class LocalOperations extends Operations {


    @Override
    public void deleteFile(String path) throws Exception {
        File file = new File(StorageInfo.getInstance().getConfig().getPath() + path);

        try {
            FileUtils.delete(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDir(String path) throws Exception {
        File dir = new File(StorageInfo.getInstance().getConfig().getPath() + path);

        if (FileUtils.isDirectory(dir)) {
            try {
                FileUtils.deleteDirectory(dir);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void deleteAll(String path) throws Exception {

    }

    @Override
    public void moveFiles(String fromPath, String toPath) throws Exception {
        fromPath = StorageInfo.getInstance().getConfig().getPath() + fromPath;  //konkatenacija putanje do skladista + relativna putanja u skladistu
        toPath = StorageInfo.getInstance().getConfig().getPath() + toPath;  //konkatenacija putanje do skladista + relativna putanja u skladistu

        File file = new File(fromPath);
        

        if (!ckeckStoragePath(fromPath)) {
            throw new UnexistingPathException(fromPath + " isn't valid path");
        }

        if (!ckeckStoragePath(toPath)) {
            throw new UnexistingPathException(toPath + " isn't valid path");
        }

        try {
            if (file.isDirectory()) {
                FileUtils.moveDirectoryToDirectory(file, new File(toPath), false);
            }
            else {
                FileUtils.moveFileToDirectory(file, new File(toPath), false);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downloadFile(String pathFrom, String pathTo) throws Exception {
        pathFrom = StorageInfo.getInstance().getConfig().getPath() + pathFrom;



        if (!ckeckStoragePath(pathFrom)) {
            throw new UnexistingPathException("Given path doesn't exist");
        }

        File file = new File(pathTo);

        try {
            if (file.isDirectory()) {
                FileUtils.copyDirectoryToDirectory(file, new File("./"));
            }
            else {
                FileUtils.copyFileToDirectory(file, new File("./"), true);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rename(String path, String name) throws Exception {
        if(FileUtils.getFile(path) == null){
            throw new UnexistingPathException("Path doesn't exist in storage");
        }

        File file = FileUtils.getFile(path);
        File file2 = new File(name);

        boolean success = file.renameTo(file2);

        if(!success){
            throw new ConfigException("File not renamed successfully");
        }
    }

    private boolean ckeckStoragePath(String path) {
        File file = new File(path);
        return file.exists();
    }
}
