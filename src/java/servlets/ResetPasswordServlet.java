/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
HttpSession session = request.getSession();
AccountService as = new AccountService();
       String email1 = (String)request.getAttribute("email");
       if(email1 != null){
           session.setAttribute("email", email1);
       }
        if( request.getParameter("uuid") != null){
            
    try {
        //set the uuid to the person in the table and then can be checked later
        String uuid = request.getParameter("uuid");
        UserDB udb = new UserDB();
        //String email = (String)request.getAttribute("email");
        String email = (String)session.getAttribute("email");
        User user = udb.get(email);
        user.setResetPasswordUuid(uuid);
        as.update(user);
        request.setAttribute("uuid", uuid);
        getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
        return;
    } catch (Exception ex) {
        Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
        }else{
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            return;
        }
        
        
        
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService as = new AccountService();
        HttpSession session = request.getSession();
       // String uuid2 = request.getParameter("uuid");
    if(request.getParameter("uuid")!= null){
        String uuid = request.getParameter("uuid");
        
        
        if(request.getParameter("newPassword")==null || request.getParameter("newPassword").equals("") ){
            request.setAttribute("msg", "Password cannot be empty");
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
            return;
        }else{
            as.changePassword(uuid, request.getParameter("newPassword"));
            request.setAttribute("msg", "Password Successfully changed");
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
            return;
        }
        
        
    }else{
            String email1 = request.getParameter("email");
            session.setAttribute("email", email1);
            //use this to send to accountservice
            String url = request.getRequestURL().toString();
            String path = getServletContext().getRealPath("/WEB-INF");
            String email = request.getParameter("email");
            
            if(email== null || email.equals("")){
                request.setAttribute("msg", "Please enter a valid email");
                getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            return;
            }else{
            as.resetPassword(email, path, url);
            request.setAttribute("msg", "If the email matches with one in the system, you will receive an email.");
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            return;
            }
        }     
    }
}
