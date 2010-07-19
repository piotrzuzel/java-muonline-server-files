/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GsCommand;

import net.sf.jmuserver.gs.muObiects.MuInventory;
import net.sf.jmuserver.gs.muObiects.MuStoreableItem;
import net.sf.jmuserver.gs.muObiects.MuItemStats;
import net.sf.jmuserver.gs.serverPackage.SPutItemInInventory;
import net.sf.jmuserver.gs.serverPackage.SServerMsg;
import net.sf.jmuserver.gs.templates.MuItemHex;

/**
 * Command used for creating new items in characters inventory.<br>
 * Format: \MakeItem GroupIndex Index Dur Lvl Opt ExcOpt Luck Skill<br>
 * The item is placed on the first available position in inventory, if
 * that exists.
 * @author Marcel
 */
public class CmdMake extends GsBaseCommand{
    private MuItemHex _itemHex;
    private MuItemStats _itemStats;
    private MuStoreableItem _item;
    private boolean _run;
     
    @Override
    public boolean RunCommand() {
        if (!_run)
            return false;
        _itemStats = MuItemStats.getItemStats(_itemHex.getGroup(), _itemHex.getIndex());
        _item.setItemStats(_itemStats);
        _run = _itemStats != null;
        if (_run) {
            _run = _cli.getActiveChar().getInventory().storeItem(_item);
            _cli.getActiveChar().sendPacket( new SPutItemInInventory(
                    _item.getItemHex().toByteArray(),(byte)_item.getPosition()));
        }
     //   System.out.println(_item);        
        return _run;
    }

    @Override
    public void ParseArgs(String[] args) {
        _run = true;        
        if (args.length != 9) {
            _cli.getActiveChar().sendPacket(new SServerMsg(getHelpToCommand(),
                    SServerMsg.BlueMsg));
            _run = false;
            return;
        }                    
        _itemHex = new MuItemHex();
        _item = new MuStoreableItem(MuInventory.InventoryWindow, (byte)0, _itemHex, _itemStats);        
        
        _itemHex.setGroupAndIndex((byte)(Integer.parseInt(args[1]) & 0x00FF), 
                (byte)(Integer.parseInt(args[2]) & 0x00FF));
        _itemHex.setDurability((byte)(Integer.parseInt(args[3]) & 0x00FF));
        _itemHex.setLvl((byte)(Integer.parseInt(args[4]) & 0x00FF));
        _itemHex.setOption((byte)(Integer.parseInt(args[5]) & 0x00FF));
        _itemHex.setExeOpt((byte)(Integer.parseInt(args[6]) & 0x00FF));
        if (args[7].equalsIgnoreCase("1"))
                _itemHex.setLuck();
        if (args[8].equalsIgnoreCase("1"))
                _itemHex.setSkill();   
    }

    @Override
    public String getCmdString() {
       return "MakeItem";
    }

    @Override
    public String getHelpToCommand() {
       return "Create item in inventory with paramameters: GroupIndex Index Dur Lvl Opt ExcOpt Luck Skill";
    }

    @Override
    public String getShortDesc() {
      return "Create item in inventory.";
    }

}