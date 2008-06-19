/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miki i Linka
 */
public class MuCharacterListDB {

    private int _userId = 0;

    public MuCharacterListDB(int UserIdInDatabase) {
        _userId = UserIdInDatabase;
    }

    public boolean itsNickAvailble(String name) {
        boolean success = false;
        try {
            java.sql.Connection con = null;
            con = MuDataBaseFactory.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement("select*  from " + MuCharactersDb.CH_TAB + " where " + MuCharactersDb.CH_NAME + " = '" + name + "' ");
            ResultSet rset = statement.executeQuery();
            try {
                success = !rset.next();
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e.getMessage());
            }
        } catch (SQLException ex) {
            Logger.getLogger(MuCharacterListDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public int SpaceAvailbele() {
        int spaces = 0;
        try {
            java.sql.Connection con = null;
            con = MuDataBaseFactory.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT u_ch_c FROM users where u_id =" + _userId);
            ResultSet rset = statement.executeQuery();
            spaces = 5 - rset.getInt("u_ch_c");


        } catch (SQLException ex) {
            Logger.getLogger(MuCharacterListDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return spaces;
    }

    public boolean addNewCharacter(String name, byte classCode) {
        boolean success = false;
        //firs check aailible name
        if (!itsNickAvailble(name)) {
            int spaces = SpaceAvailbele();
            if (spaces >= 1) {
                
            }
            }
            return success;
        }

    }
