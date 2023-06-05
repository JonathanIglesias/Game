package object;

import java.awt.Graphics2D;

import entity.Entity;
import main.GamePanel;

public class HealthBar extends Entity {
	GamePanel gp;
	Graphics2D g2;

	public HealthBar(GamePanel gp) {
		super(gp);
//		super(new Color(0, 255, 0), 140, 24, Style.ROUND, max, current);

		name = "HealthBar";
	}

	public void showHealthBar() {
//		g2.setColor(null);
//		g2.fillRect(maxLife, solidAreaDefaultY, life, actionLockCounter);

	}

}
