package com.easyconnect.filter;

/**
 * @author Ice
 */

import java.io.IOException;  
import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;

public class SecurityServlet extends HttpServlet implements Filter {  
    private static final long serialVersionUID = 1L;

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
           HttpServletRequest request=(HttpServletRequest)arg0;
           HttpServletResponse response  =(HttpServletResponse) arg1;
           HttpSession session = request.getSession(true);

           String url = request.getRequestURI().substring(request.getContextPath().length());
           if(url.equals("/") || url.equals("/index.html")) 
           {
        	   arg2.doFilter(arg0, arg1);
        	   System.out.println(1 + url);
               return;
            }
           else if(url.equals("/signin.html")) {
        	   System.out.println(4 + url);
        	   System.out.println(3);
    		   arg2.doFilter(arg0, arg1);
               return;
           }
           else
           {
        	   if(session == null || session.getAttribute("userId") == null 
        			   || session.getAttribute("userId").equals(""))
        	   {
        		   System.out.println(2 + url);
        		   //response.sendRedirect("/EasyConnect/signin.html");
        		   return ;
        	   }
        	   else
        	   {
        		   System.out.println(3);
        		   arg2.doFilter(arg0, arg1);
                   return;
        	   }
           }
    }
    public void init(FilterConfig arg0) throws ServletException {
    }

}
