package com.boneix.base.utils.http;

import com.boneix.base.utils.common.MapUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Map;

public class HttpClient {


    protected String serverUrl;

    protected String httpMethod;

    protected String clientData;

    protected int connectTimeout;

    protected int socketTimeout;

    protected Map<String, String> head;

    public HttpClient(String url, String method, String data) {
        this.serverUrl = url;
        this.httpMethod = method;
        this.clientData = data;
        this.connectTimeout = -1;
        this.socketTimeout = -1;
    }

    public HttpClient(String serverUrl, String httpMethod, String clientData, Map<String, String> head) {
        this.serverUrl = serverUrl;
        this.httpMethod = httpMethod;
        this.clientData = clientData;
        this.head = head;
    }

    public HttpClient(String url) {
        this.serverUrl = url;
        this.httpMethod = "GET";
        this.clientData = null;
        this.connectTimeout = -1;
        this.socketTimeout = -1;
    }

    public HttpClient(String url, String data) {
        this.serverUrl = url;
        this.httpMethod = "GET";
        this.clientData = data;
        this.connectTimeout = -1;
        this.socketTimeout = -1;
    }

    public void setURL(String url) {
        this.serverUrl = url;
    }

    public void setMethod(String method) {
        this.httpMethod = method;
    }

    public void setData(String data) {
        this.clientData = data;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public String execute() throws IOException {
        if (null == this.serverUrl) {
            return null;
        }
        String ret = this.httpExecute();
        return ret;
    }

    private String getURLWithData() {
        if (this.clientData != null) {
            if (this.serverUrl.endsWith("?")) {
                return this.serverUrl + this.clientData;
            }
            return this.serverUrl + "?" + this.clientData;
        }
        return this.serverUrl;
    }

    protected String httpExecute() throws IOException {
        String ret = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 设置请求和传输超时时间
        RequestConfig.Builder builderConfig = RequestConfig.custom();
        if (0 < this.connectTimeout) {
            builderConfig.setConnectTimeout(Integer.valueOf(this.connectTimeout * 1000));
        }
        if (0 < this.socketTimeout) {
            builderConfig.setSocketTimeout(Integer.valueOf(this.socketTimeout * 1000));
        }
        RequestConfig requestConfig = builderConfig.build();
        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            if (this.httpMethod.equalsIgnoreCase("GET")) {
                HttpGet httpGet = new HttpGet(getURLWithData());
                if (MapUtils.isNotEmpty(head)) {
                    for (Map.Entry<String, String> entry : head.entrySet()) {
                        httpGet.addHeader(entry.getKey(), entry.getValue());
                    }
                }
                httpGet.setConfig(requestConfig);
                ret = httpclient.execute(httpGet, responseHandler);
            } else if (this.httpMethod.equalsIgnoreCase("POST")) {
                HttpPost httpPost = new HttpPost(this.serverUrl);
                if (MapUtils.isNotEmpty(head)) {
                    for (Map.Entry<String, String> entry : head.entrySet()) {
                        httpPost.addHeader(entry.getKey(), entry.getValue());
                    }
                }
                httpPost.setConfig(requestConfig);
                if (null != this.clientData) {
                    httpPost.setEntity(new StringEntity(this.clientData, "UTF-8"));
                }
                ret = httpclient.execute(httpPost, responseHandler);
            } else if (this.httpMethod.equalsIgnoreCase("PUT")) {
                HttpPut httpPut = new HttpPut(this.serverUrl);
                httpPut.setConfig(requestConfig);
                if (null != this.clientData) {
                    httpPut.setEntity(new StringEntity(this.clientData, "UTF-8"));
                }
                ret = httpclient.execute(httpPut, responseHandler);
            } else if (this.httpMethod.equalsIgnoreCase("DELETE")) {
                HttpDelete httpDelete = new HttpDelete(getURLWithData());
                httpDelete.setConfig(requestConfig);
                ret = httpclient.execute(httpDelete, responseHandler);
            }
        } finally {
            httpclient.close();
        }
        return ret;
    }


}