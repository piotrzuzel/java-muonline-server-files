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
public class CmdStartMove extends GsBaseCommand {

	@Override
	public boolean RunCommand() {
		// MuObject[] t =
		// _cli.getActiveChar().getCurrentWorldRegion().getVisibleObjects();
		// for (MuObject muObject : t) {
		// if(muObject instanceof MuMonsterInstance) {
		// ((MuMonsterInstance)muObject).startRandomWalking();
		// }}
		return true;
	}

	@Override
	public String getCmdString() {
		return "StartMove";
	}

	@Override
	public String getHelpToCommand() {
		return " if \\startmove off then swich off";
	}

	@Override
	public String getShortDesc() {
		return "start move all monsters";
	}

}
