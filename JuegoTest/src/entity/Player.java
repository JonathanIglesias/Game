package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Vector;

import main.GamePanel;
import main.KeyHandler;
import object.key;
import skills.primaryWeapons;
import skills.secondaryWeapons;
import skills.skills;
import skills.specialEffect;

public class Player extends Entity {

	KeyHandler keyH;

	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	boolean canRun = false;
	public boolean attackCanceled = false;

	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeigth / 2 - (gp.tileSize / 2);
		// SOLID AREA
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 36;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 12;
		// ATTACK AREA
//		attackArea.width = 36;
//		attackArea.height = 36;

		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();

//		projectile = new Fireball();
	}

	public void setDefaultValues() {
		name = "";

		worldX = gp.tileSize * 24;
		worldY = gp.tileSize * 24;
		speed = 2;
		direction = "down";

		// PLAYER STATUS
		maxLife = 10;
		life = maxLife;
		maxMana = 3;
		mana = maxMana;
		maxStamina = 5;
		stamina = maxStamina;

		level = 1;
		strength = 1;
		dexterity = 1;
		exp = 0;
		nextLevelExp = 5;
		coin = 0;

//		currentWeapon = new primaryWeapons(gp, 0, "Wooden Sword", "A training weapon for many adventurers.");
		currentWeapon = setDefaultPrimary();
		currentShield = setDefaultSecondary();
		attackslot[0] = currentWeapon.getNormal();
		attackslot[1] = currentWeapon.getSpecial();
		attackslot[2] = currentShield.getNormal();
		attackslot[3] = setDefaultSkill();
//		currentWeapon = new Sword_Normal(gp);
//		currentShield = new Shield_Wood(gp);
//		attack = getAttack();
//		defense = getDefense();
	}

	public primaryWeapons setDefaultPrimary() {
		primaryWeapons weapon = new primaryWeapons(gp, 0, "Wooden Sword", "A training weapon for many adventurers.");
		skills normal = new skills(gp, 2, "Slash", "A basic move, but efective to any fighter", 2, 2);
		skills special = new skills(gp, 3, "Thrust", "Make a powerful thrust that perfore the enemy", 3, 4);

		weapon.setNormal(normal);
		weapon.setSpecial(special);

		return weapon;
	}

	public secondaryWeapons setDefaultSecondary() {
		secondaryWeapons weapon = new secondaryWeapons(gp, 0, "Wooden Shield",
				"A hand carved wooden shield that gave it away to people who want to be adventures in the future.");
		skills normal = new skills(gp, 4, "Block", "Rise your shield to block the oponent's attack", 1, 0);
		weapon.setNormal(normal);

		return weapon;
	}

	public skills setDefaultSkill() {
		skills skill = new skills(gp, 0, "War Cry", "Increase your attack power", 3, 0);
		Vector<String> attributes = new Vector<>();
		attributes.add("strength");
		Vector<Integer> value = new Vector<>();
		value.add(2);
		specialEffect effect = new specialEffect(2, "buff", "self", attributes, value);
		skill.setSpecialEffect(effect);

		return skill;
	}

	public void setDefaultPositions() {
		worldX = gp.tileSize * 24;
		worldY = gp.tileSize * 24;
		direction = "down";
	}

	public void restore() {
		life = maxLife;
		mana = maxMana;
		stamina = maxStamina;
		invincible = false;
	}

	public void setItems() {
		inventory.clear();
//		inventory.add(currentWeapon);
//		inventory.add(currentShield);
		inventory.add(new key(gp));
	}

	public int getAttack(int ability) {
		if (attackslot[ability].getDamage() == 0) {
			return 0;
		}
//		attackArea = currentWeapon.attackArea;
		return attack = strength + attackslot[ability].getDamage();
	}

//	public int getDefense() {
//		return defense = dexterity * currentShield.defenseValue;
//	}

	public void getPlayerImage() {
		up1 = setup("/player/Back1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/Back2", gp.tileSize, gp.tileSize);
		up3 = setup("/player/Back3", gp.tileSize, gp.tileSize);
		down1 = setup("/player/Front1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/Front2", gp.tileSize, gp.tileSize);
		down3 = setup("/player/Front3", gp.tileSize, gp.tileSize);
		left1 = setup("/player/Left1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/Left2", gp.tileSize, gp.tileSize);
		left3 = setup("/player/Left3", gp.tileSize, gp.tileSize);
		right1 = setup("/player/Right1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/Right2", gp.tileSize, gp.tileSize);
		right3 = setup("/player/Right3", gp.tileSize, gp.tileSize);
	}

	public void getPlayerAttackImage() {

//		if(currentWeapon == type_sword) {
//			
//		}

		attackUp1 = setup("/player/SwordBack1", gp.tileSize, gp.tileSize * 2);
		attackUp2 = setup("/player/SwordBack2", gp.tileSize, gp.tileSize * 2);
		attackDown1 = setup("/player/SwordFront1", gp.tileSize, gp.tileSize * 2);
		attackDown2 = setup("/player/SwordFront2", gp.tileSize, gp.tileSize * 2);
		attackLeft1 = setup("/player/SwordLeft1", gp.tileSize * 2, gp.tileSize);
		attackLeft2 = setup("/player/SwordLeft2", gp.tileSize * 2, gp.tileSize);
		attackRight1 = setup("/player/SwordRight1", gp.tileSize * 2, gp.tileSize);
		attackRight2 = setup("/player/SwordRight2", gp.tileSize * 2, gp.tileSize);
	}

	public void update() {

		if (attacking == true) {
			attacking();
		}

		else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
				|| keyH.rightPressed == true || keyH.select) {

			// En java la parte de arriba izquierda es 0,0. Por lo que X aumenta cuando va a
			// la derecha y Y aumenta cuando va abajo.
			if (keyH.upPressed == true) {
				direction = "up";

			} else if (keyH.downPressed == true) {
				direction = "down";

			} else if (keyH.leftPressed == true) {
				direction = "left";

			} else if (keyH.rightPressed == true) {
				direction = "right";

			}

			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);

			// Check Object Collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickObject(objIndex);

			// Check NPC Collision
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);

			// Check Monster Collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);

			// Check Interactive Tile Collision
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			damageInteractiveTile(iTileIndex);

			// Check Event
			gp.eHandler.checkEvent();

			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if (collisionOn == false && keyH.select == false) {

				switch (direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}

			if (keyH.select == true && attackCanceled == false) {
				attacking = true;
				spriteCounter = 0;
			}

			attackCanceled = false;
			gp.keyH.select = false;

			spriteCounter++;
			if (spriteCounter > 13) {
				spriteNum++;
				if (spriteNum == 5) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
			if (keyH.run && canRun) {
				speed = 5;
				if (spriteCounter > 7) {
					spriteNum++;
					if (spriteNum == 5) {
						spriteNum = 1;
					}
					spriteCounter = 0;
				}
			} else {
				speed = 2;
			}
		}

		else {
			spriteNum = 1;
			spriteCounter = 0;
		}

		// SET DEFAULT COORDINATES, DIRECTION AND USE
//		if(gp.keyH.shotKeyPressed == true && projectile.alive == false) {
//			projectile.set(worldX, worldY, direction, true, this);

		// ADD TO LIST
//		gp.projectileList.add(projectile);

//		}

		// This needs to be outside of key if statement
		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}

		if (life > maxLife) {
			life = maxLife;
		}
		if (mana > maxMana) {
			mana = maxMana;
		}
		if (stamina > maxStamina) {
			stamina = maxStamina;
		}

		if (life <= 0) {
			gp.gameState = gp.gameOverState;
			gp.ui.commandNum = -1;
			gp.stopMusic();
//			gp.playSE();
		}
	}

	public void attacking() {
		spriteCounter++;
		if (spriteCounter <= 5) {
			spriteNum = 1;
		}
		if (spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;

			// Save the current worldX, worldY, solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;

			// Adjust player's worldX/Y for the attackArea
			switch (direction) {
			case "up":
				worldY -= attackArea.height;
				break;
			case "down":
				worldY += attackArea.height;
				break;
			case "left":
				worldX -= attackArea.width;
				break;
			case "right":
				worldX += attackArea.width;
				break;
			}

			// attackArea becomes solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;

			// Check monster collision with the updated worldX, worldY, and solidArea
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex);

			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			damageInteractiveTile(iTileIndex);

			// After checking collision, restore the original data
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if (spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}

	public void pickObject(int i) {
		if (i != 999) {

			// PICKUP ONLY ITEMS
			if (gp.item[gp.currentMap][i].type == type_pickupOnly) {

				gp.item[gp.currentMap][i].use(this);
				gp.item[gp.currentMap][i] = null;
			}

			// INVENTORY ITEMS
			else {
				String text;
				if (inventory.size() != maxInventorySize) {
					inventory.add(gp.item[gp.currentMap][i]);
					gp.playSE(1);
					text = "Got a " + gp.item[gp.currentMap][i].name;
				} else {
					text = "You cannot carry any more items!";
				}
				gp.ui.addMessage(text);
				gp.item[gp.currentMap][i] = null;

//				// gp.item[i] = null;
//				attackCanceled = true;
//				String objectName = gp.item[i].name;
//				if (keyH.select) {
//					switch (objectName) {
//					case "key":
//						gp.playSE(1);
//						hasKey++;
//						gp.item[i] = null;
//						gp.ui.addMessage("You got a key!");
//						break;
				//
//					case "door":
//						if (hasKey > 0) {
//							gp.playSE(2);
//							gp.item[i] = null;
//							hasKey--;
//							gp.ui.addMessage("You opened the door!");
//						} else {
//							gp.ui.addMessage("You need a key!");
//						}
//						break;
				//
//					case "boots":
//						gp.playSE(1);
//						canRun = true;
//						gp.item[i] = null;
//						gp.ui.addMessage("Speed boost acquire! Hold Shift to run");
//						break;
//					case "chest":
//						gp.ui.gameFinished = true;
//						gp.stopMusic();
//						gp.playSE(3);
//						break;
//					}
//				}
			}

		}
	}

	public void interactNPC(int i) {
		if (gp.keyH.select) {
			if (i != 999) {
				attackCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[gp.currentMap][i].speak();
			} else {
//				gp.playSE();
				attacking = true;
			}
		}
	}

	public void contactMonster(int i) {
		if (i != 999) {
			if (invincible == false && gp.monster[gp.currentMap][i].dying == false) {
//				life -= 10;
//				gp.playSE();

//				int damage = gp.monster[i].attack - defense;

				invincible = true;
				gp.combat.monsterID = i;
				gp.combat.firstTurn = false;
				gp.stopMusic();
				gp.playMusic(7);
				gp.gameState = gp.battleState;

			}
		}
	}

	public void damageMonster(int i) {
		if (i != 999) {
			if (gp.monster[gp.currentMap][i].invincible == false) {
//				gp.monster[i].life -= 1;

//				int damage = attack - gp.monster[i].defense;
//				if (damage < 0) {
//					damage = 0;
//				}

//				gp.ui.addMessage(damage + " damage!");

				gp.monster[gp.currentMap][i].invincible = true;
				gp.monster[gp.currentMap][i].damageReaction();
				gp.combat.monsterID = i;
				gp.combat.firstTurn = true;
				gp.stopMusic();
				gp.playMusic(7);
				gp.gameState = gp.battleState;
//
				if (gp.monster[gp.currentMap][i].life <= 0) {
//					gp.monster[i] = null;
					gp.monster[gp.currentMap][i].dying = true;
//					gp.ui.addMessage("Killed the " + gp.monster[i].name + "!");
//					gp.ui.addMessage("Exp + " + gp.monster[i].exp + "!");
					exp += gp.monster[gp.currentMap][i].exp;
					checkLevelUp();
				}
			}
		}
	}

	public void damageInteractiveTile(int i) {
		if (i != 999 && gp.iTile[gp.currentMap][i].destructible == true
				&& gp.iTile[gp.currentMap][i].isCorrectItem(this) == true
				&& gp.iTile[gp.currentMap][i].invincible == false) {
			gp.iTile[gp.currentMap][i].playSE();
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;

			// Generate Particle
			generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

			if (gp.iTile[gp.currentMap][i].life == 0) {
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
			}
		}
	}

	public void checkLevelUp() {
		if (exp >= nextLevelExp) {
			level++;
			nextLevelExp = nextLevelExp * 3;
			maxLife += 2;
			strength++;
			dexterity++;
//			attack = getAttack();
//			defense = getDefense();

//			gp.playSE();
//			gp.gameState = gp.dialogueState;
//			gp.ui.currentDialogue = "You are level " + level + " now!";
		}
	}

	public void selectItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

		if (itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);

			if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
//				currentWeapon = selectedItem;
//				attack = getAttack();
//				getPlayerAttackImage();
			}
			if (selectedItem.type == type_shield) {
//				currentShield = selectedItem;
//				defense = getDefense();
			}
			if (selectedItem.type == type_consumable) {
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
		}
	}

	public void draw(Graphics2D g2) {
//		g2.setColor(Color.GREEN);
//		g2.fillRect(43 * gp.tileSize, 43 * gp.tileSize, 48, 48);

		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;

		switch (direction) {
		case "up":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
				if (spriteNum == 3) {
					image = up1;
				}
				if (spriteNum == 4) {
					image = up3;
				}
			}
			if (attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if (spriteNum == 1) {
					image = attackUp1;
				}
				if (spriteNum == 2) {
					image = attackUp2;
				}
			}
			break;
		case "down":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
				if (spriteNum == 3) {
					image = down1;
				}
				if (spriteNum == 4) {
					image = down3;
				}
			}
			if (attacking == true) {
				if (spriteNum == 1) {
					image = attackDown1;
				}
				if (spriteNum == 2) {
					image = attackDown2;
				}
			}
			break;
		case "left":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
				if (spriteNum == 3) {
					image = left1;
				}
				if (spriteNum == 4) {
					image = left3;
				}
			}
			if (attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if (spriteNum == 1) {
					image = attackLeft1;
				}
				if (spriteNum == 2) {
					image = attackLeft2;
				}
			}
			break;
		case "right":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
				if (spriteNum == 3) {
					image = right1;
				}
				if (spriteNum == 4) {
					image = right3;
				}
			}
			if (attacking == true) {
				if (spriteNum == 1) {
					image = attackRight1;
				}
				if (spriteNum == 2) {
					image = attackRight2;
				}
			}
			break;
		}

		if (invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}

		g2.drawImage(image, tempScreenX, tempScreenY, null);

		// Reset opacity
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		// Debug
//		g2.setFont(new Font("Arial", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincible:" + invincibleCounter, 10, 400);

//		// Debug
//		// AttackArea
//		tempScreenX = screenX + solidArea.x;
//		tempScreenY = screenY + solidArea.y;
//		switch (direction) {
//		case "up":
//			tempScreenY = screenY - attackArea.height;
//			break;
//		case "down":
//			tempScreenY = screenY + gp.tileSize;
//			break;
//		case "left":
//			tempScreenX = screenX - attackArea.width;
//			break;
//		case "right":
//			tempScreenX = screenX + gp.tileSize;
//			break;
//		}
//		g2.setColor(Color.red);
//		g2.setStroke(new BasicStroke(1));
//		g2.drawRect(tempScreenX, tempScreenY, attackArea.width, attackArea.height);
	}

}
