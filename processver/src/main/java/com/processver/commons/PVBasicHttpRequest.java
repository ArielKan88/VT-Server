package com.processver.commons;

import com.processver.model.FormData;
import com.processver.model.Header;
import com.processver.model.MultiPartEntity;
import com.processver.model.Response;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

public interface PVBasicHttpRequest {


    Response request(String urlStr, List<Header> reqHeaders,
                     List<FormData> formData,
                     RequestMethod requestMethod,
                     List<MultiPartEntity> multiParts) throws
                 IOException;
}
