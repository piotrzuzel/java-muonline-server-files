/*
  Copyright [mikiones] 

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/


package com.google.code.openmu.cs.codec.builder;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;

import com.google.code.openmu.cs.codec.data.ServerEntry;
import com.google.code.openmu.netty.abstracts.AbstractMuPackageBuilder;

/**
 * @author mikiones
 *
 */
public class GSServerEntryBuilder implements AbstractMuPackageBuilder<ServerEntry> {

	/* (non-Javadoc)
	 * @see com.google.code.openmu.natty.tests.AbstractMuPackageBuilder#Build(int, com.google.code.openmu.natty.tests.AbstractMuPackageData, org.jboss.netty.buffer.ChannelBuffer)
	 */
	@Override
	public void Build(int SesionID, ServerEntry data, ChannelBuffer out) {
		out.writeByte(0xc1);
		out.writeByte(0x16);
		out.writeByte(0x03); 
        try {
			out.writeBytes(data.host.getBytes("ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int b = 16 - data.host.length();
        byte [] t = new byte[b];
        out.writeBytes(t);
        out.writeShort(Short.reverseBytes((short)data.port));
		
	}

}
