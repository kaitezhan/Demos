package com.boneix.base.environment;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.Constants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * 环境变量配置类
 *
 * @author wangchong
 * @version [1.0, 2016年1月5日]
 */
public class PropertyPlaceholderConfigurerSupportMultiEnvironment extends PropertyPlaceholderConfigurer {

    private String[] placeholderConfigLocations;

    private PropertyPlaceholderHelper helper;

    private String fileEncoding;

    private String placeholderPrefix = DEFAULT_PLACEHOLDER_PREFIX;

    private String placeholderSuffix = DEFAULT_PLACEHOLDER_SUFFIX;

    private String valueSeparator = DEFAULT_VALUE_SEPARATOR;

    private boolean ignoreUnresolvablePlaceholders;

    private static final Constants constants = new Constants(PropertyPlaceholderConfigurerSupportMultiEnvironment.class);

    private int systemPropertiesMode;

    public static final int SYSTEM_PROPERTIES_MODE_NEVER = 0;

    public static final int SYSTEM_PROPERTIES_MODE_FALLBACK = 1;

    public static final int SYSTEM_PROPERTIES_MODE_OVERRIDE = 2;

    public static final String SYSTEM_PREFIX = "system.";

    public PropertyPlaceholderConfigurerSupportMultiEnvironment() {
        super();
    }

    private synchronized void initPlaceholderHelper() {
        if (helper == null) {
            helper = new PropertyPlaceholderHelper(placeholderPrefix, placeholderSuffix, valueSeparator,
                    ignoreUnresolvablePlaceholders);
        }
    }

    private PropertyPlaceholderHelper getPlaceholderHelper() {
        if (helper == null) {
            initPlaceholderHelper();
        }
        return helper;
    }

    public String[] getPlaceholderConfigLocations() {
        return placeholderConfigLocations;
    }

    public void setPlaceholderConfigLocations(String[] placeholderConfigLocations) {
        this.placeholderConfigLocations = placeholderConfigLocations;
    }

    @Override
    protected Properties mergeProperties() throws IOException {
        final Properties properties = super.mergeProperties();
        setSystemEnvProperties(properties);
        for (String configLocation : getPlaceholderConfigLocations()) {
            resolvePlaceholder(configLocation, properties);
            PlaceholderResolver placeholderResolver = new PlaceholderResolver() {
                @Override
                public String resolvePlaceholder(String placeholderName) {
                    return PropertyPlaceholderConfigurerSupportMultiEnvironment.this.resolvePlaceholder(
                            placeholderName, properties, systemPropertiesMode);
                }
            };
            String envConfigLocation = getPlaceholderHelper().replacePlaceholders(configLocation, placeholderResolver);
            Resource resource = new ClassPathResource(envConfigLocation.substring(ResourceUtils.CLASSPATH_URL_PREFIX
                    .length()), ClassUtils.getDefaultClassLoader());
            PropertiesLoaderUtils.fillProperties(properties, new EncodedResource(resource, this.fileEncoding));
        }
        return properties;
    }

    private void setSystemEnvProperties(Properties properties) {
        Set<Object> keys = properties.keySet();
        try {
            for (Object obj : keys) {
                if (obj instanceof String) {
                    String key = (String) obj;
                    if (key.startsWith(SYSTEM_PREFIX) || key.startsWith(SYSTEM_PREFIX.toUpperCase())) {
                        String subfix = null;
                        if (key.indexOf(SYSTEM_PREFIX) != -1) {
                            subfix = key.substring(key.indexOf(SYSTEM_PREFIX) + SYSTEM_PREFIX.length());
                        }
                        if (key.indexOf(SYSTEM_PREFIX.toUpperCase()) != -1) {
                            subfix = key.substring(key.indexOf(SYSTEM_PREFIX.toUpperCase()) + SYSTEM_PREFIX.length());
                        }
                        System.setProperty(subfix, properties.getProperty(key));
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void setPlaceholderPrefix(String placeholderPrefix) {
        this.placeholderPrefix = placeholderPrefix;
        initPlaceholderHelper();
        super.setPlaceholderPrefix(placeholderPrefix);
    }

    @Override
    public void setPlaceholderSuffix(String placeholderSuffix) {
        this.placeholderSuffix = placeholderSuffix;
        initPlaceholderHelper();
        super.setPlaceholderSuffix(placeholderSuffix);
    }

    @Override
    public void setValueSeparator(String valueSeparator) {
        this.valueSeparator = valueSeparator;
        initPlaceholderHelper();
        super.setValueSeparator(valueSeparator);
    }

    @Override
    public void setIgnoreUnresolvablePlaceholders(boolean ignoreUnresolvablePlaceholders) {
        this.ignoreUnresolvablePlaceholders = ignoreUnresolvablePlaceholders;
        initPlaceholderHelper();
        super.setIgnoreUnresolvablePlaceholders(ignoreUnresolvablePlaceholders);
    }

    @Override
    public void setSystemPropertiesModeName(String constantName) throws IllegalArgumentException {
        this.systemPropertiesMode = constants.asNumber(constantName).intValue();
        super.setSystemPropertiesModeName(constantName);
    }

    @Override
    public void setFileEncoding(String encoding) {
        this.fileEncoding = encoding;
        super.setFileEncoding(encoding);
    }
}
