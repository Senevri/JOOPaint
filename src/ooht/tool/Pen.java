package ooht.tool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;



public class Pen extends Tool {
	protected Point point;
	private Point lastPoint;
	protected Color m_color;
	protected Stroke m_stroke;
	protected float m_strokesize;
	//private boolean down = true;
	
	public Pen(){
		m_color = new Color(0,0,0,255);
		point = new Point(0,0);
		m_stroke = new java.awt.BasicStroke(m_strokesize);		
		lastPoint = point;
		setup();
	}
	public Pen(Point p){
		point = p;
		setup();
	}
	private void setup(){
		this.type=Type.PEN;
	}
	
	public void setPosition(Point p){
		lastPoint = p;		
	}
	public void setColor(Color c){
		m_color = c;
	}
	public void setSize(float size) {
		m_strokesize = size;
		// TODO: Alternate strokes.
		m_stroke = new java.awt.BasicStroke(m_strokesize);		
		
	}
	
	
	
	public Color getColor(){
		return m_color;
	}
	
	public void down(Point p){
		point = p;
		lastPoint = p;
	}
	
	
	@Override
	public BufferedImage apply(BufferedImage img) {
		
		if(0 <= point.x && point.x<img.getWidth() && 
				0<= point.y && point.y<img.getHeight()){
			//if(1 <= Math.abs((point.x - lastPoint.x) + (point.y-lastPoint.y))){
				//draw intermediate points
				Graphics2D g2d = img.createGraphics();
				g2d.setColor(m_color);
				g2d.setStroke(m_stroke);
				g2d.drawLine(lastPoint.x, lastPoint.y, point.x, point.y);			
			//}
			//img.setRGB(point.x, point.y, m_color.getRGB());
			//lastPoint = point;
		}		
		return img;
	}
	@Override
	public void updateOptions(int id, int value) {
		switch (id) {
		case 1:
			setSize((float) (value/10.0));
			break;
		default:
			break;		
		}
		
	}
	@Override
	public int getOptionValue(int id) {
		// TODO: add switch based on option.
		return Math.round(m_strokesize*10);
	}
}
