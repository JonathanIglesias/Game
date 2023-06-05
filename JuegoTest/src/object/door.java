package object;

import entity.Entity;
import main.GamePanel;

public class door extends Entity {
	GamePanel gp;

	public door(GamePanel gp) {

		super(gp);

		name = "door";

		down1 = setup("/objects/puerta", gp.tileSize, gp.tileSize);

		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 40;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		collission = true;
	}
}
