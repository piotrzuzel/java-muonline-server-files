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

import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Miki i Linka
 */
public class MuTaskMng extends Thread {

	PriorityQueue<MuTimeJob> jobs;
	private static MuTaskMng _instance = null;

	private MuTaskMng() {
		jobs = new PriorityQueue<MuTimeJob>();// (11,new MuTimeJobComparator());
	}

	public static MuTaskMng getInstance() {
		if (_instance == null) {
			_instance = new MuTaskMng();
			_instance.start();
		}
		return _instance;
	}

	/**
	 * add job to quene
	 * 
	 * @param j
	 *            job
	 * @param time
	 *            time for how manysec job is to run
	 */
	public void addJob(MuJob j, int time) {
		jobs.offer(new MuTimeJob(j, time));
	}

	@Override
	public void run() {
		for (;;) {
			if (!jobs.isEmpty()) {
				while ((!jobs.isEmpty())
						&& (jobs.peek().getTicket().TicketEnd())) {
					jobs.peek().Run();
					jobs.poll();
				}
			}
			try {
				sleep(100);
			} catch (final InterruptedException ex) {
				Logger.getLogger(MuTaskMng.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}
}
