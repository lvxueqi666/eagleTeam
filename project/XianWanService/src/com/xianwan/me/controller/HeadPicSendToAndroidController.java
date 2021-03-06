package com.xianwan.me.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.xianwan.me.dao.HeadPicDao;
import com.xianwan.me.service.HeadPicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HeadPicController
 */
@WebServlet("/HeadPicSendToAndroidController")
public class HeadPicSendToAndroidController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HeadPicSendToAndroidController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("textml;charset=utf-8");
		
		HeadPicService headPicService = new HeadPicService();
		//得到用户Id
		String userId = request.getParameter("userId");
		String address = headPicService.queryHeadPic(userId);
		System.out.println("address:" + address);
		System.out.println("id:" + userId);
		//将头像发送给android
		URL url = null;
		if (address != null) {
			url = new URL(address);
		}else {
			url = new URL("http://49.233.142.163:8080/images/lvgoudan.jpg");
		}
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();																																						
		OutputStream out = response.getOutputStream();
		byte[] bytes = new byte[1024];	
		int n = -1;
		while((n = in.read(bytes)) != -1) {
			out.write(bytes, 0, n);
			out.flush();
		}
		in.close();
		out.close();
		

	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);

	}
}

