package com.example.demo11.controller;

import com.example.demo11.service.ConvertJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/demo")
public class ConvertJsonController {
    @Autowired
    ConvertJsonService convertJsonService;

    @GetMapping(path = "/convertJsonGet", produces = "application/json")
    public ResponseEntity<String> convertJsonGet(@RequestParam("url") String url, String params) {
        String rs = convertJsonService.convertJson(url, params);
        if (null == rs) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @GetMapping(path = "/convertJsonPost", produces = "application/json")
    public ResponseEntity<String> convertJsonPost(@RequestParam("url") String url, @RequestBody String params) {
        String rs = convertJsonService.convertJson(url, params);
        if (null == rs) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
}