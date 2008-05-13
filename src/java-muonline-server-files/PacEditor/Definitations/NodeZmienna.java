/*
 * NodeZmienna.java
 *
 * Created on 6 grudzieñ 2007, 13:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package PacEditor.Definitations;

/**
 *
 * @author Miki
 */
public abstract  class NodeZmienna {
    private int _pos_b;
    private int _pos_e;
    private String _destripsion;
    private String _zmienna; 

    public String get_destripsion() {
        return _destripsion;
    }

    public int get_pos_b() {
        return _pos_b;
    }

    public int get_pos_e() {
        return _pos_e;
    }

    public String get_zmienna() {
        return _zmienna;
    }

    public void set_destripsion(String _destripsion) {
        this._destripsion = _destripsion;
    }

    public void set_pos_b(int _pos_b) {
        this._pos_b = _pos_b;
    }

    public void set_pos_e(int _pos_e) {
        this._pos_e = _pos_e;
    }

    public void set_zmienna(String _zmienna) {
        this._zmienna = _zmienna;
    }

    
    
    public abstract String getLine() ;
    
}
