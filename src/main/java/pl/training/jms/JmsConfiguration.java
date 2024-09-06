package pl.training.jms;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsConfiguration {

  /*@Bean
    public ConnectionFactory connectionFactory() throws JMSException {
        var connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        return new CachingConnectionFactory(connectionFactory);
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        var template = new JmsTemplate(connectionFactory);
        template.setPubSubDomain(true); // topic
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory trainingJmsContainerFactory(ConnectionFactory connectionFactory) {
        var container = new DefaultJmsListenerContainerFactory();
        container.setConnectionFactory(connectionFactory);
        container.setConcurrency("1-10");
        container.setPubSubDomain(true); // topic
        return container;
    }*/

}
