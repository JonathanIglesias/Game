package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {

	public NPC_OldMan(GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 1;

		getImage();
		setDialogue();
	}

	public void getImage() {
		up1 = setup("/NPC/OldMan/Back1", gp.tileSize, gp.tileSize);
		up2 = setup("/NPC/OldMan/Back2", gp.tileSize, gp.tileSize);
		up3 = setup("/NPC/OldMan/Back3", gp.tileSize, gp.tileSize);
		down1 = setup("/NPC/OldMan/Front1", gp.tileSize, gp.tileSize);
		down2 = setup("/NPC/OldMan/Front2", gp.tileSize, gp.tileSize);
		down3 = setup("/NPC/OldMan/Front3", gp.tileSize, gp.tileSize);
		left1 = setup("/NPC/OldMan/Left1", gp.tileSize, gp.tileSize);
		left2 = setup("/NPC/OldMan/Left2", gp.tileSize, gp.tileSize);
		left3 = setup("/NPC/OldMan/Left3", gp.tileSize, gp.tileSize);
		right1 = setup("/NPC/OldMan/Right1", gp.tileSize, gp.tileSize);
		right2 = setup("/NPC/OldMan/Right2", gp.tileSize, gp.tileSize);
		right3 = setup("/NPC/OldMan/Right3", gp.tileSize, gp.tileSize);
	}

	public void setDialogue() {
		dialogues[0] = "Hello, Player";
		dialogues[1] = "This world may only be \nused for testing code";
		dialogues[2] = "But that doesn't mean \nwe're going to limit ourselves.";
		dialogues[3] = "On the contrary, this is \nthe perfect place to see \nhow far we can go.";
		dialogues[4] = "I hope you enjoy it and say \nwhat you want to see \nor what you want us to improve";
	}

	public void setAction() {

		if (onPath) {
			int goalCol = 23;
			int goalRow = 19;

			searchPath(goalCol, goalRow);

		} else {

			actionLockCounter++;

			if (actionLockCounter == 120) {
				move = true;
				speed = 1;
				Random random = new Random();
				int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

				if (i <= 25) {
					direction = "up";
				}
				if (i > 25 && i <= 50) {
					direction = "down";
				}
				if (i > 50 && i <= 75) {
					direction = "left";
				}
				if (i > 70 && i <= 100) {
					direction = "right";
				}

			}

			if (actionLockCounter == 240) {
				move = false;
				speed = 0;
				actionLockCounter = 0;
				spriteNum = 1;
				spriteCounter = 0;
			}
		}
	}

	public void speak() {
		super.speak();

		onPath = true;
	}
}
