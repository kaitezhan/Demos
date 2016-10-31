package com.boneix.base.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.boneix.base.servlet.Base64JsonHttpServletRequestWrapper;

/**
 * Base64 Filter
 * 
 * @author jiyuliang
 * @version [1.0, 2016年1月7日]
 */
public class Base64JsonDecodingFilter implements Filter {

    private PathMatcher matcher = new AntPathMatcher();

    private List<String> noDecodeList = new ArrayList<String>();

    @Override
    public void destroy() {
        // not-found

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (ifContainsInNoDecodeList(req.getServletPath())) {
            chain.doFilter(req, resp);
        } else {
            Base64JsonHttpServletRequestWrapper DecodingRequest = new Base64JsonHttpServletRequestWrapper(req);
            chain.doFilter(DecodingRequest, resp);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String noDecodeStr = filterConfig.getInitParameter("noDecode");
        if (null != noDecodeStr) {
            noDecodeList.clear();
            noDecodeList = Arrays.asList(noDecodeStr.split(","));
        }

    }

    /**
     * 判断一个url是否在排除列表内
     * 
     * @param servletPath
     * @return true 在排除列表中； false 不在排除列表中
     */
    private boolean ifContainsInNoDecodeList(String servletPath) {
        boolean ret = false;
        if (noDecodeList.contains(servletPath)) {
            ret = true;
        } else if (!noDecodeList.isEmpty()) {
            for (String filtermapping : noDecodeList) {
                if (matcher.match(filtermapping, servletPath)) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

}
