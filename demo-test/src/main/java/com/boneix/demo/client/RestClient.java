package com.boneix.demo.client;

import java.io.IOException;


public class RestClient extends HttpClient {

    public RestClient(String url, String method, String data) {
        super(url, method, data);
    }

    public RestClient(String url) {
        super(url);
    }

    public RestClient(String url, String data) {
        super(url, data);
    }


    @Override
    public String execute() throws IOException {
        if (null == this.serverUrl) {
            return null;
        }
        this.clientData = RestCodec.encodeData(this.clientData);
        String ret = httpExecute();
        if (null != ret) {
            ret = RestCodec.decodeData(ret);
        }
        return ret;
    }


}