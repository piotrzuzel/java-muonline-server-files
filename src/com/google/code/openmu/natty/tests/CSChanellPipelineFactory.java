/*
 * Copyright [mikiones] [Michal Kinasiewicz]
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
package com.google.code.openmu.natty.tests;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.logging.LoggingHandler;

public class CSChanellPipelineFactory implements ChannelPipelineFactory {
	public CSChanellPipelineFactory() {
		super();
		
		pipe.addLast("2 frame decrypter", new MuMessageDecrytor());
		pipe.addLast("1 frameDecoder", new MuFrameDecoder());
		
		
		pipe.addLast("3 protocol Builder", new CSProtocolEncoder());
		//pipe.addLast("x Channel Upstream header", new CSChanelUpstreamHeader());
		pipe.addLast("log", new LoggingHandler(true));
		pipe.addLast("4 sesionHandler", new CSSesionHandler());
		
	}

	ChannelPipeline pipe = Channels.pipeline();

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		return pipe;
	}

}
