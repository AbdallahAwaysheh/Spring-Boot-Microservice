package com.orange.e_shop.user_service.service.auth;



import com.orange.e_shop.user_service.conf.RabbitMQConfig;
import com.orange.e_shop.user_service.dto.auth.PasswordResetEvent;
import com.orange.e_shop.user_service.dto.auth.UserRegisterdEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public UserEventPublisher(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserRegisteredEvent(UserRegisterdEvent event)
    {
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_REGISTERED_QUEUE, event);
    }

    public void publishPasswordResetEvent(PasswordResetEvent event){
        rabbitTemplate.convertAndSend(RabbitMQConfig.PASSWORD_RESET_QUEUE,event);
    }
}
