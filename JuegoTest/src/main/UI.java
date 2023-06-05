package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font maruMonica, arial_80B;
//	BufferedImage keyImage;
//	BufferedImage healthBar;
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

	public int slotCol = 0;
	public int slotRow = 0;

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
	}

	// CREATE HUD OBJECT
	// items Health = new Health(gp);

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
			drawInventory();
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
			y = gp.screenHeigth / 2 - (gp.tileSize * 3);
			g2.drawString(text, x, y);

			text = "Your time is: " + dFormat.format(playTime) + "!";
			textlength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth / 2 - textlength / 2;
			y = gp.screenHeigth / 2 + (gp.tileSize * 4);
			g2.drawString(text, x, y);

			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
//			g2.setFont(arial_80B);
			g2.setColor(Color.yellow);

			text = "Congratulations!";
			textlength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth / 2 - textlength / 2;
			y = gp.screenHeigth / 2 + (gp.tileSize * 2);
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

	public void drawInventory() {

		// FRAME
		int frameX = gp.tileSize * 9;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 6;
		int frameHeight = gp.tileSize * 5;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		// SLOT
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize + 3;

		// DRAW PLAYER'S ITEMS
		for (int i = 0; i < gp.player.inventory.size(); i++) {
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);

			slotX += slotSize;

			if (i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}

		// Cursor
		int cursorX = slotXstart + (slotSize * slotCol);
		int cursorY = slotYstart + (slotSize * slotRow);
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
		drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

		// DRAW DESCRIPTION TEXT
		int textX = dFrameX + 20;
		int textY = dFrameY + gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(28F));

		int itemIndex = getItemIndexOnSlot();

		if (itemIndex < gp.player.inventory.size()) {

			for (String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
				g2.drawString(line, textX, textY);
				textY += gp.tileSize;
			}
		}
	}

	public int getItemIndexOnSlot() {
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

		double percentage = ((double) gp.monster[gp.combat.monsterID].life / gp.monster[gp.combat.monsterID].maxLife);
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
		double percentage = ((double) gp.monster[gp.combat.monsterID].life / gp.monster[gp.combat.monsterID].maxLife);
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
			text = "Life: " + gp.monster[gp.combat.monsterID].life;
			g2.drawString(text, x, y);

			y += gp.tileSize;
			text = "Attack: " + gp.monster[gp.combat.monsterID].attack;
			g2.drawString(text, x, y);

			y += gp.tileSize;
			text = "Defense: " + gp.monster[gp.combat.monsterID].defense;
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

		if (titleScreenState == 0) {
			// SETTING THE BACKGROUND COLOR
			g2.setColor(new Color(70, 120, 80));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeigth);

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
//			g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
			g2.drawImage(gp.item[2].down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

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

		else if (titleScreenState == 1) {
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

		else if (titleScreenState == 2) {
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
	}

	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeigth / 2;
		g2.drawString(text, x, y);
	}

	public void drawDialogueScreen() {
		// WINDOW
		int x = gp.tileSize * 2;
		int y = gp.tileSize * 7;
		int width = gp.screenWidth - (gp.tileSize * 4);
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
