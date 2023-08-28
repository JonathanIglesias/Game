package main;

import java.awt.Graphics2D;
import java.util.Random;

import entity.Entity;

public class Combat {

	public int enemySlot;
//	int multipliyer = 0;
	public int monsterID;
	public int turnsPass = 0;
	public boolean firstTime, pass, firstTurn;
	int result = 1;

	GamePanel gp;
	Graphics2D g2;

	public Combat(GamePanel gp) {
		this.gp = gp;
	}

	public void turns(int ability) {
//		gp.ui.currentDialogue = "";
//		result = 1;
//		if (firstTurn) {
//			if (gp.ui.messageCounter == 0) {
//				if (ability != -1)
//					playerTurn(ability);
//				else {
//					gp.ui.messageCounter++;
//				}
//			} else if (gp.ui.messageCounter == 1) {
//				enemyTurn();
//			}
//		}
//
//		else {
//			if (gp.ui.messageCounter == 0) {
//				enemyTurn();
//			} else if (gp.ui.messageCounter == 1) {
//				if (ability != -1)
//					playerTurn(ability);
//				else {
//					gp.ui.messageCounter++;
//				}
//			}
//		}
//
//		switch (result) {
//		case 0:
////			g2.drawString("Miss", ability, ability);
//			break;
//		case 1:
//			break;
//		case 2:
////			g2.drawString("Critic", ability, ability);
//			break;
//		}

	}

	public void playerTurn(int ability) {
		gp.ui.currentDialogue = gp.player.name + " use " + gp.player.attackslot[ability].getName();

		if (firstTime) {
			gp.monster[gp.currentMap][monsterID].life -= gp.player.getAttack(ability);
			gp.player.stamina -= gp.player.attackslot[ability].getCost();
			firstTime = false;

			if (gp.player.attackslot[ability].getSpecialEffect() != null) {
				getEffect(gp.player, ability);
			}
		}
	}

	public void enemyTurn() {
		gp.ui.currentDialogue = gp.monster[gp.currentMap][monsterID].name + " use "
				+ gp.monster[gp.currentMap][monsterID].attackslot[enemySlot].getName();

		if (firstTime) {
			gp.player.life -= gp.monster[gp.currentMap][monsterID].attackslot[enemySlot].getDamage();
			firstTime = false;

			if (gp.monster[gp.currentMap][monsterID].attackslot[enemySlot].getSpecialEffect() != null) {
				getEffect(gp.monster[gp.currentMap][monsterID], enemySlot);
			}
		}
	}

	public void getEffect(Entity entity, int ability) {
		if (entity.attackslot[ability].getSpecialEffect().getObjective().equalsIgnoreCase(("player"))) {
			entity.attackslot[ability].getSpecialEffect().getEffect(gp.player);
		} else {
//			entity.attackslot[ability].getSpecialEffect().getEffect(gp.monster[monsterID]);
		}
	}

	public int enemyOption() {
		int damage = 0;
		int type = 0;
		Random random = new Random();
		int i = random.nextInt(100) + 1;

		if (i <= 25) {
//			damage = gp.monster[monsterID].attackslot[0].getDamage();
			type = 0;
		}
		if (i > 25 && i <= 50) {
//			damage = gp.monster[monsterID].attackslot[1].getDamage();
			type = 1;
		}
		if (i > 50 && i <= 75) {
//			damage = gp.monster[monsterID].attackslot[2].getDamage();
			type = 2;
		}
		if (i > 70 && i <= 100) {
//			damage = gp.monster[monsterID].attackslot[3].getDamage();
			type = 3;
		}

		return type;
	}

	public void checkResult() {
//		if (gp.player.life <= 0) {
//			gp.ui.battleScreenState = 3;
//		}

		for (int i = 0; i < gp.player.attackslot.length; i++) {
			if (gp.player.attackslot[i].getSpecialEffect() != null) {
				if (gp.player.attackslot[i].getSpecialEffect().getObjective().equals("self")) {
					gp.player.attackslot[i].getSpecialEffect().check(gp.player);
				} else {
//					gp.player.attackslot[i].getSpecialEffect().check(gp.monster[monsterID]);
				}
			}
		}

//		else if (gp.monster[monsterID].life <= 0) {
////			gp.ui.battleScreenState = 3;
//			gp.monster[monsterID].dying = true;
//			gp.gameState = gp.playState;
//		}

	}

	public void probability(int attack, Entity entity) {
		Random random = new Random();
		int i = random.nextInt(100) + 1;
		int result = 1;

		if (i <= 15) { // Miss
			entity.life -= 0;
			result = 0;
		}
		if (i > 15 && i <= 80) {
			entity.life -= attack;
			result = 1;
		}
		if (i > 80 && i <= 100) { // Critic
			entity.life -= (attack * 1.5);
			result = 2;
		}

//		return result;
	}

}
