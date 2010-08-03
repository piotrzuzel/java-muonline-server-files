/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.MuTask;

/**
 *
 * @author Miki i Linka
 */
public class MuTimeJob implements Comparable<MuTimeJob> {
    private MuTimeTicket _ticket;
    private MuJob _job;

    public MuTimeJob(MuJob _job,int s) {
        this._ticket = new MuTimeTicket(s);
        this._job = _job;
    }
    public MuTimeTicket getTicket()
    {
        return _ticket;
    }
    public boolean Compare(MuTimeJob a,MuTimeJob b)
    {
        return (a.getTicket().getTime()) < (b.getTicket().getTime());
    }
    public void Run()
    {
        _job.Run();
    }

    public int compareTo(MuTimeJob o) {
        if(getTicket().getTime()==o.getTicket().getTime())return 0;
        if(Compare(o, this))return 1;
        else return -1;
    }

 
    

}
