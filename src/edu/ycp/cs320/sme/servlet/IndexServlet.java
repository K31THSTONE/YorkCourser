package edu.ycp.cs320.sme.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.sme.controller.StudentController;
import edu.ycp.cs320.sme.model.Schedule;
import edu.ycp.cs320.sme.model.Student;

public class IndexServlet extends HttpServlet {
	
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	  	
	  ProcessRequest( req,  resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	  
	  ProcessRequest( req,  resp);
  
	  
    
  }
  protected void ProcessRequest(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {
	  //parameter for detecting whether to (fetch) build teacher, or student and direct them to proper homePage
	 String type = req.getParameter("type");
	 
	 if(type == null || type.equals("")){
		 //This should never happen.
		 req.getRequestDispatcher("./index.html").forward(req, resp);
	 }else if(type.equals("student")){
		 StudentController controller = new StudentController();
		 Student persistantStudent = controller.buildStudent();
		 
		 HttpSession session = req.getSession(true);
		 System.out.println(session.getId());
		 session.setAttribute("user", persistantStudent);
		// req.getRequestDispatcher("./index.html").forward(req, resp);
		 resp.sendRedirect("./studentHome.html");
	 }
  }
}



