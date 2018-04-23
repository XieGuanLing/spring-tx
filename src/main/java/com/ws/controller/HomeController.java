package com.ws.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by gl on 2017/9/18.
 */
@Controller
@ResponseBody
public class HomeController {


    @Autowired
    private OkHttpClient client;

    private Random random = new Random();


    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }


    @RequestMapping("/start")
    public String start() throws InterruptedException, IOException {
        int sleep= random.nextInt(100);
        TimeUnit.MILLISECONDS.sleep(sleep);
        Request request = new Request.Builder().url("http://localhost:9000/bar").get().build();
        Response response = client.newCall(request).execute();

        String result = response.body().string();
        request = new Request.Builder().url("http://localhost:9000/tar").get().build();
        response = client.newCall(request).execute();
        result += response.body().string();
        return result;
    }
}
