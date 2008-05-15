package net.sf.jmuserver.gs;

import java.io.IOException;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jmuserver.gs.database.MuCharactersDb;
import net.sf.jmuserver.gs.database.MuDataBaseFactory;
import net.sf.jmuserver.gs.muObjects.MuCharacterBase;
import net.sf.jmuserver.gs.muObjects.MuCharacterList;
import net.sf.jmuserver.gs.muObjects.MuClientSettings;
import net.sf.jmuserver.gs.muObjects.MuDepo;
import net.sf.jmuserver.gs.muObjects.MuInwentory;
import net.sf.jmuserver.gs.muObjects.MuPcInstance;
import net.sf.jmuserver.gs.muObjects.MuSkillList;
import net.sf.jmuserver.gs.muObjects.MuUser;
import net.sf.jmuserver.gs.muObjects.MuWorld;
import net.sf.jmuserver.gs.serverPackage.SHello;


/**
 * This class ...
 * 
 * @version $Revision: 1.21 $ $Date: 2004/11/19 08:54:43 $
 */
public class ClientThread extends Thread {

    private String _loginName;
    private MuUser user = null;
    private int _idConection;
    private MuPcInstance _activeChar;
    private MuCharacterList ChList = new MuCharacterList();
    private MuInwentory _inwentory = null;
    private MuClientSettings _clientSettings = null;
    private final MuSkillList _SkillList = null;
    private final MuDepo _depo = null;
    private MuWorld _world;
    private int _sessionId;
    private byte[] _cryptkey = {(byte) 0x94, (byte) 0x35, (byte) 0x00,
        (byte) 0x00, (byte) 0xa1, (byte) 0x6c, (byte) 0x54, (byte) 0x87 // these
    };
    private MuConnection _connection;
    private PacketHandler _handler;

    public ClientThread(Socket client) throws IOException {
        
        _connection = new MuConnection(client, _cryptkey);
        _sessionId = 0x12345678;
        _handler = new PacketHandler(this);
        _world = MuWorld.getInstance();

        start();
    }

    public MuCharacterList getChList() {
        return ChList;
    }

    public MuConnection getConnection() {
        return _connection;
    }

    public String getLoginName() {
        return _loginName;
    }

    public int getSessionId() {
        return _sessionId;
    }

    public MuUser getUser() {
        return user;
    }

    public MuInwentory getInwentory() {
        return _inwentory;
    }

    public void readCharacterList() throws IOException {

        int ilosc_p = user.getCh_c();
        int id = user.getId();

        boolean result = true;
        java.sql.Connection con = null;

        try {

            con = MuDataBaseFactory.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement("select * from " +
                    MuCharactersDb.CH_TAB + " where " +
                    MuCharactersDb.US_ID + " =?");
            statement.setInt(1, id);
            ResultSet rset = statement.executeQuery();
            for (int i = 0; i < ilosc_p; i++) {
                if (rset.next()) {
                    ChList.addNew(new MuCharacterBase(
                            rset.getString(MuCharactersDb.CH_NAME),
                            rset.getInt(MuCharactersDb.CH_LVL),
                            rset.getInt(MuCharactersDb.CH_CLASS), i + 1));
                }
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Nie moge pobrac danych o postaci  :" + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e1) {
            }
        }
        ChList.noNeedRead();
    }

    public void readUser(String name) {

        boolean result = true;
        java.sql.Connection con = null;

        try {
            con = MuDataBaseFactory.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement("select * from users where u_user=?");
            statement.setString(1, name);
            ResultSet rset = statement.executeQuery();
            result = rset.next();
            user = new MuUser(
                    rset.getInt("u_id"),
                    rset.getString("u_user"),
                    rset.getString("u_pass"),
                    rset.getInt("u_flag"),
                    rset.getInt("u_ch_c"));
            con.close();
        } catch (SQLException e) {
            System.out.println("Nie moge pobrac danych o userze " + name + " :" + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e1) {
            }
        }

    }
    

    @SuppressWarnings("empty-statement")
    public void run() {

        IdFactory _id = IdFactory.getInstance();
        _idConection = _id.newId();
        //System.out.println
      
        ;
        try {
            _connection.sendPacket(new SHello(_idConection, "09928"));
            boolean _while = true;
            while (_while) {

                // System.out.println("czekam na odpowiedz");
                byte[] decrypt = _connection.getPacket();
                if (decrypt != null) {
                    _handler.handlePacket(decrypt);
                } else {
                    _while = false;
                }
            // System.out.println("odebralem");

            }
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {

                try {

                } catch (Exception e2) {
                // ignore any problems here
                }

            // _connection.close();
            } catch (Exception e1) {
                System.out.println(e1.toString());
            } finally {

                try {
                    if (_activeChar != null) // this should only happen on
                    // connection loss
                    {
                        // notify the world about our disconnect
                        _activeChar.deleteMe();

                        try {
                            saveCharToDataBase(_activeChar);
                        } catch (Exception e2) {
                        // ignore any problems here
                        }
                    }

                    _connection.close();
                } catch (Exception e1) {

                } finally {
                // remove the account

                }
            }
        // remove the account
        }
        IdFactory.getInstance().deleteId(_idConection);
        System.out.println("gameserver thread[C] stopped");
    }

    public int getIdConnection() {
        return _idConection;
    }

    private void saveCharToDataBase(MuPcInstance char1) {
        storeChar(char1);
    // storeInventory(cha);
    // storeSkills(cha);
    // storeShortcuts(cha);
    // storeWarehouse(cha);
    // IdFactory.getInstance().saveCurrentState();

    }

    private void storeChar(MuPcInstance char1) {
        System.out.println("Postac zapisana w BD");

    }

    public void setLoginName(String loginName) {
        _loginName = loginName;
    }

    private MuPcInstance restoreChar(String name) {
        MuPcInstance oldChar = new MuPcInstance();
        try {
            java.sql.Connection con = null;
            con = MuDataBaseFactory.getInstance().getConnection();
            System.out.println("przed ap " + name);
            PreparedStatement statement = con.prepareStatement("select*  from " +
                    MuCharactersDb.CH_TAB + " where " +
                    MuCharactersDb.CH_NAME + " = '" + name + "' ");

            System.out.println("po zap");
            ResultSet rset = statement.executeQuery();
            System.out.println("po zap");
            while (rset.next()) {
                //oldChar.setDbId(rset.getInt("ch_id"));
                oldChar.setLvl(rset.getInt(MuCharactersDb.CH_LP));
                oldChar.setName(rset.getString(MuCharactersDb.CH_NAME));
                oldChar.setCom(rset.getInt(MuCharactersDb.CH_COM));
                oldChar.setStr(rset.getInt(MuCharactersDb.CH_STR));
                oldChar.setAgi(rset.getInt(MuCharactersDb.CH_AGI));
                oldChar.setVit(rset.getInt(MuCharactersDb.CH_VIT));
                oldChar.setEne(rset.getInt(MuCharactersDb.CH_ENR));
                oldChar.setLP(rset.getInt(MuCharactersDb.CH_LP));
                oldChar.setClas(rset.getInt(MuCharactersDb.CH_CLASS));
                oldChar.setM((byte) rset.getInt(MuCharactersDb.CH_POS_M));
                oldChar.setX( rset.getInt(MuCharactersDb.CH_POS_X));
                oldChar.setY(rset.getInt(MuCharactersDb.CH_POS_Y));
                oldChar.setObiectId((short) _idConection);
                oldChar.SetHpMpSp();
            }
 System.out.println("x" + oldChar.getX()+"y"+oldChar.getY());
            oldChar.setCurrentWorldRegion(MuWorld.getInstance().getRegion(
                    oldChar.getM()));
            MuWorld.getInstance().storeObject(oldChar);
            rset.close();
            statement.close();
            con.close();
        } catch (Exception e) {
            System.out.println("could not restore char data:" + e);
            e.printStackTrace();
        }

        return oldChar;
    }

    public MuPcInstance loadCharFromDisk(String selected) {
        MuPcInstance character = new MuPcInstance();
        character.setNetConnection(_connection);

        character = restoreChar(selected);

        if (character != null) {
            restoreInventory(character);
            restoreSkills(character);
            restoreShortCuts(character);
            restoreWarehouse(character);
        // if (character.getClanId() != 0)
        } else {
            System.out.println("could not restore :" + selected);
        }

        // setCharacter(character);
        return character;
    }

    private void restoreWarehouse(MuPcInstance character) {
    // TODO Auto-generated method stub

    }

    private void restoreShortCuts(MuPcInstance character) {
        _clientSettings = new MuClientSettings();
        _clientSettings.LoadDefault();

    }

    private void restoreSkills(MuPcInstance character) {
    // TODO Auto-generated method stub

    }

    private void restoreInventory(MuPcInstance character) {

        _inwentory = new MuInwentory();


    }

    public void setActiveChar(MuPcInstance cha) {
        _activeChar = cha;
        if (cha != null) {
            // we store the connection in the player object so that external
            // events can directly send events to the players client
            // might be changed later to use a central event management and
            // distribution system
            _activeChar.setNetConnection(_connection);

            // update world data
            _world.storeObject(_activeChar);
        }
    }

    public MuPcInstance getActiveChar() {
        return _activeChar;
    }

    public MuClientSettings getClientSettings() {

        return _clientSettings;
    }

    public void storeClientSettingsInDb() {
        System.out.println("Ustawienia Clienta zapisane w BD!");

    }
}
