package com.hst.money.mobile.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.JspView;

import com.hst.money.action.baseAction;
import com.hst.money.util.pageModel;
import com.hst.money.util.systemContext;

/**
 * @author ocean
 * BBS管理
 */
@IocBean
@InjectName
public class indexAction extends BaseAction{
	
	/**
	 * 显示在首页上的市民服务BBS头5条经审核的内容
	 * @param Id
	 * @param request
	 * @return
	 */
	@At("/portal/index")
	public View showIndex(HttpServletRequest request){
		//栏目列表
		List classList = basicDao.search(ArticleClass.class, Cnd.wrap(" C_TITLE<>'内部通知' and C_TITLE<>'资料下载' order by C_ORDER"));
		//最新动态
		List jxdt_list = basicDao.searchByPage(Article.class, Cnd.wrap("C_ID in (select C_ID from PJ_ARTICLE_CLASS where C_TITLE='最新动态') and A_LIMITDATE>(now()) and A_STATE='发布' order by A_DATE desc"),1,8);
		//通知公告
		List tzgg_list = basicDao.searchByPage(Article.class, Cnd.wrap("C_ID in (select C_ID from PJ_ARTICLE_CLASS where C_TITLE='通知公告') and A_LIMITDATE>(now()) and A_STATE='发布' order by A_DATE desc"),1,6);
		//政策法规
		List zcfg_list = basicDao.searchByPage(Article.class, Cnd.wrap("C_ID in (select C_ID from PJ_ARTICLE_CLASS where C_TITLE='政策法规') and A_LIMITDATE>(now()) and A_STATE='发布' order by A_DATE desc"),1,8);
		//文件下载
		List wjxz_list = basicDao.searchByPage(Article.class, Cnd.wrap("C_ID in (select C_ID from PJ_ARTICLE_CLASS where C_TITLE='文件下载') and A_LIMITDATE>(now()) and A_STATE='发布' order by A_DATE desc"),1,8);
		
		request.setAttribute("class_list", classList);
		request.setAttribute("jxdt_list", jxdt_list);
		request.setAttribute("tzgg_list", tzgg_list);
		request.setAttribute("zcfg_list", zcfg_list);
		request.setAttribute("wjxz_list", wjxz_list);
		 
		return new JspView("/portal/index.jsp");
	}
	
	/**
	 * 显示文章列表
	 * @param request
	 * @return
	 */
	@At("/portal/showArticleList")
	public View showArticleList(@Param("type") int type,
								@Param("classId") String clsId,
								@Param("pageNum") int currentPage,
								HttpServletRequest request){
		String title = "最新动态";
		if (clsId==null){
			switch(type){
			case 2:{ title = "政策法规"; break; }
			case 3:{ title = "文件下载"; break; }
			case 4:{ title = "通知公告"; break; }
			}
		}else{
			ArticleClass cls = basicDao.find(clsId, ArticleClass.class);
			if(cls!=null){
				title = cls.getTitle();
			}
		}
		
		if (0==currentPage){  currentPage = 1;  }
		
		//栏目列表
		List classList = basicDao.search(ArticleClass.class, Cnd.wrap(" C_TITLE<>'内部通知' and C_TITLE<>'资料下载' order by C_ORDER"));
		List tzgg_list = basicDao.searchByPage(Article.class, Cnd.wrap("C_ID in (select C_ID from PJ_ARTICLE_CLASS where C_TITLE='通知公告') and A_LIMITDATE>(now()) and A_STATE='发布' order by A_DATE desc"),1,6);
		int count = basicDao.searchCount(Article.class,Cnd.wrap("C_ID in (select C_ID from PJ_ARTICLE_CLASS where C_TITLE='"+title+"') and A_LIMITDATE>(now()) and A_STATE='发布' order by A_DATE desc"));
		int maxPage = basicDao.maxPageSize(count, SystemContext.PAGE_SIZE);
		if (currentPage>maxPage) currentPage = maxPage;

		List list = basicDao.searchByPage(Article.class, Cnd.wrap("C_ID in (select C_ID from PJ_ARTICLE_CLASS where C_TITLE='"+title+"') and A_LIMITDATE>(now()) and A_STATE='发布' order by A_DATE desc"),currentPage,SystemContext.PAGE_SIZE);
		PageModel<Article> pm = new PageModel<Article>(list,maxPage,count,currentPage,SystemContext.PAGE_SIZE);
		
		request.setAttribute("class_list", classList);
		request.setAttribute("tzgg_list", tzgg_list);
		request.setAttribute("pm", pm);
		request.setAttribute("title", title);
		request.setAttribute("type", type);
		
		return new JspView("/portal/showList.jsp");
	}
	
	@At("/portal/showArticle")
	public View showArticle(@Param("Id") String Id,
								HttpServletRequest request){
		String title = "";
		Article art = basicDao.find(Id, Article.class);
		
		//栏目列表
		List classList = basicDao.search(ArticleClass.class, Cnd.wrap(" C_TITLE<>'内部通知' and C_TITLE<>'资料下载' "));
		List tzgg_list = basicDao.searchByPage(Article.class, Cnd.wrap("C_ID in (select C_ID from PJ_ARTICLE_CLASS where C_TITLE='通知公告') and A_LIMITDATE>(now()) and A_STATE='发布' order by A_DATE desc"),1,6);
		List list = basicDao.searchByPage(Article.class, Cnd.wrap("C_ID in (select C_ID from PJ_ARTICLE_CLASS where C_TITLE='"+title+"') and A_LIMITDATE>(now()) and A_STATE='发布' order by A_DATE desc"),1,20);
		
		request.setAttribute("class_list", classList);
		request.setAttribute("tzgg_list", tzgg_list);
		request.setAttribute("art", art);
		
		return new JspView("/portal/showNews.jsp");
	}
}
