<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import ="user.UserDTO" %>
    <%@page import ="user.UserDAO" %>
    <%@page import ="java.io.PrintWriter" %>
<%   
     request.setCharacterEncoding("UTF-8");
     
     String ID =null;
     String userID =null;
     String score =null;
     
     if(request.getParameter("ID") != null) {
         ID =(String) request.getParameter("ID");  
    }
  
     if(request.getParameter("userID") != null) {
          userID =(String) request.getParameter("userID");
          
     }
     if(request.getParameter("score") != null) {
         score =(String) request.getParameter("score");
    }
    if(ID.equals("")||userID.equals("")||score.equals("")) {
    	PrintWriter script =response.getWriter();
    	script.println("<script>");
    	script.println("alert('입력이 안 된 사항이 있습니다.');");
    	script.println("history.back();");
    	script.println("</script>");
    	script.close();
    	return ;
    }
    UserDAO userDAO =new UserDAO();
    int result =userDAO.join(ID,userID, score);
    if(result == 1) {
    	PrintWriter script =response.getWriter();
    	script.println("<script>");
    	script.println("alert('점수 등록에 성공했습니다 ');");
    	script.println("location.href = 'index.jsp';");
    	script.println("</script>");
    	script.close();
    	return ;
    }
        %>     