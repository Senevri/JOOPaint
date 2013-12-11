package ooht;

//import java.awt.Dimension;
import java.awt.Color;
import java.awt.Toolkit;




import javax.swing.UIManager;

import javax.swing.UnsupportedLookAndFeelException;

//import javax.swing.JFrame;
import ooht.model.Image;
import ooht.tool.*;
import ooht.view.*;

public class UI {
	
	public enum Cmd { ZOOM_IN, ZOOM_OUT, PEN, FILL, SAVE, LOAD, NEW, UNDO, ADD_LAYER}
	public Toolkit tk = null;
	private Tool currentTool = null;
	private Toolbar tools = null;
	private ColorPicker colpick = null;
	private MainView main = null;
	private ooht.model.Image img = null;
	private ToolOptionsPanel top = null;
	
	public UI(){
		//Set look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		tk = java.awt.Toolkit.getDefaultToolkit();
		main = new MainView(this);				
		currentTool = new Pen();
		tools = new Toolbar(this);		
		colpick = new ColorPicker(this);
		colpick.setBounds(tools.getX(), tools.getHeight(), colpick.getBounds().width, colpick.getBounds().height);
		top = new ToolOptionsPanel(this);
		top.setBounds(colpick.getX(), colpick.getY() + colpick.getHeight(), 200, top.getBounds().height);
		
		
		
		//main.pack();		
	}
	
	public void update(){
		colpick.update();
		top.update();
	}
	
	void createFrame(){
		
		
	}
	
	public void toolCommand(Cmd command) {
		switch (command) {
		case PEN:
				tools.sendCommand("pen");
			break;
		case FILL:
				tools.sendCommand("fill");
			break;
		default:
			//throw new Exception("invalid toolcommand");
			break;		
		}
		update();
		
	}
	
	public void command(Cmd command){		
		main.command(command);
	}
	
	public Tool getCurrentTool(){
		return this.currentTool;
	}
	
	public Color getCurrentColor() {
		return colpick.getColor();
	}
	
	public void setCurrentTool(Tool t){
		currentTool= t;		
	}

	public void setImg(ooht.model.Image img) {
		this.img = img;
	}


	public ooht.model.Image getImg() {
		return img;
	}
	
	public ToolOptionsPanel getToolOptionsPanel() {
		return this.top;
		
	}
			
}
