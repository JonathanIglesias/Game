package object;

import entity.Entity;
import main.GamePanel;

public class coin_Bronze extends Entity {

	GamePanel gp;

	public coin_Bronze(GamePanel gp) {
		super(gp);
		this.gp = gp;

		name = "Bronce Coin";
		type = type_pickupOnly;
		down1 = setup("/objects/monedaB", gp.tileSize, gp.tileSize);
		value = 1;
	}

	public void use(Entity entity) {
//		gp.playSE(value);
		gp.ui.addMessage("Coin +" + value);
		gp.player.coin += value;

	}

}
