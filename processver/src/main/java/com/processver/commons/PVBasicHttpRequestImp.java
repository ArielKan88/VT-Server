package com.processver.commons;

import com.processver.model.*;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PVBasicHttpRequestImp implements PVBasicHttpRequest {


    InetSocketAddress proxy = null;

    public PVBasicHttpRequestImp() {

    }
    public PVBasicHttpRequestImp(InetSocketAddress proxy){
        this.proxy = proxy;
    }

    @Override
    public final Response request(final String url,
                                  final List<Header> reqHeaders,
                                  final List<FormData> formData,
                                  final RequestMethod requestMethod,
                                  final List<MultiPartEntity> multiParts
    ) throws  IOException {
        MyHttpStatus httpStatus = new MyHttpStatus();
        List<Header> respoHeaders = new ArrayList<Header>();
        StringBuilder response = new StringBuilder();
        Response responseWrapper;

        URL urlObj = new URL(url);
        HttpURLConnection conn;

        if (proxy != null){
            conn  = (HttpURLConnection) urlObj.openConnection(new Proxy(Proxy.Type.HTTP, proxy));
        }else{
            conn  = (HttpURLConnection) urlObj.openConnection();
        }

        conn.setRequestMethod(requestMethod.toString());

        setRequestHeaders(reqHeaders, conn);

        //add multipart entities
        if (multiParts != null && multiParts.size() > 0) {
            addMultiparts(multiParts, conn);
        } else {
            //add form data to the request
            addFormData(formData, conn);
        }

        //fetch response headers
        fetchResponseHeaders(respoHeaders, conn);

        //Get Response
        try {
            getResponse(httpStatus, response, conn);
        } catch (Exception e) {
            httpStatus.setStatusCode(conn.getResponseCode());
            httpStatus.setStatusCode(conn.getResponseCode());
            httpStatus.setMessage(conn.getResponseMessage());

        }
        responseWrapper = new Response(httpStatus.getStatusCode(),
                response.toString(), respoHeaders);
        return responseWrapper;
    }

    private void getResponse(MyHttpStatus httpStatus, StringBuilder response, HttpURLConnection conn) throws IOException {
        InputStream is = conn.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        httpStatus.setStatusCode(conn.getResponseCode());
        httpStatus.setStatusCode(conn.getResponseCode());
        httpStatus.setMessage(conn.getResponseMessage());
        is.close();
        rd.close();
        conn.disconnect();
    }

    private void fetchResponseHeaders(List<Header> respoHeaders, HttpURLConnection conn) {
        Map<String, List<String>> rowHeaders = conn.getHeaderFields();
        Iterator<String> keys = rowHeaders.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            List<String> vals = rowHeaders.get(key);
            if (vals.size() > 0) {
                respoHeaders.add(new Header(key, vals.get(0)));
            }
        }
    }

    private void addFormData(List<FormData> formData, HttpURLConnection conn) throws IOException {
        if (formData != null && formData.size() > 0) {
            Iterator<FormData> itrFormData = formData.iterator();
            StringBuilder content = new StringBuilder();
            while (itrFormData.hasNext()) {
                FormData formDataObj = itrFormData.next();
                content.append(URLEncoder.encode(formDataObj.getKey(),
                        "UTF-8"));
                content.append("=");
                content.append(URLEncoder.encode(formDataObj.getValue(),
                        "UTF-8"));
                content.append("&");
            }
            if (content.length() > 0) {
                conn.setDoOutput(true);
                //Send request
                DataOutputStream wr =
                        new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(content.toString());
                wr.flush();
                wr.close();
            }
        }
    }

    private void addMultiparts(List<MultiPartEntity> multiParts, HttpURLConnection conn) throws IOException {
        MultipartEntity multipartEntity =
                new MultipartEntity(HttpMultipartMode.STRICT);
        for (MultiPartEntity part : multiParts) {
            multipartEntity.addPart(part.getPartName(), part.getEntity());
        }
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type",
                multipartEntity.getContentType().getValue());

        //try to write to the output stream of the connection
        OutputStream outStream = conn.getOutputStream();
        multipartEntity.writeTo(outStream);
        outStream.close();
    }

    private void setRequestHeaders(List<Header> reqHeaders, HttpURLConnection conn) {
        if (reqHeaders != null && reqHeaders.size() > 0) {
            Iterator<Header> itrHeaders = reqHeaders.iterator();
            while (itrHeaders.hasNext()) {
                Header reqHdr = itrHeaders.next();
                conn.setRequestProperty(reqHdr.getKey(), reqHdr.getValue());
            }
        }
    }
}


