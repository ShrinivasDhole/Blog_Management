package com.db.entities;

import java.time.LocalDateTime;

public class Blogs {

	private int blogs_id;
	private String title;
	private String contents;
	private LocalDateTime created_at;
	private int user_id;
	private int cat_id;

	public Blogs(int blogs_id, String title, String contents, LocalDateTime created_at, int user_id, int cat_id) {
		super();
		this.blogs_id = blogs_id;
		this.title = title;
		this.contents = contents;
		this.created_at = created_at;
		this.user_id = user_id;
		this.cat_id = cat_id;
	}

	public int getBlogs_id() {
		return blogs_id;
	}

	public void setBlogs_id(int blogs_id) {
		this.blogs_id = blogs_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}

	@Override
	public String toString() {
		return "Blogs [blogs_id=" + blogs_id + ", title=" + title + ", contents=" + contents + ", created_at="
				+ created_at + ", user_id=" + user_id + ", cat_id=" + cat_id + "]";
	}

}