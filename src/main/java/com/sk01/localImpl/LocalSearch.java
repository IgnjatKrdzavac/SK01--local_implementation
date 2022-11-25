package com.sk01.localImpl;

import com.sk01.exceptions.UnexistingPathException;
import com.sk01.storage.Search;
import com.sk01.utils.StorageInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.HiddenFileFilter;

import java.io.File;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class LocalSearch extends Search {
    @Override
    public File getFile(String path) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;

        return FileUtils.getFile(path);
    }

    @Override
    public List<File> getFiles(String podstring) throws Exception {
        return null;
    }

    @Override
    public List<File> getFilesWithExtension(String extension) throws Exception {
        return null;
    }

    @Override
    public List<File> getAllFiles(String path) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;  //konkatenacija putanje do skladista + relativna putanja u skladistu


        if (ckeckStoragePath(path)) {
            throw new UnexistingPathException("Given path doesn't exist");
        }

        List<File> files = (List<File>) FileUtils.listFiles(new File(path), HiddenFileFilter.VISIBLE, FalseFileFilter.FALSE);
        return files;
    }

    @Override
    public List<File> getAllFiles() throws Exception {
        return null;
    }

    @Override
    public boolean containsFiles(String path, List fileNames) throws Exception {
        return false;
    }

    @Override
    public String getDir(String fileName) throws Exception {
        return null;
    }

    @Override
    public List<File> sortByName(String dirPath) throws Exception {
        return null;
    }

    @Override
    public List<File> sortByDate(String dirPath) throws Exception {
        return null;
    }

    @Override
    public List<File> getFiles(String path, Date pocetak, Date kraj) throws Exception {
        return null;
    }


    @Override
    public List<File> filtrate(String string) throws Exception {
        return null;
    }

    private boolean ckeckStoragePath(String path) {
        File file = new File(path);
        return file.exists();
    }
}
