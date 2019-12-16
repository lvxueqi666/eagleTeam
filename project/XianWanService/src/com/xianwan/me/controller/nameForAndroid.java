package com.xianwan.me.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xianwan.me.entity.User;
import com.xianwan.me.entity.UserDetail;
import com.xianwan.me.service.SearchService;
import com.xianwan.me.service.UserService;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class nameForAndroid
 */
@WebServlet("/nameForAndroid")
public class nameForAndroid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public nameForAndroid() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String chaxun = request.getParameter("userAccount");
		UserService us=new UserService();
		List<User> user = new ArrayList<>();
		if(chaxun != null) {
			user = us.queryUser(chaxun);
			JSONArray jsonArray = JSONArray.fromObject( user );
			System.out.println(jsonArray.size());
			response.getWriter().print(jsonArray.toString());
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
