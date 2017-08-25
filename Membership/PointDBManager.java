package Membership;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PointDBManager {


	private Connection getConnection() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "java7";
		String pw = "java7";

		Connection con = 
				DriverManager.getConnection(url,id,pw);
		return con;

	}

	public int insertMember(String name, int contact, int zero) throws Exception{// 멤버 넣기 위해 이름, 연락처를 받는 것.
		Connection con = this.getConnection();
		String sql = "insert into membership values(?,?,?)";//쿼리작성 //멤버라는테이블의seq칼럼에시퀀스기능추가
		PreparedStatement pstat = con.prepareStatement(sql);
		pstat.setString(1, name);
		pstat.setInt(2, contact);
		pstat.setInt(3, zero);
		int result = pstat.executeUpdate();

		con.commit();
		con.close();

		return result;
	}

	public int updatePoint (int point, int contact) throws Exception { 
		// updatePoint 메써드는 point와 contact를 받아오고 int값을 실질적으로 계산을 하는 class에 넘겨준다.
		// 그때 넘겨주는 int값은 result로 담는다.
		Connection con = this.getConnection();
		
		String sql = "update membership set point=? where contact like ?";
		PreparedStatement pstat = con.prepareStatement(sql);
		
		pstat.setInt(1, point);
		pstat.setInt(2, contact);
		int result = pstat.executeUpdate();
		
		con.commit();
		con.close();
		return result;
	}


	public ArrayList<MembershipPoint> selectPoint(int contact) throws Exception{

		Connection con = this.getConnection();
		String sql = "select * from membership where contact = ?";
		PreparedStatement pstat = con.prepareStatement(sql);
		pstat.setInt(1, contact);
		ResultSet rs = pstat.executeQuery();

		ArrayList<MembershipPoint> result = new ArrayList<MembershipPoint>();	//ResultSet에 있는 자료형이 여러개라 담을려고 어레이리스트 만들고, 그것들을 풀으려고 하니 제네릭을 써야함으로 스튜던트형 클래스를 만들어서 안에 id등등을 다 담았음
		while(rs.next()){
			String name = rs.getString("name");
			int point = rs.getInt("point");

			result.add(new MembershipPoint(name,contact, point));
		}
		return result;
	}

	


}
