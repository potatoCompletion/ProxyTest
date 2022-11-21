package com.proxytest.controller;

import com.proxytest.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class HomeController {

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String home(@RequestParam("url") String url, Model model) {
        model.addAttribute("url", url);
        model.addAttribute("request", "리퀘스트 전송완료!");
        model.addAttribute("response", "리스폰스 전송완료!");

        return "home";
    }

    @RequestMapping(value = "/프록시", method = {RequestMethod.GET, RequestMethod.POST})
    public String getRequestValue(HttpServletRequest request, @RequestParam("url") String url, @RequestBody String postData, Model model) throws IOException {
        var headerData = homeService.getHeader(request);
        StringBuilder requestData = new StringBuilder(headerData);
        requestData.append(System.getProperty("line.separator"));
        requestData.append(postData);

        String nlString = System.getProperty("line.separator");

        var temp = homeService.sendRequest("localhost:8080/프록시?url=kimwansu");

        model.addAttribute("url", url);
        model.addAttribute("request", requestData);
        model.addAttribute("nlString", nlString);

        return "home";
    }
}
