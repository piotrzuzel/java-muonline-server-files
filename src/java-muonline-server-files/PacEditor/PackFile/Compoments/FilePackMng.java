/*
 * FilePackMng.java
 *
 * Created on 14 grudzieñ 2007, 21:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package PacEditor.PackFile.Compoments;

import java.util.ArrayList;

/**
 *
 * @author Miki
 */
public class FilePackMng {
    private ArrayList _PacketsAll;
   
    
    /** Creates a new instance of FilePackMng */
    public FilePackMng() {
    }
    
    void Add(PackDocument pack)
    {
        _PacketsAll.add(pack);
    };
}
