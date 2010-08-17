/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.openmu.gs;

import java.io.IOException;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.code.openmu.gs.database.MuCharacterListDB;
import com.google.code.openmu.gs.database.MuCharactersDb;
import com.google.code.openmu.gs.database.MuDataBaseFactory;
import com.google.code.openmu.gs.muObjects.MuCharacterBase;
import com.google.code.openmu.gs.muObjects.MuCharacterInventory;
import com.google.code.openmu.gs.muObjects.MuCharacterList;
import com.google.code.openmu.gs.muObjects.MuCharacterWear;
import com.google.code.openmu.gs.muObjects.MuClientSettings;
import com.google.code.openmu.gs.muObjects.MuNpcInstance;
import com.google.code.openmu.gs.muObjects.MuPcInstance;
import com.google.code.openmu.gs.muObjects.MuUser;
import com.google.code.openmu.gs.muObjects.MuWorld;
import com.google.code.openmu.gs.serverPackets.SHello;


/**
 * This class To generaly replesent all interface from connection to clases in
 * server small core
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
	// list of charactwers
	private final MuCharacterList ChList = new MuCharacterList();
	// settings actual played character
	private MuClientSettings _clientSettings = null;
	/**
	 * world
	 */
	private final MuWorld _world;
	/**
	 * Actual Took with NPC
	 */
	private MuNpcInstance _activeNpc = null;

	/**
	 * set active npc
	 * 
	 * @param i
	 */
	public void setActiveNpc(MuNpcInstance i) {
		_activeNpc = i;
	}

	/**
	 * 
	 * @return theloggger to use
	 */
	public Logger getLogger() {
		return Logger.getLogger("CleintTHread@ID:"
				+ Integer.toString(getIdConnection()));
	}

	/**
	 * get active npc if null then no be eny yet and until we dont click to npc
	 * then be last one as active
	 * 
	 * @return active bpc if null there nobe jet any
	 */
	public MuNpcInstance getActiveNpc() {
		return _activeNpc;
	}

	private final byte[] _cryptkey = { (byte) 0x94, (byte) 0x35, (byte) 0x00,
			(byte) 0x00, (byte) 0xa1, (byte) 0x6c, (byte) 0x54, (byte) 0x87 // these
	};
	private final MuConnection _connection;
	private final PacketHandler _handler;

	/**
	 * constructor
	 * 
	 * @param client
	 *            Socket to connect to client
	 * @throws java.io.IOException
	 */
	public ClientThread(Socket client) throws IOException {

		_connection = new MuConnection(client, _cryptkey);
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
	 * return Connection to client If null then lost it
	 * 
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
	 * get user data
	 * 
	 * @return
	 */
	public MuUser getUser() {
		return user;
	}

	/**
	 * Read Character ListFrom Database
	 * 
	 * @Isuase 1 read wear set bits as well
	 * @isuase 2 move to database section
	 * @throws java.io.IOException
	 */
	public void readCharacterList() throws IOException {

		final int ilosc_p = user.getCh_c();
		final int id = user.getId();

		java.sql.Connection con = null;

		try {

			con = MuDataBaseFactory.getInstance().getConnection();
			final PreparedStatement statement = con
					.prepareStatement("select * from " + MuCharactersDb.CH_TAB
							+ " where " + MuCharactersDb.US_ID + " =?");
			statement.setInt(1, id);
			final ResultSet rset = statement.executeQuery();
			for (int i = 0; i < ilosc_p; i++) {
				if (rset.next()) {
					ChList.addNew(new MuCharacterBase(rset.getString(
							MuCharactersDb.CH_NAME).trim(), rset
							.getInt(MuCharactersDb.CH_LVL), rset
							.getInt(MuCharactersDb.CH_CLASS), i,
							new MuCharacterWear()));
				}
			}
			con.close();
		} catch (final SQLException e) {
			getLogger().log(Level.WARNING,
					"I'm cannot load data about character:" + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (final Exception e1) {
			}
		}
		ChList.noNeedRead();
	}

	/**
	 * Read specifed userdata from database
	 * 
	 * @Isusae 1 Move to database section
	 * @param name
	 *            of user login
	 */
	public void readUser(String name) {

		java.sql.Connection con = null;

		try {
			con = MuDataBaseFactory.getInstance().getConnection();
			final PreparedStatement statement = con
					.prepareStatement("select * from users where u_user=?");
			statement.setString(1, name);
			final ResultSet rset = statement.executeQuery();
			rset.next();
			user = new MuUser(rset.getInt("u_id"), rset.getString("u_user"),
					rset.getString("u_pass"), rset.getInt("u_flag"),
					rset.getInt("u_ch_c"), rset.getString("u_vol_code"));
			con.close();
		} catch (final SQLException e) {
			getLogger().log(
					Level.WARNING,
					"I'm cannot get data about user " + name + " :"
							+ e.getMessage());
		} finally {
			try {
				con.close();
			} catch (final Exception e1) {
			}
		}

	}

	@Override
	public void run() {

		final IdFactory _id = IdFactory.getInstance();
		_idConection = _id.newId();
		getLogger().log(Level.INFO, "Client Thread Started ...");

		try {
			_connection.sendPacket(new SHello(_idConection, "09928"));
			boolean _while = true;
			while (_while) {

				// System.out.println("czekam na odpowiedz");
				final byte[] decrypt = _connection.getPacket();
				if (decrypt != null) {
					_handler.handlePacket(decrypt);
				} else {
					_while = false;
				}
				// System.out.println("odebralem");

			}
		} catch (final IOException ex) {
			getLogger().log(Level.SEVERE, null, ex);
		} catch (final Throwable ex) {
			getLogger().log(Level.SEVERE, null, ex);

		} finally {
			try {

				try {
				} catch (final Exception e2) {
					// ignore any problems here
				}

				// _connection.close();
			} catch (final Exception e1) {
				getLogger().log(Level.WARNING, e1.getMessage());
			} finally {

				try {
					if (_activeChar != null) // this should only happen on
					// connection loss
					{
						// notify the world about our disconnect
						_activeChar.deleteMe();

						try {
							saveCharToDataBase(_activeChar);
						} catch (final Exception e2) {
							// ignore any problems here
						}
					}

					_connection.close();
				} catch (final Exception e1) {
				} finally {
					// remove the account
				}
			}
			// remove the account
		}
		IdFactory.getInstance().deleteId(_idConection);
		getLogger().log(Level.INFO, "gameserver thread[C] stopped");
	}

	public int getIdConnection() {
		return _idConection;
	}

	/**
	 * save all things after close connection or change character
	 * 
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
	 * 
	 * @ISuase1 Critical Need implementation
	 * @param char1
	 */
	private void storeChar(MuPcInstance char1) {
		getLogger().log(Level.INFO, "Character data stored in database");

	}

	/**
	 * @author Marcel Stores a new character in database.
	 * @param user_id
	 * @param char_name
	 * @param char_class
	 * @return boolean
	 */
	public boolean storeNewChar(int id, String name, int clas) {
		final MuCharacterListDB cdb = new MuCharacterListDB(id);
		return cdb.addNewCharacter(name, clas);
	}

	/**
	 * set login name
	 * 
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		_loginName = loginName;
	}

	/**
	 * @todo add restore character wear look
	 * @Isase 1 Move to database section restore character from Db using name of
	 *        character
	 * @param name
	 *            of character to restore
	 * @return new pcinstane obiect
	 */
	private MuPcInstance restoreChar(String name) {
		final MuPcInstance oldChar = new MuPcInstance();
		oldChar.setNetConnection(getConnection());
		try {
			java.sql.Connection con = null;
			con = MuDataBaseFactory.getInstance().getConnection();
			final PreparedStatement statement = con
					.prepareStatement("select*  from " + MuCharactersDb.CH_TAB
							+ " where " + MuCharactersDb.CH_NAME + " = '"
							+ name + "' ");
			final ResultSet rset = statement.executeQuery();
			while (rset.next()) {
				// oldChar.setDbId(rset.getInt("ch_id"));
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
				// domekind of read muweat
				oldChar.SetWearLook(new MuCharacterWear());
			}
			System.out.println("x" + oldChar.getX() + "y" + oldChar.getY());
			oldChar.setCurrentWorldRegion(MuWorld.getInstance().getMap(
					oldChar.getM()));
			// MuWorld.getInstance().storeObject(oldChar);
			rset.close();
			statement.close();
			con.close();
		} catch (final Exception e) {
			getLogger().log(
					Level.WARNING,
					"Cannot restore charatre data from database!!"
							+ e.getMessage());
			e.printStackTrace();
		}
		getLogger().log(Level.INFO,
				"Character restored from database succesfuly");
		return oldChar;
	}

	/**
	 * Get all things-need-to-play from database
	 * 
	 * @isuase 1 what when selected name is not in user login character list
	 *         -ToCHeck
	 * @param selected
	 *            name of selected character
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
			getLogger()
					.log(Level.WARNING,
							"Cannot restore " + selected
									+ " character from database!!");

		}

		// setCharacter(character);
		return character;
	}

	/**
	 * Restore Rerhause from DB
	 * 
	 * @isuase should be after get charater list Warehause is thissame for all
	 *         character !!
	 * @Isuase Critic: Implementation
	 * @param character
	 */
	private void restoreWarehouse(MuPcInstance character) {
		// TODO Auto-generated method stub
	}

	/**
	 * Resttorre settings from Database
	 * 
	 * @Isuase 1 Now get default value Must to get data from DB
	 * @param character
	 */
	private void restoreShortCuts(MuPcInstance character) {
		_clientSettings = new MuClientSettings();
		_clientSettings.LoadDefault();

	}

	/**
	 * Restore Skills List from DB
	 * 
	 * @ISUASE 1 CRITIC: Imlementation
	 * @param character
	 */
	private void restoreSkills(MuPcInstance character) {
		// TODO Auto-generated method stub
	}

	/**
	 * Restore Inwentory List from DB
	 * 
	 * @ISUASE 1 CRITIC: Implementation
	 * @param character
	 */
	private void restoreInventory(MuPcInstance character) {
		final MuCharacterInventory inw = new MuCharacterInventory();
		// inw.storeItem(new MuStoreableItem(inw.InventoryWindow, 1, new
		// MuItemHex().MakeItem(1, 1, 20, 1, 1, 1, false, false)));
		character.set_inventory(inw);

	}

	/**
	 * Set active character
	 * 
	 * @param cha
	 *            character chose in character list to play
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

			// _world.storeObject(_activeChar);
		}
	}

	/**
	 * return atual played character
	 * 
	 * @return
	 */
	public MuPcInstance getActiveChar() {
		return _activeChar;
	}

	/**
	 * get client settings actual played character
	 * 
	 * @return
	 */
	public MuClientSettings getClientSettings() {

		return _clientSettings;
	}

	/**
	 * Store Client Settings after logout or change character or also HardDc
	 * 
	 * @ISUASE 1 CRITIC: Implementation
	 */
	public void storeClientSettingsInDb() {

		getLogger().log(Level.INFO, "Client Settings saved in DB!");

	}
}
