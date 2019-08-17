package com.app.walletbuddy;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.walletbuddy.model.Category;
import com.app.walletbuddy.model.CategoryImage;
import com.app.walletbuddy.model.User;
import com.app.walletbuddy.service.CategoryImageService;
import com.app.walletbuddy.service.CategoryService;
import com.app.walletbuddy.service.TransactionService;
import com.app.walletbuddy.service.UserService;
import com.app.walletbuddy.utils.TransactionView;
import com.google.gson.Gson;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryImageService categoryImageService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, HttpSession s) {

		if (s.getAttribute("userName") != null) {
			model.addAttribute("signupMsg", " Manage Wallet");
			model.addAttribute("loginMsg", s.getAttribute("userName"));
		} else {
			model.addAttribute("signupMsg", "Sign Up for free");
			model.addAttribute("loginMsg", "Login");
		}
		return "index";
	}

	@RequestMapping(value = "/aboutus", method = RequestMethod.GET)
	public String aboutus(Model model, HttpSession s) {

		if (s.getAttribute("userName") != null) {
			model.addAttribute("signupMsg", " Manage Wallet");
			model.addAttribute("loginMsg", s.getAttribute("userName"));
		} else {
			model.addAttribute("signupMsg", "Sign Up for free");
			model.addAttribute("loginMsg", "Login");
		}

		return "aboutus";
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public String settings(HttpSession s, Model model) {
		try {
			if (s.getAttribute("userName") == null) {
				return "redirect:/";
			}
			List<CategoryImage> catImages = this.categoryImageService.getCategoryImages();
			model.addAttribute("catImages", catImages);

			int userId = (Integer) s.getAttribute("userId");
			List<Category> catList = this.categoryService.listCategory(userId);
			model.addAttribute("categories", catList);

			model.addAttribute("user", new User());
			model.addAttribute("settingsPageClass", "active");
			return "categories";
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "error";
		}
	}

	@RequestMapping(value = "/transactions", method = RequestMethod.GET)
	public String transactions(HttpSession s, Model model) throws ParseException {
		try {
			if (s.getAttribute("userName") == null) {
				return "redirect:/";
			}
			model.addAttribute("homePageClass", "active");

			final int userId = (Integer) s.getAttribute("userId");

			List<Category> catListIncome = new ArrayList<Category>(), catListExpence = new ArrayList<Category>();

			List<Category> catList = this.categoryService.listCategory(userId);

			for (Category c : catList) {
				if (c.getType().indexOf("Income") > -1) {
					catListIncome.add(c);
				}
				else {
					catListExpence.add(c);
				}
			}

			List<TransactionView> transactionViewList = new ArrayList<TransactionView>();
			TransactionView socIncomeAgr = this.transactionService.getSocIncomeAgregation(userId);
			TransactionView socExpenceAgr = this.transactionService.getSocExpenceAgregation(userId);

			if (socIncomeAgr != null) {
				transactionViewList.add(socIncomeAgr);
			}
			if (socIncomeAgr != null) {
				transactionViewList.add(socExpenceAgr);
			}

			List<Object[]> transRaw = this.transactionService.listTransactionsView(userId);
			for (Object[] row : transRaw) {
				if (Category.INCOME.equals((String) row[8]) || Category.EXPENCE.equals((String) row[8])) {
					continue;
				}
				TransactionView transactionView = new TransactionView();
				String color = (String) row[3];
				transactionView.setColor(color);
				transactionView.setCategoryName((String) row[8]);
				transactionView.setImage((String) row[5]);
				transactionView.setPrice((color.indexOf("red") > -1 ? "-" : "") + row[4].toString());
				transactionView.setNote((String) row[2]);
				transactionView.setTransactionId(Integer.valueOf(row[0].toString()));

				String tmpArr[] = row[1].toString().split(" ");
				tmpArr = tmpArr[0].toString().split("-");
				transactionView.setDate(tmpArr[2] + "-" + tmpArr[1] + "-" + tmpArr[0]);
				transactionView.setCategoryId(Integer.valueOf(row[7].toString()));
				transactionViewList.add(transactionView);
			}
			model.addAttribute("transactionsView", transactionViewList);
			model.addAttribute("catIncome", catListIncome);
			model.addAttribute("catExpence", catListExpence);

			return "transactions";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "error";
		}
	}

	@RequestMapping(value = "/socialpayments", method = RequestMethod.GET)
	public String friends(HttpSession s, Model model) {
		try {
			if (s.getAttribute("userName") == null) {
				return "redirect:/";
			}
			int userId = (Integer) s.getAttribute("userId");

			List<User> userList = new ArrayList<User>();

			for (User u : this.userService.listUsers()) {
				if (u.getId() != userId)
					userList.add(u);
			}
			List<TransactionView> tv = new ArrayList<TransactionView>();

			List<Object[]> transRaw = this.transactionService.listTransactionsView(userId);
			for (Object[] row : transRaw) {
				if (!Category.INCOME.equals((String) row[8]) && !Category.EXPENCE.equals((String) row[8])) {
					continue;
				}
				TransactionView _tv = new TransactionView();
				String color = (String) row[3];
				_tv.setColor(color);
				_tv.setCategoryName((String) row[8]);
				_tv.setImage((String) row[5]);
				_tv.setPrice((color.indexOf("red") > -1 ? "-" : "") + row[4].toString());
				_tv.setNote((String) row[2]);
				_tv.setTransactionId(Integer.valueOf(row[0].toString()));

				String tmpArr[] = row[1].toString().split(" ");
				tmpArr = tmpArr[0].toString().split("-");
				_tv.setDate(tmpArr[2] + "-" + tmpArr[1] + "-" + tmpArr[0]);
				_tv.setCategoryId(Integer.valueOf(row[7].toString()));
				tv.add(_tv);
			}

			model.addAttribute("socialpaymentsPageClass", "active");
			model.addAttribute("userList", userList);
			model.addAttribute("transactionsView", tv);

			return "socialpayments";
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "error";
		}
	}

	@RequestMapping(value = "/clusters", method = RequestMethod.GET)
	public String clusters(HttpSession s, Model model) {
		try {
			if (s.getAttribute("userName") == null) {
				return "redirect:/";
			}

			Gson gson = new Gson();
			Map<String, List<UserPoint>> colorToUserPointsData = new HashMap<>();

			int userId = (Integer) s.getAttribute("userId");

			URL url = new URL("http://localhost:5002/user/" + userId);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String JSON_from_python = br.readLine();

			List<?> points = gson.fromJson(JSON_from_python, List.class);
			for (Object object : points) {
				UserPoint p = gson.fromJson(object.toString(), UserPoint.class);
				List<UserPoint> listPoints = colorToUserPointsData.putIfAbsent(p.color, new ArrayList<>());
				if (listPoints == null) {
					listPoints = colorToUserPointsData.get(p.color);
				}
				listPoints.add(p);
			}

			conn.disconnect();

			StringBuilder data = new StringBuilder("[");
			int clusterCounter = 0;
			String label = "";

			for (Map.Entry<String, List<UserPoint>> dataSet : colorToUserPointsData.entrySet()) {
				data.append("{");
				if (dataSet.getKey().equals("red")) {
					label = "Active User";
				} else if (dataSet.getKey().equals("yellow")) {
					label = "Centroids";
				} else {
					label = "User Cluster " + ++clusterCounter;
				}
				data.append("label: '").append(label).append("',");
				data.append("borderColor: window.chartColors.").append(dataSet.getKey()).append(",");
				data.append("backgroundColor: color(window.chartColors.").append(dataSet.getKey())
						.append(").alpha(0.8).rgbString(),");
				data.append("data: [");
				for (UserPoint point : dataSet.getValue()) {
					data.append("{x:").append(point.x).append(",y:").append(point.y).append("},");
					if (dataSet.getKey().equals("red")) {
						data.append("{x:").append(point.x + 0.35).append(",y:").append(point.y).append("},");
						data.append("{x:").append(point.x - 0.35).append(",y:").append(point.y).append("},");
						data.append("{x:").append(point.x).append(",y:").append(point.y + 0.8).append("},");
						data.append("{x:").append(point.x).append(",y:").append(point.y - 0.8).append("},");
					}
				}
				data.replace(data.length() - 1, data.length(), "");
				data.append("]},");
			}
			data.replace(data.length() - 1, data.length(), "");
			data.append("]");

			model.addAttribute("data", data.toString());
			model.addAttribute("clustersPageClass", "active");
			return "clusters";
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "error";
		}
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String users(HttpSession s, Model model) {
		if (s.getAttribute("userName") == null || !s.getAttribute("userRole").toString().equals("admin")) {
			return "redirect:/";
		}
		try {
			int userId = (Integer) s.getAttribute("userId");
			List<User> userList = new ArrayList<User>(), userListAll = this.userService.listUsers();

			for (User u : userListAll) {
				if (u.getId() != userId)
					userList.add(u);
			}
			model.addAttribute("userList", userList);
			model.addAttribute("user", new User());
			model.addAttribute("usersPageClass", "active");
			return "users";
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "error";
		}
	}

	@RequestMapping(value = "category/image/{imageName}.{extension}")
	@ResponseBody
	public byte[] getImage(@PathVariable(value = "imageName") String imageName,
			@PathVariable(value = "extension") String extension) throws IOException {

		String rootPath = servletContext.getRealPath("/resources/");
		String imgLocation = rootPath + File.separator + "categoryImages\\";
		File serverFile = new File(imgLocation + imageName + "." + extension);
		if (serverFile.exists()) {
			return Files.readAllBytes(serverFile.toPath());
		}
		else {
			return new byte[] {};
		}
	}
}

class UserPoint {
	public float x;
	public float y;
	public String color;

	public UserPoint() {}

	public UserPoint(int x, int y, String color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	@Override
	public String toString() {
		return "UserPoint [x=" + x + ", y=" + y + ", color=" + color + "]";
	}
}
