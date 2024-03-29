package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.Monster_Creeper;
import object.boots;
import object.chest;
import object.door;
import object.key;
import object.test;
import skills.primaryWeapons;
import skills.skills;
import skills.specialEffect;
import stage.stage;

public class AssetSetter {

	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	// Items
	public void setObject() {

		int mapNum = 0;
		gp.item[mapNum][0] = new boots(gp);
		gp.item[mapNum][0].worldX = 24 * gp.tileSize;
		gp.item[mapNum][0].worldY = 6 * gp.tileSize;

		gp.item[mapNum][1] = new key(gp);
		gp.item[mapNum][1].worldX = 39 * gp.tileSize;
		gp.item[mapNum][1].worldY = 16 * gp.tileSize;

		gp.item[mapNum][2] = new chest(gp);
		gp.item[mapNum][2].worldX = 43 * gp.tileSize;
		gp.item[mapNum][2].worldY = 43 * gp.tileSize;

		gp.item[mapNum][3] = new door(gp);
		gp.item[mapNum][3].worldX = 43 * gp.tileSize;
		gp.item[mapNum][3].worldY = 41 * gp.tileSize;

		gp.item[mapNum][4] = new test(gp);
		gp.item[mapNum][4].worldX = gp.tileSize * 10;
		gp.item[mapNum][4].worldY = gp.tileSize * 30;
	}

	public void setNPC() {

		int mapNum = 0;
		int i = 0;

		// MAP 0
		gp.npc[mapNum][i] = new NPC_OldMan(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 9;
		gp.npc[mapNum][i].worldY = gp.tileSize * 11;
		i++;

		// MAP 1
		mapNum = 1;
		i = 0;
		gp.npc[mapNum][i] = new NPC_Merchant(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 25;
		gp.npc[mapNum][i].worldY = gp.tileSize * 23;
		i++;

	}

	public void setMonster() {

		int mapNum = 0;
		gp.monster[mapNum][0] = new Monster_Creeper(gp);
		gp.monster[mapNum][0].worldX = gp.tileSize * 10;
		gp.monster[mapNum][0].worldY = gp.tileSize * 35;
	}

	public void setInteractiveTile() {
		int i = 0;
//		gp.iTile[i] = new DryTree(gp,27,12);
	}

	public void setStage() {
		gp.stage = new stage(gp);
	}

	public void setWeapons(String fileName) {
		Vector<primaryWeapons> list = new Vector<primaryWeapons>();
		String[] id;
		String[] skillID;
		String[] name;
		String[] description;
		File data = new File("/config/" + fileName);
		if (data.exists()) {
			try {
				Scanner textFile = new Scanner(data);
				while (textFile.hasNext()) {
					id = textFile.nextLine().split(":");
					name = textFile.nextLine().split(":");
					description = textFile.nextLine().split(":");
					primaryWeapons weapon = new primaryWeapons(gp, Integer.parseInt(id[1]), name[1], description[1]);
					skillID = textFile.nextLine().split(":");
					weapon.setNormal(gp.abilities.get(Integer.parseInt(skillID[1])));
					skillID = textFile.nextLine().split(":");
					weapon.setSpecial(gp.abilities.get(Integer.parseInt(skillID[1])));
					list.add(weapon);
				}
				textFile.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		gp.weapons = list;
	}

	public void setSkills(String fileName) {
		Vector<skills> list = new Vector<skills>();
		String[] id;
		String[] name;
		String[] description;
		String[] cost;
		String[] damage;
		String[] traits;
		String[] type;
		String[] turns;
		String[] objective;

		Vector<String> attributes = new Vector<String>();
		Vector<Integer> value = new Vector<Integer>();

		File data = new File("/config/" + fileName);
		if (data.exists()) {
			try {
				Scanner textFile = new Scanner(data);
				while (textFile.hasNext()) {
					id = textFile.nextLine().split(":");
					name = textFile.nextLine().split(":");
					description = textFile.nextLine().split(":");
					cost = textFile.nextLine().split(":");
					damage = textFile.nextLine().split(":");
					skills mainSkills = new skills(gp, Integer.parseInt(id[1]), name[1], description[1],
							Integer.parseInt(cost[1]), Integer.parseInt(damage[1]));

					if (damage[0].contains(".")) {

						objective = textFile.nextLine().split(":");
						type = textFile.nextLine().split(":");

						traits = textFile.nextLine().split(":");
						while (!traits[0].equals("turns")) {
							attributes.add(traits[0]);
							value.add(Integer.parseInt(traits[1]));
							traits = textFile.nextLine().split(":");
						}

						turns = traits;

						specialEffect effect = new specialEffect(Integer.parseInt(turns[1]), type[1], objective[1],
								attributes, value);
						mainSkills.setSpecialEffect(effect);
					}

					list.add(mainSkills);

					textFile.nextLine();

				}
				textFile.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		gp.abilities = list;
	}
}
