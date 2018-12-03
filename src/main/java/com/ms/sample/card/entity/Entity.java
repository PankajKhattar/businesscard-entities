package com.ms.sample.card.entity;

public class Entity {
	private final String type;
	private final String subType;

	public Entity(String type, String subType) {
		this.type = type;
		this.subType = subType;
	}
	public String getType() {
		return type;
	}

	public String getSubType() {
		return subType;
	}

	@Override
	public String toString() {
		return this.type+(this.subType == null?"":"_"+this.subType);
	}

	@Override
	public int hashCode() {
		return this.type.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Entity)){
			return false;
		}

		if(this.type != null && !this.type.equals(((Entity)obj).getType())){
			return false;
		}

		if(this.subType != null && !this.subType.equals(((Entity)obj).getSubType())){
			return false;
		}
		return true;
	}

}
