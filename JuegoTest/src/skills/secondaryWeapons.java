package skills;

import main.GamePanel;

public class secondaryWeapons {

	GamePanel gp;
	private int id;
	private String name;
	private String description;
	private skills normal;

	public secondaryWeapons(GamePanel gp, int id, String name, String description) {
		super();
		this.gp = gp;
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public skills getNormal() {
		return normal;
	}

	public void setNormal(skills normal) {
		this.normal = normal;
	}

}
