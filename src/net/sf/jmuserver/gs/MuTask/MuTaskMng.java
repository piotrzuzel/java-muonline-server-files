/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.MuTask;

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
