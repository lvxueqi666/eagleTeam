package com.xianwan.me.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xianwan.me.entity.UserDetail;
import com.xianwan.me.service.SearchService;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class detailforAndroid
 */
@WebServlet("/detailforAndroid")
public class detailforAndroid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public detailforAndroid() {
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
		SearchService ss=new SearchService();
		List<UserDetail> userDetails = new ArrayList<>();
		if(chaxun != null) {
			userDetails = ss.queryUserDetail(chaxun);
			JSONArray jsonArray = JSONArray.fromObject( userDetails );
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
