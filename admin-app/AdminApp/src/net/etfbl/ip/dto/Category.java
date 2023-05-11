package net.etfbl.ip.dto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String type;
	private List<Attribute> attributes = new ArrayList<Attribute>();
	
	public Category(Long id, String type) {
		super();
		this.id = id;
		this.type = type;
		//this.attributes = attributes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

}
