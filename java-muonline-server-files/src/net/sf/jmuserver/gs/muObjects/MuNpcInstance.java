package net.sf.jmuserver.gs.muObjects;

import net.sf.jmuserver.gs.templates.MuNpc;
import net.sf.jmuserver.gs.templates.MuWeapon;

public class MuNpcInstance extends MuCharacter {
	/* (non-Javadoc)
	 * @see net.sf.jmuserver.gs.muObjects.MuCharacter#startAttack(net.sf.jmuserver.gs.muObjects.MuCharacter)
	 */
	public void startAttack(MuCharacter target)
	{
		super.startAttack(target);
	}
	
	public MuNpcInstance(MuNpc temp) {
		_npcTemplate=temp;
	_myType=1;
        setName(temp.getName());
        
	}
	private boolean _isAtackable;
	private MuNpc _npcTemplate;
	private int _expReward;
	/**
	 * set weward exp when die
	 * @param i exp
	 */
	public void setExpReward(int i)
	{
		_expReward=i;
	}
	/**
	 * @return reward exp for die a
	 */
	public int getExpReward()
	{
		return 10;// _expReward;
	}
	/**
	 * @return zwraca id moba/mpc
	 */
	public int getNpcId()
	{
		return _npcTemplate.getNpcId();
	}

	/**
	 * @return czy mozna go atakowac
	 */
	public boolean isAtackable()
	{
		return _isAtackable;
	}
	/**
	 * ustawia flage czy mozna go atakowac
	 * @param b flaga
	 */
	public void setAtackable(boolean b)
	{
		_isAtackable=b;
	}
	/**
	 * @return bazowe statystyki npc
	 */
	public MuNpc getNpcTemplate() {
		return _npcTemplate;
	}

	/**
	 * ustawia npctemplae
	 * @param npcTemplate
	 */
	public void setNpcTemplate(MuNpc npcTemplate) {
		_npcTemplate = npcTemplate;
	}

	/* (non-Javadoc)
	 * @see net.sf.jmuserver.gs.muObjects.MuCharacter#getActiveWeapon()
	 */
	@Override
	public MuWeapon getActiveWeapon() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    /**
     * get name from npc teplate 
     */
    public String getName() {
        return _npcTemplate.getName();
    }

    @Override
    public void moveTo(int x, int y) {
super.moveTo(x, y);
    }

    @Override
    public void updateKnownsLists() {
        super.updateKnownsLists();
        
    }
    
        
}
