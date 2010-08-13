package com.google.code.openmu.natty.tests;

import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * 
 * @author mikiones
 * @version $ref
 */
public class natty1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerBootstrap CS = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		CS.setPipelineFactory(new CSChanellPipelineFactory());
		CS.bind(new InetSocketAddress(44405));
		

	}
}
