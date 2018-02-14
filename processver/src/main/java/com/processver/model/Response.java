package com.processver.model;

import java.io.Serializable;
import java.util.*;
public class Response implements Serializable {


    private int status;
    private String response;

    public Response()
    {}

    public Response(int status,String res)
    {
        this.status = status;
        this.response = res;
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

}
