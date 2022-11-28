package com.sk01.localImpl;

import com.sk01.exceptions.UnexistingPathException;
import com.sk01.storage.Search;
import com.sk01.utils.StorageInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.HiddenFileFilter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocalSearch extends Search {
    @Override
    public File getFile(String path) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;

        if (!ckeckStoragePath(path)) {
            throw new UnexistingPathException("Given path doesn't exist");
        }

        return FileUtils.getFile(path);
    }

    @Override
    public List<File> getFiles(String podstring) throws Exception {
        String path = StorageInfo.getInstance().getConfig().getPath();

        if (!ckeckStoragePath(path)) {
            throw new UnexistingPathException("Given path doesn't exist");
        }

        List<File> files = (List<File>) FileUtils.listFiles(new File(path), HiddenFileFilter.VISIBLE, FalseFileFilter.FALSE);
        List<File> povratna = new ArrayList<>();

        for(File file:files){
            if(file.getName().contains(podstring)){
                povratna.add(file);
            }
        }

        return povratna;
    }

    @Override
    public List<File> getFilesWithExtension(String extension) throws Exception {
        String path = StorageInfo.getInstance().getConfig().getPath();

        if (!ckeckStoragePath(path)) {
            throw new UnexistingPathException("Given path doesn't exist");
        }

        List<File> files = (List<File>) FileUtils.listFiles(new File(path), HiddenFileFilter.VISIBLE, FalseFileFilter.FALSE);
        List<File> povratna = new ArrayList<>();

        for(File file:files){
            if(file.getName().endsWith(extension)){
                povratna.add(file);
            }
        }

        return povratna;
    }

    @Override
    public List<File> getAllFiles(String path) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;  //konkatenacija putanje do skladista + relativna putanja u skladistu


        if (!ckeckStoragePath(path)) {
            throw new UnexistingPathException("Given path doesn't exist");
        }

        List<File> files = (List<File>) FileUtils.listFiles(new File(path), HiddenFileFilter.VISIBLE, FalseFileFilter.FALSE);
        return files;
    }

    @Override
    public List<File> getAllFiles() throws Exception {
        String path = StorageInfo.getInstance().getConfig().getPath();  //konkatenacija putanje do skladista + relativna putanja u skladistu


        if (!ckeckStoragePath(path)) {
            throw new UnexistingPathException("Given path doesn't exist");
        }

        List<File> files = (List<File>) FileUtils.listFiles(new File(path), HiddenFileFilter.VISIBLE, FalseFileFilter.FALSE);
        return files;
    }

    @Override
    public boolean containsFiles(String path, List fileNames) throws Exception {


        path = StorageInfo.getInstance().getConfig().getPath() + path;  //konkatenacija putanje do skladista + relativna putanja u skladistu


        if (!ckeckStoragePath(path)) {
            throw new UnexistingPathException("Given path doesn't exist");
        }

        List<File> files = (List<File>) FileUtils.listFiles(new File(path), HiddenFileFilter.VISIBLE, FalseFileFilter.FALSE);

        return getNames(files).containsAll(fileNames);
    }

    @Override
    public String getDir(String path) throws Exception {
        List<File> files = getAllFiles(path);

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
        path = StorageInfo.getInstance().getConfig().getPath() + path;

        if (!ckeckStoragePath(path)) {
            throw new UnexistingPathException("Given path doesn't exist");
        }

        List<File> files = (List<File>) FileUtils.listFiles(new File(path), HiddenFileFilter.VISIBLE, FalseFileFilter.FALSE);


        for(File file:files){
            BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);


                Date d1 = new Date(String.valueOf(attr.creationTime()));
                Date d2 = new Date(String.valueOf(attr.lastModifiedTime()));
                if((d1.compareTo(pocetak) >= 0 && d1.compareTo(kraj) <= 0) ||
                        (d2.compareTo(pocetak) >= 0 && d2.compareTo(kraj) <= 0)) {
                    files.add(file);
                }

        }
        return files;
    }


    @Override
    public List<File> filtrate(String string) throws Exception {
        return null;
    }

    private boolean ckeckStoragePath(String path) {
        File file = new File(path);
        return file.exists();
    }

    private List<String> getNames(List<File> files) {
        List<String> names = new ArrayList<>();

        for (File file: files) {
            String name = file.getName();
            names.add(name);
        }

        return names;
    }
}
