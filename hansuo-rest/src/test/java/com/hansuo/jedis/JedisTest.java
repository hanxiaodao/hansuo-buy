package com.hansuo.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hansuo.rest.component.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	
	@Test
	public void testJedisSingle(){
		//创建一个jedis对象
		
		Jedis jedis = new Jedis("172.20.16.134",6379);
		jedis.set("test","hell redis");
		String str = jedis.get("test");
		System.out.println(str);
		jedis.close();
	}
	
	@Test
	public void testJedisPool (){
		//创建一个jedis对象
		//系统中应该是单例的
		JedisPool jedisPool = new JedisPool("172.20.16.134", 6379);
		//从连接池中获得一个连接
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get("test");
		System.out.println(result);
		//jedis必须关闭
		jedis.close();
	}
	//集群版使用jedis
	@Test
	public void testJedisCluster(){
		//创建一个jedisCluster对象
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("172.20.16.134", 7001));
		nodes.add(new HostAndPort("172.20.16.134", 7002));
		nodes.add(new HostAndPort("172.20.16.134", 7003));
		nodes.add(new HostAndPort("172.20.16.134", 7004));
		nodes.add(new HostAndPort("172.20.16.134", 7005));
		nodes.add(new HostAndPort("172.20.16.134", 7006));
		//在nodes中指定每个节点的地址
		//jedisCluster在系统中是单例的
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("name","张三");
		jedisCluster.set("age", "100");
		String name = jedisCluster.get("name");
		String age = jedisCluster.get("age");
		System.out.println(name);
		System.out.println(age);
		//关闭
		jedisCluster.close();
		
	}
	
	@Test
	public void testJedisClientSpring(){
		//创建一个容器
		ApplicationContext applicationContent= new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		//从容器中获取jedisClient对象
		JedisClient jedisClient = applicationContent.getBean(JedisClient.class);
		//jedisClient操作redis
		jedisClient.set("client1","1000");
		String str = jedisClient.get("client1");
		System.out.println(str);
	}
	
	public static void main(String[] args) {
		System.out.println(123);
	}
	
}
