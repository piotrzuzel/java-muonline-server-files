/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.openmu;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
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
