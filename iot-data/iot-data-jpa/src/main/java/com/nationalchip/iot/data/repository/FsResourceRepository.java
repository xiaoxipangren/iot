package com.nationalchip.iot.data.repository;

import com.google.common.base.Splitter;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.nationalchip.iot.annotation.AutoLogger;
import com.nationalchip.iot.data.configuration.DataProperties;
import com.nationalchip.iot.data.exception.HashNotMatchedException;
import com.nationalchip.iot.data.exception.ResourceStoreException;
import com.nationalchip.iot.data.model.IResource;
import com.nationalchip.iot.data.model.Resource;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Date: 5/24/18 1:38 PM
 * @Modified:
 */

@Component
public class FsResourceRepository implements IFsRepository {

    @AutoLogger
    private Logger logger;

    @Autowired
    private DataProperties dataProperties;

    private static final String TEMP_FILE_PREFIX = "tmp";
    private static final String TEMP_FILE_SUFFIX = "resource";

    @Override
    public String create(InputStream content, String sha1) {

        final MessageDigest mdSha1;

        try {
            mdSha1 = MessageDigest.getInstance("SHA1");
        } catch (final NoSuchAlgorithmException e) {
            throw new ResourceStoreException(e.getMessage(), e);
        }

        final File file = createTempFile();
        final String hash = store(content, sha1, file, mdSha1);
        renameFileToSHA1Naming(file, sha1);

        return hash;
    }


    private String store(InputStream content,String sha1,File file,MessageDigest mdSha1){
        String  sha1Hash;
        try (final DigestOutputStream outputstream = openFileOutputStream(file, mdSha1)) {
            final long size = ByteStreams.copy(content, outputstream);
            outputstream.flush();

            sha1Hash = BaseEncoding.base16().lowerCase().encode(mdSha1.digest());
            checkHashes(sha1, sha1Hash);


        } catch (final IOException e) {
            throw new ResourceStoreException(e.getMessage(), e);
        } catch (final HashNotMatchedException e) {
            if (!file.delete()) {
                logger.error("无法删除临时文件 {}", file);
            }
            throw e;
        }
        return sha1Hash;
    }

    private void checkHashes(final String providedHash, final String hash) {

        if (providedHash != null && !providedHash.equals(hash)) {
            throw new HashNotMatchedException("原始Hash " + providedHash
                    + " 与计算的Hash " + hash+"不匹配",
                    HashNotMatchedException.SHA1);
        }
    }


    private File getFile(final String sha1) {
        final File aritfactDirectory = getSha1DirectoryPath(sha1).toFile();
        aritfactDirectory.mkdirs();
        return new File(aritfactDirectory, sha1);
    }

    private Path getSha1DirectoryPath(final String sha1) {
        final int length = sha1.length();
        final List<String> folders = Splitter.fixedLength(2).splitToList(sha1.substring(length - 4, length));
        final String folder1 = folders.get(0);
        final String folder2 = folders.get(1);
        return Paths.get(dataProperties.getFs().getRepo(), folder1, folder2);
    }

    private void renameFileToSHA1Naming(final File file, final String sha1) {
        final File fileSHA1Naming = getFile(sha1);
        if (fileSHA1Naming.exists()) {
            FileUtils.deleteQuietly(fileSHA1Naming);
        } else {
            try {
                Files.move(file, fileSHA1Naming);
            } catch (final IOException e) {
                throw new ResourceStoreException("无法存储文件 " + fileSHA1Naming, e);
            }
        }

        if (!file.delete()) {
            logger.debug("无法删除临时文件 {}", file);
        }
    }


    private DigestOutputStream openFileOutputStream(final File file, final MessageDigest sha1) throws FileNotFoundException {
        return new DigestOutputStream(new BufferedOutputStream(new FileOutputStream(file)), sha1);

    }

    private File createTempFile() {
        try {
            return File.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX);
        } catch (final IOException e) {
            throw new ResourceStoreException("无法创建临时文件", e);
        }
    }

    @Override
    public void deleteBySha1(String sha1) {
        FileUtils.deleteQuietly(getFile(sha1));
    }

    @Override
    public File getBySha1(String sha1) {
        return getFile(sha1);
    }
}
