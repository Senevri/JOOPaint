/**
 * 
 */
package ooht.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * @author Esa
 *
 */
public class Image extends Model {

	private BufferedImage img = null;
	private int height = 240;
	private int width = 320;
	
	private String filename = null;
	/**
	 * 
	 */
	public Image(String filename) {
		// TODO Auto-generated constructor stub
		this.filename = filename;
		this.width = 320;
		this.height = 240;				
	}
	
	public Image(int width, int height) {
		this.filename = System.getProperty("user.dir");
		this.width = width;
		this.height = height;
	}

	public Image(BufferedImage apply) {
		this.img=apply;
		this.width = this.img.getWidth();
		this.height = this.img.getHeight();
	}

	public BufferedImage data(){
		return this.img;
	}
	
	public Dimension dimensions(){
		return new Dimension(this.width, this.height);		
	}
	
	public boolean create(){
		if(null != this.img){
			return false;
		}
		this.img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB); 
		Graphics2D g = ((Graphics2D)img.getGraphics());
		g.setBackground(Color.WHITE);		
		g.setColor(Color.WHITE);
		//g.fillRect(0, 0, img.getWidth(), img.getHeight());
		//System.err.println("create");				
		return true;		
	}
	
	/* (non-Javadoc)
	 * @see ooht.Model#save()
	 */	
	public boolean save() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean save(BufferedImage img){
		try {	    
		    File outputfile = new File(this.filename);
		    ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
		    return false;
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see ooht.Model#load()
	 */	
	public boolean load() {
		try {
		    this.img = ImageIO.read(new File(this.filename));		    
		} catch (IOException e) {
			return false;			
		}
		this.width = this.img.getWidth();
		this.height = this.img.getHeight();
		return true;		
	}
	public String getName(){
		return filename;
	}

	public void setName(String text) {
		filename = text;		
	}
	
}
