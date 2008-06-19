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

    public class BasicStat {

        public int _agi;
        public int _str;
        public int _vit;
        public int _ene;
        public int _com;
    }
    //user id in database
    private int _userId = 0;
    //connection to database
    java.sql.Connection con = null;

    public MuCharacterListDB(int UserIdInDatabase) {
        _userId = UserIdInDatabase;
        try {
            con = MuDataBaseFactory.getInstance().getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MuCharacterListDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * get the template stats fo new character
     * @param classCode 
     * @return
     */
    private BasicStat getBaseStats(byte classCode) {
        BasicStat t = new BasicStat();
        try {



            PreparedStatement statement = con.prepareStatement("SELECT *  from chatacter_base_stats where ch_base_class =" + classCode);

            ResultSet rset = statement.executeQuery();
            if (!rset.next()) {
                System.out.println("Wrong CharCode");
                return null; //  

            }
            t._agi = rset.getInt("ch_base_agi");
            t._str = rset.getInt("ch_base_str");
            t._vit = rset.getInt("ch_base_vit");
            t._ene = rset.getInt("ch_base_enr");
            t._com = rset.getInt("ch_base_com");


        } catch (SQLException ex) {
            Logger.getLogger(MuCharacterListDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }

    /**
     * check avalible nick
     * @param name
     * @return
     */
    private boolean itsNickAvailble(String name) {
        boolean success = false;
        try {

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

    /**
     * getspaces availbele
     * @return
     */
    private int getSpaceAvailbele() {
        int spaces = 0;
        try {

            PreparedStatement statement = con.prepareStatement("SELECT u_ch_c FROM users where u_id =" + _userId);
            ResultSet rset = statement.executeQuery();
            rset.next();
            spaces = rset.getInt("u_ch_c");


        } catch (SQLException ex) {
            Logger.getLogger(MuCharacterListDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return spaces;
    }

    /**
     * added new character todatabase 
     * @param name
     * @param classCode
     * @return true when done
     */
    public boolean addNewCharacter(String name, byte classCode) {
        boolean success = false;
        PreparedStatement statement;
        //firs check aailible name
        if (itsNickAvailble(name)) {
            System.out.println("nick availble");
            int spaces = getSpaceAvailbele();
            if (spaces < 5) {
                System.out.println("spaces availble");
                BasicStat s = getBaseStats(classCode);
                if (s != null) {
                    System.out.println("template stats availble");
                    try {
                        statement = con.prepareStatement("INSERT INTO characters(" + "us_id" + ", ch_name" + ", ch_class" + ", ch_stat_lvl" + ", ch_stat_str" + ", ch_stat_agi" + ", ch_stat_vit" + ", ch_stat_enr," + " ch_stat_com," + " ch_last_pos_map," + " ch_last_pos_h, " + " ch_exp_act, " + "ch_exp_lp, " + "ch_last_pos_x, ch_last_pos_y)    VALUES (?, ?, ?, ?, ?, ?,             ?, ?, ?, ?, ?,             ?, ?, ?, ?);");
                        statement.setInt(1, _userId);
                        statement.setString(2, name);
                        statement.setInt(3, classCode);
                        statement.setInt(4, 1);//lvl
                        statement.setInt(5, s._str);
                        statement.setInt(6, s._agi);
                        statement.setInt(7, s._vit);
                        statement.setInt(8, s._ene);
                        statement.setInt(9, s._com);
                        statement.setInt(10, 0); //lorencia
                        statement.setInt(11, 0);//flag
                        statement.setInt(12, 0);//exp
                        statement.setInt(13, 0);//lp
                        statement.setInt(14, 128);//x pos
                        statement.setInt(15, 128);//y pos
                        ResultSet rset = statement.executeQuery();
                       
                            System.out.println("character added to table");
                            statement=con.prepareStatement("UPDATE users SET  u_ch_c=? WHERE u_id=?");
                                    statement.setInt(1, _userId);
                                    statement.setInt(2, spaces+1);
                                    ResultSet rset1 = statement.executeQuery();
                                   
                                        success=true;
                                   
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(MuCharacterListDB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return success;
    }
}
