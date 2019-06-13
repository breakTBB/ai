package com.controller;

import com.dao.AnswerDao;
import com.dao.ImageDao;
import com.dao.UserDao;
import com.model.PageBean;
import com.model.Image;
import com.model.User;
import com.service.PageService;
import com.service.ImagesService;
import com.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ImagesController {

	@Autowired
	private ImageDao imageDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private HostHolder localHost;
	
	@Autowired
	private ImagesService imagesService;
	
	@Autowired
	private PageService pageService;
	
	/**
	 * 分页处理
	 * @param category
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping(path="/topics/{category}/{currentPage}", method=RequestMethod.GET)
	public String displayTopicPage(@PathVariable String category, @PathVariable int currentPage, Model model) {
		PageBean<Image> pageTopic=pageService.findItemByPage(category, currentPage, 10);
		List<Image> pageList=pageTopic.getItems();
		int topicsTotalNum= imagesService.getImagesByCategory(category).size();
		
		User user=localHost.getUser();
		model.addAttribute("user", user);
		model.addAttribute("topics", pageList);
		model.addAttribute("topicsTotalNum", topicsTotalNum);
		model.addAttribute("header", category);
		model.addAttribute("answerDao", answerDao);
		model.addAttribute("userDao", userDao);
		model.addAttribute("currentPage", pageTopic.getCurrentPage());
		model.addAttribute("totalPage", pageTopic.getTotalPage());
		model.addAttribute("hasNext", pageTopic.getIsMore());
		model.addAttribute("isUserTopicPage", false);
//		model.addAttribute("up", .countUp());
//		model.addAttribute("up", imageDao.countUp(Long.parseLong(id)));
//		model.addAttribute("diss", imageDao.countDiss(Long.parseLong(id)));
		return "images";
	}
	
	@RequestMapping(path = "/topics/user/{id}_{currentPage}", method = RequestMethod.GET)
	public String displayTopicsByUser(@PathVariable String id, @PathVariable int currentPage, Model model) {
		PageBean<Image> pageTopic=pageService.findItemByUser(id, currentPage, 10);
		List<Image> images =pageTopic.getItems();
		int topicsTotalNum= imagesService.getImagesByUser(id).size();
//		String header = setHeader("user");
		
		User user=localHost.getUser();
		model.addAttribute("user", user);
		model.addAttribute("topics", images);
		model.addAttribute("header", "用户");
		model.addAttribute("answerDao", answerDao);
		model.addAttribute("userDao", userDao);
		model.addAttribute("currentPage", pageTopic.getCurrentPage());
		model.addAttribute("totalPage", pageTopic.getTotalPage());
		model.addAttribute("hasNext", pageTopic.getIsMore());
		model.addAttribute("topicsTotalNum", topicsTotalNum);
		model.addAttribute("isUserTopicPage", true);
		return "images";
	}
}
