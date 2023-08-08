package main;

import javax.swing.JFrame;

public class main {

	public static JFrame window;

	public static void main(String[] args) {
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Con esto puedes cerrar el programa normalmente cuando
																// precionas "X"
		window.setResizable(false); // Esto es para que no ajusten la pantalla manualmente
		window.setTitle("Capsellan");

		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);

		gamePanel.config.loadConfig();
		if (gamePanel.fullScreenOn) {
			window.setUndecorated(true);
		}

		window.pack(); // Esto provoca que la ventana encaje con el tamaño establecido del GamePanel

		window.setLocationRelativeTo(null); // El juego estara en el centro de la pantalla
		window.setVisible(true); // Para poder ver la ventana y no se active en el fondo

		gamePanel.setupGame();
		gamePanel.startGameThread();

	}

}
