package net.sf.jmuserver.gs.muObjects;

public class MuMapSpot {
	@SuppressWarnings("unused")
	private byte _startX;	//start x
	@SuppressWarnings("unused")
	private byte _stratY;	//y
	@SuppressWarnings("unused")
	private byte _endX;
	@SuppressWarnings("unused")
	private byte _endY;
	@SuppressWarnings("unused")
	private short _monster;// rasa
	@SuppressWarnings("unused")
	private int _c;			//ilosc rasy na spocie

	@SuppressWarnings("unused")
	private String _name;	//nazwa spota
	
	 MuMapSpot(String name,byte  startX,byte startY,byte endX ,byte endY,short monster,int c)
	 {
		 _name=name;
		 _startX=startX;
		 _stratY=startY;
		 _endX=endX;
		 _endY=endY;
		 _monster=monster;
		 _c=c;
	 }
	 
}
