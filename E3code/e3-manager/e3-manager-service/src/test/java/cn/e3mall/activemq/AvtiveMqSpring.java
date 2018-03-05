package cn.e3mall.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class AvtiveMqSpring {
	
	/**
	 * 发送消息
	 * @throws Exception
	 */
	@Test
	public void sendMessage() throws Exception{
		//初始化spring容器
		ApplicationContext applicationConfig = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		//从spring容器中获得JmsTemplate对象
		JmsTemplate jmsTemplate = applicationConfig.getBean(JmsTemplate.class);
		//从spring容器中获得Destination对象
		Destination destination = (Destination) applicationConfig.getBean("queueDestination");
		//使用JmsTemplate对象发送消息
		jmsTemplate.send(destination,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//创建一个消息对象并返回
				TextMessage textMessage = session.createTextMessage("send spring activemq queue message");
				return textMessage;
			}
		});
	}
}
