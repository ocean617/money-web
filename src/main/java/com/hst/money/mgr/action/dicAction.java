package com.hst.money.mgr.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.JspView;

import com.hst.money.action.baseAction;
import com.hst.money.util.pageModel;
import com.hst.money.util.systemContext;
import com.hst.money.util.commUtil;
import com.hst.money.vo.Dic;

/**
 * @author ocean
 * 字典管理
 */

@IocBean
@InjectName
public class dicAction extends baseAction{
	
	/**
	 * 默认查出所有数据的页面跳转
	 * @param currentPage
	 * @param ioc
	 * @param request
	 * @return
	 */
	@At("/mgr/platform/dic/List")
	public View findAll(@Param("pageNum") int currentPage,
						@Param("numPerPage") int numPerPage,
						HttpServletRequest request){
		StringBuffer findSqlBuf = new StringBuffer();
		findSqlBuf.append(" 1=1 ");
		HashMap conditionMap = new HashMap();
		
		if (0==currentPage){  currentPage = 1;  }
		if (0==numPerPage){	  numPerPage = SystemContext.PAGE_SIZE;}
		
		//拼装查询条件
		this.createWhere(request, findSqlBuf, conditionMap, "find.Dic.name", "D_TYPENAME", "%");
		
		Condition condition = Cnd.wrap(findSqlBuf.toString()+" order by D_ORDER");
		List<Dic> list = basicDao.searchByPage(Dic.class, condition, currentPage, numPerPage);
		int count = basicDao.searchCount(Dic.class,condition);
		int maxPage = basicDao.maxPageSize(count, SystemContext.PAGE_SIZE);
		
		PageModel<Dic> pm = new PageModel<Dic>(list,maxPage,count,currentPage,numPerPage);
		request.setAttribute("pm", pm);
		request.setAttribute("cmap", conditionMap);
		
		return new JspView("/mgr/platform/dic/dicList");
	}
	
	/**
	 * 保存数据
	 * @param obj
	 * @return
	 */
	@At("/mgr/platform/dic/Save")
	@Ok("json:{quoteName:true, ignoreNull:true}")
	public Map Save(@Param("::Dic.") Dic obj,
			HttpServletRequest request,
			HttpSession sess){
		//如果新增则生成GUID主键
		if (null==obj.getId() || obj.getId().length()==0){
			obj.setId(commUtil.getGUID());
			basicDao.save(obj);
			this.saveLog("新增字典","新增字典" + obj.getTypename(), sess, request);
		}else{
			basicDao.update(obj);
			this.saveLog("修改字典","修改字典" + obj.getTypename(), sess, request);
		}
			
		return getJsonResult("200","数据保存成功","Dic_list","","closeCurrent","");
	}
	
	
	/**
	 * 删除数据
	 * @param ids
	 * @return
	 */
	@At("/mgr/platform/dic/Del")
	@Ok("json")
	public Map Del(@Param("ids") String ids,
			HttpServletRequest request,
			HttpSession sess){
		basicDao.deleteByIds(Dic.class, commUtil.Id2str(ids));
		
		this.saveLog("删除字典","删除字典" + ids, sess, request);
		return getJsonResult("200","数据删除成功","Dic_list","","","");
	}
	
	/**
	 * 跳转到编辑视图
	 * @param Id
	 * @param request
	 * @return
	 */
	@At("/mgr/platform/dic/Edit")
	public View EditView(@Param("Id") String Id,HttpServletRequest request){
		Dic rtobj = basicDao.find(Id, Dic.class);
		request.setAttribute("obj", rtobj);
		return new JspView("/mgr/platform/dic/dicEdit");
	}
	
}
