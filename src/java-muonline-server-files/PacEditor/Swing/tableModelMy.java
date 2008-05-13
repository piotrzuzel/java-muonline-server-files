/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PacEditor.Swing;

import PacEditor.Definitations.HexPacket;
import java.util.ArrayList;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import net.sf.jmuserver.gs.clientPackage.ClientBasePacket;

/**
 *
 * @author Miki
 */
public class tableModelMy extends DefaultTableModel {
    private ArrayList _dane=new ArrayList();
    private static final int _HEX = 0;
    private static final int _DEC = 1;
    private static final int _STR = 2;
    private boolean InsertData=false;

    private HexPacket _packet = new HexPacket(new byte[]{0x00, 0x00});
    
   public tableModelMy() {
//       addTableModelListener( );
       super();
       InsertData=true;
    }
   
    public tableModelMy(HexPacket t) {
        super();
        this._packet = t;
        System.out.println("construktor");

    }

    

    @Override
    public String getColumnName(int col) {
        return ""+col;
    }

    @Override
    public int getRowCount() {
        Document t = new Document() {

            @Override
            public int getLength() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void addDocumentListener(DocumentListener listener) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void removeDocumentListener(DocumentListener listener) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void addUndoableEditListener(UndoableEditListener listener) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void removeUndoableEditListener(UndoableEditListener listener) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Object getProperty(Object key) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void putProperty(Object key, Object value) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getText(int offset, int length) throws BadLocationException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void getText(int offset, int length, Segment txt) throws BadLocationException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Position getStartPosition() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Position getEndPosition() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Position createPosition(int offs) throws BadLocationException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Element[] getRootElements() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Element getDefaultRootElement() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void render(Runnable r) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            
            
        };
        return 3;
    }

    @Override
    public int getColumnCount() {

        if (_dane == null)_dane=new ArrayList();
        
        return _dane.size()+1;    
        
    }

    @Override
    public Object getValueAt(int row, int col) {
       // if (InsertData)return "";
        if (col == 0) {
            {
                switch (row) {
                    case _DEC:
                        return "DEc:";
                    case _HEX:
                        return "Hex:";
                    case _STR:
                        return "Str:";
                }

            }
        } else {
            switch (row) {
                case _DEC:
                    return ConvertToInt()[col - 1];
                case _HEX:
                    return ConvertToHex()[col - 1];
                case _STR:
                    return ConvertToStr().charAt(col - 1);
                default:
                    return "";
            }
        }
 return "";
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        super.setValueAt(aValue, row, column);
        if(column==getColumnCount());
    }

    void setData(HexPacket _pac) {
        _packet = _pac;
    }
    
    public HexPacket getData()
    {
        return _packet;
    }
    //public void setValueAt(Object value, int row, int col) {
    //    rowData[row][col] = value;
    //    fireTableCellUpdated(row, col);
    //  }
    
     public String[] ConvertToHex() {
        String[] _ret = new String[_dane.size()];
        for (int t = 0; t < _dane.size(); t++) {
            byte s=(Byte)(_dane.get(t));
            int b = s & 0xff;
            String _ret1 = Integer.toHexString(b).toUpperCase();
            for (int i = _ret1.length(); i < 2; i++) {
                _ret1 = "0" + _ret1;
            }
            _ret[t] = _ret1;
        }
        return _ret;
    }
  
    public String[] ConvertToInt() {
        String[] _ret =new String[_dane.size()];;
        for (int t = 0; t < _dane.size(); t++) {
            String _ret1 = ""+((int) (Byte)_dane.get(t) & 0xff);
            for (int i = _ret1.length(); i < 3; i++) _ret1 = "0" + _ret1;
		
            _ret[t]=_ret1;

        }
        return _ret;
    }
    public String ConvertToStr() {
        StringBuffer _ret = new StringBuffer();
        for (int a = 0; a < _dane.size(); a++) {
            int t1 = (Byte)_dane.get(a);
            if (t1 > 0x1f && t1 < 0x80) {
                _ret.append((char) t1);
            } else {
                _ret.append('.');
            }
        }

        return _ret.toString();
    }
    
}

