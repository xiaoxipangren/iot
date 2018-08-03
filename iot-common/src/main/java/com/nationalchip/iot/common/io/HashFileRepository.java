package com.nationalchip.iot.common.io;

import com.google.common.base.Splitter;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;
import com.nationalchip.iot.annotation.AutoLogger;
import com.nationalchip.iot.common.exception.FileStoreException;
import com.nationalchip.iot.common.exception.HashNotMatchedException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/1/18 10:08 AM
 * @Modified:
 */

@Component("hashFileRepository")
public class HashFileRepository extends AbstractFileRepository {

    private static final String TEMP_FILE_PREFIX = "tmp";
    private static final String TEMP_FILE_SUFFIX = "hashfile";



    @AutoLogger
    private Logger logger;

    @Override
    public String store(InputStream stream, String sha1, String... paths) {
        final MessageDigest mdSha1;

        try {
            mdSha1 = MessageDigest.getInstance("SHA1");
        } catch (final NoSuchAlgorithmException e) {
            throw new FileStoreException(e.getMessage(), e);
        }

        final File file = createTempFile();
        final String hash = store(stream, sha1, file, mdSha1);
        rename( file, hash,paths);

        return hash;
    }



    private String store(InputStream content,String sha1,File file,MessageDigest mdSha1){
        String  sha1Hash;
        try (final DigestOutputStream outputstream = openFileOutputStream(file, mdSha1)) {
            ByteStreams.copy(content, outputstream);
            outputstream.flush();

            sha1Hash = BaseEncoding.base16().lowerCase().encode(mdSha1.digest());
            checkHashes(sha1, sha1Hash);


        } catch (final IOException e) {
            throw new FileStoreException(e.getMessage(), e);
        } catch (final HashNotMatchedException e) {
            if (!file.delete()) {
                logger.error("无法删除临时文件 {}", file);
            }
            throw e;
        }
        return sha1Hash;
    }

    private void checkHashes(final String providedHash, final String hash) {

        if (providedHash != null && !providedHash.isEmpty() && !providedHash.equals(hash)) {
            throw new HashNotMatchedException("原始Hash " + providedHash
                    + " 与计算的Hash " + hash+"不匹配",
                    HashNotMatchedException.SHA1);
        }
    }


    @Override
    protected Path getDirectoryPath(String sha1, String... paths) {

        String path = hashPath(sha1);

        String[] array = new String[paths.length];
        for(int i=1;i<paths.length;i++){
            array[i-1]=paths[i];
        }
        array[array.length-1]=path;


        return Paths.get(paths[0], array);
    }



    private DigestOutputStream openFileOutputStream(final File file, final MessageDigest sha1) throws FileNotFoundException {
        return new DigestOutputStream(new BufferedOutputStream(new FileOutputStream(file)), sha1);

    }

    private File createTempFile() {
        try {
            return File.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX);
        } catch (final IOException e) {
            throw new FileStoreException("无法创建临时文件", e);
        }
    }

    private String hashPath(String hash){
        if(hash==null || hash.isEmpty() || hash.length()<4)
            return hash;
        int length = hash.length();
        final List<String> folders = Splitter.fixedLength(2).splitToList(hash.substring(length - 4, length));
        final String folder1 = folders.get(0);
        final String folder2 = folders.get(1);

        return Paths.get(folder1,folder2).toString();
    }

}
