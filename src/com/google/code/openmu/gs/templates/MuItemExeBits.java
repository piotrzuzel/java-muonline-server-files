/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.openmu.gs.templates;

/**
 * interface for exe options bits 4 items
 * 
 * @author Miki
 */
public interface MuItemExeBits {

	int EXEOPT1 = 0x01; // 00000001b
	int EXEOPT2 = 0x02; // 00000010b
	int EXEOPT3 = 0x04; // 00000100b
	int EXEOPT4 = 0x08;// 00001000b
	int EXEOPT5 = 0x10; // 00010000b
	int EXEOPT6 = 0x20; // 00100000b
	int IT_p16 = 0x40; // 01000000b
	int IT_LONGID = 0x80; // 10000000b
	int IT_EXE_BIT = 3;

	boolean isExeOpt1();

	boolean isExeOpt2();

	boolean isExeOpt3();

	boolean isExeOpt4();

	boolean isExeOpt5();

	boolean isExeOpt6();

	boolean isOpt_p16();

	boolean isLongId();

	void setExeOpt(byte ExcellentOption);

	void setOpt_p16();

	void setLongId();
}
