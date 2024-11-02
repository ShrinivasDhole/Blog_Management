package com.db.main;

import java.sql.SQLException;
import java.util.Scanner;

import com.db.dao.BlogDao;

public class WelcomeScreen {

	public static int menu1(Scanner sc) {
		System.out.println("0. EXIT");
		System.out.println("1. Login");
		System.out.println("2. Register");
		return sc.nextInt();
	}

	public static int menu2(Scanner sc) {
		System.out.println("1. My Blogs");
		System.out.println("2. All Blogs");
		System.out.println("3. Add Category");
		System.out.println("4. Show Categories");
		System.out.println("5. Add Blog");
		System.out.println("6. Search Blogs");
		System.out.println("7. Logout");
		return sc.nextInt();
	}

	public static boolean loginFun(Scanner sc) {
		System.out.println();
		System.out.print("Email : ");
		String email = sc.next();
		System.out.print("Password : ");
		String password = sc.next();

		try (BlogDao bd = new BlogDao()) {
			return bd.login(email, password);

		} catch (SQLException e) {
			System.out.println("User not found... Please enter correct details!!");
			return false;
		} catch (Exception e1) {
			System.out.println("User not found... Please enter correct details!!");
			return false;
		}

	}

	public static boolean registerFun(Scanner sc) {
		System.out.println();
		System.out.print("Email : ");
		String email = sc.next();
		System.out.print("Enter the full name : ");
		sc.nextLine();
		String full_name = sc.nextLine();
		System.out.print("Enter the phone number : ");
		String phone_no = sc.next();
		System.out.print("Password : ");
		String password = sc.next();

		try (BlogDao bd = new BlogDao()) {
			;
			bd.register(email, password, full_name, phone_no);
			return true;
		} catch (SQLException e) {
			System.out.println("Enter the correct details to continue...");
			return false;
		} catch (Exception e1) {
			System.out.println("Enter the correct details to continue...");
			return false;
		}

	}

	public static void myBlogsFun(Scanner sc) {
		try (BlogDao db = new BlogDao()) {
			db.myBlogs(sc);
		} catch (SQLException e) {
			System.out.println("------------------------");
			System.out.println("Error retrieving blogs: " + e.getMessage());
			System.out.println("------------------------");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("------------------------");
			System.out.println("An unexpected error occurred: " + e.getMessage());
			System.out.println("------------------------");
			e.printStackTrace();
		}
	}

	public static void allBlogsFun() {
		try (BlogDao db = new BlogDao()) {
			db.allBlogs();
		} catch (SQLException e) {
			System.out.println("------------------------");
			System.out.println("Error retrieving blogs: " + e.getMessage());
			System.out.println("------------------------");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("------------------------");
			System.out.println("Unexpected error: " + e.getMessage());
			System.out.println("------------------------");
			e.printStackTrace();
		}
	}

	public static void addBlogFun(Scanner sc) {
		try (BlogDao bd = new BlogDao()) {
			bd.addBlog(sc);
		} catch (SQLException e) {
			System.out.println("------------------------");
			System.out.println("Blog not added... Error");
			System.out.println("------------------------");
		} catch (Exception e) {
			System.out.println("------------------------");
			System.out.println("Blog not added... Error");
			System.out.println("------------------------");
		}
	}

	public static void addCategoryFun(Scanner sc) {
		System.out.print("Enter the title of category : ");
		sc.nextLine();
		String title = sc.nextLine();
		System.out.print("Enter the title of description : ");
		String description = sc.nextLine();

		try (BlogDao bd = new BlogDao()) {
			bd.addCategory(title, description);
		} catch (SQLException e) {
			System.out.println("---------------------------");
			System.out.println("Category not added...Error");
			System.out.println("---------------------------");
		} catch (Exception e) {
			System.out.println("---------------------------");
			System.out.println("Category not added...Error");
			System.out.println("---------------------------");
		}
	}

	public static void showCategoriesFun() {

		try (BlogDao bd = new BlogDao()) {
			bd.showCategories();
		} catch (SQLException e) {
			System.out.println("---------------------------");
			System.out.println("Category not added...Error");
			System.out.println("---------------------------");
		} catch (Exception e) {
			System.out.println("---------------------------");
			System.out.println("Category not added...Error");
			System.out.println("---------------------------");
		}
	}

	public static void searchBlogsFun(Scanner sc) {
		System.out.print("Enter the title of blog : ");
		sc.nextLine();
		String title = sc.nextLine();

		try (BlogDao bd = new BlogDao()) {
			bd.searchBlogs(title);
		} catch (SQLException e) {
			System.out.println("---------------------------");
			System.out.println("Category not added...Error");
			System.out.println("---------------------------");
		} catch (Exception e) {
			System.out.println("---------------------------");
			System.out.println("Category not added...Error");
			System.out.println("---------------------------");
		}
	}

	public static boolean logOutFun() {
		try (BlogDao bd = new BlogDao()) {
			return bd.logOut();
		} catch (SQLException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) {

		int choice;
		Scanner sc = new Scanner(System.in);
		while ((choice = menu1(sc)) != 0) {
			switch (choice) {
			case 1:
				System.out.println("Please login to continue > ");
				if (loginFun(sc)) {
					int choice2;
					boolean flag = true;
					while (flag && (choice2 = menu2(sc)) != 0 && flag == true) {
						switch (choice2) {
						case 1:
							myBlogsFun(sc);
							break;
						case 2:
							allBlogsFun();
							break;
						case 3:
							addCategoryFun(sc);
							break;
						case 4:
							showCategoriesFun();
							break;
						case 5:
							addBlogFun(sc);
							break;
						case 6:
							searchBlogsFun(sc);
							break;
						case 7:
							if (logOutFun()) {
								flag = false;
							}
							break;
						default:
							System.out.println("Enter the correct input...");
							break;
						}
					}

				}
				break;
			case 2:
				System.out.println("Register as user >");
				registerFun(sc);
				break;

			default:
				System.out.println("-------------------------");
				System.out.println("Enter the valid input...");
				System.out.println("-------------------------");
				break;
			}
		}
	}

}
