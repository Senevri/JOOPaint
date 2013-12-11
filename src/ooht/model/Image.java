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
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

import javax.imageio.ImageIO;


/**
 * @author Esa
 *
 */
public class Image extends Model {	
	private BufferedImage img = null;
	private int height = 240;
	private int width = 320;	
	
	private String activeLayer = "Background";
	private Map<String, ImageLayer> layers = null;
	
	private Deque<ImageLayer> undostack = null;
	
	private String filename = null;
	/**
	 * 
	 */	
	public Image(String filename) {
		// TODO Auto-generated constructor stub
		Initialize();
		this.filename = filename;
		this.width = 320;
		this.height = 240;				
	}
	
	public Image(int width, int height) {
		Initialize();
		this.filename = System.getProperty("user.dir");
		this.width = width;
		this.height = height;
	}

	public Image(BufferedImage apply) {
		Initialize();		
		this.setImg(apply);
		this.width = this.img.getWidth();
		this.height = this.img.getHeight();
	}
	
	public void Initialize() {
		layers = new LinkedHashMap<String, ImageLayer>();
		undostack = new ArrayDeque<ImageLayer>(100);
	}	
	
	public void PushUndo(BufferedImage src){
		ImageLayer il = new ImageLayer(src);
		il.Name = this.activeLayer;
		undostack.push(il);		
		if (undostack.size() > 100) {
			undostack.removeLast();			
		} 
	}
	
	public BufferedImage Undo() {
		try {
			ImageLayer il = undostack.pop();
			this.layers.put(il.Name, il);
			this.img = il;
		} catch (NoSuchElementException ex) {
			// that's cool, we just can't undo further...
			
		}
		return this.img;		
	}
	
	public void addLayer(String layername) {
		ImageLayer layer = new ImageLayer(this.img.getWidth(), this.img.getHeight(), BufferedImage.TYPE_INT_ARGB );
		layers.put(layername, layer);
		this.img = layer;
		this.activeLayer = layername;
	}
	
	public int getLayerCount() {
		return this.layers.values().size();		
	}
	
	private void setImg(BufferedImage src){
		this.img=src;		
		layers.put("Background", new ImageLayer(this.img));		
	}
	
	
	
	private void changeLayer(String layername) {
		this.img = layers.get(layername); 
		this.activeLayer = layername;
		// TODO: error when looking with invalid layername?
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
		this.setImg(new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB));
		
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
		// TODO merge all layers, save png.
		ImageLayer combined = null;
		for(ImageLayer il: layers.values()) {
			 if (null == combined) {
				 combined = il;
			 } else {
				 combined.Combine(il);				 
			 }
			
		}
		try {	    
		    File outputfile = new File(this.filename);
		    ImageIO.write(combined, "png", outputfile);
		} catch (IOException e) {
		    return false;
		}
		return true;
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
		    this.setImg(ImageIO.read(new File(this.filename)));		    
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

	public Map<String, ImageLayer> getLayers() {
		// TODO Auto-generated method stub
		return layers;
	}

	public void update(BufferedImage imgbuf) {
		layers.get(activeLayer).setData(img.getData());		
	}
	
}
