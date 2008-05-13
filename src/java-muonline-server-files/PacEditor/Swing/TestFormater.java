/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PacEditor.Swing;

import java.text.ParseException;
import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 *
 * @author Miki
 */
class TestFormater extends AbstractFormatter{

    public TestFormater() {
    }

    @Override
    public Object stringToValue(String text) throws ParseException {
        System.out.println(text);
        
        return 1;
        
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        System.out.println(value);
        return "obj";
    }

}
