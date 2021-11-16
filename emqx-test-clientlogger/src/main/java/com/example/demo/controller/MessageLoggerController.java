package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Haniyeh Nasseri
 */
@Slf4j
@RestController
public class MessageLoggerController {

    @PostMapping("/status")
    public ResponseEntity<String> getMessage(@RequestBody String status){
        log.info("status received : " + status);
        return ResponseEntity.ok("status fetched");
    }
}
