package com.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.dao.AnswerDao;
import com.dao.ImageDao;
import com.dao.UserDao;
import com.model.Answer;
import com.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.util.HostHolder;

@Service
public class ImagesService {

	@Autowired
	private ImageDao imageDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AnswerDao answerDao;


	@Autowired
	HostHolder hostHolder;

	public List<Image> getImagesByCategory(String category) {
		if (category.equals("all")) {
			return imageDao.findAll();
		} else {
			return imageDao.findImagesByCategoryOrderByCreatedDateDesc(category);
		}
	}

	public List<Image> getImagesByUser(String userId) {
		return imageDao.findImagesByUser_IdOrderByCreatedDateDesc(Long.valueOf(userId));
	}

	public void addAnswer(String content, String code, String id_topic, String id_user) {
		Answer answer = new Answer();
		answer.setContent(content);
		if (Objects.equals(code, "")) {
			answer.setCode(null);
		} else {
			answer.setCode(code);
		}
		answer.setCreatedDate(new Date());
		answer.setUseful(false);
		answer.setImage(imageDao.findImagesById(Long.valueOf(id_topic)));
		answer.setUser(userDao.getUserById(Long.parseLong(id_user)));
		answer.setIdTopic(Integer.parseInt(id_topic));
		answer.setIdUser(Integer.parseInt(id_user));
		answerDao.addAnswer(answer);
	}
}
