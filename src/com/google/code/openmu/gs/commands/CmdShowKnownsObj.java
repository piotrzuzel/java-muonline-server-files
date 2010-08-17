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

import java.util.ArrayList;
import java.util.List;

import com.google.code.openmu.gs.muObjects.MuObject;
import com.google.code.openmu.gs.muObjects.MuPcInstance;


/**
 * 
 * @author Miki i Linka
 */
public class CmdShowKnownsObj extends GsBaseCommand {

	private MuPcInstance _pcInstance;
	private final List knowns = new ArrayList();

	@Override
	public boolean RunCommand() {
//		_pcInstance = _cli.getActiveChar();
//		System.out.println("List of knowns Obj " + _pcInstance);
//		knowns.addAll(_pcInstance.oldgetKnownObjects().values());
//		for (int i = 0; i < knowns.size(); i++) {
//			final MuObject object = (MuObject) knowns.get(i);
//			System.out.println("|-List of knowns Obj " + object);
//			final List knownsKnowned = new ArrayList(object
//					.oldgetKnownObjects().values());
//			for (int j = 0; j < knownsKnowned.size(); j++) {
//				final MuObject object1 = (MuObject) knownsKnowned.get(j);
//				System.out.println("|--List of knowns Obj " + object1);
//			}
//
//		}
		return true;
	}

	@Override
	public String getCmdString() {
		return "ShowKnowns";
	}

	@Override
	public String getHelpToCommand() {
		return "Show all Ids kown andknownsthats ids";
	}

	@Override
	public String getShortDesc() {
		return "show all knownobjects";
	}

}
