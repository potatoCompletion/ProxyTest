package com.proxytest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
public class HomeService {

    private final WebClient webClient;

    @Autowired
    public HomeService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getHeader(HttpServletRequest request) {
        var headerNames = request.getHeaderNames(); // 헤더 키 값 가져오기

        HashMap<String, String> headers = new HashMap<>();  // 키 값에 밸류값 대입해서 해시맵으로 추출
        while(headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            headers.put(key, request.getHeader(key));
        }

        StringBuilder headerData = new StringBuilder(); // 저장한 해시맵 -> 스트링으로 변경
        for (String nowKey : headers.keySet()) {
            headerData.append(nowKey);
            headerData.append(": ");
            headerData.append(headers.get(nowKey)).append(System.getProperty("line.separator"));
        }

        return headerData.toString();
    }

    public Mono<String> sendRequest(String url) {
        return webClient.mutate()
                .build()
                .post()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
    }

}
