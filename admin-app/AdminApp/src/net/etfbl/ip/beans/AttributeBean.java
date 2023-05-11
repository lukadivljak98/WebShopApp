package net.etfbl.ip.beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import net.etfbl.ip.dao.AttributeDAO;
import net.etfbl.ip.dto.Attribute;

public class AttributeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public boolean add(Attribute attribute) {
		return AttributeDAO.insert(attribute);
	}

	public List<Attribute> getAttributesByCategoryId(long categoryId) {
		return AttributeDAO.selectByCategoryId(categoryId);
	}

	public void delete(Attribute attribute) {
		try {
			AttributeDAO.delete(attribute);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Attribute attribute) {
		
	}
}
