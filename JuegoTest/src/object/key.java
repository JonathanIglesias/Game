package object;

import entity.Entity;
import main.GamePanel;

public class key extends Entity {

	public key(GamePanel gp) {
		super(gp);

		name = "key";

		down1 = setup("/objects/llave", gp.tileSize, gp.tileSize);

		collission = true;

		description = "[" + name + "]\nA normal key";

		price = 25;
	}
}
