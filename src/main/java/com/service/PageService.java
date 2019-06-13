package com.service;

import java.util.List;

import com.model.Image;
import com.model.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.dao.ImageDao;

@Service
public class PageService {
	
	@Autowired
	private ImageDao imageDao;
	
	public PageBean<Image> findItemByPage(String category, int currentPage, int pageSize) {
		int countNums = 0; // 总记录数
		if(category.equals("all")) {
			countNums= imageDao.findAll().size();
		}else{
			countNums = imageDao.findImagesByCategoryOrderByCreatedDateDesc(category).size(); // 全部商品
		}
		// 设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
		PageHelper.startPage(currentPage, pageSize);
		List<Image> allImage =null;
		if(category.equals("all")) {
			allImage = imageDao.findAll();
		}else{
			allImage = imageDao.findImagesByCategoryOrderByCreatedDateDesc(category); // 全部商品
		}
		PageBean<Image> pageData = new PageBean<>(currentPage, pageSize, countNums);
		pageData.setItems(allImage);
		return pageData;
	}
	
	public PageBean<Image> findItemByUser(String userId, int currentPage, int pageSize) {
		int countNums = imageDao.findImagesByUser_IdOrderByCreatedDateDesc(Long.valueOf(userId)).size(); // 总记录数
		// 设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
		PageHelper.startPage(currentPage, pageSize);
		List<Image> allImage = imageDao.findImagesByUser_IdOrderByCreatedDateDesc(Long.valueOf(userId));
		PageBean<Image> pageData = new PageBean<>(currentPage, pageSize, countNums);
		pageData.setItems(allImage);
		return pageData;
	}
}
