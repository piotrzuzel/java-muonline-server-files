/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.openmu;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Properties;

/**
 * 
 * @author mikiones
 */
public class ServersRunner {

	// we need to deterene the working directory to .jmusrv at home directory

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		
		final ServersRunner s = new ServersRunner();
		s.PreareSettings();

	 GameServer gs = new GameServer();
	 FS fs = new FS();
	 gs.start();
	 fs.start();
	}
	
	public void PreareSettings()
	{
		File jmuserv = new File(System.getProperty("user.home")+"/.jmuserv/");
		if (jmuserv.exists())
		{
			System.out.println("Found .jmuserv home diretory contiunue loading settings ... ");
		
		} else {
			createDirestories();
			copyConfData();
			copyDataFiles();
			System.out.println("Now Edit conf Files in " + System.getProperty("user.home")+"/jmuserv/etc and run server again!!!");
			System.exit(0);
		}
	}
	
	/**
	 * Create default directories structure in home directory for server usage
	 */
	public void createDirestories() {
		System.out.println("Create Server home directories structure...");
		final File HomeDir = new File(System.getProperty("user.home")
				+ "/.jmuserv/");
		final File etcDir = new File(HomeDir, "/etc/");
		final File dataDir = new File(HomeDir, "/data/");
		final File mapsDir = new File(dataDir, "/maps/");
		final File logDir = new File(HomeDir, "/logs/");
		final File scripts = new File(HomeDir, "/scripts/");
		if (HomeDir.mkdir()) {
			System.out.println(".jmuserv... OK");
		}
		if (etcDir.mkdir()) {
			System.out.println(".jmuserv/etc... OK");
		}
		if (dataDir.mkdir()) {
			System.out.println(".jmuserv/data... OK");
		}
		if (mapsDir.mkdir()) {
			System.out.println(".jmuserv/data/maps... OK");
		}
		if (logDir.mkdir()) {
			System.out.println(".jmuserv/logs... OK");
		}
		if (scripts.mkdir()) {
			System.out.println(".jmuserv/scripts... OK");
		}
	}
	
	public static void copyFile(File in, File out) 
    throws IOException 
{
    FileChannel inChannel = new
        FileInputStream(in).getChannel();
    FileChannel outChannel = new
        FileOutputStream(out).getChannel();
    try {
        inChannel.transferTo(0, inChannel.size(),
                outChannel);
    } 
    catch (IOException e) {
        throw e;
    }
    finally {
        if (inChannel != null) inChannel.close();
        if (outChannel != null) outChannel.close();
    }
}


	public void copyConfData() {
		String [] names = {
				"database",
				"GameServer",
				"MuLog",
				"ConnectServer"
				};
		
		File targetDir= new File(System.getProperty("user.home")+"/.jmuserv/etc/");
		
		for(String name:names)
		{
			File target= new File(targetDir,name+".ini");
			File from = new File("templates/"+name+".templ");
			try {
			copyFile(from, target);
		} catch (IOException e) {
			System.out.println("copy: "+name+ ".ini to "+target.getPath() +"  ....Error !!!");
			e.printStackTrace();
		}
		System.out.println("copy: "+name+ ".ini to "+target.getPath() +"  ....OK");
		}
	}
public void coppyTerainsData()
{	
	System.out.println("Copy Terrains data ... ");
	File dest = new File (System.getProperty("user.home")+"/.jmuserv/data/maps/");
	dest.mkdirs();
	File from_dir= new File ("./data/maps/");
	String[] ls = from_dir.list();
	for (String tempName: ls){
		File to_coppy= new File(from_dir,tempName);
		try {
			copyFile(to_coppy, new File(dest,tempName));
		} catch (IOException e) {
			System.out.println("copy: "+tempName+ " to "+dest.getPath() +"  ....Error !!!");
			e.printStackTrace();
		}
		System.out.println("copy: "+tempName+ " to "+dest.getPath() +"  ....OK");
	}
	
}

public void copyDataFiles()
{
	System.out.println("Copy Data files ..... ");
	File dest = new File(System.getProperty("user.home")+"/.jmuserv/data");
	String[] filesToCoppy = {"./data/item.txt"};
	for(String file:filesToCoppy)
	{
		File to_copy= new File(file);
		File dest_copy = new File(dest,to_copy.getName());
		try {
			copyFile(to_copy, dest_copy );
			System.out.println("copy: "+dest_copy.getName()+ " to "+dest_copy.getPath() +"  ....OK");
		} catch (IOException e) {
			System.out.println("copy: "+dest_copy.getName()+ " to "+dest_copy.getPath() +"  ....ERROR!!!");
			e.printStackTrace();
		}
		
	}
	coppyTerainsData();
}
}