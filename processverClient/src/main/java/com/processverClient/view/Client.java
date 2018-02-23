package com.processverClient.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.codec.digest.DigestUtils;
import com.processver.model.ProcessStatus;
import com.processver.model.ProcessInfo;
import org.springframework.web.client.RestTemplate;
import com.processverClient.model.*;

public class Client {

    public static void main(String[] args)
    {
        String pidVal = "";
       final String uri = "http://localhost:8080/test";
        ArrayList<ProcessInfo> analysisList = new ArrayList<>();
        ArrayList<ProcessStatus> results = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ProcessStatus.class, new ProcessStatusDeserializer());
        mapper.registerModule(module);

        System.out.println("Welcome to the Process Analysis API");
        System.out.println("Enter PIDs:");
        System.out.println("-- Enter -1 to begin the analysis --");

        try{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            pidVal = br.readLine();

            while (!pidVal.equals("-1")){
            pidVal = br.readLine();

            String pidSha256 = DigestUtils.sha256Hex(pidVal);

            ProcessInfo info = new ProcessInfo(pidVal, pidSha256);

            //ToDo Pid parent and child pid calculation
            analysisList.add(info);
        }

            RestTemplate restTemplate = new RestTemplate();

          ArrayList<String> temp = restTemplate.postForObject(uri,analysisList,ArrayList.class);
            if(temp.size() == 0)
            {
                System.out.println("No data found for process - "+pidVal);
            }



            for(String val :temp) {

                final ProcessStatus ps = mapper.readValue(val,ProcessStatus.class);

                int index = analysisList.indexOf(ps.getSha256());

                String originalPid = analysisList.get(index).getPid();

                if(ps.getPositives()!=0)
                {
                    System.out.println("Process - "+originalPid+" is malicious!");
                    System.out.println("PID -"+originalPid+" Report");
                    System.out.println(ps);
                }
                else
                {
                    System.out.println("Process - "+originalPid+" Is Clean!");
                }

            }
        }
        catch(IOException | NumberFormatException nfe)
        {
            nfe.printStackTrace();
        }

    }
}
