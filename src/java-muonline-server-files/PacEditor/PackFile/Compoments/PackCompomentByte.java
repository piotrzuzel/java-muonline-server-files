/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PacEditor.PackFile.Compoments;

/**
 *
 * @author Miki
 */
public class PackCompomentByte extends PackCompoment{

    public PackCompomentByte() {
        setSize(1);
        setTypeName("BYTE");
    }

    @Override
    public String getDocumentLine() {
    return getTypeName()+getPosString()+ " "+getName() +getDescString();

    }

    @Override
    public boolean ParserQustion(String s) {
        if (s.startsWith(getTypeName())) return true;
        return false;
    }

    @Override
    PackCompomentByte getInstance() {
        return new PackCompomentByte();
    }

    
    

   

  
    

}
