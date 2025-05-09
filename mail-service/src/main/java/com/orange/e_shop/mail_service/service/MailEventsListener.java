package com.orange.e_shop.mail_service.service;

import com.orange.e_shop.mail_service.config.RabbitMQConfig;
import com.orange.e_shop.mail_service.dto.PasswordResetEvent;
import com.orange.e_shop.mail_service.dto.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailEventsListener {

    private final MailService mailService;

    @RabbitListener(queues = RabbitMQConfig.USER_REGISTERD_QUEUE)
    public void handleWelcomeMail(UserRegisteredEvent event){
        try{
            log.info("welcome mail to "+ event.getEmail() +  " initiated");
            mailService.sendMail(event.getEmail(),"Welcome!","Hello! " + event.getName() + ", Thank you for signing up for Orange Eshop!");
            log.info("welcome mail to "+ event.getEmail() +  " Sent");
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", event.getEmail(), e.getMessage());
        }

    }
    @RabbitListener(queues = RabbitMQConfig.PASSWORD_RESET_QUEUE)
    public void handleResetMail(PasswordResetEvent event)
    {
        log.info("reset mail initiated to " + event.getEmail() + " with code: \n " + event.getResetToken());
        mailService.sendMail(
                event.getEmail(),
                "Password Reset Requested",
                String.format("If you requested to reset your password, please use this token:\n%s", event.getResetToken())
        );

        log.info("reset mail sent to " + event.getEmail());
    }
}
