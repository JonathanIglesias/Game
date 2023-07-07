package object;

import entity.Entity;
import main.GamePanel;

public class Sword_Normal extends Entity {

	public Sword_Normal(GamePanel gp) {
		super(gp);

		name = "Normal Sword";
//		down1 = setup("/objects/sword", gp.tileSize, gp.tileSize);
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		description = "[Normal Sword]\nIs just a regular sword";
	}

}
