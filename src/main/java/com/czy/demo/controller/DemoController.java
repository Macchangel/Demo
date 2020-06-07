package com.czy.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.czy.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class DemoController {

    @Autowired
    DemoService demoService;

    @GetMapping("/goodgood")
    @ResponseBody
    public String getData(String keyword){
        System.out.println(keyword);
        return "taiduilege,getaidui" + " " + keyword;
    }

    @RequestMapping("index")
    @ResponseBody
    public String index(@RequestBody String name, @RequestBody String url){
        System.out.println(name + " " + url);
        return "hello user";
    }

    @GetMapping("/show")
    @ResponseBody
    public String show(String startDate, String endDate, String keywords){
        System.out.println(startDate + " " + endDate + " " + keywords);
        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        demoService.getData(startDate, endDate, keywords);
        return result.toJSONString();
    }


}
