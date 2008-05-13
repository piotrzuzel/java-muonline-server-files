/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PacEditor.PackFile.Compoments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

/**
 *
 * @author Miki
 */
public class PackDocument {
    private String _fileName;
    private String _filePath;
    private ArrayList _doc = new ArrayList();
    public void setFileName(String fn) {
        _fileName=fn;
    }
    public void setFilePath(String fp) {
        _filePath = fp;
    }
    /**
     * 
     * @return zwraca nazwe pliku
     */
    public String getFileName() {
        return _fileName;
    }
    public String getFilePAth() {
        return _filePath ;
    }
    
    public void Storge() throws FileNotFoundException, IOException {
        FileWriter fw= new FileWriter(_fileName);
        for (int i =0 ; i<_doc.size();i++)
            fw.write(((PackCompoment)_doc.get(i)).getDocumentLine());
    }
    
    public void AddFromParser(PackParserCompoment t) {
        PackCompoment t1= t.Parse();
    };
    
    public void Restorge() throws FileNotFoundException, IOException {
        LineNumberReader _fileLineReader =
                new LineNumberReader(new BufferedReader(new FileReader(new File(_fileName))));
        _doc.clear();
        for (;;) {
            String Line = _fileLineReader.readLine();
            if (Line == null) break;
            else
                AddFromParser(new PackParserCompoment(Line));
        }
    }
    void AddNew(PackCompoment t) {
        boolean add = _doc.add(t);
    }
    
    public PackCompoment geteElementAt(int pos) {
        return (PackCompoment) _doc.get(pos);
    }
    
    
}
