package org.bismi.util;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class CommonUtil {
	private static Logger log = LogManager.getLogger(CommonUtil.class);
	
	public static byte[] getScreenshotAsByte() {
		byte [] data=null;
		try {
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bos );
			data = bos.toByteArray();
		} catch (Exception e) {
			log.info(e.toString());
			
		}
		return data;
	}
	
}
