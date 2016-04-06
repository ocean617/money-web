package com.hst.money.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.JspView;

import com.hst.money.vo.User;

/**
 * @author ocean
 * 用户访问过滤
 */
public class userFilter implements ActionFilter {
	
	@Inject
	protected String excludeUrl;
	
	/* (non-Javadoc)
	 * @see org.nutz.mvc.ActionFilter#match(org.nutz.mvc.ActionContext)
	 * 登录过滤检查
	 */
	@Override
	public View match(ActionContext ac) {
		HttpServletRequest request = ac.getRequest();
		HttpSession sess = request.getSession();
		User user = sess.getAttribute("user")!=null?(User)sess.getAttribute("user"):null;
		
		if (user==null && request.getRequestURI().indexOf(excludeUrl)<=0 ){
			request.setAttribute("message", "要执行该操作请您先登录或进行注册.");
			return new JspView("/portal/loginjump");
		} 
			
		return null;
		 
	}

}
