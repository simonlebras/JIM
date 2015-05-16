package tools;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public final class ImageTools {

	//--- Calcul la hauteur et la largeur de l'image à redimensionner ---
	public static Image scaleImage(Image source, int size) {
		int width = source.getWidth(null);
		int height = source.getHeight(null);
		double f = 0;
		if (width < height) {
			f = (double) height / (double) width;
			width = (int) (size / f);
			height = size;
		} else {
			f = (double) width / (double) height;
			width = size;
			height = (int) (size / f);
		}
		return scaleImage(source, width, height);
	}

	//--- Redimensionnement de l'image ---
	private static Image scaleImage(Image source, int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(source, 0, 0, width, height, null);
		g.dispose();
		return img;
	}

}
