package online.connectjagwar.notification_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;


@Configuration
public class RabbitConfig {

    @Value ("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String vhost;

    @Value("${rabbitmq.queue.name}")
    private String emailQueueName;

    @Value("${rabbitmq.notification.exchange}")
    private String notificationExchange;

    @Value("${rabbitmq.notification.dlx}")
    private String deadLetterExchange;

    @Value("${rabbitmq.email.routing}")
    private String emailRouting;

    @Value("${rabbitmq.email.dlq.routing}")
    private String emailDlqRouting;

    @Value("${rabbitmq.queue.dlqName}")
    private String deadLetterQueue;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        
        
        factory.setHost(host);               // RABBIT HOST IP
        factory.setPort(port);               // RABBIT PORT
        factory.setUsername(username);       // RABBIT USER NAME
        factory.setPassword(password);       // RABBIT PASSWORD
        factory.setVirtualHost(vhost);       // Using the root vhost we cleaned up
        
        factory.setConnectionTimeout(15000);
        return factory;
    }


    //--------------------------------------------------------------------------------------------------
    // CREATING EXCHANGES
    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(notificationExchange);
    }

    @Bean DirectExchange deadLDirectExchange(){
        return new DirectExchange(deadLetterExchange);
    }
    //--------------------------------------------------------------------------------------------------





    //--------------------------------------------------------------------------------------------------
    // CREATING QUEUEs FOR FIRST TIME
    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(emailQueueName)
                        .withArgument("x-dead-letter-exchange", deadLetterExchange)
                        .withArgument("x-dead-letter-routing-key", emailDlqRouting).build();
    }

    // CREATING DEAD LETTER QUEUE FOR FAILURE OF SENDING
    @Bean
    public Queue deadLetterQueue(){
        return QueueBuilder.durable(deadLetterQueue).build();
    }
    //--------------------------------------------------------------------------------------------------


    


    //--------------------------------------------------------------------------------------------------
    // BINDING QUEUEs WITH NOTIFINCATION EXCHANGE
    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(emailQueue).to(notificationExchange).with(emailRouting);
    }
    @Bean
    public Binding dlqBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange){
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(emailDlqRouting);
    }
    //--------------------------------------------------------------------------------------------------




    //--------------------------------------------------------------------------------------------------
    // RETRY SENDING FOR 5 TIMES...
    // INITIAL DURATION 20000, THEN MULTIPLE BY 2 AND MAX TIMEFRAME 300000 MS
    // SEND BACK TO DEAD LETTER EXCHANGE
    @Bean
    public RetryOperationsInterceptor retryInspector(){
        return RetryInterceptorBuilder.stateless().maxAttempts(5)
        .backOffOptions(20000, 2.0, 300000)
        .recoverer(new RejectAndDontRequeueRecoverer()) //SEND TO DEAD LETTER EXCHANGE
        .build();
    }
    //--------------------------------------------------------------------------------------------------



    //--------------------------------------------------------------------------------------------------
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);        // MAKE CONNECTION USING CONNECTION CONFIG
        factory.setMessageConverter(jsonMessageConverter());    // CONVERT MESSAGE INTO JSON
        factory.setAdviceChain(retryInspector());               // WHILE LISTENING , USE THE RETRY LOGIC
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}