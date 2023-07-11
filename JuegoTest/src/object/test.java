package object;

import entity.Entity;
import main.GamePanel;

public class test extends Entity {

	public test(GamePanel gp) {
		super(gp);

		name = "test";

		down1 = setup("/test", gp.tileSize, gp.tileSize);

		collission = true;

		description = "[" + name + "]\nEste objeto es usado para \n" + "verificar si funciona un codigo"
				+ "\ncorrectamente o simplemente " + "\nel desarrollador no sabe que dibujar"
				+ "\ny no quiere perder tiempo";
	}
}
