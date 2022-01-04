package uEM;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyAuthentication extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pass= req.getParameter("password").toString();
		String uname= req.getParameter("username").toString();
		HttpSession session= req.getSession();
		try {
			confirm(uname, pass, res,req);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			res.getWriter().print(e);
		}
	}
	public void confirm(String username,String password, HttpServletResponse res,HttpServletRequest req) throws ClassNotFoundException, SQLException, IOException 
	{
		Class.forName("com.mysql.jdbc.Driver");
		String url ="jdbc:mysql://localhost:3307/ewa";
		String uname="root";
		String pass="3241";
		String query="select * from Facultycreds where UserName=?";
		Connection con=DriverManager.getConnection(url,uname,pass);
		PreparedStatement st=con.prepareStatement(query);
		st.setString(1, username);
		ResultSet rs=st.executeQuery();
		HttpSession session=req.getSession();
		boolean isTrue=rs.next();
		String Cpass="";
		if(isTrue) 
		{
			Cpass=rs.getString(4);
		}
		if(!isTrue) 
		{
			//no such user present
			
			session.setAttribute("FloginFail", "true");
			session.setAttribute("FloginMessage", "No such User present");
			res.sendRedirect("Faculty_login.jsp");
		}
		else if(!Cpass.equals(password))	
		{
			session.setAttribute("FloginFail", "true");
			session.setAttribute("FloginMessage", "Wrong Password");
			res.sendRedirect("Faculty_login.jsp");
		}
		else
		{
			session.setAttribute("FName", username);
			res.sendRedirect("FacultyHome.jsp");
		}
		
	}

}