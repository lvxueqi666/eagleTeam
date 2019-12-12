package com.xianwan.home.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xianwan.home.service.DetailImageService;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class DetailImageOperate
 */
@WebServlet("/DetailImageOperate")
public class DetailImageOperate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailImageOperate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String userAccount = request.getParameter("userAccount");
		String firstUrl = request.getParameter("firstUrl");
		DetailImageService dis = new DetailImageService();
		List<String> images = new ArrayList<>();
		if(userAccount != null) {
			images = dis.queryImageUrl(userAccount, firstUrl);
			JSONArray jsonArray = JSONArray.fromObject( images );
			System.out.println(jsonArray.toString());
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
