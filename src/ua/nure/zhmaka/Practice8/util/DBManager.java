package ua.nure.zhmaka.Practice8.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.zhmaka.Practice8.dto.Group;
import ua.nure.zhmaka.Practice8.dto.User;

public class DBManager {

	private static DBManager instance;

	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private DBManager() {
	}

	private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/practice8?user=root&password=root";

	private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";
	private static final String SQL_FIND_ALL_GROUPS = "SELECT * FROM groups";

	public List<User> findAllUsers() {
		List<User> users = new ArrayList<>();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(CONNECTION_URL);
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_USERS);
			while (rs.next()) {
				users.add(retreiveUser(rs));
			}
		} catch (SQLException ex) {

			ex.printStackTrace();
		} finally {
			close(rs, stmt, con);
		}

		return users;
	}

	public List<Group> findAllGroups() {
		List<Group> groups = new ArrayList<>();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(CONNECTION_URL);
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_GROUPS);
			while (rs.next()) {
				groups.add(retreiveGroup(rs));
			}
		} catch (SQLException ex) {

			ex.printStackTrace();
		} finally {
			close(rs, stmt, con);
		}

		return groups;
	}

	private static void close(ResultSet rs, Statement stat, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
		if (stat != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
		if (conn != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}

	private static User retreiveUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setLogin(rs.getString("login"));
		return user;
	}

	private static Group retreiveGroup(ResultSet rs) throws SQLException {
		Group group = new Group();
		group.setId(rs.getInt("id"));
		group.setName(rs.getString("name"));
		return group;
	}

	public void insertUser(User user) throws SQLException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String insertQuery = "INSERT INTO users (" + " login" + ") VALUES (" + " ?)";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			st = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, user.getLogin());
			int rows = st.executeUpdate();
			if (rows > 0) {
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					user.setId(rs.getInt(1));
				}
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new SQLException();
		} finally {
			close(rs, st, conn);
		}
	}

	public void insertGroup(Group group) throws SQLException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String insertQuery = "INSERT INTO groups (" + " name" + ") VALUES (" + " ?)";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			st = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, group.getName());
			int rows = st.executeUpdate();
			if (rows > 0) {
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					group.setId(rs.getInt(1));
				}
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new SQLException();
		} finally {
			close(rs, st, conn);
		}
	}

	public User getUser(String login) throws SQLException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		User user = new User();
		String selectQuery = "SELECT * FROM users WHERE login = ?";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			st = conn.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, login);
			rs = st.executeQuery();
			while (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setLogin(rs.getString("login"));
			}

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new SQLException();
		} finally {
			close(rs, st, conn);
		}
		return user;
	}

	public Group getGroup(String name) throws SQLException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Group group = new Group();
		String selectQuery = "SELECT * FROM groups WHERE name = ?";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			st = conn.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, name);
			rs = st.executeQuery();
			while (rs.next()) {
				group.setId(rs.getInt("id"));
				group.setName(rs.getString("name"));
			}

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new SQLException();
		} finally {
			close(rs, st, conn);
		}
		return group;
	}

	public void setGroupsForUser(User user, Group... groups) throws SQLException{
		Connection conn = null;
		PreparedStatement st = null;
		String insertQuery = "INSERT INTO users_groups(user_id,group_id) VALUES(?,?)";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			st = conn.prepareStatement(insertQuery);
			conn.setAutoCommit(false);

			for (Group group : groups) {
				st.setInt(1, user.getId());
				st.setInt(2, group.getId());
				int affectedRows = st.executeUpdate();
				if(affectedRows > 0) {
					conn.commit();
				}
			}
		} catch (SQLException e ) {
			System.out.println(e.toString());
	        if (conn != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                conn.rollback();
	            } catch(SQLException excep) {
	                System.out.println(excep.toString());
	                throw new SQLException();
	            }
	        }
	    }finally {
	    	conn.setAutoCommit(true);
	        close(conn,st);
	    }
	}
	
	public List<Group> getUserGroups(User user) throws SQLException{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Group> userGroup = new ArrayList<>();
		String getGroupsByUser = "Select g.id, g.name from users_groups ug "
				+ "inner join users u on u.id = ug.user_id inner join groups g on g.id = ug.group_id where u.id = ?";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			st = conn.prepareStatement(getGroupsByUser);
			st.setInt(1, user.getId());
			rs = st.executeQuery();
			while(rs.next()) {
				userGroup.add(retreiveGroup(rs));
			}
			
		}catch (SQLException ex) {
			System.out.println(ex.toString());
			throw new SQLException();
		}
		
		return userGroup;
	}
	
	public void deleteGroup(Group group) throws SQLException {
		Connection conn = null;
		PreparedStatement st = null;
		String deleteGroups = "DELETE FROM groups where id = ?";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			st = conn.prepareStatement(deleteGroups);
			st.setInt(1, group.getId());
			st.executeUpdate();
		}catch (SQLException ex) {
			System.out.println(ex.toString());
			throw new SQLException();
		}finally{
			close(conn,st);
		}
	}
	
	
	public void updateGroup(Group group) throws SQLException{
		Connection conn = null;
		PreparedStatement st = null;
		String updateGroups = "update groups set name = ? where id = ?";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			st = conn.prepareStatement(updateGroups);
			st.setString(1, group.getName());
			st.setInt(2, group.getId());
			st.executeUpdate();
		}catch (SQLException ex) {
			System.out.println(ex.toString());
			throw new SQLException();
		}finally{
			close(conn,st);
		}

	}
	private void close(Connection con,Statement st) throws SQLException {
		try {
        	if(st != null) {
        		st.close();
        	}
        	if(con !=null) {
        		con.close();
        	}
        }catch (SQLException e) {
        	 System.out.println(e.toString());
        	 throw new SQLException();
		}
	}

}
