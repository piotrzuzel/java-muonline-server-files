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

import java.util.ArrayList;

import org.jboss.netty.buffer.ChannelBuffer;

import com.google.code.openmu.cs.ServerList;
import com.google.code.openmu.cs.codec.data.GSSerersList;
import com.google.code.openmu.cs.codec.data.ServerEntry;
import com.google.code.openmu.netty.abstracts.AbstractMuPackageBuilder;

/**
 * @author mikiones
 * 
 */
public class GSServersListBuilder implements
		AbstractMuPackageBuilder<GSSerersList> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.code.openmu.natty.tests.AbstractMuPackageBuilder#Build(int,
	 * com.google.code.openmu.natty.tests.AbstractMuPackageData,
	 * org.jboss.netty.buffer.ChannelBuffer)
	 */
	@Override
	public void Build(int SesionID, GSSerersList data, ChannelBuffer out) {
		ArrayList<ServerEntry> list = ServerList.getInstance().asArrayList();
		int size=(6 + (list.size() * 4));
		out.writeByte(0xc2);
		out.writeShort(size);
		out.writeByte(0xf4);
		out.writeByte(0x02);
		out.writeByte(list.size());
		for (ServerEntry i : list) {
			out.writeByte(i.pos);
			out.writeByte(i.pos);
			out.writeByte(i.load);
			out.writeByte(0xcc);
		}
	}

}
