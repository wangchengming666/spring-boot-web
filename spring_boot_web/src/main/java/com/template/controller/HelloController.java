package com.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.template.service.GetCountService;

@RestController
@RequestMapping("/api")
public class HelloController {

  @Autowired
  private GetCountService getCountService;

  @GetMapping("/getCount")
  public JSONObject getCount(@RequestParam("tableName") String tableName) {

    JSONObject josn = new JSONObject();
    josn.put("result", getCountService.getCount(tableName));
    return josn;
  }
}
