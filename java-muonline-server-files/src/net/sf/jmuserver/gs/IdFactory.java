package net.sf.jmuserver.gs;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public final class IdFactory {
private static IdFactory _instance=null;
private final Random random=new Random();
private IdFactory(){};
public static IdFactory getInstance()
{
	if(_instance==null)_instance=new IdFactory();
	return _instance;
}
private Set<Integer> _set=new HashSet<Integer>();
public  int newId()
{
	int t=1 + (int)(Math.random() * 250);
	System.out.println("Found new  id :"+t);
	while (_set.contains(t))
	{
		t=1 + (int)(Math.random() * 250);;
		
	}
	_set.add(t);
	System.out.println("Added new id["+t+"] to set, set.size= "+_set.size());
	return t;	
}
public void deleteId(int id)
{
	_set.remove(id);
}
}
