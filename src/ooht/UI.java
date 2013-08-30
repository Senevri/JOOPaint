package ooht;

//import java.awt.Dimension;
import java.awt.Color;
import java.awt.Toolkit;

//import javax.swing.JFrame;
import ooht.model.Image;
import ooht.tool.*;
import ooht.view.*;

public class UI {
	
	public enum Cmd { ZOOM_IN, ZOOM_OUT, PEN, FILL, SAVE, LOAD, NEW}
	public Toolkit tk = null;
	private Tool currentTool = null;
	private Toolbar tools = null;
	private ColorPicker colpick = null;
	private MainView main = null;
	private ooht.model.Image img = null;
	private ToolOptionsPanel top = null;
	
	public UI(){
		tk = java.awt.Toolkit.getDefaultToolkit();
		main = new MainView(this);				
		currentTool = new Pen();
		tools = new Toolbar(this);
		
		colpick = new ColorPicker(this);
		top = new ToolOptionsPanel(this);
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
