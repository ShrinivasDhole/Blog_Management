package com.db.entities;

public class Categories {
	private int cat_id;
	private String title;
	private String description;

	public int getCat_id() {
		return cat_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Categories(int cat_id, String title, String description) {
		super();
		this.cat_id = cat_id;
		this.title = title;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Categories [cat_id=" + cat_id + ", title=" + title + ", description=" + description + "]";
	}

}
