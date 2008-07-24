package net.sf.jmuserver.gs.templates;

/**
 * klasa ma za zadanie danie podsawowegi interfejsu do zazadzaniem kodow
 * hexowych itemow
 * 
 * @author MikiOne, Marcel
 * @example hex itemu: c0 00 16 00 00 <br>
 * 
 */
public class MuItemHex implements MuItemOptBits, MuItemExeBits {

    private byte[] _item = {0,0,0,0,0};
    
    /**
     * @return hex itemu
     */
    public byte[] toByteArray() {
        return _item;
    }

    /**
     * funkcja pobiera hex itemu z denego bufora t i ofsetu of
     * 
     * @param t
     *                bufor zrudlowy
     * @param of
     *                offset w buforze
     */
    public void fromByteArray(byte[] t, int of) {
        System.arraycopy(t, of, _item, 0, 5);

    }

    /**
     * wstawia hex itemu do danego lancucha t na miejsce o ofsecie of
     * 
     * @param t
     *                buffwhere put bits
     * @param of
     *                offset in buffor
     */
    public void intoByteArray(byte[] t, int of) {
        System.arraycopy(_item, 0, t, of, 5);
    }

    /**
     * clean hex items <br>
     * rewrite it by 0x00
     */
    public void clearHexItem() {
        _item[0] = 0;
        _item[1] = 0;
        _item[2] = 0;
        _item[3] = 0;
        _item[4] = 0;
    }

    public byte getGroup() {
        return (byte)((_item[0] >> 4) & 0x0f);
    }
    
    public byte getIndex() {
        return (byte)(_item[0] & 0x0f);
    }
    
    public boolean setGroupAndIndex(byte GroupIndex, byte Index) {
        if ((GroupIndex > 0x0F) || (Index > 0x0F))
            return false;
        byte t = (byte) GroupIndex;
        t = (byte) (t << 4);
        t |= (byte) Index;
        _item[0] = t;
        return true;
    }
       
//    /**
//     * pobiera lvl itemu z hexa
//     */
//    public void lvlFromHex() {
//	_lvl = (_item[2] >> 3) & 0x0f;
//    }
//
//    /**
//     * stawial lvl itemu do hexa
//     */
//    public void lvlIntoHex() {
//	_item[2] = (byte) (_lvl << 3);
//    }

    /**
     * wdurabilaty from hex u
     */
    public byte getDurability() {
        return _item[1];
    }

    /**
     * set durabilaty to hex
     */
    public void setDurability(byte Durability) {
        _item[1] = Durability;
    }

    public int getOption() {
        if ((_item[IT_BIT_OPT] & IT_OPTp4) == 0) {
            return 4;
        }
        if ((_item[IT_BIT_OPT] & IT_OPTp8) == 0) {
            return 8;
        }
        if ((_item[IT_BIT_OPT] & IT_OPTp12) == 0) {
            return 12;
        }
        return 0;
    }

    public int getLvl() {
        return (_item[IT_BIT_OPT] >> 3) & 0x0f;
    }

    public boolean isLuck() {
        return (_item[IT_BIT_OPT] & IT_LUCK) == 0;
    }

    public boolean isSkill() {
        return (_item[IT_BIT_OPT] & IT_SKILL) == 0;
    }

    public void setLuck() {
        _item[IT_BIT_OPT] = (byte) (_item[IT_BIT_OPT] | IT_LUCK);
    }

    public void setSkill() {
        _item[IT_BIT_OPT] = (byte) (_item[IT_BIT_OPT] | IT_SKILL);
    }

    public boolean isExeOpt1() {
        return (_item[IT_EXE_BIT] & EXEOPT1) == 0;
    }

    public boolean isExeOpt2() {
        return (_item[IT_EXE_BIT] & EXEOPT2) == 0;
    }

    public boolean isExeOpt3() {
        return (_item[IT_EXE_BIT] & EXEOPT3) == 0;
    }

    public boolean isExeOpt4() {
        return (_item[IT_EXE_BIT] & EXEOPT4) == 0;
    }

    public boolean isExeOpt5() {
        return (_item[IT_EXE_BIT] & EXEOPT5) == 0;
    }

    public boolean isExeOpt6() {
        return (_item[IT_EXE_BIT] & EXEOPT6) == 0;
    }

    public boolean isOpt_p16() {
        return (_item[IT_EXE_BIT] & IT_p16) == 0;
    }

    public boolean isLongId() {
        return (_item[IT_EXE_BIT] & IT_LONGID) == 0;
    }

    public void setExeOpt(byte ExcellentOption) {
        switch (ExcellentOption) {
            case 1:
                _item[IT_EXE_BIT] = (byte) (_item[IT_EXE_BIT] | EXEOPT1);
                return;
            case 2:
                _item[IT_EXE_BIT] = (byte) (_item[IT_EXE_BIT] | EXEOPT2);
                return;
            case 3:
                _item[IT_EXE_BIT] = (byte) (_item[IT_EXE_BIT] | EXEOPT3);
                return;
            case 4:
                _item[IT_EXE_BIT] = (byte) (_item[IT_EXE_BIT] | EXEOPT4);
                return;
            case 5:
                _item[IT_EXE_BIT] = (byte) (_item[IT_EXE_BIT] | EXEOPT5);
                return;
            case 6:
                _item[IT_EXE_BIT] = (byte) (_item[IT_EXE_BIT] | EXEOPT6);
                return;
            default:
                return;
        }
    }
    
    public void setOpt_p16() {
        _item[IT_EXE_BIT] = (byte) (_item[IT_EXE_BIT] | IT_p16);
    }

    public void setLongId() {
        _item[IT_EXE_BIT] = (byte) (_item[IT_EXE_BIT] | IT_LONGID);
    }

    public void setOption(int opt) {
        switch (opt) {
            case 0:
                return;
            case 4:
                _item[IT_BIT_OPT] = (byte) (_item[IT_BIT_OPT] | IT_OPTp4);
                return;
            case 8:
                _item[IT_BIT_OPT] = (byte) (_item[IT_BIT_OPT] | IT_OPTp8);
                return;
            case 12:
                _item[IT_BIT_OPT] = (byte) (_item[IT_BIT_OPT] | IT_OPTp12);
                return;
            case IT_OPTp4:
                _item[IT_BIT_OPT] = (byte) (_item[IT_BIT_OPT] | IT_OPTp4);
                setOpt_p16();
                return;
            case IT_OPTp8:
                _item[IT_BIT_OPT] = (byte) (_item[IT_BIT_OPT] | IT_OPTp8);
                setOpt_p16();
                return;
            case IT_OPTp12:
                _item[IT_BIT_OPT] = (byte) (_item[IT_BIT_OPT] | IT_OPTp12);
                setOpt_p16();
                return;

        }
        throw new WrongOptionInItemToSetOnBitOpt("Zla opcja podana");
    }

    public void setLvl(int lvl) {
        _item[IT_BIT_OPT] |= (lvl << 3);
    }
}
