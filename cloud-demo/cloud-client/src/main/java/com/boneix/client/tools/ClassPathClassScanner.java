package com.boneix.client.tools;

import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rzhang on 2017/11/24.
 */
public class ClassPathClassScanner {
    private ClassPathClassScanningProvider provider;

    public ClassPathClassScanner(List<TypeFilter> includeFilters, List<TypeFilter> excludeFilters) {
        ClassPathClassScanningProvider defaultProvider = new ClassPathClassScanningProvider();
        if (!CollectionUtils.isEmpty(includeFilters)) {
            for (TypeFilter typeFilter : includeFilters) {
                defaultProvider.addIncludeFilter(typeFilter);
            }
        }
        if (!CollectionUtils.isEmpty(excludeFilters)) {
            for (TypeFilter typeFilter : excludeFilters) {
                defaultProvider.addExcludeFilter(typeFilter);
            }
        }
        this.provider = defaultProvider;
    }

    public Set<Class> doScan(String... basePackages) {
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        Set<Class> beanDefinitions = new LinkedHashSet<>();
        for (String basePackage : basePackages) {
            Set<Class> candidates = findCandidateClasses(basePackage);
            beanDefinitions.addAll(candidates);
        }
        return beanDefinitions;
    }

    private Set<Class> findCandidateClasses(String basePackage) {
        return this.provider.findCandidateClasses(basePackage);
    }
}
