package com.xianwan.login.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xianwan.login.service.LoginService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String userAccount = request.getParameter("userAccount");
		String userPassword = request.getParameter("userPassword");
		String result;
		
		LoginService loginService = new LoginService();
		try {
			result = loginService.loginCheck(userAccount,userPassword);
			System.out.println("登录账号：" + userAccount + ",登录密码：" + userPassword + ",登录结果：" + result);
			if(!result.equals("")) {
				loginService.insertAccountAndName(userAccount,result);
				response.setCharacterEncoding("utf-8");
				PrintWriter writer = response.getWriter();
				writer.print(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
