package br.bayclass.util;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class ImgUtil {

	public static BufferedImage mix(BufferedImage imgArr[]) {
		int w = imgArr[0].getWidth();
		int h = imgArr[0].getHeight();
		BufferedImage out = new BufferedImage(w, h, imgArr[0].getType());
		WritableRaster outRaster = out.getRaster();
		int[] pix = { 0, 0, 0, 255 };
		for (BufferedImage img : imgArr) {
			Raster raster = img.getData();
			try {
				for (int y = 0; y < h; y++) {
					for (int x = 0; x < w; x++) {
						pix = raster.getPixel(x, y, pix);
						int soma = pix[0] + pix[1] + pix[2];
						if (soma > 5)
							outRaster.setPixel(x, y, pix);
					}
				}

			} catch (Exception ex) {
				int aa = 0;
				aa++;
			}
		}

		return out;
	}
	
}
