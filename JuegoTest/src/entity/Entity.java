package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import skills.primaryWeapons;
import skills.secondaryWeapons;
import skills.skills;

// Esta clase va a guardar variables que se usaran para las clases jugadores, enemigos y NPC
public class Entity {
	GamePanel gp;

	public int worldX, worldY;
	public int speed;

	// It describes an image with an accessible buffer of image data. (We use this
	// to store our image file)
	public BufferedImage up1, up2, up3, left1, left2, left3, right1, right2, right3, down1, down2, down3;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1,
			attackRight2;
	public String direction = "down";

	public int spriteCounter = 0;
	public int spriteNum = 1;

	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public int actionLockCounter;
	String dialogues[] = new String[20];
	int dialogueIndex = 0;
	public boolean onPath = false;

	// Combat
	public boolean invincible = false;
	public int invincibleCounter = 0;

	boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	int dyingCounter = 0;
	public skills[] attackslot = new skills[4];

	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public primaryWeapons currentWeapon;
	public secondaryWeapons currentShield;

	// ITEM ATRIBUTES
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	public int value;
	public int attackValue;
	public int defenseValue;
	public String description = "";
	public int price;

	// TYPE modificar
	public int type; // 0 = player, 1 = npc, 2 = monster
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3; // Usa este type para reparar las armas al convertilos en una entidad
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	public final int type_pickupOnly = 7;

	// public BufferedImage image;
	public String name;
	public boolean collission = false;

	// CHARACTER STATUS
	public int maxLife;
	public int life;
	public int maxMana;
	public int mana;
	public int maxStamina;
	public int stamina;

	public Projectile projectile;
	public int useCost;

	public boolean move = false;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public void setAction() {
	}

	public void damageReaction() {
	}

	public void speak() {
		if (dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;

		switch (gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
		}
	}

	public void use(Entity entity) {
	}

	public void checkDrop() {
	}

	public void dropItem(Entity droppedItem) {
		for (int i = 0; i < gp.item[1].length; i++) {
			if (gp.item[gp.currentMap][i] == null) {
				gp.item[gp.currentMap][i] = droppedItem;
				gp.item[gp.currentMap][i].worldX = worldX; // The dead monster's coordinates
				gp.item[gp.currentMap][i].worldY = worldY;
				break;
			}
		}
	}

	public Color getParticleColor() {
		Color color = null;
		return color;
	}

	public int getParticleSize() {
		int size = 0; // 0 pixels
		return size;
	}

	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}

	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
	}

	public void generateParticle(Entity generator, Entity target) {
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();

		Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1); // Top left
		Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1); // Top Right
		Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1); // Down left
		Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1); // Down right
		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);

	}

	public void checkCollision() {
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		gp.cChecker.checkEntity(this, gp.iTile);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		if (this.type == type_monster && contactPlayer == true) {
			if (gp.player.invincible == false) {
				// We can give damage
//				int damage = attack - gp.player.defense;
//				gp.player.life -= 10;
//				gp.playSE();
				gp.player.invincible = true;
			}
		}
	}

	public void update() {
		setAction();
		checkCollision();

		if (collisionOn == false) {

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

		if (move == true) {
			spriteCounter++;
			if (spriteCounter > 13) {
				spriteNum++;
				if (spriteNum == 5) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}

		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter > 40) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

//		// STOP MOVING CAMERA
//		if (gp.player.worldX < gp.player.screenX) {
//			screenX = worldX;
//		}
//		if (gp.player.worldY < gp.player.screenY) {
//			screenY = worldY;
//		}
//		int rightOffset = gp.screenWidth - gp.player.screenX;
//		if (rightOffset > gp.worldWidth - gp.player.worldX) {
//			screenX = gp.screenWidth - (gp.worldWidth - worldX);
//		}
//		int bottomOffset = gp.screenHeight - gp.player.screenY;
//		if (bottomOffset > gp.worldHeight - gp.player.worldY) {
//			screenY = gp.screenHeight - (gp.worldHeight - worldY);
//		}

		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			switch (direction) {
			case "up":
				if (spriteNum == 1) {
					image = up1;
				} else if (spriteNum == 2) {
					image = up2;
				} else if (spriteNum == 3) {
					image = up1;
				} else if (spriteNum == 4) {
					image = up3;
				}
				break;
			case "down":
				if (spriteNum == 1) {
					image = down1;
				} else if (spriteNum == 2) {
					image = down2;
				} else if (spriteNum == 3) {
					image = down1;
				} else if (spriteNum == 4) {
					image = down3;
				}
				break;
			case "left":
				if (spriteNum == 1) {
					image = left1;
				} else if (spriteNum == 2) {
					image = left2;
				} else if (spriteNum == 3) {
					image = left1;
				} else if (spriteNum == 4) {
					image = left3;
				}
				break;
			case "right":
				if (spriteNum == 1) {
					image = right1;
				} else if (spriteNum == 2) {
					image = right2;
				} else if (spriteNum == 3) {
					image = right1;
				} else if (spriteNum == 4) {
					image = right3;
				}
				break;
			}

			if (invincible == true) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
			}
			if (dying == true) {
				dyingAnimation(g2);
			}

			g2.drawImage(image, screenX, screenY, null);

//			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
//					&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
//					&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
//					&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
//				g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
//			}
//			// If player is around the edge, draw everything
//			else if (gp.player.worldX < gp.player.screenX || gp.player.worldY < gp.player.screenY
//					|| rightOffset > gp.worldWidth - gp.player.worldX
//					|| bottomOffset > gp.worldHeight - gp.player.worldY) {
//				g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
//			}

			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}

	}

	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		int i = 5;
		if (dyingCounter <= i) {
			changeAlpha(g2, 0f);
		}
		if (dyingCounter > i && dyingCounter <= i * 2) {
			changeAlpha(g2, 1f);
		}
		if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
			changeAlpha(g2, 0f);
		}
		if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
			changeAlpha(g2, 1f);
		}
		if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
			changeAlpha(g2, 0f);
		}
		if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
			changeAlpha(g2, 1f);
		}
		if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
			changeAlpha(g2, 0f);
		}
		if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
			changeAlpha(g2, 1f);
		}
		if (dyingCounter > i * 8) {
			alive = false;
		}

	}

	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}

	public BufferedImage setup(String imagePath, int width, int height) {

		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

	public void searchPath(int goalCol, int goalRow) {

		direction = "down";
		System.out.println("Start");

		int startCol = (worldX + solidArea.x) / gp.tileSize;
		int startRow = (worldY + solidArea.y) / gp.tileSize;

		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

		if (gp.pFinder.search()) {

			System.out.println(collisionOn);

			// Next worldX & worldY
			int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

			// Entity's solidArea position
			int enLeftX = worldX + solidArea.x;
			int enRightX = worldX + solidArea.x + solidArea.width;
			int enTopY = worldY + solidArea.y;
			int enBottomY = worldY + solidArea.y + solidArea.height;

			if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "up";
			} else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "down";
			} else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
				// left or right
				if (enLeftX > nextX) {
					direction = "left";
				} else if (enLeftX < nextX) {
					direction = "right";
				}
			} else if (enTopY > nextY && enLeftX > nextX) {
				// up or left
				direction = "up";
				System.out.println("up2");
				checkCollision();
				if (collisionOn) {
					direction = "left";
					System.out.println("left2");
				}
			} else if (enTopY > nextY && enLeftX < nextX) {
				// up or right
				direction = "up";
				System.out.println("up3");
				checkCollision();
				if (collisionOn) {
					direction = "right";
					System.out.println("right2");
				}
			} else if (enTopY < nextY && enLeftX > nextX) {
				// down or left
				direction = "down";
				System.out.println("down2");
				checkCollision();
				if (collisionOn) {
					direction = "left";
					System.out.println("left3");
				}
			} else if (enTopY < nextY && enLeftX < nextX) {
				// down or right
				direction = "down";
				System.out.println("down3");
				checkCollision();
				if (collisionOn) {
					direction = "right";
					System.out.println("right3");
				}
			}
		}

		// Goal achieved
		int nextCol = gp.pFinder.pathList.get(0).col;
		int nextRow = gp.pFinder.pathList.get(0).row;
		if (nextCol == goalCol && nextRow == goalRow) {
			onPath = false;
		}
	}
}
