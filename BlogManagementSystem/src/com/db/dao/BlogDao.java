package com.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.db.entities.Blogs;
import com.db.entities.Categories;
import com.db.entities.User;
import com.db.utils.DBUtils;

public class BlogDao implements AutoCloseable {

	Connection connection;

	static User logUser;
	static List<Blogs> blogList;
	static List<Blogs> allBlogList;
	static List<Categories> catList;

	public BlogDao() throws SQLException {
		connection = DBUtils.getConnection();
		allBlogList = new ArrayList<>();
		catList = new ArrayList<>();
		blogList = new ArrayList<>();
	}

	public boolean login(String email, String password) throws SQLException {
		String query = "SELECT * FROM user WHERE email = ? AND password = ?";

		try (PreparedStatement loginStmt = connection.prepareStatement(query)) {
			loginStmt.setString(1, email);
			loginStmt.setString(2, password);

			try (ResultSet rs = loginStmt.executeQuery()) {
				if (rs.next()) {
					logUser = new User(rs.getInt("user_id"), rs.getString("full_name"), rs.getString("email"),
							rs.getString("phone_no"), rs.getString("password"));
					System.out.println("User logged in successfully: " + logUser.getEmail());
					return true;
				} else {
					System.out.println("Invalid email or password.");
					return false;
				}
			}
		} catch (SQLException e) {
			System.out.println("An error occurred during login: " + e.getMessage());
			throw e;
		}

	}

	public void register(String email, String password, String full_name, String phone_no) throws SQLException {

		String query = "Insert into user (full_name ,  email , phone_no , password) values (?,?,?,?)";

		try (PreparedStatement rstrStmt = connection.prepareStatement(query)) {
			rstrStmt.setString(1, full_name);
			rstrStmt.setString(2, email);
			rstrStmt.setString(3, phone_no);
			rstrStmt.setString(4, password);
			int c = rstrStmt.executeUpdate();
			if (c == 1) {
				System.out.println("Registered Successfully...");

			} else {
				System.out.println("Registeration Failed...");
			}

		}

	}

	public void myBlogs(Scanner sc) throws SQLException {
		if (logUser == null) {
			System.out.println("You must be logged in to view your blogs.");
			return;
		}

		String query = "SELECT b.blog_id, b.title, b.contents, b.created_time, b.user_id, b.cat_id "
				+ "FROM user u JOIN blogs b ON u.user_id = b.user_id WHERE u.email = ?";
		try (PreparedStatement myBlogsStmt = connection.prepareStatement(query)) {
			myBlogsStmt.setString(1, logUser.getEmail());
			blogList.clear();

			try (ResultSet rs = myBlogsStmt.executeQuery()) {
				while (rs.next()) {
					blogList.add(new Blogs(rs.getInt("blog_id"), rs.getString("title"), rs.getString("contents"),
							rs.getTimestamp("created_time").toLocalDateTime(), rs.getInt("user_id"),
							rs.getInt("cat_id")));
				}
			}

			if (!blogList.isEmpty()) {
				System.out.println("-----------\nBlogs -> \n-----------");
				blogList.forEach(System.out::println);
			} else {
				System.out.println("No blogs found. Want to create one? Press 1 to create or any key to continue.");
				if (sc.nextInt() == 1) {
					addBlog(sc);
				}
			}
		}
	}

	public void addBlog(Scanner sc) throws SQLException {
		String query = "Insert into blogs (title, contents , user_id , cat_id) values (? , ? , ? , ?)";

		try (PreparedStatement addBlogStmt = connection.prepareStatement(query)) {
			System.out.println("Enter the title of blog : ");
			sc.nextLine();
			String title = sc.nextLine();
			System.out.println("Enter the contents of blog : ");
			String contents = sc.nextLine();
			System.out.println("Enter the category id :  ");
			int cat_id = sc.nextInt();
			addBlogStmt.setString(1, title);
			addBlogStmt.setString(2, contents);
			addBlogStmt.setInt(3, logUser.getUser_id());
			addBlogStmt.setInt(4, cat_id);

			int c = addBlogStmt.executeUpdate();
			if (c == 1) {
				System.out.println("----------------");
				System.out.println("Blog added !");
				System.out.println("-----------------");
			} else {
				System.out.println("-------------------");
				System.out.println("Blog not added !");
				System.out.println("-------------------");
			}
		}

	}

	public void allBlogs() throws SQLException {
		String query = "SELECT * FROM blogs";

		try (PreparedStatement allBlogStmt = connection.prepareStatement(query);
				ResultSet rs = allBlogStmt.executeQuery()) {

			allBlogList.clear();
			while (rs.next()) {
				allBlogList.add(new Blogs(rs.getInt("blog_id"), rs.getString("title"), rs.getString("contents"),
						rs.getTimestamp("created_time").toLocalDateTime(), rs.getInt("user_id"), rs.getInt("cat_id")));
			}

			if (!allBlogList.isEmpty()) {
				System.out.println("-----------");
				System.out.println("Blogs -> ");
				System.out.println("-----------");
				for (Blogs b : allBlogList) {
					System.out.println(b);

				}
			} else {
				System.out.println("------------------");
				System.out.println("No blog found...");
				System.out.println("------------------");
			}
		}
	}

	public void addCategory(String title, String description) throws SQLException {
		String query = "Insert into categories (title , description) values (? , ?)";

		try (PreparedStatement addCatStmt = connection.prepareStatement(query)) {
			addCatStmt.setString(1, title);
			addCatStmt.setString(2, description);
			int c = addCatStmt.executeUpdate();
			if (c == 1) {
				System.out.println("--------------------------------");
				System.out.println("Category added successfully...");
				System.out.println("--------------------------------");
			} else {
				System.out.println("--------------------------------");
				System.out.println("Category not added...");
				System.out.println("--------------------------------");
			}
		}
	}

	public void showCategories() throws SQLException {
		String query = "Select * from categories";

		try (PreparedStatement showCatStmt = connection.prepareStatement(query)) {
			ResultSet rs = showCatStmt.executeQuery();

			while (rs.next()) {
				catList.add(new Categories(rs.getInt(1), rs.getString(2), rs.getString(3)));

			}
			if (!catList.isEmpty()) {
				System.out.println("----------------");
				System.out.println("Categories -> ");
				System.out.println("----------------");
				for (Categories c : catList) {
					System.out.println(c);
				}
			} else {
				System.out.println("------------------");
				System.out.println("No Categories...");
				System.out.println("------------------");
			}
		}
	}

	public void searchBlogs(String title) throws SQLException {
		String query = "SELECT * FROM blogs WHERE title LIKE ?";
		try (PreparedStatement searchStmt = connection.prepareStatement(query)) {
			searchStmt.setString(1, "%" + title + "%");
			try (ResultSet rs = searchStmt.executeQuery()) {
				if (rs.next()) {
					do {
						System.out.println("Blog ID: " + rs.getInt("blog_id") + ", Title: " + rs.getString("title")
								+ ", Contents: " + rs.getString("contents") + ", Created Time: "
								+ rs.getTimestamp("created_time").toLocalDateTime());
					} while (rs.next());
				} else {
					System.out.println("No blog found...");
				}
			}
		}
	}

	public boolean logOut() {
		if (logUser != null) {
			logUser = null;
			System.out.println("Logged Out !");
			return true;
		}
		return false;
	}

	@Override
	public void close() throws Exception {

		if (connection != null) {
			connection.close();
		}
	}

}
