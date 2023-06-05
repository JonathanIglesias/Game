package skills;

import main.GamePanel;

public class skills {

	GamePanel gp;
	int id;
	String name;
	String description;
	int cost;
	int damage;
	specialEffect specialEffect;

	public skills(GamePanel gp, int id, String name, String description, int cost, int damage) {
		this.gp = gp;
		this.id = id;
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.damage = damage;

		specialEffect = null;
	}

	public GamePanel getGp() {
		return gp;
	}

	public void setGp(GamePanel gp) {
		this.gp = gp;
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

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public specialEffect getSpecialEffect() {
		if (specialEffect == null) {
			return null;
		}
		return specialEffect;
	}

	public void setSpecialEffect(specialEffect specialEffect) {
		this.specialEffect = specialEffect;
	}

}
