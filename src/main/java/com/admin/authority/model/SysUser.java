package com.admin.authority.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * JavaBean工具类
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-09-16 下午4:12:56
 */
public class SysUser implements java.io.Serializable {
	
    /** 版本号 */
    private static final long serialVersionUID = 2389974384668826845L;
    
    /**  */
    private String user_id;
    
    /**  */
    private String username;
    
    /**  */
    private String password;
    
    /**  */
    private String nick;
    
    /**  */
    private String sex;
    
    /**  */
    private String location;
    
    /**  */
    private Timestamp created;
    
    /**  */
    private Timestamp lastVisit;
    
    /**  */
    private String visitTimes;
    
    /**  */
    
    private Date birthday;
    
    /**  */
    private String type;
    
    /**  */
    private String status;
    
    /**  */
    private String zip;
    
    /**  */
    private String address;
    
    /**  */
    private String city;
    
    /**  */
    private String state;
    
    /**  */
    private String country;
    
    /**  */
    private String district;
    
    public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
     * 获取
     * 
     * @return 
     */
    public String getUsername() {
        return this.username;
    }
     
    /**
     * 设置
     * 
     * @param username
     *          
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getPassword() {
        return this.password;
    }
     
    /**
     * 设置
     * 
     * @param password
     *          
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getNick() {
        return this.nick;
    }
     
    /**
     * 设置
     * 
     * @param nick
     *          
     */
    public void setNick(String nick) {
        this.nick = nick;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getSex() {
        return this.sex;
    }
     
    /**
     * 设置
     * 
     * @param sex
     *          
     */
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getLocation() {
        return this.location;
    }
     
    /**
     * 设置
     * 
     * @param location
     *          
     */
    public void setLocation(String location) {
        this.location = location;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public Timestamp getCreated() {
        return this.created;
    }
     
    /**
     * 设置
     * 
     * @param created
     *          
     */
    public void setCreated(Timestamp created) {
        this.created = created;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public Timestamp getLastVisit() {
        return this.lastVisit;
    }
     
    /**
     * 设置
     * 
     * @param lastVisit
     *          
     */
    public void setLastVisit(Timestamp lastVisit) {
        this.lastVisit = lastVisit;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getVisitTimes() {
        return this.visitTimes;
    }
     
    /**
     * 设置
     * 
     * @param visitTimes
     *          
     */
    public void setVisitTimes(String visitTimes) {
        this.visitTimes = visitTimes;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public Date getBirthday() {
        return this.birthday;
    }
     
    /**
     * 设置
     * 
     * @param birthday
     *          
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getType() {
        return this.type;
    }
     
    /**
     * 设置
     * 
     * @param type
     *          
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getStatus() {
        return this.status;
    }
     
    /**
     * 设置
     * 
     * @param status
     *          
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getZip() {
        return this.zip;
    }
     
    /**
     * 设置
     * 
     * @param zip
     *          
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getAddress() {
        return this.address;
    }
     
    /**
     * 设置
     * 
     * @param address
     *          
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getCity() {
        return this.city;
    }
     
    /**
     * 设置
     * 
     * @param city
     *          
     */
    public void setCity(String city) {
        this.city = city;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getState() {
        return this.state;
    }
     
    /**
     * 设置
     * 
     * @param state
     *          
     */
    public void setState(String state) {
        this.state = state;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getCountry() {
        return this.country;
    }
     
    /**
     * 设置
     * 
     * @param country
     *          
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getDistrict() {
        return this.district;
    }
     
    /**
     * 设置
     * 
     * @param district
     *          
     */
    public void setDistrict(String district) {
        this.district = district;
    }
}