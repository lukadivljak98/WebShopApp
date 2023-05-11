package net.etfbl.ip.beans;
import java.io.Serializable;
import java.util.ArrayList;

import net.etfbl.ip.dao.CategoryDAO;
import net.etfbl.ip.dto.Category;;

public class CategoryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public boolean add(Category category) {
		return CategoryDAO.insert(category);
	}
	
	public ArrayList<Category> getAll(){
		return CategoryDAO.selectAll();
	}
	
	public Category getById(long id) {
		return CategoryDAO.getById(id);
	}
	
	public boolean delete(Category category) {
		return CategoryDAO.delete(category);
	}
	
	public boolean update(Category category) {
		return CategoryDAO.update(category);
	}

}
