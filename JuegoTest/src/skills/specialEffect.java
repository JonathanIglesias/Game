package skills;

import java.util.Vector;

import entity.Entity;

public class specialEffect {

	private int duration;
	private String type;
	private String objective;
	private Vector<String> attributeList;
	private Vector<Integer> valueList;
	private int timePass;
	private boolean activated;

	public specialEffect(int duration, String type, String objective, Vector<String> attributeList,
			Vector<Integer> valueList) {
		this.duration = duration;
		this.type = type;
		this.objective = objective;
		this.attributeList = attributeList;
		this.valueList = valueList;

		activated = false;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Vector<String> getAttributeList() {
		return attributeList;
	}

	public void addAttribute(String attribute) {
		attributeList.add(attribute);
	}

	public void setAttributeList(Vector<String> attributeList) {
		this.attributeList = attributeList;
	}

	public Vector<Integer> getValueList() {
		return valueList;
	}

	public void setValueList(Vector<Integer> valueList) {
		this.valueList = valueList;
	}

	public void getEffect(Entity entity) {
		if (!activated) {
			Vector<String> list = getAttributeList();
			Vector<Integer> value = getValueList();

			for (int i = 0; i < list.size(); i++) {

				int attributes = getEntityAttributes(entity, list.get(i));

				switch (type) {
				case "buff":
					attributes += value.get(i);
					break;

				case "debuff":
					attributes -= value.get(i);
					break;
				}
			}
			activated = true;
		}
		timePass = duration;

	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	private int getEntityAttributes(Entity entity, String attributes) {
		switch (attributes) {
		case "level":
			return entity.level;
		case "strength":
			return entity.strength;
		case "dexterity":
			return entity.dexterity;
		case "attack":
			return entity.attack;
		case "defense":
			return entity.defense;
		}
		return 0;
	}

	public boolean isExpired() {
		return timePass <= 0;
	}

	public void check(Entity entity) {
		if (isExpired()) {
			Vector<String> list = getAttributeList();
			Vector<Integer> value = getValueList();

			for (int i = 0; i < list.size(); i++) {

				int attributes = getEntityAttributes(entity, list.get(i));

				switch (type) {
				case "buff":
					if (list.get(i).equals("life")) {
						break;
					}
					attributes -= value.get(i);
					break;

				case "debuff":
					attributes += value.get(i);
					break;
				}
			}
			activated = false;
		} else {
			timePass--;
		}

	}
}
