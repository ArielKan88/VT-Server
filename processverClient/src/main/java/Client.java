import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import org.apache.commons.codec.digest.DigestUtils;
import com.processver.model.ProcessStatus;
import com.processver.model.ProcessInfo;
import org.springframework.web.client.RestTemplate;

public class Client {

    public static void main(String[] args)
    {
       final String uri = "http://localhost:8080/test";
        ArrayList<ProcessInfo> analysisList = new ArrayList<>();
        ArrayList<ProcessStatus> results = new ArrayList<>();

        System.out.println("Welcome to the Process Analysis API");
        try{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter PID:");
            String pidVal = br.readLine();

            String pidSha256 = DigestUtils.sha256Hex(pidVal);

            ProcessInfo info = new ProcessInfo(pidVal,pidSha256);

            //ToDo Pid parent and child pid calculation
            analysisList.add(info);

            RestTemplate restTemplate = new RestTemplate();

            results = restTemplate.postForObject(uri,analysisList,ArrayList.class);

            System.out.println(results.get(0));
        }
        catch(IOException | NumberFormatException nfe)
        {
            nfe.printStackTrace();
        }

    }
}
