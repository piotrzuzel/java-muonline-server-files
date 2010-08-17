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

package com.google.code.openmu.gs.MuTask;

/**
 * 
 * @author Miki i Linka
 */
public class MuTimeJob implements Comparable<MuTimeJob> {
	private final MuTimeTicket _ticket;
	private final MuJob _job;

	public MuTimeJob(MuJob _job, int s) {
		this._ticket = new MuTimeTicket(s);
		this._job = _job;
	}

	public MuTimeTicket getTicket() {
		return _ticket;
	}

	public boolean Compare(MuTimeJob a, MuTimeJob b) {
		return (a.getTicket().getTime()) < (b.getTicket().getTime());
	}

	public void Run() {
		_job.Run();
	}

	@Override
	public int compareTo(MuTimeJob o) {
		if (getTicket().getTime() == o.getTicket().getTime()) {
			return 0;
		}
		if (Compare(o, this)) {
			return 1;
		} else {
			return -1;
		}
	}

}
