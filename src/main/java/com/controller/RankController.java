package com.controller;

import java.util.List;

import com.dao.ImageDao;
import com.model.Image;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dao.UserDao;
import com.util.HostHolder;

@Controller
public class RankController {
	
	@Autowired
	private HostHolder hostHolder;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ImageDao imageDao;


	@RequestMapping(path="/rank",method=RequestMethod.GET)
	public String rankPoint(Model model) {
		User user=hostHolder.getUser();
		Long points = userDao.getPoints(user.getId());
		List<Image> images = imageDao.getImagesOrderByUp();
		model.addAttribute("user", user);
		model.addAttribute("topicDao", imageDao);
		model.addAttribute("images", images);
		model.addAttribute("userDao", userDao);
		return "rank";
	}
}
