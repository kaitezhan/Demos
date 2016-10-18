package com.boneix.base.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Base64解码包装HttpServletRequest
 *
 * @author xiehui
 * @version [1.0, 2016年1月7日]
 */
public class Base64JsonHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String> parameters = new HashMap<String, String>();

    private byte[] bytes;

    private boolean firstTime = true;

    private String encoding = "UTF-8";

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Base64JsonHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            parseParameters();
        } catch (IOException e) {
            throw new RuntimeException("IOException", e);
        }
    }

    protected void parseParameters() throws IOException {
        String method = this.getHttpServletRequest().getMethod();
        StringBuilder jsonstr = new StringBuilder();
        String str = null;
        if (method.equalsIgnoreCase("GET") || method.equalsIgnoreCase("DELETE")) {
            jsonstr.append(this.getQueryString());
            str = jsonstr.toString();
        } else {
            String line;
            BufferedReader reader = getReader();
            while ((line = reader.readLine()) != null) {
                jsonstr.append(line);
            }
            str = jsonstr.toString();
        }
        if (jsonstr.length() > 0) {
            JSONObject jObject = JSON.parseObject(str);
            if (jObject != null) {
                for (Map.Entry<String, Object> entry : jObject.entrySet()) {
                    String key = entry.getKey();
                    String value = JSON.toJSONString(entry.getValue());
                    if (value.length() > 2 && value.startsWith("\"")) {
                        parameters.put(key,
                                value.substring(1, value.length() - 1));
                    } else {
                        parameters.put(key, value);
                    }

                }
            }

        }

    }

    private HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) super.getRequest();
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameMap = new LinkedHashMap<String, String[]>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String[] value = new String[]{
                    entry.getValue()
            };
            parameMap.put(key, value);
        }
        return parameMap;
    }

    public String[] getParameterValues(String name) {
        String value = getParameter(name);
        if (value != null) {
            return new String[]{
                    value
            };
        }
        return null;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    @SuppressWarnings("unchecked")
    public Enumeration<String> getParameterNames() {
        return new IteratorEnumeration<String>(parameters.keySet());
    }

    public String getQueryString() {

        try {
            String str = super.getQueryString();
            String queryString = null;
            if (null != str) {
                queryString = URLdecode(str, "UTF-8");
            }
            if (queryString != null) {
                if (Base64.isBase64(queryString)) {
                    return new String(Base64.decodeBase64(queryString
                            .getBytes(encoding)), encoding);
                } else {
                    return queryString;
                }
            }
            return null;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
    }

    public BufferedReader getReader() throws IOException {
        if (firstTime)
            firstTime();
        InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(
                bytes), encoding);
        return new BufferedReader(isr);
    }

    private void firstTime() throws IOException {
        firstTime = false;
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = this.getHttpServletRequest().getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        byte[] originBytes = buffer.toString().getBytes(encoding);
        if (Base64.isBase64(originBytes)) {
            bytes = Base64.decodeBase64(originBytes);
        } else {
            bytes = originBytes;
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (firstTime) {
            firstTime();
        }
        ServletInputStream sis = new ServletInputStream() {
            private int i;

            @Override
            public int read() throws IOException {
                byte b;
                if (bytes.length > i)
                    b = bytes[i++];
                else
                    b = -1;

                return b;
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener paramReadListener) {
            }
        };

        return sis;
    }

    @SuppressWarnings("rawtypes")
    public class IteratorEnumeration<E> implements Enumeration {

        private Iterator<E> iterator;

        public IteratorEnumeration(Set<E> set) {
            this.iterator = set.iterator();
        }

        public boolean hasMoreElements() {
            return iterator.hasNext();
        }

        public E nextElement() {
            return iterator.next();
        }

    }

    /**
     * 请求串的url安全处理 Base64编码一定是要URL安全的--(不能包含 + 和 / 若包含把 + 替换为 - ,把 / 替换为 _ ) 实现代码雷同于 URLDecoder.decode
     *
     * @param s
     * @param enc
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String URLdecode(String s, String enc)
            throws UnsupportedEncodingException {

        boolean needToChange = false;
        int numChars = s.length();
        StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2
                : numChars);
        int i = 0;

        if (enc.length() == 0) {
            throw new UnsupportedEncodingException(
                    "URLDecoder: empty string enc parameter");
        }

        char c;
        byte[] bytes = null;
        while (i < numChars) {
            c = s.charAt(i);
            switch (c) {
                case '+':
                    sb.append('-');
                    i++;
                    needToChange = true;
                    break;
                case '/':
                    sb.append('_');
                    i++;
                    needToChange = true;
                    break;
                case '%':
                    try {

                        if (bytes == null)
                            bytes = new byte[(numChars - i) / 3];
                        int pos = 0;

                        while (((i + 2) < numChars) && (c == '%')) {
                            bytes[pos++] = (byte) Integer.parseInt(
                                    s.substring(i + 1, i + 3), 16);
                            i += 3;
                            if (i < numChars)
                                c = s.charAt(i);
                        }

                        if ((i < numChars) && (c == '%'))
                            throw new IllegalArgumentException(
                                    "URLDecoder: Incomplete trailing escape (%) pattern");

                        sb.append(new String(bytes, 0, pos, enc));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(
                                "URLDecoder: Illegal hex characters in escape (%) pattern - "
                                        + e.getMessage());
                    }
                    needToChange = true;
                    break;
                default:
                    sb.append(c);
                    i++;
                    break;
            }
        }

        return (needToChange ? sb.toString() : s);
    }

}
