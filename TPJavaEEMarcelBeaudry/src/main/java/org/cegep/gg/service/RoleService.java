package org.cegep.gg.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.cegep.gg.model.Role;

public class RoleService {

	private DataSource dataSource;

	public RoleService(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Role getRoleByDescription(String description) {
		String sql = "SELECT * FROM roles WHERE role = ?";

		ResultSet rs = null;
		try (Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, description);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Role role = new Role();
				role.setId(rs.getInt("id"));
				role.setRole(rs.getString("role"));
				return role;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
