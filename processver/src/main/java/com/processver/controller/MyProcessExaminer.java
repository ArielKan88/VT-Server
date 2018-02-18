package com.processver.controller;
import com.google.gson.Gson;
import com.processver.commons.PVBasicHttpRequest;
import com.processver.commons.PVBasicHttpRequestImp;
import com.processver.model.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.StringBody;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.*;


@RestController
public class MyProcessExaminer {

    private String URI_VT2_URL_SCAN_REPORT = "https://www.virustotal.com/vtapi/v2/file/report";
    private String API_KEY_FIELD = "ca11151a31b8d829bf92055fe9149b6e46870a6efb58ed3d8bdda243e9ad652d";
    private PVBasicHttpRequest httpRequest;
    public ArrayList<ProcessStatus> statusListToReturn;


    @RequestMapping(value = "/test",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public ArrayList<ProcessStatus> examineProcesses(@RequestBody ArrayList<ProcessInfo> processesToExamine)
    {
        Response responseWrapper = new Response();
        Gson gsonProcessor = new Gson();
        httpRequest = new PVBasicHttpRequestImp();
        statusListToReturn = new ArrayList<>();

        int statusCode;

        if(processesToExamine == null)
        {
            return null;
        }

        for(ProcessInfo p:processesToExamine)
        {

            MultiPartEntity apiEntity = new MultiPartEntity("apikey",new StringBody(API_KEY_FIELD,ContentType.TEXT_PLAIN));
            MultiPartEntity resourceEntity = new MultiPartEntity("resource",new StringBody(p.getPidSHA256(),ContentType.TEXT_PLAIN));

            List<MultiPartEntity> multiParts = new ArrayList<MultiPartEntity>();
            multiParts.add(apiEntity);
            multiParts.add(resourceEntity);

            try {
                //Apply to VT server
                responseWrapper = httpRequest.request(URI_VT2_URL_SCAN_REPORT
                        , null, null, RequestMethod.POST, multiParts);
                statusCode = responseWrapper.getStatus();
            }
            catch(IOException e){
                e.printStackTrace();
                return null;
            }

            if(statusCode == HttpURLConnection.HTTP_OK)
            {
                String serviceResponse = responseWrapper.getResponse();
                ProcessStatus ps = gsonProcessor.fromJson(serviceResponse,ProcessStatus.class);

                System.out.println(ps);


                statusListToReturn.add(ps);
            }
            else
            {
                return null;
            }

         }
        return statusListToReturn;
    }

}
