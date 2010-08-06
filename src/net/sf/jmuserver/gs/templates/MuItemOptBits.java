/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.templates;

/**
 * 
 * @author Miki
 */
public interface MuItemOptBits {
	byte IT_NORM = 0x00; // 00000000b
	byte IT_OPTp4 = 0x01; // 00000001b
	byte IT_OPTp8 = 0x02; // 00000010b
	byte IT_OPTp12 = 0x03; // 00000011b
	byte IT_LUCK = 0x04; // 00000100b
	byte IT_LVL1 = 0x08; // 00001000b
							// 00010000b
							// 00100000b
	// 01000000b
	// 01111
	byte IT_SKILL = (byte) 0x80; // 10000000b
	byte IT_BIT_OPT = 1;

	int getOption();

	int getLvl();

	boolean isLuck();

	boolean isSkill();

	void setOption(int opt);

	void setLvl(int lvl);

	void setLuck();

	void setSkill();

}
