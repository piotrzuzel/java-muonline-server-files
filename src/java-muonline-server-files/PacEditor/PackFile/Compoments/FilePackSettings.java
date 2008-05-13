/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PacEditor.PackFile.Compoments;

/**
 *
 * @author Miki
 */
public class FilePackSettings {

    private String _globalPath;
    private String _KtoSPath;
    private String _StoKPath;

    public void setGlobalPath(String path) {
        _globalPath = path;
    }

    public void setKtoSPath(String path) {
        _KtoSPath = path;
    }

    public void setStoKPath(String path) {
        _StoKPath = path;
    }

    public String getStoKPath() {
        return _globalPath + "/" + _StoKPath;
    }

    public String getKtoSPath() {
        return _globalPath + "/" + _KtoSPath;
    }
}

