/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.openmu.gs.database;

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

	// user id in database
	private int _userId = 0;
	// connection to database
	java.sql.Connection con = null;

	public MuCharacterListDB(int UserIdInDatabase) {
		_userId = UserIdInDatabase;
		try {
			con = MuDataBaseFactory.getInstance().getConnection();
		} catch (final SQLException ex) {
			Logger.getLogger(MuCharacterListDB.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	/**
	 * get the template stats fo new character
	 * 
	 * @param classCode
	 * @return
	 */
	private BasicStat getBaseStats(int classCode) {
		final BasicStat t = new BasicStat();
		try {
			final PreparedStatement statement = con
					.prepareStatement("SELECT *  from chatacter_base_stats where ch_base_class ="
							+ classCode);

			final ResultSet rset = statement.executeQuery();
			if (!rset.next()) {
				System.out.println("Wrong CharCode " + classCode);
				return null; //
			}
			t._agi = rset.getInt("ch_base_agi");
			t._str = rset.getInt("ch_base_str");
			t._vit = rset.getInt("ch_base_vit");
			t._ene = rset.getInt("ch_base_enr");
			t._com = rset.getInt("ch_base_com");

			rset.close();
			statement.close();

		} catch (final SQLException ex) {
			Logger.getLogger(MuCharacterListDB.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return t;
	}

	/**
	 * check avalible nick
	 * 
	 * @param name
	 * @return
	 */
	private boolean isNickAvailable(String name) {
		boolean success = false;
		try {

			final PreparedStatement statement = con
					.prepareStatement("select*  from " + MuCharactersDb.CH_TAB
							+ " where " + MuCharactersDb.CH_NAME + " = '"
							+ name + "' ");
			final ResultSet rset = statement.executeQuery();
			try {
				success = !rset.next();
			} catch (final SQLException e) {
				System.out.println("SQL Error: " + e.getMessage());
			}
			rset.close();
			statement.close();

		} catch (final SQLException ex) {
			Logger.getLogger(MuCharacterListDB.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return success;
	}

	/**
	 * getspaces availbele
	 * 
	 * @return
	 */
	private int getAvailableSpace() {
		int spaces = 0;
		try {

			final PreparedStatement statement = con
					.prepareStatement("SELECT u_ch_c FROM users where u_id ="
							+ _userId);
			final ResultSet rset = statement.executeQuery();
			rset.next();
			spaces = rset.getInt("u_ch_c");
			rset.close();
			statement.close();

		} catch (final SQLException ex) {
			Logger.getLogger(MuCharacterListDB.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return spaces;
	}

	/**
	 * added new character todatabase
	 * 
	 * @param name
	 * @param classCode
	 * @return true when done
	 */
	public boolean addNewCharacter(String name, int classCode) {
		boolean success = false;
		PreparedStatement statement;
		// firs check aailible name
		if (isNickAvailable(name)) {
			final int spaces = getAvailableSpace();
			if (spaces < 5) {
				final BasicStat _basicStats = getBaseStats(classCode);
				if (_basicStats != null) {
					try {
						statement = con
								.prepareStatement("INSERT INTO characters("
										+ "us_id"
										+ ", ch_name"
										+ ", ch_class"
										+ ", ch_stat_lvl"
										+ ", ch_stat_str"
										+ ", ch_stat_agi"
										+ ", ch_stat_vit"
										+ ", ch_stat_enr,"
										+ " ch_stat_com,"
										+ " ch_last_pos_map,"
										+ " ch_last_pos_h, "
										+ " ch_exp_act, "
										+ "ch_exp_lp, "
										+ "ch_last_pos_x, ch_last_pos_y)    VALUES (?, ?, ?, ?, ?, ?,             ?, ?, ?, ?, ?,             ?, ?, ?, ?);");
						statement.setInt(1, _userId);
						statement.setString(2, name);
						statement.setInt(3, classCode);
						statement.setInt(4, 1);// lvl
						statement.setInt(5, _basicStats._str);
						statement.setInt(6, _basicStats._agi);
						statement.setInt(7, _basicStats._vit);
						statement.setInt(8, _basicStats._ene);
						statement.setInt(9, _basicStats._com);
						statement.setInt(10, 0); // lorencia
						statement.setInt(11, 0);// flag
						statement.setInt(12, 0);// exp
						statement.setInt(13, 0);// lp
						statement.setInt(14, 125);// x pos
						statement.setInt(15, 125);// y pos
						if (statement.executeUpdate() == 1) {
							statement.close();
							statement = con
									.prepareStatement("UPDATE users SET  u_ch_c= ? WHERE u_id= ? ");
							statement.setInt(2, _userId);
							statement.setInt(1, spaces + 1);
							if (statement.executeUpdate() == 1) {
								success = true;
							}
						}
						statement.close();
					} catch (final SQLException ex) {
						Logger.getLogger(MuCharacterListDB.class.getName())
								.log(Level.SEVERE, null, ex);
					}
				}
			}
		}
		return success;
	}

	/**
	 * removing character from Database
	 * 
	 * @param name
	 * @return true when done
	 */
	public boolean removeCharacterFromDB(String name) {
		boolean success = false;
		try {

			final PreparedStatement statement1 = con
					.prepareStatement("DELETE FROM characters WHERE ch_name = ? and us_id= ?");
			statement1.setString(1, name);
			statement1.setInt(2, _userId);
			if (statement1.executeUpdate() == 1) {
				final int spaces = getAvailableSpace();
				final PreparedStatement statement2 = con
						.prepareStatement("UPDATE users SET  u_ch_c= ? WHERE u_id= ? ");
				statement2.setInt(2, _userId);
				statement2.setInt(1, spaces - 1);
				if (statement2.executeUpdate() == 1) {
					success = true;
				}
				statement2.close();
			}
			statement1.close();
		} catch (final SQLException ex) {
			Logger.getLogger(MuCharacterListDB.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return success;
	}
}
