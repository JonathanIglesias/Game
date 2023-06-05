package main;

public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][];

	int previousEventX, previousEventY;
	boolean canTouchEvent = true;

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

		int col = 0;
		int row = 0;
		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}

	public void checkEvent() {

		if (canTouchEvent) {
			if (hit(24, 47, "down")) {
				// Do some method
				damagePit(gp.dialogueState);
				teleport(gp.dialogueState);
			}
			if (hit(9, 9, "up") || hit(14, 17, "up") || hit(5, 17, "up")) {
				warning(gp.dialogueState);
			}
			if (hit(26, 6, "right")) {
				// Do some method
				damagePit(gp.dialogueState);
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

	public boolean hit(int col, int row, String reqDirection) {

		boolean hit = false;

		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

		if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;

				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}

		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

		return hit;
	}

	public void damagePit(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You fall into a pit";
		gp.player.life -= 10;
//		eventRect[col][row].eventDone = true;
	}

	public void teleport(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "The game is still under development. \nPlease wait until further notice";
		gp.player.worldX = gp.tileSize * 9;
		gp.player.worldY = gp.tileSize * 11;
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
}
