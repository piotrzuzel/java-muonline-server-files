package tools;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Toolstest {
	public static final int _DEC=1;
	public static final int _DES=2;
	public static final int _HEX=3;
	public static final int _STR=4;
	public static final int _UNK=-1;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
	String _line="dec,1012,20,";
	StringTokenizer st = new StringTokenizer(_line,",");
	Toolstest ta=new Toolstest();
	int type=ta.getType(st.nextToken());

	  String t1=st.nextToken();
	  switch (type)
	  {
	  case Toolstest._DEC:break;
	  case Toolstest._DES:break;
	  case Toolstest._HEX:break;
	  case Toolstest._STR:break;
	 }	
	}
	
	public int getType(String token)
	{
		int ret=_UNK;
		if (token.compareToIgnoreCase("dec")==0)ret=_DEC;
		else if(token.compareToIgnoreCase("des")==0) ret=_DES;
		else if(token.compareToIgnoreCase("str")==0) ret =_STR;
		else if (token.compareToIgnoreCase("hex")==0) ret =_HEX;
	    return ret;
	}
	

}
