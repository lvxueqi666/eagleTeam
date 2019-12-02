package com.xianwan.home.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xianwan.home.service.CollectService;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class CollectOperate
 */
@WebServlet("/CollectOperate")
public class CollectOperate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CollectOperate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currentId = request.getParameter("currentId");
		String commodityId = request.getParameter("commodityId");
		String operate = request.getParameter("operate");
		CollectService cs = new CollectService();
		List<Integer> list = new ArrayList<>();
		if(currentId != null) {
			if(operate.equals("query")) {
				list = cs.queryCommodityIdByUserId(currentId);
				JSONArray jsonArray = JSONArray.fromObject( list );
				System.out.println(jsonArray.size());
				response.getWriter().print(jsonArray.toString());
			}else if(operate.equals("add")){
				cs.addCollection(Integer.parseInt(commodityId), Integer.parseInt(currentId));
			}else if(operate.equals("cancel")){
				cs.cancelCollection(Integer.parseInt(commodityId), Integer.parseInt(currentId));
			}else {
				if(cs.adjustIfExistCollection(Integer.parseInt(commodityId), Integer.parseInt(currentId))) {
					response.getWriter().print("exist");
				}else {
					response.getWriter().print("unexist");
				}
			}
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
