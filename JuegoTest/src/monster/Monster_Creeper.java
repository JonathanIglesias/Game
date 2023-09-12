package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.coin_Bronze;
import skills.skills;

public class Monster_Creeper extends Entity {

	GamePanel gp;

	public Monster_Creeper(GamePanel gp) {
		super(gp);

		this.gp = gp;

		type = type_monster;
		name = "Creeper";

		level = 1;
		attack = 5;
		defense = 0;
		speed = 1;
		maxLife = 4;
		life = maxLife;

		exp = 2;

		attackslot[0] = new skills(gp, 0, "Tackle", "Hits with his body because he has no arms", 1, 2);
		attackslot[1] = new skills(gp, 0, "Self destruction", "Any creeper's favorite skill", 3, 5);
		attackslot[2] = new skills(gp, 0, "Canalize", "Concentrates for the next attack", 2, 0);
		attackslot[3] = new skills(gp, 0, "The sound of death", "Anyone who listens to it is terrified by what comes",
				1, 0);

		solidArea.x = 9;
		solidArea.y = 0;
		solidArea.width = 30;
		solidArea.height = 48;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImage();
	}

	public void getImage() {
		up1 = setup("/monster/Creeper/Back", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/Creeper/Back2", gp.tileSize, gp.tileSize);
		up3 = setup("/monster/Creeper/Back3", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/Creeper/Front", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/Creeper/Front2", gp.tileSize, gp.tileSize);
		down3 = setup("/monster/Creeper/Front3", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/Creeper/Left", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/Creeper/Left2", gp.tileSize, gp.tileSize);
		left3 = setup("/monster/Creeper/Left3", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/Creeper/Right", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/Creeper/Right2", gp.tileSize, gp.tileSize);
		right3 = setup("/monster/Creeper/Right3", gp.tileSize, gp.tileSize);
	}

	public void update() {
		super.update();

		int xDistance = Math.abs(worldX - gp.player.worldX);
		int yDistance = Math.abs(worldY - gp.player.worldY);
		int tileDistance = (xDistance + yDistance) / gp.tileSize;

		if (onPath == false && tileDistance < 5) {
			int i = new Random().nextInt(100) + 1;
			if (i > 50) {
				onPath = true;
			}
		}
		if (onPath && tileDistance > 20) {
			onPath = false;
		}
	}

	public void setAction() {

		if (onPath == true) {

			int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

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

	public void damageReaction() {
		actionLockCounter = 0;
//		direction = gp.player.direction;
		onPath = true;
	}

	public void checkDrop() {

		// CAST A DIE
		int i = new Random().nextInt(100) + 1;

		// SET THE MONSTER DROP
		if (i < 50) {
			dropItem(new coin_Bronze(gp));
		}
//		if(i >= 50 && i < 75) {
//			
//		}
//		if(i >= 75 && i < 100) {
//			
//		}
		if (i >= 50) {
			dropItem(new coin_Bronze(gp));
		}
	}

}
