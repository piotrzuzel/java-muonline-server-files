/*
 * ProtocolFile.java
 *
 * Created on 6 grudzie  2007, 12:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */



package PacEditor;

//~--- JDK imports ------------------------------------------------------------

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import java.util.ArrayList;

/**
 *
 * @author Miki
 */
public class ProtocolFile {
    private ArrayList<String> _LineArray;

    /** Creates a new instance of ProtocolFile */
    public ProtocolFile(String file_name) throws IOException {
        try {
            String           _temp;
            LineNumberReader _fileLineReader =
                new LineNumberReader(new BufferedReader(new FileReader(new File(file_name))));

            for (;;) {
                _temp = _fileLineReader.readLine();

                if (_temp == null) {
                    break;
                }

                _LineArray.add(_temp);
            }
            ;
            _fileLineReader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
    }
    public void Open(String fileame) throws IOException {
        _LineArray.clear();

        try {
            String           _temp;
            LineNumberReader _fileLineReader =
                new LineNumberReader(new BufferedReader(new FileReader(new File(fileame))));

            for (;;) {
                _temp = _fileLineReader.readLine();

                if (_temp == null) {
                    break;
                }

                _LineArray.add(_temp);
            }
           _fileLineReader.close();
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
    }
    private int getNumberOfLines() {
        return _LineArray.size();
    }
    
 
}


//~ Formatted by Jindent --- http://www.jindent.com
