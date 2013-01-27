/*
 * Copyright 2012 Jordan Hobgood
 * 
 * This file is part of Shield.
 *
 * Shield is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Shield is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Shield.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.malikk.shield.flags;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.malikk.shield.Shield;

public class FlagPersister {

	Shield plugin;

	public FlagPersister(Shield instance){
		plugin = instance;
	}

	public void save(){

		int amt = plugin.fm.flags.size();

		if (amt == 0){
			plugin.log("No flags to save");
			return;
		}

		//create a new File
		File saveFile = new File(plugin.getDataFolder() + File.separator + "Flags.dat");

		if (!saveFile.exists()){
			try {
				plugin.getDataFolder().mkdir();
				saveFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		try{
			FileOutputStream fos = new FileOutputStream(plugin.getDataFolder() + File.separator + "Flags.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			//Write file here
			oos.writeInt(amt);

			for (Flag f: plugin.fm.flags){
				oos.writeObject(f);
			}

			oos.close();

			plugin.log(amt + " Flag(s) Saved");

		}catch(Exception e){
			e.printStackTrace();
			plugin.log("Failed to save Flags");
		}


	}

	public void load(){

		File saveFile = new File(plugin.getDataFolder() + File.separator + "Flags.dat");

		if(saveFile.exists()){
			FileInputStream fis = null;
			ObjectInputStream ois = null;

			try{
				fis = new FileInputStream(saveFile);
				ois = new ObjectInputStream(fis);

				Integer recordCount = ois.readInt();

				for(int i = 0; i < recordCount; i ++){
					Flag f = (Flag) ois.readObject();
					plugin.fm.flags.add(f);
				}

				plugin.log(recordCount + " Flag(s) loaded");

			}catch(FileNotFoundException e){
				plugin.log("Could not locate data file... ");
				e.printStackTrace();
			}catch(IOException e){
				plugin.log("IOException while trying to read data file");
			}catch(ClassNotFoundException e){
				plugin.log("Could not find class to load");
			}finally{
				try{
					ois.close();
				}catch(IOException e){
					plugin.log("Error while trying to close input stream");
				}
			}
		}
	}
}
