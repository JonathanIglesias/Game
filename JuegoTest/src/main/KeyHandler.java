package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

// Esta clase permite configurar los controles del juego (Por ahora)
public class KeyHandler implements KeyListener {

	GamePanel gp;

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean run, select;

//	public boolean enterPressed,shotKeyPressed;

	// DEBUG
	public boolean showDebugText = false;
	public boolean checkStats = false;

	// Time Limit
	public long lastTime;
	private static final long Limit = 50;

	public String[] text;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
		long lastTime = 0;
		text = new String[10];
		Arrays.fill(text, " ");
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// el getKeyCode() regresa un int asociado con el teclado. Por ejemplo el
		// KeyCode asociado con el espacio es 8
		int code = e.getKeyCode();
		long time = e.getWhen();
		long difference = time - lastTime;

		// System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));

		// Title State
		if (gp.gameState == gp.titleState) {
			titleState(code);
		}
		// BATTLE STATE
		else if (gp.gameState == gp.battleState && difference > Limit) {
			battleState(code);
		}

		// Play State
		else if (gp.gameState == gp.playState) {
			playState(code);
		}

		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			pauseState(code);
		}

		// DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}

		// CHARACTER STATE
		else if (gp.gameState == gp.characterState) {
			characterState(code);
		}

		// OPTION STATE
		else if (gp.gameState == gp.optionState) {
			optionState(code);
		}

		// GAME OVER STATE
		else if (gp.gameState == gp.gameOverState) {
			gameOverState(code);
		}

		// TRADE STATE
		else if (gp.gameState == gp.tradeState) {
			tradeState(code);
		}

		lastTime = time;
	}

	public void optionState(int code) {
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_ENTER) {
			select = true;
		}

		int maxCommandNum = 0;
		switch (gp.ui.subState) {
		case 0:
			maxCommandNum = 5;
			break;
		case 3:
			maxCommandNum = 1;
			break;
		}

		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			gp.ui.commandNum--;
//			gp.playSE();
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
			}
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			gp.ui.commandNum++;
//			gp.playSE();
			if (gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
//					gp.playSE();
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
//					gp.playSE();
				}
			}
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
//					gp.playSE();
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
//					gp.playSE();
				}
			}
		}
	}

	public void titleState(int code) {
		if (gp.ui.titleScreenState == 0) {

			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				gp.ui.commandNum--;
				gp.playSE(4);
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				gp.ui.commandNum++;
				gp.playSE(4);
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
				gp.playSE(1);
				if (gp.ui.commandNum == 0) {
//					gp.gameState = gp.playState;
					gp.ui.titleScreenState = 1;
//					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 1) {
					// coming soon
				}
				if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}
		}

		else if (gp.ui.titleScreenState == 1) {

			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				gp.playSE(4);
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				gp.playSE(4);
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
				gp.playSE(1);
				if (gp.ui.commandNum == 0) {
					System.out.println("Do some fighter specific stuff!");
					gp.ui.titleScreenState = 2;
//					gp.ui.commandNum = -1;

				}
				if (gp.ui.commandNum == 1) {
					System.out.println("Do some sorcerer specific stuff!");
//					gp.gameState = gp.playState;
					gp.ui.commandNum = 0;
					gp.ui.titleScreenState = 2;
				}
				if (gp.ui.commandNum == 2) {
					gp.ui.titleScreenState = 0;
				}

			}
		}

		else if (gp.ui.titleScreenState == 2) {
			if (code == KeyEvent.VK_ENTER) {
				for (int i = 0; i < text.length; i++) {
//					if (text[i] != " ")
					gp.player.name += text[i];
				}
				gp.playSE(1);
				gp.stopMusic();
				gp.gameState = gp.playState;
				gp.playMusic(5);
				gp.ui.titleScreenState = 0;
			}

			else if (code == KeyEvent.VK_BACK_SPACE && gp.ui.commandNum > 0) {
				for (int i = text.length - 1; i >= 0; i--) {
					if (text[i] != " ") {
						gp.ui.commandNum = i;
						text[gp.ui.commandNum] = " ";
						break;
					}
				}
			}

			else if (code == KeyEvent.VK_DELETE) {
				text[gp.ui.commandNum] = " ";
			}

			else if (code == KeyEvent.VK_LEFT) {
				gp.playSE(4);
				gp.ui.commandNum--;

			} else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_SPACE) {
				gp.playSE(4);
				gp.ui.commandNum++;

			} else if (KeyEvent.getKeyText(code).matches("[a-zA-Z]")) { // Corregir
//				text.set(gp.ui.commandNum, KeyEvent.getKeyText(code));
				text[gp.ui.commandNum] = KeyEvent.getKeyText(code);
				gp.ui.commandNum++;
			}

			if (text.length > 10) {
//				text.remove(text.length - 1);
				text[text.length - 1] = " ";
			}

			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 9;
			}
			if (gp.ui.commandNum > 9) {
				gp.ui.commandNum = 0;
			}
		}
	}

	public void playState(int code) {
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_SHIFT) {
			run = true;
		}
		if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
			select = true;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.pauseState;
		}
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionState;
		}

//		if (code == KeyEvent.VK_F) {
//			shotKeyPressed = true;
//		}

		// DEBUG
		if (code == KeyEvent.VK_T) {
			if (showDebugText == false) {
				showDebugText = true;
			} else if (showDebugText == true) {
				showDebugText = false;
			}
		}

		if (code == KeyEvent.VK_R) {
			switch (gp.currentMap) {
			case 0:
				gp.tileM.loadMap("/maps/mundo00.txt", 0);
				break;
			case 1:
				gp.tileM.loadMap("/maps/house00.txt", 0);
				break;
			}

		}
	}

	public void pauseState(int code) {
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
	}

	public void dialogueState(int code) {
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
			select = true;
		}
	}

	public void battleState(int code) {
//		if (code == KeyEvent.VK_T) {
//			if (checkStats == false) {
//				checkStats = true;
//			} else if (checkStats == true) {
//				checkStats = false;
//			}
//		}
//
//		if (gp.ui.battleScreenState == 0) {
//			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_D || code == KeyEvent.VK_LEFT) {
//				gp.ui.commandNum--;
//				gp.playSE(4);
//				if (gp.ui.commandNum < 0) {
//					gp.ui.commandNum = 3;
//				}
//			}
//			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_A
//					|| code == KeyEvent.VK_RIGHT) {
//				gp.ui.commandNum++;
//				gp.playSE(4);
//				if (gp.ui.commandNum > 3) {
//					gp.ui.commandNum = 0;
//				}
//			}
//
//			if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
//				gp.playSE(4);
//				if (gp.ui.commandNum == 0) {
////					select = false;
//					gp.playSE(1);
//					gp.ui.commandNum = 0;
//					gp.ui.battleScreenState = 1;
//				}
//
//				if (gp.ui.commandNum == 1) {
//					gp.ui.commandNum = -1;
//					gp.ui.messageCounter = 0;
//					gp.ui.battleScreenState = 2;
//				}
//
//				if (gp.ui.commandNum == 3) {
//					gp.playSE(1);
//					gp.stopMusic();
//					gp.playMusic(5);
//					gp.gameState = gp.playState;
//				}
//			}
//		} else if (gp.ui.battleScreenState == 1) {
//			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_D
//					|| code == KeyEvent.VK_LEFT) {
//				gp.ui.commandNum--;
//				gp.playSE(4);
//				if (gp.ui.commandNum < 0) {
//					gp.ui.commandNum = 3;
//				}
//			}
//			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_A || code == KeyEvent.VK_RIGHT) {
//				gp.ui.commandNum++;
//				gp.playSE(4);
//				if (gp.ui.commandNum > 3) {
//					gp.ui.commandNum = 0;
//				}
//			}
//			if (code == KeyEvent.VK_ESCAPE) {
//				gp.playSE(1);
//				gp.ui.battleScreenState = 0;
//			}
//			if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
//				gp.playSE(4);
//				if (gp.ui.commandNum >= 0 && gp.ui.commandNum < 4
//						&& gp.player.stamina >= gp.player.attackslot[gp.ui.commandNum].getCost()) {
//					gp.playSE(1);
//					gp.ui.battleScreenState = 2;
//					gp.ui.messageCounter = 0;
//				}
//			}
//
//		}
//
//		else if (gp.ui.battleScreenState == 2) {
//			if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
//				gp.playSE(1);
////				gp.ui.attackFase = true;
////				gp.combat.checkResult();
//				gp.combat.firstTime = true;
//				gp.combat.enemySlot = gp.combat.enemyOption();
//				gp.ui.messageCounter++;
//			}
//		}
	}

	public void characterState(int code) {
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}

		if (code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
		}

		playerInventory(code);

	}

	public void playerInventory(int code) {
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			if (gp.ui.playerSlotRow != 0) {
				gp.ui.playerSlotRow--;
			}
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			if (gp.ui.playerSlotRow != 0) {
				gp.ui.playerSlotRow--;
			}
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			if (gp.ui.playerSlotRow != 3) {
				gp.ui.playerSlotRow++;
			}
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			if (gp.ui.playerSlotCol != 4) {
				gp.ui.playerSlotCol++;
			}
		}
	}

	public void npcInventory(int code) {
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			if (gp.ui.npcSlotRow != 0) {
				gp.ui.npcSlotRow--;
			}
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			if (gp.ui.npcSlotRow != 0) {
				gp.ui.npcSlotRow--;
			}
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			if (gp.ui.npcSlotRow != 3) {
				gp.ui.npcSlotRow++;
			}
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			if (gp.ui.npcSlotCol != 4) {
				gp.ui.npcSlotCol++;
			}
		}
	}

	public void gameOverState(int code) {
		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
//			gp.playSE();
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
//			gp.playSE();
		}

		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.retry();
//				gp.playMusic();
			} else if (gp.ui.commandNum == 1) {
				gp.gameState = gp.tileSize;
				gp.restart();

			}
		}
	}

	public void tradeState(int code) {
		if (code == KeyEvent.VK_ENTER) {
			select = true;
		}

		if (gp.ui.subState == 0) {
			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
//				gp.playSE();
			}
			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
//				gp.playSE();
			}
		}
		if (gp.ui.subState == 1) {
			npcInventory(code);
			if (code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
		if (gp.ui.subState == 2) {
			playerInventory(code);
			if (code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_SHIFT) {
			run = false;
		}
		if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
			select = false;
		}
		if (code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
		}

//		if (code == KeyEvent.VK_F) {
//			shotKeyPressed = false;
//		}

	}

}
