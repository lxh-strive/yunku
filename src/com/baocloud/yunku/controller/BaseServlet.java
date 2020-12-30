package com.baocloud.yunku.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.baocloud.yunku.pojo.User;

/**
 * Servlet 基类
 * 
 * @author wzr
 *
 */
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BaseServlet() {
		super();
	}

	/**
	 * 获取URL 基础路径. 格式:http://IP地址或主机名或域名:端口/yunku/
	 * 
	 * @param request
	 * @return
	 */
	public String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		return basePath;
	}

	/**
	 * 获取用户ID
	 * 
	 * @param request
	 * @return
	 */
	public Integer getUserId(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		return null != user ? user.getUserId() : null;
	}

	/**
	 * 设置用户名
	 * 
	 * @param request
	 * @param userName
	 */
	public void setUserName(HttpServletRequest request, String userName) {
		User user = (User) request.getSession().getAttribute("user");
		if (null != user) {
			user.setUsername(userName);
		}
	}

	/**
	 * 设置手机号码
	 * 
	 * @param request
	 * @param userName
	 */
	public void setMobile(HttpServletRequest request, String mobile) {
		User user = (User) request.getSession().getAttribute("user");
		if (null != user) {
			user.setMobile(mobile);
		}
	}

	/**
	 * 设置用户头像
	 * 
	 * @param request
	 * @param userName
	 */
	public void setPic(HttpServletRequest request, String pic) {
		User user = (User) request.getSession().getAttribute("user");
		if (null != user) {
			user.setPic(pic);
		}
	}

	/**
	 * 
	 * @describe 获取客户端IP地址
	 * @method getIpAddr
	 * @param request
	 * @return String
	 */
	protected String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	protected Integer getInteger(HttpServletRequest request, String param) {
		String value = request.getParameter(param);
		Integer integer;
		if (null != value) {
			integer = Integer.valueOf(value);
		} else {
			integer = null;
		}
		return integer;
	}

	protected Long getLong(HttpServletRequest request, String param) {
		String value = request.getParameter(param);
		Long data;
		if (null != value) {
			data = Long.valueOf(value);
		} else {
			data = null;
		}
		return data;
	}

	protected String getString(HttpServletRequest request, String param) {
		return request.getParameter(param);
	}

	protected String[] getStringArray(HttpServletRequest request, String param) {
		return request.getParameterValues(param);
	}

	protected List<String> getStringList(HttpServletRequest request,
			String param) {
		List<String> valueList;
		String values[] = request.getParameterValues(param);
		if (null != values) {
			int length = values.length;
			valueList = new ArrayList<String>(length);
			for (int index = 0; index < length; index++) {
				valueList.add(values[index]);
			}
		} else {
			valueList = null;
		}
		return valueList;
	}

	protected Integer[] getIntegerArray(HttpServletRequest request, String param) {
		String values[] = request.getParameterValues(param);
		Integer integers[];
		if (null != values) {
			int length = values.length;
			integers = new Integer[length];
			for (int index = 0; index < length; index++) {
				integers[index] = Integer.valueOf(values[index]);
			}
		} else {
			integers = null;
		}
		return integers;
	}

	protected List<Integer> getIntegerList(HttpServletRequest request,
			String param) {
		String values[] = request.getParameterValues(param);
		List<Integer> integerList;
		if (null != values) {
			int length = values.length;
			integerList = new ArrayList<Integer>(length);
			for (int index = 0; index < length; index++) {
				integerList.add(Integer.valueOf(values[index]));
			}
		} else {
			integerList = null;
		}
		return integerList;
	}

	protected Double[] getDoubleArray(HttpServletRequest request, String param) {
		String values[] = request.getParameterValues(param);
		Double doubles[];
		if (null != values) {
			int length = values.length;
			doubles = new Double[length];
			for (int index = 0; index < length; index++) {
				doubles[index] = Double.valueOf(values[index]);
			}
		} else {
			doubles = null;
		}
		return doubles;
	}

	protected List<Double> getDoubleList(HttpServletRequest request,
			String param) {
		String values[] = request.getParameterValues(param);
		List<Double> doubleList;
		if (null != values) {
			int length = values.length;
			doubleList = new ArrayList<Double>(length);
			for (int index = 0; index < length; index++) {
				doubleList.add(Double.valueOf(values[index]));
			}
		} else {
			doubleList = null;
		}
		return doubleList;
	}

	protected void setRequestAttr(HttpServletRequest request, String key,
			Object value) {
		request.setAttribute(key, value);
	}

	protected Object getRequestAttr(HttpServletRequest request, String key) {
		return request.getAttribute(key);
	}

	protected void setSessionAttr(HttpServletRequest request, String key,
			Object value) {
		HttpSession session = request.getSession();
		session.setAttribute(key, value);
	}

	protected Object getSessionAttr(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		return session.getAttribute(key);
	}

	/** 获取客户端mac地址 */
	protected String getMACAddress(HttpServletRequest request) {
		final String ipAddress = getIpAddr(request);
		String strMAC;
		try {
			Process pp = Runtime.getRuntime().exec("nbtstat -a " + ipAddress);
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line;
			int macIndex = -1;
			while ((line = input.readLine()) != null) {
				if (line != null) {
					macIndex = line.indexOf("MAC Address");
					if (macIndex > 0) {
						break;
					}
				}
			}
			if (-1 != macIndex) {
				strMAC = line.substring(line.indexOf("MAC Address") + 14,
						line.length());
			} else {
				strMAC = "UU-UU-UU-UU-UU-UU";
			}
		} catch (IOException e) {
			e.printStackTrace();
			strMAC = "EE-EE-EE-EE-EE-EE";
		}
		return strMAC;
	}

	/**
	 * 向客户端响应输出操作状态
	 * 
	 * @param request
	 * @param response
	 * @param state
	 * @throws IOException
	 */
	protected void responeState(HttpServletRequest request,
			HttpServletResponse response, int state) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		response.setHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "must-revalidate");
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		out.print(state);
	}
}
