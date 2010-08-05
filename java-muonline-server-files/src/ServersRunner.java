/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mikiones
 */
public class ServersRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        GameServer gs = new GameServer();
        FS fs  = new FS();
        gs.start();
        fs.start();
    }

}
