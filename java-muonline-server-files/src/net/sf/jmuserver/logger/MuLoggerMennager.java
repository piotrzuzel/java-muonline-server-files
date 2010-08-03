/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author mikiones
 */
public class MuLoggerMennager {

    private MuLoggerMennager() {
        menager= LogManager.getLogManager();
        try {
            
            FileInputStream fis = new FileInputStream("MuLog.conf");
            menager.readConfiguration(fis);
        //LogManager.getLogManager().readConfiguration()
            fis.close();
        } catch (IOException ex) {
            getLogger("MuLoggerMenager").log(Level.SEVERE, "Cannot find/read configuration file", ex);
        } catch (SecurityException ex) {
            getLogger("MuLoggerMenager").log(Level.SEVERE, "Cannot aces to configuatio file", ex);
        }

    }
    public LogManager menager = null;
    protected static MuLoggerMennager _instance;

    public static MuLoggerMennager getInstance() {
        if (_instance == null) {
            _instance = new MuLoggerMennager();
        }
        return _instance;
    }

    public static Logger getLogger(String name) {
        if (_instance==null)_instance =new MuLoggerMennager();
        return Logger.getLogger(name);
    }
}


