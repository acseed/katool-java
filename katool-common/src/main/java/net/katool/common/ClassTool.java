package net.katool.common;

import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.util.Set;

/**
 * @author hongchen.cao
 * @since 01 四月 2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassTool {
    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    /**
     * 扫描给定包下的所有类并缓存
     * @param basePackage
     * @return
     */
    public static Set<Class<?>> scanTypes(String basePackage) {
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);

        try {
            Set<Class<?>> set = Sets.newHashSet(); 
            Resource[] resources = resolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    set.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
                }
            }
            return set;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 
}
