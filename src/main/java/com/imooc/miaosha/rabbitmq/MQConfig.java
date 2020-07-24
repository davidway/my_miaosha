package com.imooc.miaosha.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
	
	public static final String MIAOSHA_QUEUE = "miaosha.queue";
	public static final String QUEUE = "queue";
	public static final String TOPIC_QUEUE1 = "topic.queue1";
	public static final String TOPIC_QUEUE2 = "topic.queue2";
	public static final String HEADER_QUEUE = "header.queue";
	public static final String TOPIC_EXCHANGE = "topicExchage";
	public static final String FANOUT_EXCHANGE = "fanoutxchage";
	public static final String HEADERS_EXCHANGE = "headersExchage";
	public static final String MY_TOPIC_EXCHANGE = "mytopicExchange";
	public static final String  MY_TOPIC_QUENEN1 = "mytopic.queue1";
	public static final String  MY_TOPIC_QUENEN2 = "mytopic.queue2";
	private static final String HELLO_FANOUT_EXCHANGE ="helloFanout";


	/**
	 * Direct模式 交换机Exchange
	 * */
	@Bean
	public Queue queue() {
		return new Queue(QUEUE, true);
	}

	/**
	 * 绑定mytopic到 topicExchange当中 并且还绑定了key
	 * @return
	 */
	@Bean
	public Queue mytopicQueue1() {
		return new Queue(MY_TOPIC_QUENEN1,true);
	}
	@Bean
	public Queue mytopicQueue2() {
		return new Queue(MY_TOPIC_QUENEN2,true);
	}


	@Bean
	public TopicExchange mytopicExchange(){
		return new TopicExchange(MY_TOPIC_EXCHANGE);
	}

	@Bean
	public Binding mytopicBinding1(){
		return BindingBuilder.bind(mytopicQueue1()).to(mytopicExchange()).with("mytopic.key1");
	}
	@Bean
	public Binding mytopicBinding2(){
		return BindingBuilder.bind(mytopicQueue2()).to(mytopicExchange()).with("mytopic.#");
	}

	@Bean
	public FanoutExchange fanoutExchange(){
		return new FanoutExchange(HELLO_FANOUT_EXCHANGE);
	}
	@Bean
	public Binding myFanoutBinding1(){
		return BindingBuilder.bind(mytopicQueue1()).to(fanoutExchange());
	}
	@Bean
	public Binding myFanoutBinding2(){
		return BindingBuilder.bind(mytopicQueue2()).to(fanoutExchange());
	}


	/**
	 * 绑定mytopic到 topicExchange当中 并且还绑定了key
	 * #是匹配 0个或者多个单词
	 * *是多个单词
	 * @return
	 */


	/**
	 * Topic模式 交换机Exchange
	 * */
	@Bean
	public Queue topicQueue1() {
		return new Queue(TOPIC_QUEUE1, true);
	}
	@Bean
	public Queue topicQueue2() {
		return new Queue(TOPIC_QUEUE2, true);
	}
	@Bean
	public TopicExchange topicExchage(){
		return new TopicExchange(TOPIC_EXCHANGE);
	}
	@Bean
	public Binding topicBinding1() {
		return BindingBuilder.bind(topicQueue1()).to(topicExchage()).with("topic.key1");
	}
	@Bean
	public Binding topicBinding2() {
		return BindingBuilder.bind(topicQueue2()).to(topicExchage()).with("topic.#");
	}
	/**
	 * Fanout模式 交换机Exchange
	 * */
	@Bean
	public FanoutExchange fanoutExchage(){
		return new FanoutExchange(FANOUT_EXCHANGE);
	}
	@Bean
	public Binding FanoutBinding1() {
		return BindingBuilder.bind(topicQueue1()).to(fanoutExchage());
	}
	@Bean
	public Binding FanoutBinding2() {
		return BindingBuilder.bind(topicQueue2()).to(fanoutExchage());
	}
	/**
	 * Header模式 交换机Exchange
	 * */
	@Bean
	public HeadersExchange headersExchage(){
		return new HeadersExchange(HEADERS_EXCHANGE);
	}
	@Bean
	public Queue headerQueue1() {
		return new Queue(HEADER_QUEUE, true);
	}
	@Bean
	public Binding headerBinding() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("header1", "value1");
		map.put("header2", "value2");
		return BindingBuilder.bind(headerQueue1()).to(headersExchage()).whereAll(map).match();
	}
	
	
}
