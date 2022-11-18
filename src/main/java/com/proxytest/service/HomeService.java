package com.proxytest.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
public class HomeService {

    public String getHeader(HttpServletRequest request) {
        var headerNames = request.getHeaderNames();

        HashMap<String, String> headers = new HashMap<>();
        while(headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            headers.put(key, request.getHeader(key));
        }

        StringBuilder headerData = new StringBuilder();
        for (String nowKey : headers.keySet()) {
            headerData.append(nowKey);
            headerData.append(": ");
            headerData.append(headers.get(nowKey)).append(System.getProperty("line.separator"));
        }

        return headerData.toString();
    }

}
