package petfinder.site.common.pet;

import alloy.util.Identifiable;

/**
 * Created by jlutteringer on 8/23/17.
 */
public class PetDto implements Identifiable {
	private Long id;
	private String name;
	private String type;
	private String owner;
	private String subtype;
	private String preferences;
	public PetDto(){}
	public PetDto(Long id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.owner = null;
		this.subtype = null;
		preferences = "";
	}
	public PetDto(Long id, String name, String type, String owner, String subtype) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.owner = owner;
		this.subtype = subtype;
		preferences = "";
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getPreferences() {
		return preferences;
	}

	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}
}