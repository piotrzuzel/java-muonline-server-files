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

package com.google.code.openmu.gs.commands;

/**
 * 
 * @author Miki i Linka
 */
public class CmdTestArgs extends GsBaseCommand {

	@Override
	public boolean RunCommand() {
		System.out.println(getCmdString() + " Runned!");
		return true;
	}

	@Override
	public void ParseArgs(String[] args) {
		System.out.println("|Arguements list fo commaand :" + getCmdString());
		for (int i = 1; i < args.length; i++) {
			System.out.println("|-- Arg[" + args[i] + "]");
		}
		System.out.println("|End Arguements list fo commaand :'"
				+ getCmdString() + "'");
	}

	@Override
	public String getCmdString() {
		return "CommTestArgs";
	}

	@Override
	public String getHelpToCommand() {
		return "Test for args :\n type CommTestArgs args to show this args parsed";
	}

	@Override
	public String getShortDesc() {
		return " tests for command args";
	}

}
