package com.qijiabin.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

/**
 * ========================================================
 * 日 期：2016年8月18日 上午10:05:21
 * 版 本：1.0.0
 * 类说明：二维码生成器
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class CreateQRcode {

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 * @param imgPath
	 */
	public void encoderQRCode(String content, String imgPath, int version) {
		try {
			Qrcode qrcodeHandler = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
			qrcodeHandler.setQrcodeErrorCorrect('M');
			// N代表数字,A代表字符a-Z,B代表其他字符
			qrcodeHandler.setQrcodeEncodeMode('B');
			// 版本1为21*21矩阵，版本每增1，二维码的两个边长都增4；所以版本7为45*45的矩阵；最高版本为是40，是177*177的矩阵
			qrcodeHandler.setQrcodeVersion(version);
			int imgSize = 67 + 12 * (version - 1);
			System.out.println(content);
			byte[] contentBytes = content.getBytes("gb2312");

			BufferedImage bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();

			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);

			// 设定图像颜色 > BLACK
			gs.setColor(Color.BLACK);

			// 设置偏移量 不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容 > 二维码
			if (contentBytes.length > 0 && contentBytes.length < 130) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,130 ]. ");
			}
			gs.dispose();
			bufImg.flush();
			File imgFile = new File(imgPath);
			// 生成二维码QRCode图片
			ImageIO.write(bufImg, "png", imgFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String imgPath = "D:/Relieved_QRCode.png";
		String content = "http://blog.csdn.net/gao36951";
		CreateQRcode handler = new CreateQRcode();
		handler.encoderQRCode(content, imgPath, 8);
		System.out.println("encoder QRcode success");
	}
	
}

