package user;

import java.sql.Connection;
import java.sql.PreparedStatement;

import util.DatabaseUtil;

public class UserDAO {
   
	public int join(String ID,String userID, String score) {
		String SQL = "INSERT INTO USER VALUES (?,?,?)";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstmt =conn.prepareStatement(SQL);
			pstmt.setString(1,ID);
			pstmt.setString(2,userID);
			pstmt.setString(3,score);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
