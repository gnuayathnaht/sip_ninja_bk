package org.example.sip_bk.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRest {

    @RequestMapping("/")
    public String hello() {
        return "Hello World!";
    }

}
