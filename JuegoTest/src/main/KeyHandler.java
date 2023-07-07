package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

// Esta clase permite configurar los controles del juego (Por ahora)
public class KeyHandler implements KeyListener {

	GamePanel gp;

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean run, select;
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
		lastTime = time;
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

		// DEBUG
		if (code == KeyEvent.VK_T) {
			if (showDebugText == false) {
				showDebugText = true;
			} else if (showDebugText == true) {
				showDebugText = false;
			}
		}

		if (code == KeyEvent.VK_R) {
			gp.tileM.loadMap("/maps/tiledata.txt");
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

		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			if (gp.ui.slotRow != 0) {
				gp.ui.slotRow--;
			}
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			if (gp.ui.slotCol != 0) {
				gp.ui.slotCol--;
			}
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			if (gp.ui.slotRow != 3) {
				gp.ui.slotRow++;
			}
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			if (gp.ui.slotCol != 4) {
				gp.ui.slotCol++;
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

	}

}