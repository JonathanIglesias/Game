package main;

import entity.Entity;

public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][][];

	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

		int map = 0;
		int col = 0;
		int row = 0;
		while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {

			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height = 2;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

			col++;

			if (col == gp.maxWorldCol) {
				col = 0;
				row++;

				if (row == gp.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}
	}

	public void checkEvent() {

		if (canTouchEvent) {
			if (hit(0, 24, 47, "down")) {
				// Do some method
//				damagePit(gp.dialogueState);
//				teleport(gp.dialogueState);
			}
//			if (hit(9, 9, "up") || hit(14, 17, "up") || hit(5, 17, "up")) {
//				warning(gp.dialogueState);
//			}
			else if (hit(0, 26, 6, "right")) {
				// Do some method
//				damagePit(gp.dialogueState);
			}

			else if (hit(0, 14, 23, "up")) {
				teleport(1, 25, 28);
			} else if (hit(1, 25, 28, "any")) {
				teleport(0, 14, 23);
			} else if (hit(1, 25, 25, "up")) {
				System.out.println("Speak");
				speak(gp.npc[1][0]);
			}
		}

		// Check if the player is more than 1 title away from the last event
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if (distance > gp.tileSize) {
			canTouchEvent = true;
		}

	}

	public boolean hit(int map, int col, int row, String reqDirection) {

		boolean hit = false;

		if (map == gp.currentMap) {
			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
			eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
			eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

			if (gp.player.solidArea.intersects(eventRect[map][col][row])
					&& eventRect[map][col][row].eventDone == false) {
				if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
					hit = true;

					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
				}
			}

			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
		}

		return hit;
	}

	public void damagePit(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You fall into a pit";
		gp.player.life -= 1;
//		eventRect[col][row].eventDone = true;
	}

	public void teleport(int map, int col, int row) {
		gp.gameState = gp.transitionState;
		tempMap = map;
		tempCol = col;
		tempRow = row;
		canTouchEvent = false;
//		gp.playSE();
	}

	public void warning(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "The game is still under development. \nI still didn't created the option to enter the house";

	}

	public void healingPool(int gameState) {
		if (gp.keyH.select) {
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.ui.currentDialogue = "You drink the water \nYour health has been recover";
			gp.player.life = gp.player.maxLife;
			gp.aSetter.setMonster();
		}

		gp.keyH.select = false;
	}

	public void speak(Entity entity) {
		if (gp.keyH.select) {
			gp.gameState = gp.dialogueState;
			gp.player.attackCanceled = true;
			entity.speak();
		}
	}

}