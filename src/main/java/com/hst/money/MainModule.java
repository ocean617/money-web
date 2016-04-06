package com.hst.money;

import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.hst.money.util.userFilter;

/**
 * @author ocean
 * 主模块配置
 */

@Fail("json")
@Filters({@By(type=userFilter.class)})
@IocBy(type=ComboIocProvider.class,args={
	"*org.nutz.ioc.loader.json.JsonLoader","/conf/datasource.json",
	"*org.nutz.ioc.loader.annotation.AnnotationIocLoader","com.hst.money"})
@Modules(scanPackage=true)
@Encoding(input="UTF-8",output="UTF-8")
public class MainModule {
	
}
