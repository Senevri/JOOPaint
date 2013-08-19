package ooht.tool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Fill extends Pen {

	public Fill(){
		this.point = new Point(10,1);
		setup();
		// TODO Auto-generated constructor stub
	}

	public Fill(Point p) {
		super(p);
		setup();
		// TODO Auto-generated constructor stub
	}
	public void setup(){
		this.type=Type.FILL;
		
	}
	
	public BufferedImage apply(BufferedImage img) {		
		
		if(0 <= point.x && point.x<img.getWidth() && 
				0<= point.y && point.y<img.getHeight()){
			int old = img.getRGB(point.x, point.y);
			if (old == m_color.getRGB()) return img;
			flood(img, old, point);
			
		}		
		return img;
	}
	void drawHorizLine(BufferedImage img, int old, Point p){
		Graphics2D g2d = img.createGraphics();			
		g2d.setColor(m_color);		
		int x = p.x;
		int y = p.y;
		while(x>0 && old==img.getRGB(x-1, y)){
			if(x==0) break;
			if(x>0) x--; else break;
		}	
		if (old!=img.getRGB(x, y)) return;
		g2d.drawLine(x, y, p.x, y);	
		
		x=p.x+1;
		while(x<img.getWidth()-1 && old==img.getRGB(x+1, y)) {
			if (old!=img.getRGB(x+1, y)) return;
			if(x<img.getWidth()-1) x++; else break;			
		}			
		g2d.drawLine(x, y, p.x, y);		
	}
	/*void drawVertLine(BufferedImage img, int old, Point p){
		Graphics2D g2d = img.createGraphics();			
		g2d.setColor(m_color);		
		int x = p.x;
		int y = p.y;
		while(old==img.getRGB(x, y)){
			if(y==0) break;
			if(y>0) y--; else break;
		}		
		g2d.drawLine(x, y, p.x, p.y);		
		
		y=p.y+1;
		if (y>=img.getHeight()) return; 
		while(old==img.getRGB(x, y)) {
			if (y==img.getHeight()) break;
			if(y<img.getHeight()-1) y++; else break;			
		}			
		g2d.drawLine(x, y-1, x, p.y);		
		
	}*/
	//third version...
	void flood(BufferedImage img, int old, Point p){
		if (old!=img.getRGB(p.x, p.y)) return;
		Graphics2D g2d = img.createGraphics();			
		g2d.setColor(m_color);		
		int x = p.x;
		int y = p.y;
		int sx;
		while(x>0 && old==img.getRGB(x-1, y)){
			if(x==0) break;
			if(x>0) x--; else break;
		}	
		//if (old!=img.getRGB(x, y)) return;
		g2d.drawLine(x, y, p.x, y);
		
		sx=x;
		x=p.x;
		while(x<img.getWidth()-1 && old==img.getRGB(x+1, y)) {
			//if (old!=img.getRGB(x+1, y)) return;
			if(x<img.getWidth()-1) x++; else break;			
		}			
		g2d.drawLine(x, y, p.x, y);
		
		// on range sx - x scan if y-1 or y+1 = old. 
		// if so, horiz fill 
		while(sx<=x){
			if(y>0 && old==img.getRGB(sx, y-1)){
				flood(img, old, new Point(sx, y-1));
			}
			if(y<img.getHeight()-1 && old==img.getRGB(sx, y+1)){
				flood(img, old, new Point(sx, y+1));
			}
			sx++;
		}
		
	}
	/*void floodv2(BufferedImage img, int old, Point p){		
		int x = p.x;
		int y = p.y;
		if(old!=img.getRGB(x, y)) return;
		//drawHorizLine(img, old, p);
		//img.setRGB(x, y, m_color.getRGB());
		Graphics2D g2d = img.createGraphics();			
		g2d.setColor(m_color);		
		
		int ny=y; int nx=x;
		while ( ny < img.getHeight()-1 && 
				old==img.getRGB(x, ny+1)){
			drawHorizLine(img, old, new Point(x, ny));
			ny++;
			
		}
		ny=y;
		while ( ny > 0 && 
				old==img.getRGB(x, ny-1)){
			drawHorizLine(img, old, new Point(x, ny));
			ny--;
			
			
		}
			//flood(img, old, new Point(x, y+1));			
		while ( nx > 0 && 
				old==img.getRGB(nx-1, ny)) {			
			drawVertLine(img, old, new Point(nx-1, ny));
			nx--;
		}
		
			//flood(img, old, new Point(x-1, y));
		
		
			//flood(img, old, new Point(x, y-1));
		while ( x < img.getWidth()-1 && 
				old==img.getRGB(x+1, y)){
			x++;
		}
		drawVertLine(img, old, new Point(x, y));
			//flood(img, old, new Point(x+1, y));
	}*/
	
	/*void floodv1(BufferedImage img, int old, Point p){
		
		//TODO: correct fill algorithm
		int x = p.x;
		int y = p.y;
		
		drawHorizLine(img, old, p);
		
		while(p.y>0 && old==img.getRGB(p.x, p.y-1)){		
			if (y>0) {
				p.y--;	
				drawHorizLine(img, old, p);
			}
		}
		p.y=y;
		while(p.y<img.getHeight()-1 && old==img.getRGB(p.x, p.y+1)){		
			if (y<(img.getHeight()-1)) {
				p.y++;	
				drawHorizLine(img, old, p);
			}
		}
	}*/
	
}