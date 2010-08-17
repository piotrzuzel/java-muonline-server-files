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
   
package com.google.code.openmu.netty.abstracts;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * 
 * @author mikiones
 *
 * @param <T> the Data required by builder extends {@link AbstractMuPackageData}
 *  
 */
public interface AbstractMuPackageBuilder <T extends AbstractMuPackageData>{
/**
 * 
 * @param SesionID the session Id can be use to get data in getter kind of packages
 * @param data {@link AbstractMuPackageData} kind data for event procesed data 
 * @param out {@link ChannelBuffer} out buffer to write to package
 */
	void Build(int SesionID, T data, ChannelBuffer out);

}
