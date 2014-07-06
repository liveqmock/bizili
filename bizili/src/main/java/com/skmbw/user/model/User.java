package com.skmbw.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user", schema = "skmbw")
public class User {
    /**
     * 对应数据库表字段 user.user_id
     *
     * @date 2014-07-06 11:03:58
     */
    private Long userId;

    /**
     * 对应数据库表字段 user.user_name
     *
     * @date 2014-07-06 11:03:58
     */
    private String userName;

    /**
     * 对应数据库表字段 user.nick_name
     *
     * @date 2014-07-06 11:03:58
     */
    private String nickName;

    /**
     * 对应数据库表字段 user.email
     *
     * @date 2014-07-06 11:03:58
     */
    private String email;

    /**
     * 对应数据库表字段 user.password
     *
     * @date 2014-07-06 11:03:58
     */
    private String password;

    /**
     * 对应数据库表字段 user.user_account
     *
     * @date 2014-07-06 11:03:58
     */
    private String userAccount;

    /**
     * 对应数据库表字段 user.register_date
     *
     * @date 2014-07-06 11:03:58
     */
    private Date registerDate;

    /**
     * 对应数据库表字段 user.last_login_date
     *
     * @date 2014-07-06 11:03:58
     */
    private Date lastLoginDate;

    /**
     * 对应数据库表字段 user.address
     *
     * @date 2014-07-06 11:03:58
     */
    private String address;

    /**
     * 对应数据库表字段 user.zipcode
     *
     * @date 2014-07-06 11:03:58
     */
    private Integer zipcode;

    /**
     * 对应数据库表字段 user.province
     *
     * @date 2014-07-06 11:03:58
     */
    private String province;

    /**
     * 对应数据库表字段 user.city
     *
     * @date 2014-07-06 11:03:58
     */
    private String city;

    /**
     * 对应数据库表字段 user.district
     *
     * @date 2014-07-06 11:03:58
     */
    private String district;

    /**
     * 对应数据库表字段 user.street
     *
     * @date 2014-07-06 11:03:58
     */
    private String street;

    /**
     * 对应数据库表字段 user.mobile_phone
     *
     * @date 2014-07-06 11:03:58
     */
    private String mobilePhone;

    /**
     * 对应数据库表字段 user.telphone
     *
     * @date 2014-07-06 11:03:58
     */
    private String telphone;

    /**
     * 对应数据库表字段 user.level
     *
     * @date 2014-07-06 11:03:58
     */
    private Integer level;

    /**
     * 对应数据库表字段 user.enable
     *
     * @date 2014-07-06 11:03:58
     */
    private Integer enable;

    /**
     * 对应数据库表字段 user.gender
     *
     * @date 2014-07-06 11:03:58
     */
    private Integer gender;

    /**
     * 获得字段 user.user_id 的值
     *
     * @return the value of user.user_id
     *
     * @date 2014-07-06 11:03:58
     */
    @Id
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置字段 user.user_id 的值
     *
     * @param userId the value for user.user_id
     *
     * @date 2014-07-06 11:03:58
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获得字段 user.user_name 的值
     *
     * @return the value of user.user_name
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    /**
     * 设置字段 user.user_name 的值
     *
     * @param userName the value for user.user_name
     *
     * @date 2014-07-06 11:03:58
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获得字段 user.nick_name 的值
     *
     * @return the value of user.nick_name
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "nick_name")
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置字段 user.nick_name 的值
     *
     * @param nickName the value for user.nick_name
     *
     * @date 2014-07-06 11:03:58
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获得字段 user.email 的值
     *
     * @return the value of user.email
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    /**
     * 设置字段 user.email 的值
     *
     * @param email the value for user.email
     *
     * @date 2014-07-06 11:03:58
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获得字段 user.password 的值
     *
     * @return the value of user.password
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    /**
     * 设置字段 user.password 的值
     *
     * @param password the value for user.password
     *
     * @date 2014-07-06 11:03:58
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获得字段 user.user_account 的值
     *
     * @return the value of user.user_account
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "user_account")
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 设置字段 user.user_account 的值
     *
     * @param userAccount the value for user.user_account
     *
     * @date 2014-07-06 11:03:58
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 获得字段 user.register_date 的值
     *
     * @return the value of user.register_date
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "register_date")
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * 设置字段 user.register_date 的值
     *
     * @param registerDate the value for user.register_date
     *
     * @date 2014-07-06 11:03:58
     */
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * 获得字段 user.last_login_date 的值
     *
     * @return the value of user.last_login_date
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "last_login_date")
    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * 设置字段 user.last_login_date 的值
     *
     * @param lastLoginDate the value for user.last_login_date
     *
     * @date 2014-07-06 11:03:58
     */
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * 获得字段 user.address 的值
     *
     * @return the value of user.address
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    /**
     * 设置字段 user.address 的值
     *
     * @param address the value for user.address
     *
     * @date 2014-07-06 11:03:58
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获得字段 user.zipcode 的值
     *
     * @return the value of user.zipcode
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "zipcode")
    public Integer getZipcode() {
        return zipcode;
    }

    /**
     * 设置字段 user.zipcode 的值
     *
     * @param zipcode the value for user.zipcode
     *
     * @date 2014-07-06 11:03:58
     */
    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * 获得字段 user.province 的值
     *
     * @return the value of user.province
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    /**
     * 设置字段 user.province 的值
     *
     * @param province the value for user.province
     *
     * @date 2014-07-06 11:03:58
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获得字段 user.city 的值
     *
     * @return the value of user.city
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    /**
     * 设置字段 user.city 的值
     *
     * @param city the value for user.city
     *
     * @date 2014-07-06 11:03:58
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获得字段 user.district 的值
     *
     * @return the value of user.district
     *
     * @date 2014-07-06 11:03:58
     */@Column(name = "district")
    
    public String getDistrict() {
        return district;
    }

    /**
     * 设置字段 user.district 的值
     *
     * @param district the value for user.district
     *
     * @date 2014-07-06 11:03:58
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * 获得字段 user.street 的值
     *
     * @return the value of user.street
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    /**
     * 设置字段 user.street 的值
     *
     * @param street the value for user.street
     *
     * @date 2014-07-06 11:03:58
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * 获得字段 user.mobile_phone 的值
     *
     * @return the value of user.mobile_phone
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "mobile_phone")
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * 设置字段 user.mobile_phone 的值
     *
     * @param mobilePhone the value for user.mobile_phone
     *
     * @date 2014-07-06 11:03:58
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * 获得字段 user.telphone 的值
     *
     * @return the value of user.telphone
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "telphone")
    public String getTelphone() {
        return telphone;
    }

    /**
     * 设置字段 user.telphone 的值
     *
     * @param telphone the value for user.telphone
     *
     * @date 2014-07-06 11:03:58
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    /**
     * 获得字段 user.level 的值
     *
     * @return the value of user.level
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置字段 user.level 的值
     *
     * @param level the value for user.level
     *
     * @date 2014-07-06 11:03:58
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获得字段 user.enable 的值
     *
     * @return the value of user.enable
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "enable")
    public Integer getEnable() {
        return enable;
    }

    /**
     * 设置字段 user.enable 的值
     *
     * @param enable the value for user.enable
     *
     * @date 2014-07-06 11:03:58
     */
    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    /**
     * 获得字段 user.gender 的值
     *
     * @return the value of user.gender
     *
     * @date 2014-07-06 11:03:58
     */
    @Column(name = "gender")
    public Integer getGender() {
        return gender;
    }

    /**
     * 设置字段 user.gender 的值
     *
     * @param gender the value for user.gender
     *
     * @date 2014-07-06 11:03:58
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }
}