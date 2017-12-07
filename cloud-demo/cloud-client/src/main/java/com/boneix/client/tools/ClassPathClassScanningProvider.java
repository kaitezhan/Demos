package com.boneix.client.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by rzhang on 2017/11/27.
 */
@Slf4j
public class ClassPathClassScanningProvider implements ResourceLoaderAware {
    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private String resourcePattern = DEFAULT_RESOURCE_PATTERN;

    private final List<TypeFilter> includeFilters = new LinkedList<>();

    private final List<TypeFilter> excludeFilters = new LinkedList<>();

    private ResourcePatternResolver resourcePatternResolver;

    private MetadataReaderFactory metadataReaderFactory;

    ClassPathClassScanningProvider() {
        setResourceLoader(null);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
    }

    public void setResourcePattern(String resourcePattern) {
        Assert.notNull(resourcePattern, "'resourcePattern' must not be null");
        this.resourcePattern = resourcePattern;
    }

    /**
     * Add an include type filter to the <i>end</i> of the inclusion list.
     */
    void addIncludeFilter(TypeFilter includeFilter) {
        this.includeFilters.add(includeFilter);
    }

    /**
     * Add an exclude type filter to the <i>front</i> of the exclusion list.
     */
    void addExcludeFilter(TypeFilter excludeFilter) {
        this.excludeFilters.add(0, excludeFilter);
    }

    Set<Class> findCandidateClasses(String basePackage) {
        Set<Class> candidates = new LinkedHashSet<>();
        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    resolveBasePackage(basePackage) + '/' + this.resourcePattern;
            Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
            boolean traceEnabled = log.isTraceEnabled();
            boolean debugEnabled = log.isDebugEnabled();
            for (Resource resource : resources) {
                if (traceEnabled) {
                    log.trace("Scanning " + resource);
                }
                if (resource.isReadable()) {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                    if (isCandidateComponent(metadataReader)) {
                        String clazzName = metadataReader.getClassMetadata().getClassName();
                        try {
                            candidates.add(Class.forName(clazzName));
                        } catch (ClassNotFoundException e) {
                            if (debugEnabled) {
                                log.debug("metadataReader analyze class {} exception {} ", clazzName, e);
                            }
                        }
                    }
                } else {
                    if (traceEnabled) {
                        log.trace("Ignored because not readable:{} ", resource);
                    }
                }
            }
        } catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }
        return candidates;
    }

    /**
     * Resolve the specified base package into a pattern specification for
     * the package search path
     */
    private String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
    }

    private boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
        for (TypeFilter tf : this.excludeFilters) {
            if (tf.match(metadataReader, this.metadataReaderFactory)) {
                return false;
            }
        }
        for (TypeFilter tf : this.includeFilters) {
            if (tf.match(metadataReader, this.metadataReaderFactory)) {
                return true;
            }
        }
        return false;
    }
}
