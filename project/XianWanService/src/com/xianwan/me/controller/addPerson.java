package com.xianwan.me.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xianwan.me.service.personService;

/**
 * Servlet implementation class addPerson
 */
@WebServlet("/addPerson")
public class addPerson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addPerson() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String userAccount=request.getParameter("userAccount");
		String userName=request.getParameter("userName");
		String userSex=request.getParameter("userSex");
		String userBirth=request.getParameter("userBirth");
		String userLocaton=request.getParameter("userLocation");
		String userJianjie=request.getParameter("userJianjie");
		String userJob=request.getParameter("userJob");
		String userJobName=request.getParameter("userJobName");
		String operate=request.getParameter("operate");
		System.out.println(operate);
		personService pService=new personService();
		 if(operate.equals("addperson")){
				pService.addPerson(userAccount,userName,userSex,userBirth,userLocaton,userJianjie,userJob,userJobName);
				 System.out.println("ps");
			}
			}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
