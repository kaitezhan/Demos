package com.boneix.base.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RestServer {
    private HttpServletRequest request;

    private HttpServletResponse response;

    public RestServer(HttpServletRequest req, HttpServletResponse resp) {
        this.request = req;
        this.response = resp;
    }

    public String getRestData() throws RestException {
        if (null == this.request) {
            return null;
        }

        String method = this.request.getMethod();
        String ret = null;
        if ((method.equalsIgnoreCase("GET")) || (method.equalsIgnoreCase("DELETE"))) {
            ret = this.request.getQueryString();
        } else ret = getBodyData();

        if (null == ret) {
            return null;
        }

        return RestCodec.decodeData(ret);
    }

    public boolean sendRestData(String data) throws RestException {
        if (null == this.response) {
            return false;
        }

        this.response.setContentType("application/json");
        this.response.setCharacterEncoding("utf-8");
        PrintWriter writer = null;
        try {
            writer = this.response.getWriter();
            writer.print(RestCodec.encodeData(data));
        } catch (IOException e) {
            throw new RestException(e.getMessage(), e.getCause());
        } finally {
        }
        return true;
    }

    private String getBodyData() throws RestException {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = this.request.getReader();
            while (null != (line = reader.readLine()))
                data.append(line);
        } catch (IOException e) {
            throw new RestException(e.getMessage(), e.getCause());
        } finally {
        }
        return data.toString();
    }
}