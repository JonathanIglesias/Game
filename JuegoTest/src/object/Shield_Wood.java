package object;

import entity.Entity;
import main.GamePanel;

public class Shield_Wood extends Entity {

	public Shield_Wood(GamePanel gp) {
		super(gp);

		name = "Wood Shield";
//		down1 = setup(direction, attack, actionLockCounter);
		defenseValue = 1;
	}

}
