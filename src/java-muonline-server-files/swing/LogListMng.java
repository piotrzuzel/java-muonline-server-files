/*
 * LogListMng.java
 *
 * Created on 24 listopad 2007, 13:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package swing;

import java.util.ArrayList;
import javax.swing.AbstractListModel;


/**
 *
 * @author Miki
 */
public class LogListMng extends AbstractListModel{
    
    public void add(String co)
    {
        t.add(co);
        fireIntervalAdded(this,t.size(),0);
    }
    ArrayList t = new ArrayList();
    /** Creates a new instance of LogListMng */
    public LogListMng() {
    }

    public int getSize() {
        return t.size();
    }

    public Object getElementAt(int index) {
        return t.get(index);
    }

}
