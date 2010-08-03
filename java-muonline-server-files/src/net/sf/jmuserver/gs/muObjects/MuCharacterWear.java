/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.muObjects;

/**
 *
 * unsigned short hands;	// items in hands (1byte left hand | 1byte right hand) formula of ID: item's BYTE = (ItemSection << 5) | (ItemId)
 * 
 *	// default 0xFFFFFF
 *	// {	// see item glow levels
 *	unsigned char helm_armor;	// 4bits helm type | 4bits armor type
 *	unsigned char pants_gloves;	// 4bits pants type | 4bits gloves type
 *	unsigned char boots_wings_animal;	// 4bits boots type | 2bits (first level wings) (if not equipped: 11) | 2bits (first level animal) (if not equipped: 11)
 *	// }
 *
 *	// default 0x000000
 *	unsigned char glow_levels[3];
 *
 *	// default 0xF8 (11111000)
 *	// see item list byte description
 *	unsigned char armortopbits_wingslevel2;	// ~(5 top bits of (helm|armor|pants|gloves|boots)) | 3bits second level wings
 *
 *	// default 0x00
 *	unsigned char violeteglowing;		//violete glowing
 *
 *	// default 0x00
 *	unsigned char blueglowing;	// blue glowing
 *
 *	// default 0x00
 *	unsigned char additional;	// dark horse & flag with dragons
 *};
 *
 *additional
 *00000001 - dark horse
 *00000010 - flag with dragons
 *00000100 - nothing
 *00001000 - nothing
 *00010000 - nothing
 *00100000 - nothing
 *01000000 - nothing
 *10000000 - nothing*
 *
 *violete glowing
 *00000001 - (equipped DINORANT) :)))))))) (KOREICI PROSTO CHERTI)
 *00000010 - right hand glows
 *00000100 - left hand glows
 *00001000 - boots glows
 *00010000 - gloves glows
 *00100000 - pants glows
 *01000000 - armor glows
 *10000000 - helm glows
 *blue glowing
 *00000001 - unknown
 *00000010 - unknown
 *00000100 - unknown
 *00001000 - boots
 *00010000 - gloves
 *00100000 - pants
 *01000000 - armor
 *10000000 - helm*
 * item glow levels
 *Weapon-1:
 *+0 +2		00000000 	00000000 	00000000	(0x000000)
 *+3 +4		00000000 	00000000 	00000001	(0x000001)
 *+5 +6		00000000 	00000000 	00000010	(0x000002)
 *+7		00000000 	00000000 	00000011	(0x000003)
 *+8		00000000 	00000000 	00000100	(0x000004)
 *+9		00000000 	00000000 	00000101	(0x000005)
 *+10		00000000 	00000000 	00000110	(0x000006)
 *+11		00000000 	00000000 	00000111	(0x000007)*
 *Weapon-2:
 *+0 +2		00000000 	00000000 	00000000	(0x000000)
 *+3 +4		00000000 	00000000 	00001000	(0x000008)
 *+5 +6		00000000 	00000000 	00010000	(0x000010)
 *+7		00000000 	00000000 	00011000	(0x000018)*
 *+8		00000000 	00000000 	00100000	(0x000020)
 *+9		00000000 	00000000 	00101000	(0x000028)
 *+10		00000000 	00000000 	00110000	(0x000030)
 *+11		00000000 	00000000 	00111000	(0x000038)*
 *Helm:
 *+0 +2		00000000 	00000000 	00000000	(0x000000)
 *+3 +4		00000000 	00000000 	01000000	(0x000040)
 *+5 +6		00000000 	00000000 	10000000	(0x000080)
 *+7		00000000 	00000000 	11000000	(0x0000C0)
 *+8		00000000 	00000001 	00000000	(0x000100)
 *+9		00000000 	00000001 	01000000	(0x000140)
 *+10		00000000 	00000001 	10000000	(0x000180)
 *+11		00000000 	00000001 	11000000	(0x0001C0)*
 *Armor:
 *+0		00000000 	00000000 	00000000	(0x000000)
 *+1 +2		00000000 	00000001 	00000000	(0x000100)
 *+3 +4		00000000 	00000010 	00000000	(0x000200)
 *+5 +6		00000000 	00000100 	00000000	(0x000400)
 *+7		00000000 	00000110 	00000000	(0x000600)
 *+8		00000000 	00001000 	00000000	(0x000800)
 *+9		00000000 	00001010 	00000000	(0x000A00)
 *+10		00000000 	00001100 	00000000	(0x000C00)
 *+11		00000000 	00001110 	00000000	(0x000E00)*
 *Pants:
 *+0 +2		00000000 	00000000 	00000000	(0x000000)
 *+3 +4		00000000 	00010000 	00000000	(0x001000)
 *+5 +6		00000000 	00100000 	00000000	(0x002000)
 *+7		00000000 	00110000 	00000000	(0x003000)
 *+8		00000000 	01000000 	00000000	(0x004000)
 *+9		00000000 	01010000 	00000000	(0x005000)
 *+10		00000000 	01100000 	00000000	(0x006000)
 *+11		00000000 	01110000 	00000000	(0x007000)*
 *Gloves:
 *+0 +2		00000000 	00000000 	00000000	(0x000000)
 *+3 +4		00000000 	10000000 	00000000	(0x008000)
 *+5 +6		00000001 	00000000 	00000000	(0x010000)
 *+7		00000001 	10000000 	00000000	(0x018000)
 *+8		00000010 	00000000 	00000000	(0x020000)
 *+9		00000010 	10000000 	00000000	(0x028000)
 *+10		00000011 	00000000 	00000000	(0x030000)
 *+11		00000011 	10000000 	00000000	(0x038000)
 *Boots:
 *+0 +2		00000000 	00000000 	00000000	(0x000000)
 *+3 +4		00000100 	00000000 	00000000	(0x040000)
 *+5 +6		00001000 	00000000 	00000000	(0x080000)
 *+7		00001100 	00000000 	00000000	(0x0C0000)
 *+8		00010000 	00000000 	00000000	(0x100000)
 *+9		00010100 	00000000 	00000000	(0x140000)
 *+10		00011000 	00000000 	00000000	(0x180000)
 *+11		00011100 	00000000 	00000000	(0x1C0000)*
 * first level wing type | first level animal type
 *{
 * animal type
 *00 - g. angel
 *01 - satan
 *10 - horn
 *11 - nothing equipped
 * wing type
 *00 - fairy wings
 *01 - angel wings
 *10 - satan wings
 *11 - nothing equipped
 *}
 * second level wings
 *001 - butterfly wings
 *010 - archangel wings
 *011 - devil wings
 *100 - magic gladiator wings
 *101 - dark lord cape (vidno tolko na dark lorde)
 *110 - kakoi-to sinij orb na spine (NULL pohodu)
 *111 - kakoi-to sinij orb na spine (NULL pohodu)
 * @author Miki i Linka
 */
public class MuCharacterWear {

    private byte[] _wear = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xF8, (byte) 0x00, (byte) 0x00, (byte) 0x00};

    public byte[] getBytes() {
        return _wear;
    }
}
