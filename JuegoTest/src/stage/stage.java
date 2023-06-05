package stage;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class stage {
	GamePanel gp;
	public BufferedImage combat;
//	public scene[] trade;
//	public scene[] dialog;

	public stage(GamePanel gp) {

		combat();
//		File directory = new File("/stage/combat");

//		combat = new BufferedImage;
//		trade = new scene[1];
//		dialog = new scene[1];

	}

	public void combat() {
		BufferedImage image = null;
		UtilityTool uTool = new UtilityTool();
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/stage/combat/test.png"));
//			image = uTool.scaleImage(image, gp.tileSize * 2, gp.tileSize * 2);
			combat = image;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
