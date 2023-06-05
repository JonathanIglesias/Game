package object;

import entity.Entity;
import main.GamePanel;

public class chest extends Entity {
	GamePanel gp;

	public chest(GamePanel gp) {
		super(gp);

		name = "chest";

		down1 = setup("/objects/cofre", gp.tileSize, gp.tileSize);

		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 40;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		collission = true;

	}
}
