/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PacEditor.PackFile.Compoments;

/**
 *
 * @author Miki
 */
abstract class PackCompoment {

    private int _pos;
    private int _size;
    private String _name;
    private String _descrypton;
    private String _typeName;

    abstract public String getDocumentLine();

    public String getTypeName() {
        return _typeName;
    }

    protected void setTypeName(String s) {
        _typeName = s;
    }

    protected void setSize(int s) {
        _size = s;
    }

    public int getSize() {
        return _size;
    }

    public int getPos() {
        return _pos;
    }

    public void setPos(int p) {
        _pos = p;
    }

    public String getName() {
        return _name;
    }

    public String getDesc() {
        return _descrypton;
    }

    abstract public boolean ParserQustion(String s);

    protected String getPosString() {
        return "(" + getPos() + ")";
    }

    protected String getDescString() {
        return "; //" + getDesc();
    }

    public void setName(String n) {
        _name = n;
    }

    public void setDesc(String d) {
        _descrypton = d;
    }
    abstract PackCompoment getInstance();
    public void set(PackCompoment t)
    {
        _descrypton=t.getDesc();
        _name=t.getName();
        _pos=t.getPos();
        _size=t.getSize();
        _typeName=t.getTypeName();
    }
}
