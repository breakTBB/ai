package com.dao;

import java.util.List;

import com.model.Image;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * topic的dao层
 *
 */
@Mapper
public interface ImageDao {
	String TABLE_NAME = "image";
	String INSERT_FIELDS = "title,content,category,created_date,code,id_user";
	String SELECT_FIELDS = "id,title,content,category,created_date,code,id_user";

	/**
	 * 插入一条话题记录
	 * @param image
	 * @return int
	 */
	int addImage(Image image);
	
	/**
	 * 根据id删除一条话题记录
	 * @param id
	 */
	void deleteImageById(@Param("id") Long id);

	/**
	 * 统计用户上传图片的数量
	 * @param userId
	 * @return Long
	 */
	Long countImagesByUser_Id(@Param("userId") Long userId);


	void upById(@Param("id") long id);
	void dissById(@Param("id") long id);
	List<Image> getImagesOrderByUp();
	/**
	 * 根据id查找话题
	 * @param id
	 * @return Image
	 */
	Image findImagesById(@Param("id") Long id);
	
	/**
	 * 获取目录category下的所有话题
	 * @param category
	 * @return List<Image>
	 */
	List<Image> findImagesByCategoryOrderByCreatedDateDesc(@Param("category") String category);
	
	/**
	 * 获取用户发布的所有话题记录
	 * @param id
	 * @return List<Image>
	 */
	List<Image> findImagesByUser_IdOrderByCreatedDateDesc(@Param("id") Long id);
	 
	/**
	 * 获得所有话题
	 * @return List<Image>
	 */
	List<Image> findAll();


	
	/**
	 * 根据topic的id获得用户id
	 * @param id
	 * @return int
	 */
	int getId_userById(@Param("id") Long id);
}
