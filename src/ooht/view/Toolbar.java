package ooht.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ooht.UI;
import ooht.tool.Pen;
import ooht.tool.Tool;

@SuppressWarnings("serial")
public class Toolbar extends View {

	ToolCommand tc = null;
	//JPanel display = null;
	public Toolbar(UI ui) {		
		super(ui);
		this.setTitle("Toolbar");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tc = new ToolCommand();
		Container pane = this.getContentPane();		
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
		buttons.add(tButton("Pen", "pen", tc));
		buttons.add(tButton("Fill", "fill", tc));
		buttons.add(tButton("Zoom In", "zoom_in", tc));
		buttons.add(tButton("Zoom Out", "zoom_out", tc));
		buttons.add(tButton("New", "new", tc));
		buttons.add(tButton("Load", "load", tc));
		buttons.add(tButton("Save", "save", tc));
		
		/*display = new JPanel();
		display.setLayout(new BoxLayout(display, BoxLayout.LINE_AXIS));
		display.add(new JLabel("Zoom:"));
		container.add(display); */
		container.add(buttons);
		pane.add(container);
		
		//pane.add(display);
		this.setVisible(true);
		this.pack();
	}
	public void repaint(){
		super.repaint();
	}
	
	public void sendCommand(String command) {
		tc.sendCommand(command);
		
	}
	
	private class ToolCommand implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			String action = event.getActionCommand();			
			sendCommand(action);
			//FIXME: tool change forgets color
			
		}
		
		public void sendCommand(String action) {
			if(action.equals("pen")){
				usePen();
				//m_ui.command(UI.Cmd.PEN);
			} else if (action.equals("fill")){
				useFill();				
				
				//m_ui.command(UI.Cmd.PEN);
			} else if(action.equals("zoom_in")){
				m_ui.command(UI.Cmd.ZOOM_IN);
			} else if (action.equals("zoom_out")){
				m_ui.command(UI.Cmd.ZOOM_OUT);				
			}else if (action.equals("new")){
				NewImageDialog nid = new NewImageDialog(m_ui);
			}else if (action.equals("load")){			
				LoadDialog ld = new LoadDialog(m_ui);				
			}else if (action.equals("save")){
				SaveDialog sd = new SaveDialog(m_ui);				
			}
			
		} 

		public void useFill() {
			Tool t = m_ui.getCurrentTool();
			ooht.tool.Fill f = new ooht.tool.Fill();							
			if(t instanceof Pen){
				f.setColor(((Pen) t).getColor());
			}				
			m_ui.setCurrentTool(f);
		}

		public void usePen() {
			Tool t = m_ui.getCurrentTool();
			Pen p = new Pen();							
			if(t instanceof Pen){
				p.setColor(((Pen) t).getColor());
				p.setSize((float) t.getOptionValue(1) / 10);
			}				
			m_ui.setCurrentTool(p);
		}
				
	} // class ends

}
