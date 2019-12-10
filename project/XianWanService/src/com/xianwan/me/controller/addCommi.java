package com.xianwan.me.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xianwan.me.dao.CommiDao;
import com.xianwan.me.service.Commi;

/**
 * Servlet implementation class addCommi
 */
@WebServlet("/addCommi")
public class addCommi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addCommi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String introduce=request.getParameter("introduce");
		String price=request.getParameter("price");
		String attr=request.getParameter("attr");
		String userAccount=request.getParameter("userAccount");
		String userName=request.getParameter("userName");
		System.out.println(userAccount);
		Commi commi=new Commi();
		String address;
		try {
			address=commi.findAddressByAccount(userAccount);
			System.out.println("zzzzzzzzzzzzzzz"+address);
			 if(userAccount!=null&&address!=null){
				commi.addCommi(address,introduce, price,attr,userAccount,userName);
				System.out.println("cg");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
