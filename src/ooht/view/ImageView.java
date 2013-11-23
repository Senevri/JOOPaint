package ooht.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ooht.model.Image;
import ooht.tool.*;
import ooht.UI;


@SuppressWarnings("serial")
public class ImageView extends JPanel{	
	BufferedImage imgbuf = null;
	private UI m_ui=null;
	//private JPanel internal_img;
	public Image m_image = null;
	
	private double zoomlevel=2;
	public ImageView(UI ui){
		this.m_ui = ui;
		
		DrawingListener dl = new DrawingListener();
		this.addMouseListener(dl);
		this.addMouseMotionListener(dl);
		this.setAutoscrolls(true);
		
		Image out = new Image(System.getProperty("user.dir"));
		out.create();		
		m_ui.setImg(out);
		this.imgbuf = out.data();
		//fill background:
		ooht.tool.Fill fill = new ooht.tool.Fill();
		fill.setColor(Color.BLACK);
		this.imgbuf = fill.apply(this.imgbuf);
		m_image = out;
	}
	
	public void update(){
		this.repaint();
	}
	public void setZoom(double zl){
		this.zoomlevel = zl;
	}
	
	public double zoomlevel(){
		return this.zoomlevel;		
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(imgbuf, 
				0, 0, 
				(int)(imgbuf.getWidth()*zoomlevel), 
				(int)(imgbuf.getHeight()*zoomlevel), 
				0, 0, 
				imgbuf.getWidth(), 
				imgbuf.getHeight(), 
				this);
		this.setPreferredSize(
				new Dimension(
						(int)(imgbuf.getWidth()*zoomlevel), (int)(imgbuf.getHeight()*zoomlevel)));
		/*
		Graphics gfx = internal_img.getGraphics();
		gfx.drawImage(imgbuf, 0, 0, (int)(imgbuf.getWidth()*zoomlevel), (int)(imgbuf.getHeight()*zoomlevel), 
				0, 0, imgbuf.getWidth(), imgbuf.getHeight(), this);
		*/
		//Dimension d = m_ui.tk.getScreenSize();
		//this.setBounds(0, 0, d.width, d.height);
		this.revalidate();	
	}	
	
	private class DrawingListener implements MouseListener, MouseMotionListener{

		private Point lastPoint= null;
		boolean apply = true;
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent event) {
			/* Create backup of manipulated layer
			 * Store the IMGBuf in... image's undo stack? 
			 * */
			m_image.PushUndo(imgbuf);		
			
			int button = event.getButton();
			apply = true;
			lastPoint = event.getPoint();
			lastPoint.x = (int)(lastPoint.x/zoomlevel);
			lastPoint.y = (int)(lastPoint.y/zoomlevel);
			Tool t = m_ui.getCurrentTool();			
			if (t instanceof Pen) {
				if(button == MouseEvent.BUTTON1) {
					((Pen)t).down(lastPoint);		
				} else if (button == MouseEvent.BUTTON3) {
					((Pen)t).setColor(new Color(imgbuf.getRGB(lastPoint.x, lastPoint.y), true));
					((Pen)t).down(lastPoint);	
					apply = false;
				} else {
					apply=false;
				}
				
			}			
			if(apply) t.apply(imgbuf);
			m_ui.update();
			update();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {			
			
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(MouseEvent event) {			
			Point p = event.getPoint();			
			p.x = (int)(p.x/zoomlevel);
			p.y = (int)(p.y/zoomlevel);
			
			Tool t = m_ui.getCurrentTool();			
			if (t instanceof Pen) {
				if (t.type==Tool.Type.PEN){
					if (apply) ((Pen)t).setPosition(p);
				} else {
					apply=false;
				}
			}			
			if(apply) t.apply(imgbuf);
			if (t instanceof Pen) {							
					if (apply) ((Pen)t).down(p);			
			}
			
			update();
			apply = true;
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}	
	}
			
}
