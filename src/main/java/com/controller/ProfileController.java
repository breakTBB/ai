package com.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import com.dao.AnswerDao;
import com.dao.ImageDao;
import com.dao.UserDao;
import com.model.Image;
import com.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.util.HostHolder;

/**
 * 个人简介接口
 *
 */

@Controller
public class ProfileController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ImageDao imageDao;

	@Autowired
	private AnswerDao answerDao;


	@Autowired
	private HostHolder hostHolder;


	@RequestMapping(path = "/profile/{id}", method = RequestMethod.GET)
	public String displayProfileById(@PathVariable Long id, Model model) {
		User user = userDao.getUserById(id);
		logger.warn("[displayMyProfile] display profile, username:{}",user.getUsername());
		Long points = userDao.getPoints(user.getId());
		Long numberOfTopics = imageDao.countImagesByUser_Id(id);
		Long numberOfAnswers = answerDao.countAnswersByUser_Id(id);
		Long numberOfHelped = answerDao.countAnswersByUser_IdAndUseful(id, true);
		List<Image> myImgs = imageDao.findImagesByUser_IdOrderByCreatedDateDesc(id);
		User otherUser=hostHolder.getUser();

		model.addAttribute("user", otherUser);
		model.addAttribute("otherUser", user);
		model.addAttribute("points", points);
		model.addAttribute("numberOfTopics", numberOfTopics);
		model.addAttribute("numberOfAnswers", numberOfAnswers);
		model.addAttribute("numberOfHelped", numberOfHelped);
		model.addAttribute("myImgs", myImgs);
		return "profile";
	}

	@RequestMapping(path = "/profile", method = RequestMethod.POST)
	public View addTask(@RequestParam("category") String category, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("code") String code,
			@RequestParam("id_user") String id_user, HttpServletRequest request) {
		Image image = new Image();
		image.setCategory(category);
		if (Objects.equals(code, "")) {
			image.setCode(null);
		} else {
			image.setCode(code);
		}
		image.setContent(content);
		image.setTitle(title);
		image.setCreatedDate(new Date());
		image.setIdUser(Integer.parseInt(id_user));
		image.setUser(userDao.getUserById(Long.parseLong(id_user)));

		imageDao.addImage(image);
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/profile");
	}
}
