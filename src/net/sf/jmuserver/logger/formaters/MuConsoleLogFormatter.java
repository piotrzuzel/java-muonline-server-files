/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.logger.formaters;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author mikiones
 */
public class MuConsoleLogFormatter extends Formatter{

    @Override
    public String format(LogRecord record) {
       String entry = "";
       entry+="[" + DateFormat.getTimeInstance().format(new Date(record.getMillis())) +"]"; //[date]
       entry+=record.getLevel().getName().substring(0,3)+":";
       entry+=record.getLoggerName()+"|>";//[who/what]
       entry+=record.getMessage() + " \n";
       return entry;
    }

}
