package com.baocloud.yunku.dao;

import java.util.List;
import com.baocloud.yunku.pojo.User;
import com.baocloud.yunku.pojo.UserInte;

/**
 * 用户接口
 * 
 * @author DELL
 * 
 */
public interface UserDao {
	/**
	 * 添加用户(用户注册)
	 * 
	 * @param u
	 * @return
	 */
	public int userReg(String email, String password);

	/**
	 * 根据邮箱和密码查询用户
	 * 
	 * @param map
	 * @return
	 */
	public User getUser(String emal, String password);

	/**
	 * 查找用户信息
	 * 
	 * @param u
	 * @return
	 */
	public User getUser(Integer userId);

	/***
	 * 检查邮箱是否被注册
	 * 
	 * @param email
	 * @return
	 */
	public int checkEmail(String email);

	/**
	 * 用户修改手机号码时,检测手机号码是否被使用
	 * 
	 * @param mobile
	 * @param userId
	 * @return
	 */
	public int checkMobile(String mobile, Integer userId);

	/***
	 * 根据邮箱查询用户ID
	 * 
	 * @param email
	 * @return
	 */
	public Integer getUserId(String email);

	/**
	 * 根据用户ID查询该用户重置密码状态
	 * 
	 * @param userId
	 * @return
	 */
	public int getPwdResetSate(Integer userId);

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPwd
	 * @return
	 */
	public int updatePwd(Integer userId, String newPwd);

	/**
	 * 修改重置密码状态
	 * 
	 * @param userId
	 * @param state
	 * @return
	 */
	public int updateResetSate(Integer userId, int state);

	/***
	 * 查询每日签到状态
	 * 
	 * @param userId
	 * @param yyyyMMdd
	 * @return
	 */
	public int lookSign(Integer userId, int yyyyMMdd);

	/**
	 * 获取连续签到天数
	 * 
	 * @param userId
	 * @param yyyyMMdd
	 * @return
	 */
	public int getSignDays(Integer userId, int yyyyMMdd);

	/**
	 * 添加签到天数
	 * 
	 * @param userId
	 * @param days
	 * @return
	 */
	public int addSign(Integer userId, int days);

	/**
	 * 查询积分
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserInte> queryInte(Integer userId);

	/**
	 * 检查用户名是否存在
	 * 
	 * @param userName
	 * @return
	 */
	public int isNameExist(String userName);

	/***
	 * 设置用户名
	 * 
	 * @param userName
	 * @param userId
	 * @return
	 */
	public int setName(String userName, Integer userId);

	/**
	 * 修改手机
	 * 
	 * @param mobile
	 * @param userId
	 * @return
	 */
	public int setMobile(String mobile, Integer userId);

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	public int checkPwd(Integer userId, String password);

	/**
	 * 修改头像
	 * 
	 * @param userId
	 * @param pic
	 * @return
	 */
	public int updatePic(Integer userId, String pic);

	/**
	 * 修改用户登录状态
	 * 
	 * @param userId
	 * @param s
	 * @return
	 */
	public int updateUser(Integer userId, int s);
}
