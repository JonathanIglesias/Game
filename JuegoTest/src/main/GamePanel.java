package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JPanel;

import entity.Entity;
import entity.player;
import skills.primaryWeapons;
import skills.skills;
import stage.stage;
import tile.tileManager;
import tile_interactive.InteractiveTile;

// En esta clase se va a configurar la resolucion
public class GamePanel extends JPanel implements Runnable {

	// Screen Settings
	final int originalTitleSize = 16; // 16 x 16 pixeles por defecto Tite(Mozaico)
	// Para escalar
	final int scale = 3; // 16 x 3 = 48 pixeles

	// El public se va utilizar para que se pueda acceder a todos los paquetes
	public final int tileSize = originalTitleSize * scale; // 48 x 48 tile

	// Tamaño de la ventana
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixeles
	public final int screenHeigth = tileSize * maxScreenRow;// 576 pixeles

	// WORLD SETTINGS
	public int maxWorldCol;
	public int maxWorldRow;

	// FPS
	int FPS = 60;

	// SYSTEM
	tileManager tileM = new tileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	sound music = new sound();
	sound se = new sound();
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Thread gameThread; // El juego vas a seguir a pesar que tu no hacer nada tambien tiene que ver con
						// los FPS
	public Combat combat = new Combat(this);

	// COLLISION
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);

	// ENTITY AND OBJECT
	public Vector<primaryWeapons> weapons = new Vector<>(10);
	public Vector<skills> abilities = new Vector<>(10);

	public player player = new player(this, keyH);
	public Entity npc[] = new Entity[10];
	public Entity item[] = new Entity[20];
	public Entity monster[] = new Entity[20];
	public ArrayList<Entity> entityList = new ArrayList<>();
	public InteractiveTile iTile[] = new InteractiveTile[50];
	public ArrayList<Entity> particleList = new ArrayList<>();
//	public ArrayList<Entity> projectileList = new ArrayList<>();

	public stage stage;

	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int battleState = 4;
	public final int characterState = 5;

	public GamePanel() {
		setPreferredSize(new Dimension(screenWidth, screenHeigth));
		setBackground(Color.black); // Opcional
		setDoubleBuffered(true); // Si se establece como verdadero, todos los dibujos de este componente se
									// realizarán en un búfer de pintura fuera de la pantalla.
									// Por lo tanto, al activarlo puede aumentar la renderizacion
		addKeyListener(keyH);
		// Con esto el juego se va a concentrar a recibir el key input
		setFocusable(true);
	}

	public void setupGame() {
		aSetter.setSkills("skills.txt");
		aSetter.setWeapons("primary.txt");
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
		aSetter.setStage();
		playMusic(6);
		gameState = titleState;

	}

	public void startGameThread() {
		gameThread = new Thread(this); // This quiere decir que es esta clase
		gameThread.start(); // Esta va a llamar el metodo run

	}

	// En este metodo se va hacer dos cosas:
	// 1. UPDATE: update information such as character position
	// 2. DRAW: draw the screen with the updated information.
	// Esto tambien dependera de los FPS. Por ejemplo, si el juego tiene 30FPS este
	// metodo lo hara 30 veces por segundo

	@Override
	public void run() {
		// Por ahora hay dos metodos que permiten restringir el juego para que corra
		// solo a 60 FPS

		// Metodo 1 @: Sleep method, usualmente se usan nano segundos como metodo de
		// Metodo 2 $: Delta, supuestamente es el mas eficiente a pesar de que los dos
		// hacen lo mismo
		// calculacion
		// Esto es 1 segundo en nano segundos
		double drawInterval = 1000000000 / FPS; // 0.0166666 segundos
		// $ double nextDrawTime = System.nanoTime() + drawInterval;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		// Para ver los FPS
		long timer = 0;
		int drawCount = 0;

		// Mientras que el gameThread este activo, se va a repetir el proceso
		while (gameThread != null) {
			/*
			 * // Para poder ver el tiempo actual que esta corriendo el sistema // long
			 * currentTime = System.nanoTime(); // System.out.println("Current Time: " +
			 * currentTime);
			 */

//			update();
//			repaint();

			// $
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			// $

			if (timer >= 1000000000) {
//				ui.showMessage("FPS: " + drawCount);
				// System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}

//	$		try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				// Esto es para pasar de nano segundo a mili segundos ya que el sleep solo
//				// acepta mili segundos
//				remainingTime = remainingTime / 1000000;
//				if (remainingTime < 0) {
//					remainingTime = 0;
//				}
//				Thread.sleep((long) remainingTime);
//
//				nextDrawTime += drawInterval;
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//	$		}

		}

	}

	public void update() {

		if (gameState == playState) {
			// Player
			player.update();
			// NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].update();
				}
			}
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					if (monster[i].alive == true && monster[i].dying == false) {
						monster[i].update();
					}
					if (monster[i].alive == false) {
						monster[i].checkDrop();
						monster[i] = null;
					}
				}
			}
//			for (int i = 0; i < projectileList.size(); i++) {
//				if (projectileList.get(i) != null) {
//					if (projectileList.get(i) == true) {
//						projectileList.get(i).update();
//					}
//					if (projectileList.get(i).alive == false) {
//						projectileList.remove(i);
//					}
//				}
//			}

			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					if (particleList.get(i).alive == true) {
						particleList.get(i).update();
					}
					if (particleList.get(i).alive == false) {
						particleList.remove(i);
					}
				}
			}

			for (int i = 0; i < iTile.length; i++) {
				if (iTile[i] != null) {
					iTile[i].update();
				}
			}

		}
		if (gameState == pauseState) {

		}
	}

	// Graphics es una clase que tiene la funcion de dibujar objetos en la patalla
	// El metodo repaint va a llamar a este metodo
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Graphics2D es una clase que extiende la clase Graphics. Este te da un control
		// mas sofisticado
		Graphics2D g2 = (Graphics2D) g;

		// DEBUG
		long drawStart = 0;
		if (keyH.showDebugText) {
			drawStart = System.nanoTime();
		}

		// TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);
		} else {
			// Mundo
			tileM.draw(g2);

//			// NPC
//			for (int i = 0; i < npc.length; i++) {
//				if (npc[i] != null) {
//					npc[i].draw(g2);
//				}
//			}
//
//			// Objeto
//			for (int i = 0; i < item.length; i++) {
//				if (item[i] != null) {
//					item[i].draw(g2, this);
//				}
//			}
//
//			// Jugador
//			player.draw(g2);

			// INTERACTIVE TILE
			for (int i = 0; i < iTile.length; i++) {
				if (iTile[i] != null) {
					iTile[i].draw(g2);
				}
			}

			// ADD ENTITY TO THE LIST
			entityList.add(player);

			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					entityList.add(npc[i]);
				}
			}

			for (int i = 0; i < item.length; i++) {
				if (item[i] != null) {
					entityList.add(item[i]);
				}
			}

			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
//			for (int i = 0; i < projectileList.size(); i++) {
//				if (projectileList.get(i) != null) {
//					entityList.add(projectileList.get(i));
//				}

			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					entityList.add(particleList.get(i));
				}

			}
			// SORT
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}

			});

			// Draw Entities
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}

			// EMPTY ENTITY LIST
			entityList.clear();

			// UI
			ui.draw(g2);
		}

		// DEBUG
		if (keyH.showDebugText) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;

			g2.setFont(new Font("Arial", Font.PLAIN, 20));
			g2.setColor(Color.white);
			int x = 10;
			int y = 400;
			int lineHeight = 20;

			g2.drawString("WorldX " + player.worldX, x, y);
			y += lineHeight;
			g2.drawString("WorldY " + player.worldY, x, y);
			y += lineHeight;
			g2.drawString("Col" + (player.worldX + player.solidArea.x) / tileSize, x, y);
			y += lineHeight;
			g2.drawString("Row" + (player.worldY + player.solidArea.y) / tileSize, x, y);
			y += lineHeight;

			g2.drawString("Draw Time: " + passed, x, y);
			System.out.println("Draw time: " + passed);
		}

		// Opcional, esto permite liberar un poco la memoria
		g2.dispose();
	}

	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		music.stop();
	}

	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}

}
