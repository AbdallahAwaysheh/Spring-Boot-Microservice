package com.orange.e_shop.mail_service.controller;

import com.orange.e_shop.mail_service.dto.MailRequest;
import com.orange.e_shop.mail_service.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/mail")
@RestController
public class TestController {

    private final MailService mailService;

    @PostMapping
    public ResponseEntity<String> sendMail(@RequestBody MailRequest request){

        mailService.sendMail(request.getTo(),request.getSubject(), request.getBody());

        return  ResponseEntity.ok("done");
    }
}
