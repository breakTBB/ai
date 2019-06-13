package com.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * topic的model实体层
 *
 */
public class Image {
	private Long id; //话题的topicId
	private int up; //赞
	private int diss; // 踩
	private String category;//图片分类
	private String code;//话题附加的代码
	private String content;//图片的url
	private Date createdDate; //图片的上传时间
	private String title;//图片的描述
	private Integer idUser;//上传图片的用户的userId
	
	private User user;
	private List<Answer> answers;
	
	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() { return title; }

	public int getUp() {return up;}

	public int getDiss() {return diss;}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setUp(int up) {this.up = up;}
	public void setDiss(int diss) {this.diss = diss;}
	public void setCategory(String category) {
		this.category = category;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public String displayParsedCreatedDate() {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(this.createdDate);
    }

    public String displayCode() {
        if (Optional.ofNullable(code).isPresent())
            return Optional.ofNullable(code).get();
        else
            return "";
    }
}
