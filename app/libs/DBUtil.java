
import java.sql.*;
  
 public class DBUtil {
   //其中mysql是数据库名称，在mysql57版本的数据库中已经预先新建完成；3306是mysql数据库的端口号。
	 private static final String url = "jdbc:mysql://localhost:3306/sys?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
   //com.mysql.jdbc.Driver是mysql-connector-java-5.1.40中的驱动包路径
   private static String driverClass="com.mysql.cj.jdbc.Driver";
   //mysql的账号和密码是在安装mysql中进行设置的，这里拿来用即可。
   private static String username="root";
   private static String password="903064675";
   private static Connection conn;
   //装载驱动
   static{
     try{
       Class.forName(driverClass);
     }
     catch(ClassNotFoundException e){
       e.printStackTrace();
     }
   }
   //获取数据库连接
   public static Connection getConnection(){
     try{
       conn=DriverManager.getConnection(url,username,password);
     }
     catch(SQLException e){
       e.printStackTrace();
     }
     return conn;
   }
   //建立数据库连接
   public static void main(String[] args){
     Connection conn=DBUtil.getConnection();
     if(conn!=null){
       System.out.println("数据库连接成功");
     }
     else{
       System.out.println("数据库连接失败");
     }
   }
   //关闭数据库连接
   public static void Close(){
     if(conn!=null){
       try{
         conn.close();
       }
       catch(SQLException e){
         e.printStackTrace();
       }
     }
   }
 }
