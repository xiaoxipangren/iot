package com.nationalchip.iot.common.io;

import com.google.common.io.Files;
import com.nationalchip.iot.common.exception.FileStoreException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/1/18 2:57 PM
 * @Modified:
 */
public abstract class AbstractFileRepository implements IFileRepository{


    private File getFile(String filename,String ...paths){
        final File file = getDirectoryPath(filename,paths).toFile();
        if(!file.exists())
            file.mkdirs();
        return new File(file, filename);
    }

    protected void rename(final File file, final String filename,String ... paths){
        final File fileSHA1Naming = getFile(filename,paths);
        rename(file,fileSHA1Naming);
    }


    protected abstract Path getDirectoryPath(String filename, String ... paths);



    @Override
    public File get(String filename, String... paths) {
        return getFile(filename,paths);
    }

    @Override
    public void delete(String filename, String... paths) {
        FileUtils.deleteQuietly(getFile(filename,paths));
    }

    @Override
    public boolean exists(String filename, String... paths) {
        return getFile(filename,paths).exists();
    }


    private void rename(final File file,final File newFile){
        if (newFile.exists()) {//如果文件存在直接返回
            FileUtils.deleteQuietly(file);
            return;
        } else {
            try {
                Files.move(file, newFile);
            } catch (final IOException e) {
                throw new FileStoreException("无法存储文件 " + newFile, e);
            }
        }
    }

}
