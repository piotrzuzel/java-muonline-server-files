package com.google.code.openmu.cs.serverPackets;

public class ServerEntry{
	public String name;
    public String host;
    public int port;
    public byte pos;
    public byte grup;
    public byte load;

    public ServerEntry(String name , String host, int port, byte pos, byte grup, byte load) {
        this.host = host;
        this.name = name;
        this.port = port;
        this.pos = pos;
        this.grup = grup;
        this.load = load;
    }
    @Override
    public String toString() {
    return "SererEntry:"+name+" host:" + host + " on port:" + port +" group:"+ grup + " pos:" +pos;
    }
}