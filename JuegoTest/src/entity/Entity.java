package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

	// Combat
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public int type; // 0 = player, 1 = npc, 2 = monster
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
	public int attackValue;
	public int defenseValue;
	public String description = "";

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

	public void update() {
		setAction();

		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		if (this.type == 2 && contactPlayer == true) {
			if (gp.player.invincible == false) {
				// We can give damage
//				int damage = attack - gp.player.defense;
//				gp.player.life -= 10;
//				gp.playSE();
				gp.player.invincible = true;
			}
		}

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

			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

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
			dying = false;
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

}
