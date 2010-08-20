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

package com.google.code.openmu.cs;

/**
 * @author mikiones
 * 
 */
public class ServerEntryNotFound extends Exception {

	int pos;
	int flag;

	/**
	 * @param pos
	 * @param flag
	 */
	public ServerEntryNotFound(int pos, int flag) {
		this.pos = pos;
		this.flag = flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {

		return "ServerENtry not exist[pos:" + pos + ",flag" + flag + "]!!";
	}
}
