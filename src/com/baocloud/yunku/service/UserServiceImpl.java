package com.baocloud.yunku.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.baocloud.yunku.dao.UserDao;
import com.baocloud.yunku.dao.UserDaoImpl;
import com.baocloud.yunku.pojo.UserInte;
import com.baocloud.yunku.pojo.User;
import com.baocloud.yunku.utils.DateTimeUtil;
import com.baocloud.yunku.utils.EncryptUtils;
import com.baocloud.yunku.utils.RandomCodeUtils;
import com.baocloud.yunku.utils.SignUtils;
import com.baocloud.yunku.utils.StringUtils;
import com.baocloud.yunku.utils.mail.BaocloudEmailService;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public UserServiceImpl() {
		super();
		userDao = new UserDaoImpl();
	}

	@Override
	public int userReg(String email, String password) {
		return userDao.userReg(email, getPasswordMD5(password));
	}

	/**
	 * 密码加密
	 * 
	 * @param password
	 * @return
	 */
	private static String getPasswordMD5(String password) {
		for (int i = 1; i <= 5; i++) {
			password = EncryptUtils.getMD5(password + "http://www.yunku.com");
		}
		return password;
	}
	public static void main(String[] args) {
		System.out.println(getPasswordMD5("123456"));
	}

	@Override
	public int checkEmail(String email) {
		return userDao.checkEmail(email);
	}

	@Override
	public String sentEmailCodes(String email) {
		try {
			String subject = "注册验证码";
			// 随机产生6个数字验证码
			String codes = RandomCodeUtils.getCheckCode();
			String sendHtml = "欢迎注册云酷网会员,你的验证码是:<span style='color:#FF0000'>" + codes + "</span>";
			BaocloudEmailService.sentMailHtml(subject, sendHtml, email);
			return codes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User userLogin(String email, String password) {
		User user = userDao.getUser(email, getPasswordMD5(password));
		if (null != user) {
			if (null != user.getUserId() && user.getLoginState().equals("0")) {
				// 登录成功,设置登录状态为1
				userDao.updateUser(user.getUserId(), 1);
			}
		}
		return user;
	}

	@Override
	public int updateUser(Integer userId, int s) {
		return userDao.updateUser(userId, s);
	}

	@Override
	public int sendRestPwdURL(String email, String url) {
		int state = userDao.checkEmail(email);
		// 当邮件存在时,发送电子邮件
		if (1 == state) {
			try {
				Map<String, String> paramMap = new HashMap<String, String>();
				Integer userId = userDao.getUserId(email);
				paramMap.put("user_id", String.valueOf(userId));
				SignUtils signUtils = new SignUtils();
				paramMap.put("nonce_str", signUtils.getNonceStr());
				long expire = signUtils.getTimeExpire(15);
				paramMap.put("expire", String.valueOf(expire));
				String sign = signUtils.getSign(paramMap);
				StringBuffer paramBuf = new StringBuffer();
				paramBuf.append('?');
				Iterator<String> keys = paramMap.keySet().iterator();
				paramBuf.append("sign=" + sign);
				while (keys.hasNext()) {
					String key = keys.next();
					String value = paramMap.get(key);
					paramBuf.append("&" + key + "=" + value);
				}
				// 绑定参数
				url += paramBuf.toString();
				String subject = "找回密码";
				String sendHtml = "请点击以下超链接修改密码:<a href=\"?\">?</a>";
				sendHtml = sendHtml.replaceAll("[?]", url);
				BaocloudEmailService.sentMailHtml(subject, sendHtml, email);
				state = userDao.updateResetSate(userId, 1);
			} catch (Exception e) {
				e.printStackTrace();
				state = -1;
			}
		}
		return state;
	}

	@Override
	public int getResetPwdSate(Map<String, String> paramMap) {
		String userId = paramMap.get("user_id");
		String nonceStr = paramMap.get("nonce_str");
		String expire = paramMap.get("expire");
		String sign = paramMap.get("sign");
		// 判断是否缺少参数
		if (StringUtils.isEmptyStr(userId)) {
			return 500;
		}
		if (StringUtils.isEmptyStr(nonceStr)) {
			return 500;
		}
		if (StringUtils.isEmptyStr(expire)) {
			return 500;
		}
		if (StringUtils.isEmptyStr(sign)) {
			return 500;
		}
		SignUtils signUtils = new SignUtils();
		paramMap.remove("sign");
		// 判断签名是否错误
		String checkedSign = signUtils.getSign(paramMap);
		if (!checkedSign.equals(sign)) {
			return 110;
		}
		// 判断重置密码时间是否失效
		long nowSeconds = signUtils.getNowSeconds();
		if (nowSeconds >= Long.parseLong(expire)) {
			return 120;
		}
		// 查询当前用户是否可以重置密码
		return userDao.getPwdResetSate(Integer.valueOf(userId));

	}

	@Override
	public int updatePwd(Integer userId, String newPwd) {
		return userDao.updatePwd(userId, getPasswordMD5(newPwd));
	}

	@Override
	public int resetPwd(Integer userId, String newPwd) {
		int state = userDao.updatePwd(userId, getPasswordMD5(newPwd));
		if (1 == state) {
			// 重置密码后将密码重置状态设置为0,即不可以重置
			state = userDao.updateResetSate(userId, 0);
		}
		return state;
	}

	@Override
	public int addSign(Integer userId) {
		int days;// 连续签到天数
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (1 == day) {
			// 连续签到天数已月为单位(不累加),每月1日连续签到天数从1开始重新计算
			days = 1;
		} else {
			int today = DateTimeUtil.formatToNumber(new Date(), DateTimeUtil.YYYYMMDD);
			int y = today - 1;// 昨天
			// 获取昨天连续签到天数
			days = userDao.getSignDays(userId, y);
			days++;
		}
		return userDao.addSign(userId, days);
	}

	@Override
	public int getSignState(Integer userId) {
		int today = DateTimeUtil.formatToNumber(new Date(), DateTimeUtil.YYYYMMDD);
		int state = userDao.lookSign(userId, today);
		if (1 == state) {
			// 如果当日已经签,则获取当日连续签到天数
			state = userDao.getSignDays(userId, today);
		}
		return userDao.lookSign(userId, today);
	}

	@Override
	public List<UserInte> queryInte(Integer userId) {
		return userDao.queryInte(userId);
	}

	@Override
	public int isNameExist(String userName) {
		return userDao.isNameExist(userName);
	}

	@Override
	public int setName(String userName, Integer userId) {
		return userDao.setName(userName, userId);
	}

	@Override
	public int checkPwd(Integer userId, String password) {
		return userDao.checkPwd(userId, getPasswordMD5(password));
	}

	@Override
	public int updatePic(Integer userId, String pic) {
		return userDao.updatePic(userId, pic);
	}

	@Override
	public int checkMobile(String mobile, Integer userId) {
		return userDao.checkMobile(mobile, userId);
	}

	@Override
	public int setMobile(String mobile, Integer userId) {
		return userDao.setMobile(mobile, userId);
	}

}
