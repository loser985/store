package 工具;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class jdbcUtils_2 {
	private static ComboPooledDataSource cpds=new ComboPooledDataSource();

	public static DataSource getDataSource(){
		return cpds;
	}
	public static Connection getConnection() throws SQLException{
		
		return cpds.getConnection();
	}
	public static void close(Connection conn,PreparedStatement pst, ResultSet rs) throws SQLException{
		if(conn!=null){
			conn.close();
		}
		if(pst!=null){
			pst.close();
		}
		if(rs!=null){
			rs.close();
		}
		conn=null;
		pst=null;
		rs=null;
		
	}
}
