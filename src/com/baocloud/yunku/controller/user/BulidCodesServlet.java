package com.baocloud.yunku.controller.user;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.baocloud.yunku.utils.RandomCodeUtils;

/**
 * 生成验证吗
 * 
 * @author wzr
 *
 */
@WebServlet(name = "BulidCodesServlet", value = "/common/getcodes")
public class BulidCodesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1752677520406123894L;
	private static final int IMG_WIDTH = 240;// 验证码图片的宽度
	private static final int IMG_HEIGHT = 40;// 验证码图片的高度
	private static final int LINE_COUNT = 125;// 验证码图片干扰线数量
	private static final int FONT_SIZE = 25;// 验证码字符大小
	private static final int CODE_COUNT = 6;// 验证码数量
	private Random random = new Random();
	private int code_spacing;// 验证码间距

	public BulidCodesServlet() {
		super();
		code_spacing = (IMG_WIDTH - FONT_SIZE * CODE_COUNT) / 7;
	}

	/**
	 * 获得字体
	 */
	private Font getFont() {
		return new Font("Times New Roman", Font.BOLD, FONT_SIZE);
	}

	/**
	 * 获得随机颜色
	 */
	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/**
	 * 绘制字符串
	 */
	private void drawOneCode(Graphics g, char code, int index) {
		g.setFont(getFont());
		g.setColor(new Color(random.nextInt(255), random.nextInt(255), random
				.nextInt(255)));
		g.translate(random.nextInt(3), random.nextInt(3));
		int x = code_spacing * index + FONT_SIZE * (index - 1);
		int y = IMG_HEIGHT/2;
		g.drawString(String.valueOf(code), x, y);
	}

	/**
	 * 绘制干扰线
	 */
	private void drowLine(Graphics g) {
		int x = random.nextInt(IMG_WIDTH);
		int y = random.nextInt(IMG_HEIGHT);
		int xl = random.nextInt(13);
		int yl = random.nextInt(15);
		g.drawLine(x, y, x + xl, y + yl);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		bulidCodes(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 禁止post提交:请求方法（GET、POST、HEAD、DELETE、PUT、TRACE等)对指定的资源不适用
		resp.setStatus(405);

	}

	public String bulidCodes(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT,
				BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		g.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
		g.setColor(getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i <= LINE_COUNT; i++) {
			drowLine(g);
		}
		// 绘制随机字符
		String codesStr = RandomCodeUtils.bulidNoRepeatCodes(CODE_COUNT);
		char[] codes = codesStr.toCharArray();
		for (int index = 0; index < CODE_COUNT; index++) {
			drawOneCode(g, codes[index], index + 1);
		}
		session.setAttribute("codes", codesStr);
		g.dispose();
		try {
			ImageIO.write(image, "JPEG", resp.getOutputStream());// 将内存中的图片通过流动形式输出到客户端
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
