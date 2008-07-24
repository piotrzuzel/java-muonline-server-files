/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GsCommand;

import net.sf.jmuserver.gs.muObjects.MuInventory;
import net.sf.jmuserver.gs.muObjects.MuInventoryItem;
import net.sf.jmuserver.gs.muObjects.MuItem;
import net.sf.jmuserver.gs.serverPackage.SPutItemInInventory;
import net.sf.jmuserver.gs.serverPackage.SServerMsg;
import net.sf.jmuserver.gs.templates.MuItemHex;

/**
 *
 * @author Marcel
 */
public class CmdMake extends GsBaseCommand{
    private MuItemHex _itemHex;
    private MuItem _itemStats;
    private MuInventoryItem _item;
    
    public CmdMake() {
        _itemHex = new MuItemHex();
        _item = new MuInventoryItem(MuInventory.InventoryWindow, (byte)0, _itemHex, _itemStats);
    }
    
    @Override
    public boolean RunCommand() {
        System.out.println(_cli);
        System.out.println(_cli.getActiveChar());
        _itemStats = MuItem.getItemStats(_itemHex.getGroup(), _itemHex.getIndex());        
        _item.setItemStats(_itemStats);
        //boolean result = _itemStats != null
        boolean result = true;
        if (result) 
            _cli.getActiveChar().getInventory().storeItem(_item);
        _cli.getActiveChar().sendPacket(new SPutItemInInventory(_item, result));        
        System.out.println(getCmdString()+" command executed!");
        return true;
    }

    @Override
    public void ParseArgs(String[] args) {
        if (args.length != 9) {
            _cli.getActiveChar().sendPacket(new SServerMsg(getHelpToCommand(),
                    SServerMsg.BlueMsg));
            return;
        }            
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
        System.out.println(_item.toByteArray());
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