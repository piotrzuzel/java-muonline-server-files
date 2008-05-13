/*
 * ConnectedList.java
 *
 * Created on 27 listopad 2007, 08:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package swing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractListModel;
import net.sf.jmuserver.gs.ClientThread;

/**
 *
 * @author Miki
 */
public class ConnectedList extends AbstractListModel{
    private ArrayList conectedList;
    private ArrayList adressList;
    
    public ConnectedList() {
        conectedList=new ArrayList ();
        adressList=new ArrayList ();
    
    }
    
    public void add(ClientThread t,String id)
    {
        conectedList.add(t);
        adressList.add(id);
    
        fireIntervalAdded(this,1,0);
    }
    
    public void remove(int t)
    {
        
    }
    
    public int getSize() {  
        return conectedList.size();
    
    }

    public Object getElementAt(int index) {
    ClientThread t = (ClientThread) conectedList.get(index);
    return ""+t.getIdConnection();
    }
    
    
}
