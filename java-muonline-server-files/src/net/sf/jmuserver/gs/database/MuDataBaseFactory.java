package net.sf.jmuserver.gs.database;

//~--- non-JDK imports --------------------------------------------------------

import com.mchange.v2.c3p0.DataSources;

//~--- JDK imports ------------------------------------------------------------

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class MuDataBaseFactory {
    private static MuDataBaseFactory _instance;
    private String                   Driver, Url, Login, Password;
    private DataSource               _source;
    private Statement                _syst;

    public MuDataBaseFactory() {
        try {

            // Properties serverSettings = new Properties();
            // InputStream is = getClass().getResourceAsStream("/data/server.cfg");
            // serverSettings.load(is);
            Driver = "org.postgresql.Driver";    // "com.mysql.jdbc.Driver";//serverSettings.getProperty("db.driver");
            Url    = "jdbc:postgresql:mu_online";    // "jdbc:mysql://localhost:3306/mu_online";//serverSettings.getProperty("db.url");
            Login    = "root";         // serverSettings.getProperty("db.user");
            Password = "michalki1";    // serverSettings.getProperty("db.pass");
            Class.forName(Driver).newInstance();

            DataSource unpooled = DataSources.unpooledDataSource(Url, Login, Password);

            _source = DataSources.pooledDataSource(unpooled);

            // _source = DriverManager.getConnection("jdbc:mysql://localhost:3306/mu_online", "root", "mikione");
            // _syst=_source.createStatement();

            /* Test the connection */

            // _source.close();
            _source.getConnection().close();
        } catch (SQLException e) {
            System.out.println("blad polaczenia do bd1");
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("blad polaczenia do bd2");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("blad polaczenia do bd3");

            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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


//~ Formatted by Jindent --- http://www.jindent.com
