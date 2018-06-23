package com.nationalchip.iot.common.io;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.nationalchip.iot.annotation.AutoLogger;
import com.nationalchip.iot.common.exception.FileStoreException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/1/18 9:35 AM
 * @Modified:
 */

@Component("fileRepository")
public class FileRepository extends AbstractFileRepository{


    private static final String TEMP_FILE_PREFIX = "tmp";
    private static final String TEMP_FILE_SUFFIX = "file";



    @AutoLogger
    private Logger logger;

    @Override
    public String store(InputStream stream, String filename, String... paths) {

        final File file = createTempFile();
        store(stream,file);
        rename(file, filename,paths);

        return filename;
    }



    private void store(InputStream content,File file){
        try (final BufferedOutputStream outputstream = openFileOutputStream(file)) {
            ByteStreams.copy(content, outputstream);
            outputstream.flush();
        } catch (final IOException e) {
            throw new FileStoreException(e.getMessage(), e);
        }
    }


    @Override
    protected Path getDirectoryPath(String filename, String... paths) {
        return Paths.get("", paths);
    }



    private BufferedOutputStream openFileOutputStream(final File file) throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(file));

    }

    private File createTempFile() {
        try {
            return File.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX);
        } catch (final IOException e) {
            throw new FileStoreException("无法创建临时文件", e);
        }
    }
}
