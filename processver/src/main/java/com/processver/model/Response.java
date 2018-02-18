package com.processver.model;

import java.io.Serializable;
import java.util.*;
public class Response implements Serializable {


    private int status;
    private String response;
    private List<Header> respoHeaders;

    public Response()
    {}

    public Response(int status,String res,List<Header> respoHeaders)
    {
        this.status = status;
        this.response = res;
        this.respoHeaders = respoHeaders;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Header> getRespoHeaders() {
        return respoHeaders;
    }

    public void setRespoHeaders(List<Header> respoHeaders) {
        this.respoHeaders = respoHeaders;
    }

}
