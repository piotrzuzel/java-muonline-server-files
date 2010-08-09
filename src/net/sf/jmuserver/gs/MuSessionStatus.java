package net.sf.jmuserver.gs;

public interface MuSessionStatus {
	/**
	 * Client connected no yet authorysed
	 */
	public final byte STAGE_AUTCHORIING = 0x00;
	/**
	 * Client authorsed = chose the characted
	 */
	public final byte STAGE_CHARACTER_CHOSING = 0x01;
	/**
	 * client chosed character and play game
	 */
	public final byte STAGE_INGAME = 0x02;

}
