package entity;

import main.GamePanel;
import object.boots;
import object.key;

public class NPC_Merchant extends Entity {

	public NPC_Merchant(GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 1;

		getImage();
		setDialogue();
		setItems();
	}

	public void getImage() {
		up1 = setup("/NPC/Merchant/Down1", gp.tileSize, gp.tileSize);
		up2 = setup("/NPC/Merchant/Down1", gp.tileSize, gp.tileSize);
		up3 = setup("/NPC/Merchant/Down1", gp.tileSize, gp.tileSize);
		down1 = setup("/NPC/Merchant/Down1", gp.tileSize, gp.tileSize);
		down2 = setup("/NPC/Merchant/Down1", gp.tileSize, gp.tileSize);
		down3 = setup("/NPC/Merchant/Down1", gp.tileSize, gp.tileSize);
		left1 = setup("/NPC/Merchant/Down1", gp.tileSize, gp.tileSize);
		left2 = setup("/NPC/Merchant/Down1", gp.tileSize, gp.tileSize);
		left3 = setup("/NPC/Merchant/Down1", gp.tileSize, gp.tileSize);
		right1 = setup("/NPC/Merchant/Down1", gp.tileSize, gp.tileSize);
		right2 = setup("/NPC/Merchant/Down1", gp.tileSize, gp.tileSize);
		right3 = setup("/NPC/Merchant/Down1", gp.tileSize, gp.tileSize);
	}

	public void setDialogue() {
		dialogues[0] = "He he he, hello Player. \nWhat're ya Buyin?";
	}

	public void setItems() {
		inventory.add(new boots(gp));
		inventory.add(new key(gp));
	}

	public void speak() {
		super.speak();
		gp.gameState = gp.tradeState;
		gp.ui.npc = this;
	}
}
