/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PacEditor.Swing;

import java.text.ParseException;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NavigationFilter;

/**
 *
 * @author Miki
 */
public class myFormaterFactory extends AbstractFormatter{
    private MaskFormatter test;

    @Override
    public Object stringToValue(String text) throws ParseException {
        test = new javax.swing.text.MaskFormatter("");
        test.setMask("");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected NavigationFilter getNavigationFilter() {
        NavigationFilter t= new NavigationFilter();
       
        return t;
        //return super.getNavigationFilter();
    }

    public myFormaterFactory() {
    }

}
