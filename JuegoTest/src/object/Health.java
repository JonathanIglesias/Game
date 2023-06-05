package object;

import entity.Entity;
import main.GamePanel;

public class Health extends Entity {
	GamePanel gp;

	public Health(GamePanel gp) {

		super(gp);

		name = "Health";

	}
}
