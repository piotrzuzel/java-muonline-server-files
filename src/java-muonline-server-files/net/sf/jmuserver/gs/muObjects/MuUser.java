package net.sf.jmuserver.gs.muObjects;

public class MuUser {

    /**
     *
     */
    private String user;
    private String pass;
    private int id;
    private int ch_c;
    int flag;

    public MuUser() {
        user = "";
        pass = "";
        flag = 0;
    }

    public MuUser(int u_id, String us, String pa, int f, int u_ch_c) {
        id = u_id;
        ch_c = u_ch_c;
        user = us;
        flag = f;
        pass = pa;
    }

    /**
     * @return the ch_c
     */
    public int getCh_c() {
        return ch_c;
    }

    public int getFlag() {
        return flag;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    public String getPass() {
        return pass;
    }

    public String getUser() {
        return user;
    }
}
