package com.boneix.client.service.impl;

import com.boneix.client.annotation.ApiCode;
import com.boneix.client.annotation.ApiScanner;
import com.boneix.client.entity.ApiDetailVO;
import com.boneix.client.service.InnerService;
import com.boneix.client.tools.ClassPathClassScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by rzhang on 2017/11/24.
 */
@Slf4j
@Service
public class InnerServiceImpl implements InnerService {

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public List<ApiDetailVO> scanApi() {
        if (StringUtils.isEmpty(appName)) {
            log.error(" spring.application.name is empty");
            return new ArrayList<>();
        }
        Set<Class> beanDefinitions = this.parser("com.boneix.client.controller");
        return analyzeComponents(beanDefinitions);
    }


    private Set<Class> parser(String basePackage) {
        String[] basePackages = resolvePlaceHolders(basePackage);
        ClassPathClassScanner scanner = configureAnnotationScanner();
        return scanner.doScan(basePackages);
    }

    private List<ApiDetailVO> analyzeComponents(Set<Class> beanDefinitions) {
        List<ApiDetailVO> detailVOList = new ArrayList<>();
        for (Class clazz : beanDefinitions) {
            String baseMapping = "";
            if (Objects.nonNull(clazz.getAnnotation(RequestMapping.class))) {
                String[] values = ((RequestMapping) clazz.getAnnotation(RequestMapping.class)).value();
                if (values.length > 0) {
                    baseMapping = values[0];
                }
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                ApiCode apiCode = method.getAnnotation(ApiCode.class);
                if (Objects.isNull(apiCode) || StringUtils.isEmpty(apiCode.value()) || apiCode.skip()) {
                    continue;
                }
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                String[] methodValues = requestMapping.value();
                String mapping = baseMapping;
                if (methodValues.length > 0) {
                    mapping = baseMapping + methodValues[0];
                }
                ApiDetailVO apiDetailVO = new ApiDetailVO();
                apiDetailVO.setCode(appName + "." + apiCode.value());
                apiDetailVO.setMapping(mapping);
                detailVOList.add(apiDetailVO);
            }
        }
        return detailVOList;
    }

    private String[] resolvePlaceHolders(String basePackage) {
        return StringUtils.tokenizeToStringArray(basePackage,
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);

    }

    private ClassPathClassScanner configureAnnotationScanner() {
        List<TypeFilter> includeFilters = new ArrayList<>();
        TypeFilter typeFilter = new AnnotationTypeFilter(ApiScanner.class);
        includeFilters.add(typeFilter);
        return new ClassPathClassScanner(includeFilters, null);
    }
}
