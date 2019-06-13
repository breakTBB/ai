package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dao.AnswerDao;
import com.dao.ImageDao;
import com.dao.UserDao;
import com.model.Answer;
import com.model.User;
import com.service.ImagesService;
import com.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.model.Image;

@Controller
public class ImageController {
	@Autowired
	private UserDao userDao;

	@Autowired
	private ImageDao imageDao;

	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	HostHolder hostHolder;

	@Autowired
	ImagesService imagesService;

	@RequestMapping(path = "/topic/up/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String up(@PathVariable String id) {
		imageDao.upById(Integer.parseInt(id));
		return "up successfully";
	}

	@RequestMapping(path = "/topic/diss/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String diss(@PathVariable String id) {
		imageDao.dissById(Integer.parseInt(id));
		return "diss successfully";
	}
	
	@RequestMapping(path = "/topic/{id}", method = RequestMethod.GET)
	public String displayTopic(@PathVariable String id, Model model) {
		
		User user = hostHolder.getUser();
		Long idUser = user.getId();
		
		Image image = imageDao.findImagesById(Long.valueOf(id));
		List<Answer> answers = answerDao.findAnswerByTopic_Id(Long.valueOf(id));
		
		model.addAttribute("user", user);
		model.addAttribute("topic", image);
//		model.addAttribute("up", imageDao.countUp(Long.parseLong(id)));
//		model.addAttribute("diss", imageDao.countDiss(Long.parseLong(id)));
		model.addAttribute("answers", answers);
		model.addAttribute("idUser", idUser);
		model.addAttribute("userDao", userDao);
		return "image";
	}

	/**
	 * 删除评论或置为有用/没用的评论
	 * 
	 * @param id_topic
	 * @param action
	 * @param id_answer
	 * @param state
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/topic/{id}", method = RequestMethod.POST)
	public View updateAnswer(@RequestParam String id_topic, @RequestParam String action, @RequestParam String id_answer,
			@RequestParam(required = false) String state, HttpServletRequest request) {
		if ("delete".equals(action)) {
			answerDao.deleteAnswerById(Long.valueOf(id_answer));
		}
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/topic/" + id_topic);
	}

	/**
	 * 话题评论接口
	 * 
	 * @param content 评论的内容
	 * @param id_topic 话题的id
	 * @param id_user 该话题的用户的userId
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/topic", method = RequestMethod.POST)
	public View addAnswer(@RequestParam("content") String content,
			@RequestParam("id_topic") String id_topic, @RequestParam("id_user") String id_user,
			HttpServletRequest request) {
		imagesService.addAnswer(content, "", id_topic, id_user);
		
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/topic/" + id_topic);
	}
	
	/**
	 * 解决按分话题时候再点击站内信无法跳转到站内信的页面，此方法主要实现一个跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/topics/message", method = RequestMethod.GET)
	public View topicsTransform(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/message");
	}
	
	@RequestMapping(path = "/topic/message", method = RequestMethod.GET)
	public View topicTransform(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/message");
	}

}
