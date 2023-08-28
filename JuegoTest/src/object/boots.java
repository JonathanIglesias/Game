package object;

import entity.Entity;
import main.GamePanel;

public class boots extends Entity {
	GamePanel gp;

	public boots(GamePanel gp) {
		super(gp);

		name = "boots";

		down1 = setup("/objects/botas", gp.tileSize, gp.tileSize);

		collission = true;

		price = 50;
	}

}
