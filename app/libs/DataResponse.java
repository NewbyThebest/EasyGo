import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class DataResponse extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public DataResponse() {
		super();
	}

	/**
		 * Destruction of the servlet. <br>
		 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
		 * The doGet method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to get.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
		 * The doPost method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to post.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 String ID = request.getParameter("id"); 
	     String PW= request.getParameter("psw");
	     boolean type=false;
	 
	     response.setContentType("application/json; charset=utf-8");
		 response.setCharacterEncoding("UTF-8");
	     PrintWriter out = response.getWriter();
	     String resJson = "";
	     try
	     {
	       Connection con=DBUtil.getConnection();
//	       Statement stmt=con.createStatement();
	       //mysql数据库中的数据表，表名叫：idcard ，需要自己预先在数据库中进行创建，包含相应的字段和记录。
//	       String sql="select * from sys.idcard where name="+ID+" and year="+PW;
//	       ResultSet rs=stmt.executeQuery(sql);
	       Map<String,Object> resMap = new HashMap<>();    // 使用Map存储键值对
//	       while(rs.next())
//	       {
//	    	   String title = rs.getString("name");
//	    	   String price = rs.getString("year");
//	    	   resMap.put("title",title);   // 向Map对象中添加内容
//		       resMap.put("price",price);
//	       }
	       resMap.put("title","zsahdks");   // 向Map对象中添加内容
	       resMap.put("price","hsadhaiu");
	      
	       Gson gson = new Gson();
	       resJson = gson.toJson(resMap);
	     
	     }
	     catch(Exception ex)
	     {
	       ex.printStackTrace();
	     }
	     finally
	     {
	       DBUtil.Close();
	       out.print(resJson);
	       out.flush();
	       out.close();
	     }
	}

	/**
		 * Initialization of the servlet. <br>
		 *
		 * @throws ServletException if an error occurs
		 */
	public void init() throws ServletException {
		// Put your code here
	}

}
