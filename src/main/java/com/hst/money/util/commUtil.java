package com.hst.money.util;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import com.hst.money.vo.Right;

/**
 * @author ocean
 * 通用工具
 */
public class commUtil {
	
	/**
	 * 生成GUID
	 * @return
	 */
	static public String getGUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	
	/**
	 * MD5加密
	 * @param encodeStr
	 * @return
	 */
	public static String getMD5(String encodeStr) {
		  byte[] source =encodeStr.getBytes();
		  String s = null;
		  char hexDigits[] = {       // 用来将字节转换成 16 进制表示的字符
		     '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'}; 
		   try
		   {
		    java.security.MessageDigest md = java.security.MessageDigest.getInstance( "MD5" );
		    md.update( source );
		    byte tmp[] = md.digest();          // MD5 的计算结果是一个 128 位的长整数，
		                                                // 用字节表示就是 16 个字节
		    char str[] = new char[16 * 2];   // 每个字节用 16 进制表示的话，使用两个字符，
		                                                 // 所以表示成 16 进制需要 32 个字符
		    int k = 0;                                // 表示转换结果中对应的字符位置
		    for (int i = 0; i < 16; i++) {          // 从第一个字节开始，对 MD5 的每一个字节
		                                                 // 转换成 16 进制字符的转换
		     byte byte0 = tmp[i];                 // 取第 i 个字节
		     str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // 取字节中高 4 位的数字转换, 
		                                                             // >>> 为逻辑右移，将符号位一起右移
		     str[k++] = hexDigits[byte0 & 0xf];            // 取字节中低 4 位的数字转换
		    } 
		    s = new String(str);                                 // 换后的结果转换为字符串
		    s = s.toUpperCase();
		   }catch( Exception e )
		   {
		    e.printStackTrace();
		   }
		   return s;
		 }

	
	 /* 将字符串加’号
	 * @param srcStr
	 * @return
	 * 
	 */
	static public String Id2str(String srcStr){
		String arry[]  = srcStr.split("[,]");
		String retvalue = "";
		for (int i =0;i<arry.length-1 ;i++){
			retvalue+="'"+arry[i]+"',";
		}
		retvalue+="'"+arry[arry.length-1]+"'";
		return retvalue;
	}
	
	/**
	 * 保存上传的文件
	 * @return
	 * @throws Exception 
	 */
	static public void copyUploadFile(File srcfile,File descFile) throws Exception{
			  int length=2097152;  
			  FileInputStream in=new FileInputStream(srcfile);
			  FileOutputStream out=new FileOutputStream(descFile);
			  
			  byte[] buffer=new byte[length];
			  while(true){
			   int ins=in.read(buffer);
			   if(ins==-1){
			    in.close();
			    out.flush();
			    out.close();
			   }else{
			    out.write(buffer,0,ins);
			  }
	 }
  }
	
/**
 * 字符转义
 * @param string
 * @return
 */
public static String stringToHTMLString(String string) {
	    StringBuffer sb = new StringBuffer(string.length());
	    // true if last char was blank
	    boolean lastWasBlankChar = true;
	    int len = string.length();
	    char c;

	    for (int i = 0; i < len; i++)
	        {
	        c = string.charAt(i);
	        if (c == ' ') {
	            if (lastWasBlankChar) {
	                lastWasBlankChar = false;
	                sb.append("&nbsp;");
	                }
	            else {
	                lastWasBlankChar = true;
	                sb.append(' ');
	                }
	            }
	        else {
	            lastWasBlankChar = false;
	            //
	            // HTML Special Chars
	            if (c == '"')
	                sb.append("&quot;");
	            else if (c == '&')
	                sb.append("&amp;");
	            else if (c == '<')
	                sb.append("&lt;");
	            else if (c == '>')
	                sb.append("&gt;");
	            else if (c == '\n')
	                // Handle Newline
	                sb.append("<br />");
	            else if (c == '\r')
	                // Handle Newline
	                sb.append("<br />");
	            else {
	                int ci = 0xffff & c;
	                if (ci < 160 )
	                    // nothing special only 7 Bit
	                    sb.append(c);
	                else {
	                    // Not 7 Bit use the unicode system
	                    sb.append("&#");
	                    sb.append(new Integer(ci).toString());
	                    sb.append(';');
	                    }
	                }
	            }
	        }
	    return sb.toString();
	}


/**
 * 检查某权限是否存在于当前用户的权限列表中
 * @param RightName
 * @param lst
 * @return
 */ 
public static boolean IsHasRight(String RightName,List lst){
	Right r_vo =null;
	boolean isHas = false;
	if (lst!=null)
		for (Object obj:lst){
			r_vo = (Right)obj;
			if(r_vo.getName()!=null && r_vo.getName().equals(RightName)){
				isHas  =  true;
				break;
			}
		}
	
		return isHas;
	}

/**
 * 是否拥有某一类权限（即以某一类权限开头）
 * @param RightNames
 * @param lst
 * @return
 */
public static boolean IsHasSomeRight(String RightNames,List lst){
	Right r_vo =null;
	boolean isHas = false;
	String rights[] = RightNames.split(",");
	
	if (lst!=null)
		for (Object obj:lst){
			r_vo = (Right)obj;
			
			for (Object strObj:rights){
				if(r_vo.getName()!=null && strObj!=null && strObj.toString().length()>0 && r_vo.getName().startsWith(strObj.toString())){
					isHas  =  true;
					break;
				}
			}
			
			if (isHas) break;
		}
	
		return isHas;
	}

/**
 * 读取配置
 * @param typeName
 * @param confName
 * @return
 * @throws Exception 
 */
public static String getConf(String typeName,String confName) {
	String retstr = "";
	try
	{
		InputStream in = commUtil.class.getResourceAsStream("/com/fz/project/util/sync_"+typeName+".prop");
		Properties p = new Properties();
		p.load(in);
		retstr = p.getProperty(confName);
	}catch(Exception e){
		e.printStackTrace();
	}
	return retstr;
	
	}

/**
 * 根据区域进行属性值读取
 * @param string
 * @param string2
 * @param area
 * @return
 */
public static String getConf(String typeName, String confName, String area) {
	String retStr = "";
	String propes = getConf(typeName,confName);
	String areas_array[] = {"市级","荔湾","越秀","海珠","天河","白云","黄埔","番禺","花都","南沙","萝岗","从化"};
	
	if (propes.length()>0){
		String[] conf_array = propes.split("[;]");
		int i=0;
		for (String area_obj:areas_array){
			if (area_obj.equals(area.trim())){
				if (i<conf_array.length) retStr = conf_array[i];
				break;
			}
			i++;
		}
	}
	return retStr;
}

//复制文件
public static void copyFile(File sourceFile, File targetFile) throws IOException {
    BufferedInputStream inBuff = null;
    BufferedOutputStream outBuff = null;
    try {
        // 新建文件输入流并对它进行缓冲
        inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

        // 新建文件输出流并对它进行缓冲
        outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();
    } finally {
        // 关闭流
        if (inBuff != null)
            inBuff.close();
        if (outBuff != null)
            outBuff.close();
    }
}
}