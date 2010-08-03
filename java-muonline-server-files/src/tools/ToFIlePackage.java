package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;



public class ToFIlePackage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Program do przerabiana hex'ow na inty do pliku *pac");
	File file = new java.io.File("test.pac");
	try {
		LineNumberReader lnr = new LineNumberReader(new BufferedReader( new FileReader(file)));
		lnr.readLine();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}

}
