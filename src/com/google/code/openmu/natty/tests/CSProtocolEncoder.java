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
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.google.code.openmu.natty.CS.HelloClientBuilder;
import com.google.code.openmu.natty.CS.HelloClientData;

public class CSProtocolEncoder extends OneToOneEncoder {

	static final HelloClientBuilder helloClient = new HelloClientBuilder();

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		if (!(msg instanceof AbstractMuPackageData)) {
			return msg;
		}
		ChannelBuffer out = ChannelBuffers.dynamicBuffer();
		AbstractMuPackageData mesage = (AbstractMuPackageData) msg;
		switch (mesage.getMessageID()) {
		case 0x00:
			this.helloClient.Build(0, (HelloClientData) mesage, out);
			break;
		default:
			return msg;
		}
		System.out.println(ChannelBuffers.hexDump(out));
		return out;
	}

}
