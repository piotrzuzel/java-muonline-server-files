/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PacEditor.PackFile.Compoments;

/**
 *
 * @author Miki
 */
public class PackCompomentString extends PackCompoment{

    @Override
    public String getDocumentLine() {
        return getTypeName()+" "+ getName()+ "("+getPos()+") -"+getDesc();
    }

    @Override
    public void setSize(int t)
    {
        setSize(t);
    }
    
    public PackCompomentString() {
    
    setTypeName("STRNG");
    }

    @Override
    public boolean ParserQustion(String s) {
        if(s.startsWith(getTypeName()))return true;
        return false;
    }

    @Override
PackCompomentString getInstance() {
      return new  PackCompomentString();
    }

    
    
    

}
