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

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.google.code.openmu.natty.CS.builders.GSServerEntryBuilder;
import com.google.code.openmu.natty.CS.builders.GSServersListBuilder;
import com.google.code.openmu.natty.CS.builders.HelloClientBuilder;
import com.google.code.openmu.natty.CS.data.GSSerersList;
import com.google.code.openmu.natty.CS.data.HelloClientData;
import com.google.code.openmu.natty.CS.data.ServerEntry;
import com.google.code.openmu.utils.Protocol;

public class CSProtocolEncoder extends OneToOneEncoder {

	static final HelloClientBuilder helloClient = new HelloClientBuilder();
	static final GSServersListBuilder gsSererList = new GSServersListBuilder();
	static final GSServerEntryBuilder GS_SERVER_ENTRY_BUILDER = new GSServerEntryBuilder();
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		if (!(msg instanceof AbstractMuPackageData)) {
			System.out.println("kajsklasj "+msg.toString());
			return msg;
		}
		ChannelBuffer out = ChannelBuffers.dynamicBuffer(4);
		AbstractMuPackageData mesage = (AbstractMuPackageData) msg;
		switch (mesage.getMessageID()) {
		case 0x00://hello CL
			CSProtocolEncoder.helloClient.Build(0, (HelloClientData) mesage,out);
			break;
		case 0xf402: // server list ansfer
			CSProtocolEncoder.gsSererList.Build(0, (GSSerersList) mesage, out);
			break;
		case 0xf403: // chosed server data
			CSProtocolEncoder.GS_SERVER_ENTRY_BUILDER.Build(0, (ServerEntry) mesage, out);
			break;
		default:
			return msg;
		}
		
		System.out.println(Protocol.printData(out.array(), out.array().length, "").toUpperCase());
		//return ChannelBuffers.EMPTY_BUFFER;
	
		return out;
	}

}
