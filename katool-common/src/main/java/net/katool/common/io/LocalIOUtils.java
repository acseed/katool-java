package net.katool.common.io;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * @author hongchen.cao
 * @since 09 七月 2021
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalIOUtils {
    public static byte[] readFromClassPath(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        try {
            return FileUtils.readFileToByteArray(resource.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] readToBytes(InputStream inputStream) {
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            log.error("读输入流出错");
            throw new RuntimeException(e);
        }
    }

    public static String loadClassPathFile(String classPathFile) {
        ClassPathResource resource = new ClassPathResource(classPathFile);
        try (InputStream inputStream = resource.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            return bufferedReader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            log.info("load classpath file error, classPathFile={}", classPathFile, e);
            throw new RuntimeException(e);
        }
    }
}
