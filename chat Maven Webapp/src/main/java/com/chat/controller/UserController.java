package com.chat.controller;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chat.pojo.User;

/**
 * 用户登录
 * 主要是学习websocket,数据库和权限判断就不写了
 * @author chenxin
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	//分配user的id,需设计为线程安全的
	private static int count=1;
	
	//在线用户列表，需设计成线程安全的
	private static List<User> userList = new  CopyOnWriteArrayList<User>();
	
	
	/**
	 * 跳转到登陆页面
	 * @return
	 */
	@RequestMapping("/tologin")
	public String toregister(){
		return "login";
	}	
	
	/**
	 * 登陆
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(User user,HttpServletRequest request){
		//生成id
		user.setId(count);
		//id增长
		UserController.increase();
		//把用户存入session域中
		request.getSession().setAttribute("user", user);
		//把登陆用户传入用户列表中
		userList.add(user);
		return "index";
	}
	
	/**
	 * 得到在线人数及用户名
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAll")
	public @ResponseBody Collection<User> getAllUser(HttpServletRequest request){
		return UserController.userList;
	}
	
	/**
	 * 下线，当页面关闭时前台页面通过ajax调用该handler
	 * @return
	 */
	@RequestMapping("/downLine")
	public void downLine(HttpServletRequest request){
		//得到session中的user
		User user = (User)request.getSession().getAttribute("user");
		//遍历用户列表，删除自己
		for(User item:userList){
			if(user.getId()==item.getId())
				userList.remove(item);
		}
	}
	//用来id增长
	private static synchronized void  increase(){
		 UserController.count++;
	}
}
