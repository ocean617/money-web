package com.hst.money.vo;

import java.io.Serializable;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
/* vt_user VO */

@Table("pj_user")
 public class User implements Serializable { 
 private static final long serialVersionUID = 1L;

//用户编号
 @Name(casesensitive=false)
 @Column("U_ID")
private String id; 

//部门编号
 @Column("D_ID")
private String did; 

//部门编码
 @Column("D_CODE")
private String code; 

//部门名称
@Column("D_NAME")
private String dname;

public String getDname() {
	return dname;
}
public void setDname(String dname) {
	this.dname = dname;
}
//用户姓名
 @Column("U_NAME")
private String name; 

//用户帐户
 @Column("U_ACCOUNT")
private String account; 

//用户密码
 @Column("U_PWD")
private String pwd; 

//用户工号
 @Column("U_WORKID")
private String workid; 

//用户工作单位
 @Column("U_DW")
private String dw; 

//用户工作部门
 @Column("U_BM")
private String bm; 

//用户办公电话
@Column("U_WORKPHONE")
private String workphone; 

//用户手机
@Column("U_MOBILEPHONE")
private String mobilephone; 

//用户职务
 @Column("U_DUTY")
private String duty; 

//用户是否有效
 @Column("U_ENABLED")
private String enabled; 

 public String getDw() {
	return dw;
}
public void setDw(String dw) {
	this.dw = dw;
}
public String getBm() {
	return bm;
}
public void setBm(String bm) {
	this.bm = bm;
}
public String getWorkphone() {
	return workphone;
}
public void setWorkphone(String workphone) {
	this.workphone = workphone;
}
public String getMobilephone() {
	return mobilephone;
}
public void setMobilephone(String mobilephone) {
	this.mobilephone = mobilephone;
}
public String getDid() {
	return did;
}
public void setDid(String did) {
	this.did = did;
}
public String getId() {
   return id;
 }
 public void setId(String id) {
   this.id=id;
 }
 public String getCode() {
   return code;
 }
 public void setCode(String code) {
   this.code=code;
 }
 public String getName() {
   return name;
 }
 public void setName(String name) {
   this.name=name;
 }
 public String getAccount() {
   return account;
 }
 public void setAccount(String account) {
   this.account=account;
 }
 public String getPwd() {
   return pwd;
 }
 public void setPwd(String pwd) {
   this.pwd=pwd;
 }
 public String getWorkid() {
   return workid;
 }
 public void setWorkid(String workid) {
   this.workid=workid;
 }
  public String getDuty() {
   return duty;
 }
 public void setDuty(String duty) {
   this.duty=duty;
 }
 public String getEnabled() {
   return enabled;
 }
 public void setEnabled(String enabled) {
   this.enabled=enabled;
 }
 
 }
