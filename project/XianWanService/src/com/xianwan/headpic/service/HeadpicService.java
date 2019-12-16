package com.xianwan.headpic.service;

import java.sql.SQLException;
import java.util.List;

import com.xianwan.headpic.dao.HeadpicDao;
import com.xianwan.headpic.entity.Headpic;

public class HeadpicService {
	public List<Headpic> querytoHeadpic() throws SQLException{
		return new HeadpicDao().querytoHeadpic();
	}

}
