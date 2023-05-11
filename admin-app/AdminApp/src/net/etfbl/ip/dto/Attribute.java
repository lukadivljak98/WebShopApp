package net.etfbl.ip.dto;
import java.io.Serializable;


public class Attribute implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Long categoryId;
	
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Attribute(Long id, String name, Long categoryId) {
		super();
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
