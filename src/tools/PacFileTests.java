package tools;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PacFileTests {
	private PacFile pac;

	public PacFileTests() {
		try {
			pac = new PacFile("test.pac");
		} catch (final FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test1() {
		try {
			pac.getContent();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean teststr2hex() {
		final boolean t = true;// nieudany
		if (10 != pac.hex2int("0a")) {
			System.out.println("0a nieudane");
		}
		;
		if (15 != pac.hex2int("0F")) {
			System.out.println("0F nieudane");
		}
		;
		if (16 != pac.hex2int("10")) {
			System.out.println("10 nieudane");
		}
		;
		if (255 != pac.hex2int("fF")) {
			System.out.println("Ff neudane");
		}

		return t;
	}

	public static void main(String[] args) {
		final PacFileTests test = new PacFileTests();
		System.out.println(test.teststr2hex());
		test.test1();
	}
}
