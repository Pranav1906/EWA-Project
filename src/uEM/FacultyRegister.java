package uEM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FacultyRegister extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("detail")!=null && request.getParameter("detail").equals("core")) 
		{
			request.removeAttribute("detail");
			String nameString=request.getParameter("FName").toString();
			String categString=request.getParameter("FCateg").toString();
			String hospString=request.getParameter("FCourse").toString();
			String timingsString=request.getParameter("FTimetable").toString();
			int slots= Integer.parseInt(request.getParameter("FSlots")) ;
			String detailsString=request.getParameter("Detais").toString();
			try {
				addData2(nameString, categString, hospString, timingsString, slots, detailsString);
				response.sendRedirect("FacultyHome.jsp");
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				response.getWriter().print(e);
			}
			return;
		}
		String nameString=request.getParameter("fullname").toString();
		String userString=request.getParameter("username1").toString();
		String emailString=request.getParameter("email").toString();
		String upassString=request.getParameter("upass").toString();
		
		try {
			addData(nameString, userString, emailString, upassString);
			response.sendRedirect("Faculty_login.jsp");
		} catch (ClassNotFoundException | SQLException e) {
			HttpSession session= request.getSession();
			session.setAttribute("DregistFail", "true");
			response.sendRedirect("registerFaculty.jsp");
		}
	}
	public void addData(String name,String username,String email,String password) throws ClassNotFoundException, SQLException 
	{
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3307/ewa";
		String uname="root";
		String pass="3241";
		String query="insert into Facultycreds values (?,?,?,?)";
		Connection con=DriverManager.getConnection(url,uname,pass);
		PreparedStatement st=con.prepareStatement(query);
		st.setString(1,name);
		st.setString(2,username);
		st.setString(3,email);
		st.setString(4,password);
		st.executeUpdate();
		st.close();
		con.close();
	}
	public void addData2(String name,String category,String hospital,String timings,int slots,String details) throws ClassNotFoundException, SQLException 
	{
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3307/ewa";
		String uname="root";
		String pass="3241";
		String query="insert into Faculty values (?,?,?,?,?,?)";
		Connection con=DriverManager.getConnection(url,uname,pass);
		PreparedStatement st=con.prepareStatement(query);
		st.setString(1,name);
		st.setString(2,category);
		st.setString(3,timings);
		st.setString(4,hospital);
		st.setString(5,details);
		st.setInt(6,slots);
		st.executeUpdate();
		st.close();
		con.close();
	}


}

