package com.hst.money.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.hst.money.dao.basicDao;
import com.hst.money.util.systemContext;
import com.hst.money.util.commUtil;
import com.hst.money.vo.*;

/**
 * @author ocean
 * Action父类，实现DAO对象的注入
 */
public class baseAction {

	@Inject
	protected basicDao basicDao;
	
	public void setBasicDao(basicDao basicDao) {
		this.basicDao = basicDao;
	}
	 
	/**
	 * 返回结果Map，以方便转换为Json串返回
	 * @param statusCode
	 * @param message
	 * @param navTabId
	 * @param rel
	 * @param callbackType
	 * @param forwardUrl
	 * @return
	 */
	public HashMap<String, String> getJsonResult(String statusCode,
												 String message,
												 String navTabId,
												 String rel,
												 String callbackType,
												 String forwardUrl){
		HashMap<String, String> mp = new HashMap<String, String>();
		mp.put("statusCode", statusCode);
		mp.put("message",message);
		mp.put("navTabId",navTabId);
		mp.put("rel",rel);
		mp.put("callbackType",callbackType);
		mp.put("forwardUrl",forwardUrl);
		return mp;
	}
	
	
	/**
	 * 拼装查询条件
	 * @param request
	 * @param buf
	 * @param conditionMap
	 * @param paramName
	 * @param paramFieldname
	 * @param symbol 操作符
	 *        %:字符型，生成like条件
	 *        d+符号:日期型
	 *        s+符号:字符
	 *        符号：数字
	 *        in:IN操作符，该操作符意味着paramName有多个，以;号隔开
	 */
	protected void createWhere(HttpServletRequest request,StringBuffer buf,HashMap conditionMap,String paramName,String paramFieldname,String symbol){
		//排除空值
		Object paramValue = request.getParameter(paramName);
		if (null!=paramValue && paramValue.toString().length()>0)
		{
			if (symbol.equals("%")){
				buf.append(" and "+paramFieldname+" like '%" +paramValue +"%' ");
			}else if (symbol.startsWith("s")){
				buf.append(" and "+paramFieldname+" "+symbol.substring(1)+" '" +paramValue +"' ");
			}else if (symbol.startsWith("d")){
				buf.append(" and "+paramFieldname+" "+symbol.substring(1)+" '" +paramValue +"' ");
			}else{
				buf.append(" and "+paramFieldname+" "+symbol + paramValue +" ");
			}
			conditionMap.put(paramName, paramValue);
			
		}else if (symbol.equals("in")){
			String ParamNames[] = paramName.split(";");
			if (ParamNames.length==0) return;
			buf.append(" and ( 1=1 ");
			for (String param : ParamNames){
				paramValue = request.getParameter(param);
				if (null!=paramValue && paramValue.toString().length()>0){
					buf.append(" or "+paramFieldname+" ='"+paramValue +"' ");
					conditionMap.put(param, paramValue);
				}
			}
			buf.append(" ) ");
			
		}
	}
	
	/**
	 * 保存日志
	 * @param strType
	 * @param strDesc
	 * @param sess
	 * @param req
	 */
	protected void saveLog(String strType,String strDesc,HttpSession sess,HttpServletRequest req){
		try
		{
			Log log = new Log();
			log.setType(strType);
			log.setDesc(strDesc);
			
			String uName = "未知";
			if (sess.getAttribute("user")!=null && sess.getAttribute("user") instanceof User ){
				uName = ((User)sess.getAttribute("user")).getName();
			}
			
			log.setOperman(uName);
			log.setIp(req.getRemoteAddr());
			log.setId(commUtil.getGUID());
			log.setDate(new java.sql.Timestamp(System.currentTimeMillis()));
			
			basicDao.save(log);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据配置名称得到配置项的值
	 * @param configName
	 * @return
	 */
	private String getConfig(String configName){
		String val = "未设置";
		sysConfig config = basicDao.findByCondition(sysConfig.class, Cnd.wrap(" C_CONFIGNAME='" +configName+ "'"));
		if (null!=config && config.getConfigvalue()!=null) val = config.getConfigvalue();
		return val;
	}
	
	/**
	 * 读取配置名称放至页面
	 * @param configName
	 * @param request
	 * @param sess
	 */
	protected void getConfigToPage(String configName,
			HttpServletRequest request,
			HttpSession sess){
		String configValue = sess.getAttribute(configName)!=null?sess.getAttribute(configName).toString():getConfig(configName);
		request.setAttribute(configName, configValue);
	}
	
	/**
	 * 得到某字典类型值的列表
	 * @param typeName
	 * @param request
	 * @return
	 */
	public List getDicList(String typeName){
		List list=null;
		if(!systemContext.dicMap.containsKey(typeName) || (systemContext.dicMap.get(typeName)!=null)  ){
			list = basicDao.search(Dic.class, Cnd.wrap(" D_TYPENAME='"+typeName+"' order by D_ORDER"));
			systemContext.dicMap.put(typeName, list);
		}
		
		list = (List) systemContext.dicMap.get(typeName);
		return list;
	}
	
}
