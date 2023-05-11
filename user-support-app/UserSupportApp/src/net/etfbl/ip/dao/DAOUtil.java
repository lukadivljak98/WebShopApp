package net.etfbl.ip.dao;

import java.sql.*;

import net.etfbl.ip.UserRole;

public final class DAOUtil {

	public static PreparedStatement prepareStatement(Connection connection,
			String sql, boolean returnGeneratedKeys, Object... values)
			throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(sql,
				returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS
						: Statement.NO_GENERATED_KEYS);
		setValues(preparedStatement, values);
		return preparedStatement;
	}

	public static void setValues(PreparedStatement preparedStatement,
			Object... values) throws SQLException {
		for (int i = 0; i < values.length; i++) {
			if(values[i] instanceof UserRole) {
				UserRole userRole = (UserRole) values[i];
				preparedStatement.setObject(i + 1, userRole.name(), Types.VARCHAR);
			} 
			else {
				preparedStatement.setObject(i + 1, values[i]);
			}
		}
	}
}
