package com.baocloud.yunku.service;

import java.util.List;
import java.util.Map;
import com.baocloud.yunku.pojo.User;
import com.baocloud.yunku.pojo.UserInte;

/**
 * 用户Service接口
 * 
 * @author wzr
 * 
 */
public interface UserService {
	/**
	 * 用户注册
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public int userReg(String email, String password);

	/**
	 * 检测邮箱是否被注册
	 * 
	 * @param email
	 * @return
	 */
	public int checkEmail(String email);

	/**
	 * 发送邮件验证码
	 * 
	 * @param email
	 * @return
	 */
	public String sentEmailCodes(String email);

	/**
	 * 用户登录
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public User userLogin(String email, String password);

	/**
	 * 修改用户登录状态
	 * 
	 * @param userId
	 * @param s
	 * @return
	 */
	public int updateUser(Integer userId, int s);

	/**
	 * 发送重置密码邮件
	 * 
	 * @param email
	 * @param url
	 * @return
	 */
	public int sendRestPwdURL(String email, String url);

	/**
	 * 获取重置密码状态
	 * 
	 * @param paramMap
	 * @return
	 */
	public int getResetPwdSate(Map<String, String> paramMap);

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPwd
	 * @return
	 */
	public int updatePwd(Integer userId, String newPwd);

	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @param newPwd
	 * @return
	 */
	public int resetPwd(Integer userId, String newPwd);

	/**
	 * 用户签到
	 * 
	 * @param userId
	 * @return
	 */
	public int addSign(Integer userId);

	/**
	 * 获取用户签到状态
	 * 
	 * @param userId
	 * @return
	 */
	public int getSignState(Integer userId);

	/**
	 * 查询积分
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserInte> queryInte(Integer userId);

	/**
	 * 判断用户名是否存在
	 * 
	 * @param userName
	 * @return
	 */
	public int isNameExist(String userName);

	/**
	 * 设置用户名
	 * 
	 * @param userName
	 * @param userId
	 * @return
	 */
	public int setName(String userName, Integer userId);

	/**
	 * 检查新密码与就密码是否相同
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	public int checkPwd(Integer userId, String password);

	/**
	 * 修改用户头像
	 * 
	 * @param userId
	 * @param pic
	 * @return
	 */
	public int updatePic(Integer userId, String pic);

	/**
	 * 查询手机是否被注册
	 * 
	 * @param mobile
	 * @param userId
	 * @return
	 */
	public int checkMobile(String mobile, Integer userId);

	/**
	 * 修改手机号码
	 * 
	 * @param mobile
	 * @param userId
	 * @return
	 */
	public int setMobile(String mobile, Integer userId);
}
