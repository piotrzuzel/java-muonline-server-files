package net.sf.jmuserver.gs.database;

//~--- non-JDK imports --------------------------------------------------------

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import net.sf.jmuserver.gs.GameServerConfig;

import com.mchange.v2.c3p0.DataSources;

public class MuDataBaseFactory {
	private static MuDataBaseFactory _instance;
	private String Driver, Url, Login, Password;
	private DataSource _source;
	private Statement _syst;

	public MuDataBaseFactory() {
		try {

			// Properties serverSettings = new Properties();
			// InputStream is =
			// getClass().getResourceAsStream("/data/server.cfg");
			// serverSettings.load(is);
			Driver = "org.postgresql.Driver"; // 
			Url = "jdbc:postgresql://"+GameServerConfig.databse.getProperty("DataBase.Host")+":"+GameServerConfig.databse.getProperty("DataBase.Port")+"/"+GameServerConfig.databse.getProperty("DataBase.Name");
			Login = GameServerConfig.databse.getProperty("DataBase.UserName");
			Password = GameServerConfig.databse.getProperty("DataBase.Password");
			Class.forName(Driver).newInstance();

			final DataSource unpooled = DataSources.unpooledDataSource(Url,
					Login, Password);
			_source = DataSources.pooledDataSource(unpooled);

			// _source =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/mu_online",
			// "root", "mikione");
			// _syst=_source.createStatement();

			/* Test the connection */

			// _source.close();
			_source.getConnection().close();
		} catch (final SQLException e) {
			System.out.println("blad polaczenia do bd1");
			e.printStackTrace();
		} catch (final InstantiationException e) {
			System.out.println("blad polaczenia do bd2");
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			System.out.println("blad polaczenia do bd3");

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			System.out.println("blad polaczenia do bd4");

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static MuDataBaseFactory getInstance() throws SQLException {
		if (_instance == null) {
			_instance = new MuDataBaseFactory();
		}

		return _instance;
	}

	public Connection getConnection() throws SQLException {
		return _source.getConnection();
	}

	public Statement getSyst() {
		return _syst;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
