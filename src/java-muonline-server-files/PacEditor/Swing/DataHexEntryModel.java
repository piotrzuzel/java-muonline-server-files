/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PacEditor.Swing;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Miki
 */
class DataHexEntryModel extends DefaultTableModel {

    @Override
    public String getColumnName(int column) {
        return Integer.toString(column);
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
    return 3;
    }

    @Override
    public Object getValueAt(int row, int column) {
       return "test" + row + column;
    }
    
    public DataHexEntryModel() {
    }
}
