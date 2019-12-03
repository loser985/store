package 工具;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class jdbcUtils {
	private static ComboPooledDataSource cpds=new ComboPooledDataSource();
	private static ThreadLocal<Connection> tl= new ThreadLocal<Connection>();
	public static DataSource getDataSource(){
		return cpds;
	}
	public static Connection getConnection() throws SQLException{
		Connection conn=tl.get();
		if(conn==null){
			conn=cpds.getConnection();
			tl.set(conn);
		}
		
		return conn;
	}
	//开启事务
	public static void startTransaction() throws SQLException{
		getConnection().setAutoCommit(false);
	}
	//事务提交
	public static void commitAndClose(){
		try {
			Connection conn=getConnection();
			conn.commit();	//事务提交
			conn.close();	//关闭资源
			tl.remove();	//解除线程绑定
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//事务回滚
	public static void rollbackAndClose(){
		try {
			Connection conn=getConnection();
			conn.rollback();
			conn.close();
			tl.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static void close(Connection conn,PreparedStatement pst,ResultSet rs) throws SQLException{
		if(conn!=null){
			conn.close();
		}
		close(pst, rs);
		conn=null;
	}
	public static void close(PreparedStatement pst, ResultSet rs) throws SQLException{
		if(pst!=null){
			pst.close();
		}
		if(rs!=null){
			rs.close();
		}
		pst=null;
		rs=null;
	}
}
