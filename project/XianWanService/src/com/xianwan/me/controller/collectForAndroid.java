package com.xianwan.me.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xianwan.home.entity.Commodity;
import com.xianwan.me.service.CollectService;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class collectForAndroid
 */
@WebServlet("/collectForAndroid")
public class collectForAndroid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public collectForAndroid() {
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
		List<Commodity> listCommodity = new ArrayList<>();
		CollectService cs = new CollectService();
		if(userAccount != null) {
			listCommodity = cs.queryCommodityIdByUserAccount(userAccount);
			JSONArray jsonArray = JSONArray.fromObject( listCommodity );
			System.out.println(jsonArray.size());
			response.getWriter().print(jsonArray.toString());
		}	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
