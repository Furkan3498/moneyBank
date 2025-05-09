package com.moneyBank.moneyBank.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMqConfig {

    @Value("${sample.rabbitmq.exchange}")
     String exchage;

    @Value("${sample.rabbitmq.queue}")
    String queueName;

    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;

    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    //durable: This is the feature of whether your message can be deleted or not.
    @Bean
    public Queue secondStepQueue() {
        return new Queue("secondStepQueue", true);
    }
    @Bean
    public Queue thirdStepQueue() {
        return new Queue("thirdStepQueue", true);
    }


    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(exchage);
    }

    @Bean
    Binding binding (Queue queue, DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
        //this our quequ first binding to exchane with key

    }
    @Bean
    Binding secondStepQueueBinding (Queue secondStepQueue, DirectExchange exchange){
        return BindingBuilder.bind(secondStepQueue).to(exchange).with("secondStepQueue");
        //this our quequ first binding to exchane with key

    }
    @Bean
    Binding thirdStepQueueBinding (Queue thirdStepQueue, DirectExchange exchange){
        return BindingBuilder.bind(thirdStepQueue).to(exchange).with("thirdStepQueue");
        //this our quequ first binding to exchane with key

    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
        //message sendin JSON type we must tu convert object
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter( jsonMessageConverter());
        return rabbitTemplate;
    }



}
