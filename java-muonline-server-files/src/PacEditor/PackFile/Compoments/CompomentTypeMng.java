/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PacEditor.PackFile.Compoments;

import java.util.ArrayList;

/**
 *
 * @author Miki
 */
public abstract class CompomentTypeMng {

    private ArrayList _CompomentTypes;

    public CompomentTypeMng() {
        init();
    }
abstract  void init();
    public void addCopoment(PackCompoment t) {
        _CompomentTypes.add(t);
    }

    public int getCompomentCount() {
        return _CompomentTypes.size();
    }

    public PackCompoment getCompomentFromLine(String Line) {
        for (int t = 0; t < _CompomentTypes.size(); t++) {
            PackCompoment temp = (PackCompoment) _CompomentTypes.get(t);

            if (temp.ParserQustion(Line)) {
                return temp.getInstance();
            }
        }
        return null;
    }
    
}
