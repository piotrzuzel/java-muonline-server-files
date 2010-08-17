package com.google.code.openmu;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.Channels;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.google.code.openmu.cs.CSChanellPipelineFactory;
import com.google.code.openmu.cs.ServerList;

/**
 * 
 * @author mikiones
 * @version $ref
 */
public class ConnectServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerList.getInstance().load();
		ServerBootstrap CS = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		CS.setPipelineFactory(new CSChanellPipelineFactory());
		CS.setOption("tcpNoDelay", true);
        CS.setOption("keepAlive", true);
		CS.bind(new InetSocketAddress(44405));


		

	}
}
