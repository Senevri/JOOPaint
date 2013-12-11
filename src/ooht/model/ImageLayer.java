/**
 * 
 */
package ooht.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

/**
 * @author Senevri
 *
 */
public class ImageLayer extends BufferedImage {
	public String Name = "";
	public enum BlendMode {
		NORMAL, MULTIPLY, OVERWRITE 		
	} 
	
	public BlendMode blending = BlendMode.NORMAL;
	public double opacity = 1;
	public boolean show = true;
	
	public ImageLayer Combine(ImageLayer top) {
		ImageLayer out = new ImageLayer(this.getWidth(), this.getHeight(), this.TYPE_INT_ARGB);
		Graphics g = out.getGraphics();
		g.drawImage(this, 0, 0, null);
		g.drawImage(top, 0, 0, null);
		return out;
	}
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public ImageLayer(int arg0, int arg1, int arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public ImageLayer(int arg0, int arg1, int arg2, IndexColorModel arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public ImageLayer(ColorModel arg0, WritableRaster arg1, boolean arg2,
			Hashtable<?, ?> arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}
	public ImageLayer(BufferedImage img) {			 
		 super(img.getColorModel(), img.copyData(null), img.isAlphaPremultiplied(), null);		 
	}
	
	
}
