package uEM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentRegister extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nameString=request.getParameter("fullname").toString();
		String userString=request.getParameter("username1").toString();
		String emailString=request.getParameter("email").toString();
		String upassString=request.getParameter("upass").toString();
		
		try {
			addData(nameString, userString, emailString, upassString);
			response.sendRedirect("client_login.jsp");
		} catch (ClassNotFoundException | SQLException e) {
			HttpSession session= request.getSession();
			session.setAttribute("registFail", "true");
			response.sendRedirect("regiserClient.jsp");
		}
	}
	public void addData(String name,String username,String email,String password) throws ClassNotFoundException, SQLException 
	{
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3307/ewa";
		String uname="root";
		String pass="3241";
		String query="insert into usercreds values (?,?,?,?)";
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

	

}

	

}
