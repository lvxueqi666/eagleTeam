package com.xianwan.me.controller;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import com.xianwan.me.service.Commi;
import com.xianwan.me.service.FabuPicService;



/**
 * Servlet implementation class FabuPicSaveController
 */
@WebServlet("/FabuPicSaveController")
public class FabuPicSaveController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FabuPicSaveController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		 System.out.println("mmmmmmmmmmmmmmm");
		FabuPicService fabuPicService = new FabuPicService();
		//设置第几次发布
		int frequency = 0;
		PrintWriter pw = response.getWriter();
		String userId = "";
		//设置第一张图片地址
		boolean flag=false;
		String firstUrl=null;
		 System.out.println("11111");
		//设置系统环境
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(10*1024*1024);
		upload.setSizeMax(10*1024*1024);
		
		//解析
		try {
			List<FileItem> items = upload.parseRequest(request);
			for(FileItem item : items) {
				//普通字段
				if(item.isFormField()) {
					userId = item.getString();
					System.out.println("userid"+userId);
					frequency = fabuPicService.queryRrequency(userId);
				}
				else {
					InputStream in = item.getInputStream();
					String fileName = item.getName();
					System.out.println("fileName:"+fileName);
					//设置id 结构：fileName +FD+userId+N+用户第几次发布
					String id = fileName +"FD"+userId+"N"+frequency;
					//设置存放地址
					String address = "http://49.233.142.163:8080/images/"+id+".jpg";
					if (flag!=true) {
						firstUrl=address;
						flag=true;
					}
					System.out.println("id"+id);
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					byte[] pic = null;
					byte[] b = new byte[1024];
					int len = -1;
					while((len = in.read(b))!= -1) {
						out.write(b, 0, len);
					}
					pic = out.toByteArray();
					sendPic(id,pic);
					//添加
					java.sql.Date uploadTime = new java.sql.Date(new java.util.Date().getTime());
					fabuPicService.addPicToSQL(id, userId, address,uploadTime, frequency,firstUrl);
					System.out.println("nnnnnnnnnnnn");
					in.close();
					out.close();
					item.delete();
				}
				PrintWriter out = response.getWriter();
				out.print("上传成功！");
			}
		}catch (org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException e) {
			System.out.println("111222");
            pw.write("单个文件不能超过4M");
        } catch (org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException e) {
        	System.out.println("11122222332");
            pw.write("总文件不能超过6M");
        } 

		catch (FileUploadException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
	}
	public void sendPic(String PicName,byte[] bytes) {
		//实例化一个Jersey
		Client client = new Client();
		//保存图片服务器的请求路径
        String url = "http://49.233.142.163:8080/images/"+PicName+".jpg";
        System.out.println("url:"+url);
		//设置请求路径
		WebResource resource = client.resource(url);
		//发送post get put 
		//resource.put(String.class, p);
		resource.put(bytes);
		System.out.println("成功发送");

	}
	

}

