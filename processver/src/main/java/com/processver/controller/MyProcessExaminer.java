package com.processver.controller;
import com.google.gson.Gson;
import com.processver.model.ProcessInfo;
import com.processver.model.ProcessStatus;
import com.processver.model.Response;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.processver.model.MultiPartEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@RestController
public class MyProcessExaminer {

    private String URI_VT2_URL_SCAN_REPORT = "https://www.virustotal.com/vtapi/v2/file/report";
    private String API_KEY_FIELD = "ca11151a31b8d829bf92055fe9149b6e46870a6efb58ed3d8bdda243e9ad652d";
    List<ProcessStatus> statusListToReturn = null;

    //List<ProcessStatus> examineProcesses(List<ProcessInfo> processesToExamine)
    @RequestMapping(value = "/test",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public List<ProcessStatus> examineProcesses(@RequestBody List<ProcessInfo> processesToExamine)
    {
        Response responseWrapper = new Response();
        Gson gsonProcessor = new Gson();

        if(processesToExamine == null)
        {
            return null;
        }
        //statusListToReturn = processesToExamine.stream().map( car -> car.getName() ).collect( Collectors.toList() );

        for(ProcessInfo p:processesToExamine)
        {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {

                HttpPost httppost = new HttpPost(URI_VT2_URL_SCAN_REPORT);
                StringBody resource = new StringBody(p.getPidSHA256(),ContentType.TEXT_PLAIN);
                StringBody apiKey = new StringBody(API_KEY_FIELD,ContentType.TEXT_PLAIN);

                HttpEntity reqEntity = MultipartEntityBuilder.create()
                        .addPart("apikey", apiKey)
                        .addPart("resource", resource)
                        .build();
                httppost.setEntity(reqEntity);

                responseWrapper = (Response) httpclient.execute(httppost);

                       //not final
                    System.out.println(responseWrapper.getStatus());
                    int statusCode = responseWrapper.getStatus();
                    if (statusCode == 1) {
                        String processResponse = responseWrapper.getResponse();
                        ProcessStatus pStat = gsonProcessor.fromJson(processResponse,ProcessStatus.class);
                        statusListToReturn.add(pStat);

                    }

            }catch (IOException | ClassCastException e  ){

                e.printStackTrace();
            }finally {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return statusListToReturn;
    }

}
