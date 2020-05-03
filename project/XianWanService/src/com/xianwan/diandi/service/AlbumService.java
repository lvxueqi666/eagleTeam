package com.xianwan.diandi.service;

import java.util.List;

import com.xianwan.diandi.dao.AlbumDao;
import com.xianwan.diandi.entity.Album;

public class AlbumService {
	
	public List<List<Album>> queryAlbumByUserAccount(String userAccount) {
		return new AlbumDao().queryAlbumByUserAccount(userAccount);
	}
}
