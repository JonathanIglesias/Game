package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import entity.Entity;
import object.coin_Bronze;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font maruMonica, arial_80B;
//	BufferedImage keyImage;
//	BufferedImage healthBar;
	BufferedImage coin;
	public boolean messageOn = false;
//	public String message = "";
//	int messageCounter = 0;

	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();

	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public boolean attackFase = false;

	public int titleScreenState = 0;
	public int battleScreenState = 0;

	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;

	int subState = 0;
	int counter = 0;
	public Entity npc;

	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");

	public UI(GamePanel gp) {
		this.gp = gp;
		arial_80B = new Font("Arial", Font.BOLD, 80);
//		key key = new key(gp);
//		keyImage = key.image;

		try {
			InputStream is = getClass().getResourceAsStream("/Font/MaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		// CREATE HUD OBJECT
		// items Health = new Health(gp);
		Entity bronzeCoin = new coin_Bronze(gp);
		coin = bronzeCoin.down1;

	}

	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0);
	}

	public void draw(Graphics2D g2) {

		this.g2 = g2;

		g2.setFont(maruMonica);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);

		if (gp.gameState == gp.titleState) {
//			g2.setColor(new Color(0, 0, 0, 255));
//			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
//			subState = 0;
			drawTitleScreen();
		}

		if (gp.gameState == gp.playState) {
			// Do playState stuff
//			drawPlayerLife();
			drawMessage();
		}

		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
			drawPlayerLife();
		}

		if (gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}

		if (gp.gameState == gp.battleState) {
//			drawPlayerLife();
			drawBattleScreen();
		}

		if (gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory(gp.player, true);
		}

		if (gp.gameState == gp.optionState) {
			drawOptionScreen();
		}

		if (gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}

		if (gp.gameState == gp.transitionState) {
			drawTransition();
		}

		if (gp.gameState == gp.tradeState) {
			drawTradeScreen();
		}

		if (gameFinished) {

			g2.setFont(maruMonica);
			g2.setColor(Color.white);

			String text;
			int textlength;
			int x;
			int y;

			text = "You found the treasure";
			textlength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth / 2 - textlength / 2;
			y = gp.screenHeight / 2 - (gp.tileSize * 3);
			g2.drawString(text, x, y);

			text = "Your time is: " + dFormat.format(playTime) + "!";
			textlength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth / 2 - textlength / 2;
			y = gp.screenHeight / 2 + (gp.tileSize * 4);
			g2.drawString(text, x, y);

			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
//			g2.setFont(arial_80B);
			g2.setColor(Color.yellow);

			text = "Congratulations!";
			textlength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth / 2 - textlength / 2;
			y = gp.screenHeight / 2 + (gp.tileSize * 2);
			g2.drawString(text, x, y);

			gp.gameThread = null;
		}

//		else {
//			g2.setFont(maruMonica);
//			g2.setColor(Color.white);
////			g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
////			g2.drawString("X " + gp.player.hasKey, 74, 65);
//
//			// TIME
//			playTime += (double) 1 / 60;
////			g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 11, 65);
//
//			// MESSAGE
//			if (messageOn == true) {
//
//				g2.setFont(g2.getFont().deriveFont(30F));
//				g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
//
//				messageCounter++;
//
//				if (messageCounter > 120) {
//					messageCounter = 0;
//					messageOn = false;
//				}
//			}
//		}
	}

	public void drawMessage() {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize * 4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

		for (int i = 0; i < message.size(); i++) {
			if (message.get(i) != null) {

				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX + 2, messageY + 2);

				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);

				int counter = messageCounter.get(i) + 1; // messageCounter++
				messageCounter.set(i, counter); // set the counter to the array
				messageY += 50;

				if (messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}

	public void drawCharacterScreen() {
		// CREATE A FRAME
		final int frameX = gp.tileSize * 2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize * 5;
		final int frameHeight = gp.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));

		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 40;

		// NAMES
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight;
		g2.drawString("Shield", textX, textY);
		textY += lineHeight;

		// VALUES
		int tailX = (frameX + frameWidth) - 30;
		textY = frameY + gp.tileSize;
		String value;

		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.strength);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.attack);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.defense);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.exp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight + 20;

//		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY, null)
		textY += gp.tileSize;
//		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);

	}

	public void drawInventory(Entity entity, boolean cursor) {

		int frameX = 0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;

		if (entity == gp.player) {

			frameX = gp.tileSize * 12;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize * 6;
			frameHeight = gp.tileSize * 5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		} else {
			frameX = gp.tileSize * 2;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize * 6;
			frameHeight = gp.tileSize * 5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}

		// FRAME
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		// SLOT
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize + 3;

		// DRAW PLAYER'S ITEMS
		for (int i = 0; i < entity.inventory.size(); i++) {

			// EQUIP CURSOR REPARAR
//			if(entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield) {
//				g2.setColor(new Color(240,190,90));
//				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
//			}

			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

			slotX += slotSize;

			if (i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}

		// Cursor
		if (cursor) {
			int cursorX = slotXstart + (slotSize * playerSlotCol);
			int cursorY = slotYstart + (slotSize * playerSlotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;

			// DRAW CURSOR
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

			// DESCRIPTION FRAME
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize * 3;

			// DRAW DESCRIPTION TEXT
			int textX = dFrameX + 20;
			int textY = dFrameY + gp.tileSize;
			g2.setFont(g2.getFont().deriveFont(28F));

			int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

			if (itemIndex < entity.inventory.size()) {

				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

				for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);
					textY += gp.tileSize;
				}
			}
		}
	}

	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		int itemIndex = slotCol + (slotRow * 5);
		return itemIndex;
	}

	public void drawEnemyLife() {
		int x = gp.tileSize * 4;
		int y = gp.tileSize * 2 - gp.tileSize / 4;
		int width = gp.tileSize * 3;
		int height = gp.tileSize / 2;

		Color c = new Color(136, 8, 8);
		g2.setColor(c);
		g2.fillRect(x - 1, y - 1, width + 2, height + 2);

		double percentage = ((double) gp.monster[gp.currentMap][gp.combat.monsterID].life
				/ gp.monster[gp.currentMap][gp.combat.monsterID].maxLife);
		double healthRemaining = percentage * width;

		c = new Color(239, 23, 0);
		g2.setColor(c);
		g2.fillRect(x, y, (int) healthRemaining, height);

	}

	public void drawPlayerLife() {

//		// Draw max life
		int x = gp.tileSize / 2;
		int y = gp.tileSize / 2;
		int width = gp.tileSize * 4;
		int height = gp.tileSize / 2;

		if (gp.gameState == gp.battleState) {
			x = gp.tileSize;
			y = gp.tileSize * 2 - gp.tileSize / 4;
			width = gp.tileSize * 3;
		}

		// Base
		Color c = new Color(136, 8, 8);
		g2.setColor(c);
		g2.fillRect(x - 1, y - 1, width + 2, height + 2);

		// Vida
		double percentage = ((double) gp.player.life / gp.player.maxLife);
		double healthRemaining = percentage * width;

		c = new Color(239, 23, 0);
		g2.setColor(c);
		g2.fillRect(x, y, (int) healthRemaining, height);

	}

	public void drawMonsterLife() {
		int x = gp.tileSize * 8;
		int y = gp.tileSize * 2 - gp.tileSize / 4;
		int width = gp.tileSize * 4;
		int height = gp.tileSize / 2;

		// Base
		Color c = new Color(136, 8, 8);
		g2.setColor(c);
		g2.fillRect(x - 1, y - 1, width + 2, height + 2);

		// Vida
		double percentage = ((double) gp.monster[gp.currentMap][gp.combat.monsterID].life
				/ gp.monster[gp.currentMap][gp.combat.monsterID].maxLife);
		double healthRemaining = percentage * width;

		c = new Color(239, 23, 0);
		g2.setColor(c);
		g2.fillRect(x, y, (int) healthRemaining, height);
	}

	public void drawBattleScreen() {

		// Status Window
		int x = gp.tileSize / 2;
		int y = gp.tileSize / 2;
		int width = gp.tileSize * 4;
		int height = gp.tileSize * 8 - gp.tileSize / 2;
		drawSubWindow(x, y, width, height);

		g2.setColor(Color.WHITE);
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

		x = gp.tileSize;
		y = gp.tileSize * 2 - gp.tileSize / 3;

		g2.drawString("HP", x, y);
		drawPlayerLife();
		x = gp.tileSize * 1;
		y = gp.tileSize * 3 - gp.tileSize / 5;
		String text = gp.player.life + " / " + gp.player.maxLife;
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
		g2.drawString(text, x, y);

		y = gp.tileSize * 4 - gp.tileSize / 6;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
		g2.drawString("Mana", x, y);
		drawPlayerMana();
		text = gp.player.mana + " / " + gp.player.maxMana;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
		g2.setColor(Color.WHITE);
		y = gp.tileSize * 5;
		g2.drawString(text, x, y);

		y = gp.tileSize * 6 - gp.tileSize / 16;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
		g2.drawString("Stamina", x, y);
		drawPlayerStamina();
		text = gp.player.stamina + " / " + gp.player.maxStamina;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
		g2.setColor(Color.WHITE);
		y = gp.tileSize * 7 + gp.tileSize / 8;
		g2.drawString(text, x, y);

		// Monster Window
		x = gp.tileSize * 5;
		y = gp.tileSize / 2;
		width = gp.tileSize * 11 - gp.tileSize / 2;
		height = gp.tileSize * 8 - gp.tileSize / 2;
		drawSubWindow(x, y, width, height);
		g2.drawImage(gp.stage.combat, x * 2, y * 2, gp.tileSize * 5, gp.tileSize * 5, null);
		drawMonsterLife();
//		drawBackground(x, y);

		if (gp.keyH.checkStats) {
			x = gp.tileSize * 6;
			y = gp.tileSize * 3;
			text = "Life: " + gp.monster[gp.currentMap][gp.combat.monsterID].life;
			g2.drawString(text, x, y);

			y += gp.tileSize;
			text = "Attack: " + gp.monster[gp.currentMap][gp.combat.monsterID].attack;
			g2.drawString(text, x, y);

			y += gp.tileSize;
			text = "Defense: " + gp.monster[gp.currentMap][gp.combat.monsterID].defense;
			g2.drawString(text, x, y);

		}

		// Option Window
		x = gp.tileSize / 2;
		y = gp.tileSize * 9 - gp.tileSize / 2;
		width = gp.screenWidth - gp.tileSize * 1;
		height = gp.tileSize * 3;
		drawSubWindow(x, y, width, height);

		x = gp.tileSize + gp.tileSize / 2;
		y = gp.tileSize * 10 + gp.tileSize / 4;

		if (battleScreenState == 0) {

			g2.drawString("ATTACK", x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - gp.tileSize / 2, y);
			}
			x += gp.tileSize * 4;
			g2.drawString("PASS", x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - gp.tileSize / 2, y);
			}
			x += gp.tileSize * 4;
			g2.drawString("ITEMS", x, y);
			if (commandNum == 2) {
				g2.drawString(">", x - gp.tileSize / 2, y);
			}
			x += gp.tileSize * 4;
			g2.drawString("RUN", x, y);
			if (commandNum == 3) {
				g2.drawString(">", x - gp.tileSize / 2, y);
			}
		}

		else if (battleScreenState == 1) {
			y = gp.tileSize * 10;

			// Test
			for (int i = 0; i < 4; i++) {

				if (commandNum == i) {
					g2.drawString(">", x - gp.tileSize / 2, y);
				}

				if (gp.player.stamina < gp.player.attackslot[i].getCost()) {
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
				} else {
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				}
				g2.drawString(gp.player.attackslot[i].getName(), x, y);

				Color c = new Color(23, 239, 0);
				g2.setColor(c);
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
				g2.drawString(String.valueOf("-" + gp.player.attackslot[i].getCost()), x + gp.tileSize / 2,
						y + gp.tileSize / 2);
				g2.setColor(Color.WHITE);
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));

				x += gp.tileSize * 4;

			}

		}

		else if (battleScreenState == 2) {
//
//			gp.combat.turns(commandNum);
//
//			g2.drawString(currentDialogue, getXforCenteredText(currentDialogue), y);
//
//			if (messageCounter == 2) {
//				battleScreenState = 0;
//				if (gp.player.stamina < gp.player.maxStamina)
//					gp.player.stamina += 1;
//			}
//
		}

//		else if (battleScreenState == 3) {
//
//		}
	}

	public void drawPlayerStamina() {
//		// Draw max stamina
		int x = gp.tileSize / 2;
		int y = gp.tileSize * 2;
		int width = gp.tileSize * 4;
		int height = gp.tileSize / 2;

		if (gp.gameState == gp.battleState) {
			x = gp.tileSize;
			y = gp.tileSize * 6 + 2;
			width = gp.tileSize * 3;
		}

		// Base
		Color c = new Color(8, 136, 8);
		g2.setColor(c);
		g2.fillRect(x - 1, y - 1, width + 2, height + 2);

		// Stamina
		double percentage = ((double) gp.player.stamina / gp.player.maxStamina);
		double healthRemaining = percentage * width;

		c = new Color(23, 239, 0);
		g2.setColor(c);
		g2.fillRect(x, y, (int) healthRemaining, height);
	}

	public void drawPlayerMana() {
//		// Draw max mana
		int x = gp.tileSize / 2;
		int y = gp.tileSize * 2;
		int width = gp.tileSize * 4;
		int height = gp.tileSize / 2;

		if (gp.gameState == gp.battleState) {
			x = gp.tileSize;
			y = gp.tileSize * 4 - gp.tileSize / 16;
			width = gp.tileSize * 3;
		}

		// Base
		Color c = new Color(8, 8, 136);
		g2.setColor(c);
		g2.fillRect(x - 1, y - 1, width + 2, height + 2);

		// Mana
		double percentage = ((double) gp.player.mana / gp.player.maxMana);
		double healthRemaining = percentage * width;

		c = new Color(0, 23, 239);
		g2.setColor(c);
		g2.fillRect(x, y, (int) healthRemaining, height);
	}

	// Arreglar
//	public void drawBackground(int x, int y) {
//
//		g2.drawImage(gp.stage.combat[0], x, y, gp.tileSize * 3, gp.tileSize * 3, null);
//
//	}

	public void drawTitleScreen() {

		switch (titleScreenState) {
		case 0:
			title_main();
			break;

		case 1:
			title_character();
			break;

		case 2:
			title_name();
			break;
		}

	}

	public void title_main() {
		// SETTING THE BACKGROUND COLOR
		g2.setColor(new Color(70, 120, 80));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
		String text = "Capsellan";
		int x = getXforCenteredText(text);
		int y = gp.tileSize * 3;

		// Shadow
		g2.setColor(Color.black);
		g2.drawString(text, x + 5, y + 5);

		// Main color
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		// Image
		x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
		y += gp.tileSize * 2;
//					g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
		g2.drawImage(gp.item[gp.currentMap][2].down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

		// Menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		text = "NEW GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize * 3.5;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y);
		}

		text = "LOAD GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize, y);
		}

		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize, y);
		}
	}

	public void title_character() {
		g2.setColor(new Color(70, 120, 80));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		// Class Selection Screen
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(42F));

		String text = "Select your character";
		int x = getXforCenteredText(text);
		int y = gp.tileSize * 3;
		g2.drawString(text, x, y);

		text = "Fighter";
		x = getXforCenteredText(text);
		y += gp.tileSize * 3;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y);
		}

		text = "Sorcerer";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize, y);
		}

		text = "Back";
		x = getXforCenteredText(text);
		y += gp.tileSize * 2;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize, y);
		}
	}

	public void title_name() {
		g2.setColor(new Color(70, 120, 80));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(45F));
		g2.drawString("Please enter your name:", getXforCenteredText("Please enter your name:"), gp.tileSize * 2);

		g2.drawString("V", gp.tileSize * (commandNum + 3) + 14, gp.tileSize * 3);

		for (int i = 3; i < 13; i++) {
			g2.drawRect(gp.tileSize * i, gp.tileSize * 3 + 9, gp.tileSize, gp.tileSize);
		}

		for (int i = 0; i < gp.keyH.text.length; i++) {
			g2.drawString(gp.keyH.text[i] + " ", gp.tileSize * (i + 3) + 14, gp.tileSize * 4);
		}

		g2.drawString("Press Enter to continue", getXforCenteredText("Press Enter to continue"), gp.tileSize * 6);
	}

	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 2;
		g2.drawString(text, x, y);
	}

	public void drawDialogueScreen() {
		// WINDOW
		int x = gp.tileSize * 3;
		int y = gp.tileSize / 2;
		int width = gp.screenWidth - (gp.tileSize * 6);
		int height = gp.tileSize * 4;
		drawSubWindow(x, y, width, height);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28));
		x += gp.tileSize;
		y += gp.tileSize;

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}

	}

	public void drawSubWindow(int x, int y, int width, int height) {

		Color c = new Color(0, 0, 0, 220);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		// Se define el borde
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5)); // 5 pixels
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

	}

	public void drawGameOverScreen() {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

		text = "Game Over";

		// Shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize * 4;
		g2.drawString(text, x, y);

		// Main
		g2.setColor(Color.white);
		g2.drawString(text, x - 4, y - 4);

		// Retry
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - 40, y);
		}

		// Return to Title
		text = "Quit";
		x = getXforCenteredText(text);
		y += 55;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - 40, y);
		}
	}

	public void drawOptionScreen() {
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(32F));

		// SUB WINDOW
		int frameX = gp.tileSize * 6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 8;
		int frameHeight = gp.tileSize * 10;

		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		switch (subState) {
		case 0:
			option_top(frameX, frameY);
			break;
		case 1:
			option_fullScreenNotification(frameX, frameY);
			break;
		case 2:
			options_control(frameX, frameY);
			break;
		case 3:
			options_endGameConfirmation(frameX, frameY);
			break;
		}

		gp.keyH.select = false;
	}

	public void option_top(int frameX, int frameY) {
		int textX;
		int textY;

		String text = "Options";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);

		// FULL SCREEN ON/OFF
		textX = frameX + gp.tileSize;
		textY += gp.tileSize * 2;
		g2.drawString("Full Screen", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.select) {
				if (gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				} else if (gp.fullScreenOn) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
		}

		// MUSIC
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
		}

		// SE
		textY += gp.tileSize;
		g2.drawString("SE", textX, textY);
		if (commandNum == 2) {
			g2.drawString(">", textX - 25, textY);
		}

		// CONTROL
		textY += gp.tileSize;
		g2.drawString("Control", textX, textY);
		if (commandNum == 3) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.select) {
				subState = 2;
				commandNum = 0;
			}
		}

		// END GAME
		textY += gp.tileSize;
		g2.drawString("End Game", textX, textY);
		if (commandNum == 4) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.select) {
				subState = 3;
				commandNum = 0;
			}
		}

		// BACK
		textY += gp.tileSize * 2;
		g2.drawString("Back", textX, textY);
		if (commandNum == 5) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.select) {
				gp.gameState = gp.playState;
				commandNum = 0;
			}
		}

		// FULL SCREEN CHECK BOX
		textX = frameX + (int) (gp.tileSize * 4.5);
		textY = frameY + gp.tileSize * 2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if (gp.fullScreenOn) {
			g2.fillRect(textX, textY, 24, 24);
		}

		// MUSIC VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24); // 120/5 = 24
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);

		// SE VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);

		gp.config.saveConfig();
	}

	public void option_fullScreenNotification(int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 3;

		currentDialogue = "The change will take \neffect after restarting \nthe game.";

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}

		// BACK
		textY = frameY + gp.tileSize * 9;
		g2.drawString("Back", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.select) {
				subState = 0;
			}
		}
	}

	public void options_control(int frameX, int frameY) {
		int textX;
		int textY;

		// TITLE
		String text = "Controls";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);

		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Confirm", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Character Screen", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Pause", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Options", textX, textY);
		textY += gp.tileSize;

		textX = frameX + gp.tileSize * 6;
		textY = frameY + gp.tileSize * 2;
		g2.drawString("WASD", textX, textY);
		textY += gp.tileSize;
		g2.drawString("ENTER", textX, textY);
		textY += gp.tileSize;
		g2.drawString("C", textX, textY);
		textY += gp.tileSize;
		g2.drawString("P", textX, textY);
		textY += gp.tileSize;
		g2.drawString("ESC", textX, textY);
		textY += gp.tileSize;

		// BACK
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize * 9;
		g2.drawString("Back", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.select) {
				subState = 0;
				commandNum = 3;
			}
		}
	}

	public void options_endGameConfirmation(int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 3;

		currentDialogue = "Quit the game and \nreturn to the title screen?";

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}

		// YES
		String text = "Yes";
		textX = getXforCenteredText(text);
		textY += gp.tileSize * 3;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.select) {
				System.exit(1);
//				subState = 0;
//				titleScreenState = 0;
//				gp.gameState = gp.tileSize;

			}
		}

		// NO
		text = "No";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.select) {
				subState = 0;
				commandNum = 4;
			}
		}
	}

	public void drawTransition() {
		counter += 2;
		g2.setColor(new Color(0, 0, 0, counter * 5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		if (counter == 50) {
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
		}
	}

	public void drawTradeScreen() {
		switch (subState) {
		case 0:
			trade_select();
			break;
		case 1:
			trade_buy();
			break;
		case 2:
			trade_sell();
			break;
		}
		gp.keyH.select = false;
	}

	public void trade_select() {
		drawDialogueScreen();

		// DRAW WINDOW
		int x = gp.tileSize * 15;
		int y = gp.tileSize * 4;
		int width = gp.tileSize * 3;
		int height = (int) (gp.tileSize * 3.5);
		drawSubWindow(x, y, width, height);

		// DRAW TEXT
		x += gp.tileSize;
		y += gp.tileSize;
		g2.drawString("Buy", x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize / 2, y);
			if (gp.keyH.select) {
				subState = 1;
			}
		}
		y += gp.tileSize;
		g2.drawString("Sell", x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize / 2, y);
			if (gp.keyH.select) {
				subState = 2;
			}
		}
		y += gp.tileSize;
		g2.drawString("Leave", x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize / 2, y);
			if (gp.keyH.select) {
				commandNum = 0;
				gp.gameState = gp.dialogueState;
				currentDialogue = "Come again!";
			}
		}
		y += gp.tileSize;
	}

	public void trade_buy() {

		// DRAW PLAYER INVENTORY
		drawInventory(gp.player, false);
		// DRAW NPC INVENTORY
		drawInventory(npc, true);

		// DRAW HINT WINDOW
		int x = gp.tileSize * 2;
		int y = gp.tileSize * 9;
		int width = gp.tileSize * 6;
		int height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x + 24, y + 60);

		// DRAW PLAYER COIN WINDOW
		x = gp.tileSize * 12;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if (itemIndex < npc.inventory.size()) {
			x = (int) (gp.tileSize * 5.5);
			y = (int) (gp.tileSize * 5.5);
			width = (int) (gp.tileSize * 2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

			int price = npc.inventory.get(itemIndex).price;
			String text = "" + price;
			x = getXforAlignToRightText(text, gp.tileSize * 8 - 20);
			g2.drawString(text, x, y + 34);

			// BUY AN ITEM
			if (gp.keyH.select) {
				if (npc.inventory.get(itemIndex).price > gp.player.coin) {
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "You nedd more coin to buy that!";
					drawDialogueScreen();
				} else if (gp.player.inventory.size() == gp.player.maxInventorySize) {
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "You cannot carry any more!";
				} else {
					gp.player.coin -= npc.inventory.get(itemIndex).price;
					gp.player.inventory.add(npc.inventory.get(itemIndex));
				}
			}
		}

	}

	public void trade_sell() {

		// DRAW PLAYER INVENTORY
		drawInventory(gp.player, true);

		int x;
		int y;
		int width;
		int height;

		// DRAW HINT WINDOW
		x = gp.tileSize * 2;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x + 24, y + 60);

		// DRAW PLAYER COIN WINDOW
		x = gp.tileSize * 12;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if (itemIndex < gp.player.inventory.size()) {
			x = (int) (gp.tileSize * 15.5);
			y = (int) (gp.tileSize * 5.5);
			width = (int) (gp.tileSize * 2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

			int price = gp.player.inventory.get(itemIndex).price / 2;
			String text = "" + price;
			x = getXforAlignToRightText(text, gp.tileSize * 18 - 20);
			g2.drawString(text, x, y + 34);

			// SELL AN ITEM
			if (gp.keyH.select) {
//				if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon || gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
//					commandNum = 0;
				// subState = 0;
//					gp.gameState = gp.dialogueState;
//				currentDialogue = "You cannot sell an equipped item!";
//				}
//			else {}
				gp.player.inventory.remove(itemIndex);
				gp.player.coin += price;
			}
		}

	}

	public int getXforCenteredText(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;
	}

	public int getXforAlignToRightText(String text, int tailX) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}
