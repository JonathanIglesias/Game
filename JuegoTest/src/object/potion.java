package object;

import entity.Entity;
import main.GamePanel;

public class potion extends Entity {

	GamePanel gp;

	public potion(GamePanel gp) {
		super(gp);

		this.gp = gp;
		value = 5;
		type = type_consumable;
		name = "Red Potion";
//		down1 = setup();
		description = "[Red Potion]\nHeals your life by " + value + ".";
	}

	public void use(Entity entity) {
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You drink the " + name + "!\n" + "Your life has been recovered by " + value + ".";
		entity.life += value;
		if (gp.player.life > gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
//		gp.playSE(value);

	}

}
