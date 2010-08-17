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

package com.google.code.openmu.cs.codec.data;

import com.google.code.openmu.netty.abstracts.AbstractMuPackageData;

/**
 * 
 * @author mikiones
 *
 *	The serer entry represent GS instances
 * //TODO fix the GRUP& pos into one byte value and  
 */
public class ServerEntry implements AbstractMuPackageData{
	public static final short FLAG_PREPARING=0xf4;
	public static final short FLAG_EMPTY=0x00;
	public static final short FLAG_FULL=0x64;
	public static final short TYPE_NORMAL=0x00;
	public static final short TYPE_TEST=0x01;
	public static final byte GRUPMULTIPLAYER=0x10; //0x00-0x0f 1'st group 10-1f 2'nd pos etc 
	public String name;
    public String host;
    public int port;
    public byte pos;
    public byte load;
    public byte flag;
    public ServerEntry(){};
    public ServerEntry(String name , String host, int port, byte pos, byte flag, byte load) {
        this.host = host;
        this.name = name;
        this.port = port;
        this.pos = pos;
        this.flag = flag;
        this.load = load;
    }
    @Override
    public String toString() {
    return "SererEntry:[Name:"+name+",Host:" + host + ",Port:" + port +",Pos:"+ pos + ",Flag:" +flag+"]";
    }
	/* (non-Javadoc)
	 * @see com.google.code.openmu.natty.tests.AbstractMuPackageData#getMessageID()
	 */
	@Override
	public int getMessageID() {
		return 0xf403;
	}
}