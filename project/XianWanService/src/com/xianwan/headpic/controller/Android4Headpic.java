package com.xianwan.headpic.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xianwan.headpic.entity.Headpic;
import com.xianwan.headpic.service.HeadpicService;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class Android4Headpic
 */
@WebServlet("/Android4Headpic")
public class Android4Headpic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Android4Headpic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		List<Headpic> list = new ArrayList<Headpic>();
		PrintWriter writer = response.getWriter();
		try {
			list = new HeadpicService().querytoHeadpic();
			JSONArray jsonArray = new JSONArray();
			jsonArray = JSONArray.fromObject(list);
			System.out.println(jsonArray.toString());
			writer.print(jsonArray.toString());
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
