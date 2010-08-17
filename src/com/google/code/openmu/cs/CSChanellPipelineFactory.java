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
package com.google.code.openmu.cs;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.logging.LoggingHandler;

import com.google.code.openmu.netty.abstracts.AbstractMuPackageBuilder;
import com.google.code.openmu.netty.abstracts.AbstractMuPackageData;
import com.google.code.openmu.netty.abstracts.MuMessageFrame;
import com.google.code.openmu.netty.filters.MuFrameDecoder;
import com.google.code.openmu.netty.filters.MuMessageFrameEncryptor;
/**
 * 
 * @author mikiones 
 *	<br>The {@link ChannelPipelineFactory} for CS<br>
 *	
 *	the income messages are: <br>
 *	1: decided into single frames( {@link MuMessageFrame} ) by {@link MuFrameDecoder}<br> 
 *	2: The income frames are encrypted by {@link MuMessageFrameEncryptor}<br>
 *	3: and finally go to {@link CSSesionHandler}<br><br>
 *	
 *	The Outcome messages are represent by {@link AbstractMuPackageData}<br> 
 *	1: the {@link AbstractMuPackageData} are coded by specified PackageBuilder {@link AbstractMuPackageBuilder}<br>
 *	2: the package builder create binary frame what is sanded to client<br>
 *
 */
public class CSChanellPipelineFactory implements ChannelPipelineFactory {
	public CSChanellPipelineFactory() {
		super();
		pipe.addLast("1 frameDecoder", new MuFrameDecoder());
		pipe.addLast("2 frame decrypter", new MuMessageFrameEncryptor());
		pipe.addLast("3 protocol Builder", new CSProtocolEncoder());
		pipe.addLast("4 sesionHandler", new CSSesionHandler());
	}

	ChannelPipeline pipe = Channels.pipeline();

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		return pipe;
	}

}
