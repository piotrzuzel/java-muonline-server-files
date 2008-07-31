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
import net.sf.jmuserver.gs.database.MuCharacterListDB;
import net.sf.jmuserver.gs.muObjects.MuCharacterBase;
import net.sf.jmuserver.gs.muObjects.MuCharacterInventory;
import net.sf.jmuserver.gs.muObjects.MuCharacterList;
import net.sf.jmuserver.gs.muObjects.MuCharacterWear;
import net.sf.jmuserver.gs.muObjects.MuClientSettings;
import net.sf.jmuserver.gs.muObjects.MuDepo;
import net.sf.jmuserver.gs.muObjects.MuInventory;
import net.sf.jmuserver.gs.muObjects.MuStoreableItem;
import net.sf.jmuserver.gs.muObjects.MuItemStats;
import net.sf.jmuserver.gs.muObjects.MuNpcInstance;
import net.sf.jmuserver.gs.muObjects.MuPcInstance;
import net.sf.jmuserver.gs.muObjects.MuSkillList;
import net.sf.jmuserver.gs.muObjects.MuUser;
import net.sf.jmuserver.gs.muObjects.MuWorld;
import net.sf.jmuserver.gs.serverPackage.SHello;
import net.sf.jmuserver.gs.templates.MuItemHex;

/**
 * This class To generaly replesent all interface from connection to clases in server
 * small core
 * 
 * @author Mikione
 */
public class ClientThread extends Thread {

    /**
     * User and login data
     */
    private String _loginName;
    private MuUser user = null;
    // propably never used
    private int _idConection;
    /**
     * character datas
     */
    // stats
    private MuPcInstance _activeChar;
    //list of charactwers
    private MuCharacterList ChList = new MuCharacterList();
    //settings actual played character
    private MuClientSettings _clientSettings = null;
    //skilllist actual played character
    private MuSkillList _SkillList = null;
    //depo of loged user
    private MuDepo _depo = null;
    /**
     * world 
     */
    private MuWorld _world;
    private int _sessionId;
    /**
     * Actual Took with NPC
     */
    private MuNpcInstance _activeNpc = null;

    /**
     * set active npc
     * @param i
     */
    public void setActiveNpc(MuNpcInstance i) {
        _activeNpc = i;
    }

    /**
     * get active npc
     * if null then no be eny yet and until we dont 
     * click to npc then be last one as active
     * @return active bpc if null there nobe jet any 
     */
    public MuNpcInstance getActiveNpc() {
        return _activeNpc;
    }
    private byte[] _cryptkey = {(byte) 0x94, (byte) 0x35, (byte) 0x00,
        (byte) 0x00, (byte) 0xa1, (byte) 0x6c, (byte) 0x54, (byte) 0x87 // these
    };
    private MuConnection _connection;
    private PacketHandler _handler;

    /**
     * constructor
     * @param client Socket to  connect to client
     * @throws java.io.IOException 
     */
    public ClientThread(Socket client) throws IOException {

        _connection = new MuConnection(client, _cryptkey);
        _sessionId = 0x12345678;
        _handler = new PacketHandler(this);
        _world = MuWorld.getInstance();

        start();
    }

    /**
     * 
     * @return character list of login user
     */
    public MuCharacterList getChList() {
        return ChList;
    }

    /**
     * return Connection to client
     * If null then lost it 
     * @return
     */
    public MuConnection getConnection() {
        return _connection;
    }

    /**
     * 
     * @return login name 
     */
    public String getLoginName() {
        return _loginName;
    }

    /**
     * Sesion Id?
     * @ISUASE 1 propably never used
     * @return
     */
    public int getSessionId() {
        return _sessionId;
    }

    /**
     * get user data
     * @return
     */
    public MuUser getUser() {
        return user;
    }

  

    /**
     * Read Character ListFrom Database 
     * @Isuase 1 read wear set bits as well 
     * @isuase 2 move to database section
     * @throws java.io.IOException
     */
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
                            rset.getString(MuCharactersDb.CH_NAME).trim(),
                            rset.getInt(MuCharactersDb.CH_LVL),
                            rset.getInt(MuCharactersDb.CH_CLASS), i, new MuCharacterWear()));
                }
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("i'm connt load data about character  :" + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e1) {
            }
        }
        ChList.noNeedRead();
    }

    /**
     * Read specifed userdata from database
     * @Isusae 1 Move to database section
     * @param name of user login
     */
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
                    rset.getInt("u_ch_c"),
                    rset.getString("u_vol_code"));
            con.close();
        } catch (SQLException e) {
            System.out.println("I'm connt get data aboutuser " + name + " :" + e.getMessage());
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

    /**
     * save all things after close connection or change character
     * @Isuasse 1 storge inwentory, skills, settings
     * @param char1
     */
    private void saveCharToDataBase(MuPcInstance char1) {
        storeChar(char1);
    // storeInventory(cha);
    // storeSkills(cha);
    // storeShortcuts(cha);
    // storeWarehouse(cha);
    // IdFactory.getInstance().saveCurrentState();

    }

    /**
     * Save Character Stats in DAtabase
     * @ISuase1 Critical Need implementation
     * @param char1
     */
    private void storeChar(MuPcInstance char1) {
        System.out.println("Character saved in DB");

    }

    /**
     * @author Marcel
     * Stores a new character in database.
     * @param user_id
     * @param char_name
     * @param char_class
     * @return boolean
     */
    public boolean storeNewChar(int id, String name, int clas) {
        MuCharacterListDB cdb = new MuCharacterListDB(id);
        return cdb.addNewCharacter(name, clas);
    }

    /**
     * set login name
     * @param loginName
     */
    public void setLoginName(String loginName) {
        _loginName = loginName;
    }

    /**
     * @todo add restore character wear look
     * @Isase 1 Move to database section
     * restore character from Db using name of character
     * @param name of character to restore
     * @return new pcinstane obiect
     */
    private MuPcInstance restoreChar(String name) {
        MuPcInstance oldChar = new MuPcInstance();
        oldChar.setNetConnection(getConnection());
        try {
            java.sql.Connection con = null;
            con = MuDataBaseFactory.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement("select*  from " +
                    MuCharactersDb.CH_TAB + " where " +
                    MuCharactersDb.CH_NAME + " = '" + name + "' ");
            ResultSet rset = statement.executeQuery();
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
                oldChar.setX(rset.getInt(MuCharactersDb.CH_POS_X));
                oldChar.setY(rset.getInt(MuCharactersDb.CH_POS_Y));
                oldChar.setObiectId((short) _idConection);
                oldChar.SetHpMpSp();
                //domekind of read muweat
                oldChar.SetWearLook(new MuCharacterWear());
            }
            System.out.println("x" + oldChar.getX() + "y" + oldChar.getY());
            oldChar.setCurrentWorldRegion(MuWorld.getInstance().getRegion(
                    oldChar.getM()));
            // MuWorld.getInstance().storeObject(oldChar);
            rset.close();
            statement.close();
            con.close();
        } catch (Exception e) {
            System.out.println("could not restore char data:" + e);
            e.printStackTrace();
        }

        return oldChar;
    }

    /**
     * Get all things-need-to-play from database 
     * @isuase 1 what when selected name is not in user login character list -ToCHeck
     * @param selected name of selected character
     * @return
     */
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

    /**
     * Restore Rerhause from DB 
     * @isuase should be after get charater list Warehause is thissame for all character !!
     * @Isuase Critic: Implementation 
     * @param character 
     */
    private void restoreWarehouse(MuPcInstance character) {
        // TODO Auto-generated method stub
    }

    /**
     * Resttorre settings from Database
     * @Isuase 1 Now get default value Must to get data from DB
     * @param character
     */
    private void restoreShortCuts(MuPcInstance character) {
        _clientSettings = new MuClientSettings();
        _clientSettings.LoadDefault();

    }

    /**
     * Restore Skills List from DB
     * @ISUASE 1 CRITIC: Imlementation
     * @param character
     */
    private void restoreSkills(MuPcInstance character) {
        // TODO Auto-generated method stub
    }

    /**
     * Restore Inwentory List from DB
     * @ISUASE 1 CRITIC: Implementation
     * @param character
     */
    private void restoreInventory(MuPcInstance character) {
        MuCharacterInventory inw= new MuCharacterInventory(); 
        inw.storeItem(new MuStoreableItem(inw.InventoryWindow, 1, new MuItemHex().MakeItem(1, 1, 20, 1, 1, 1, false, false)));
        character.set_inventory(inw);
         
       


    }

    /**
     * Set active character 
     * @param cha character chose in character list to play
     */
    public void setActiveChar(MuPcInstance cha) {
        _activeChar = cha;
        if (cha != null) {
            // we store the connection in the player object so that external
            // events can directly send events to the players client
            // might be changed later to use a central event management and
            // distribution system
            _activeChar.setNetConnection(_connection);

        // update world data

        //_world.storeObject(_activeChar);
        }
    }

    /**
     * return atual played character 
     * @return
     */
    public MuPcInstance getActiveChar() {
        return _activeChar;
    }

    /**
     * get client settings actual played character
     * @return
     */
    public MuClientSettings getClientSettings() {

        return _clientSettings;
    }

    /**
     * Store Client Settings after logout or change character or also HardDc
     * @ISUASE 1 CRITIC: Implementation
     */
    public void storeClientSettingsInDb() {
        System.out.println("Client Settings saved in DB!");

    }
}
