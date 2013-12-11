package ooht.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.KeyStroke;

import ooht.UI;
import ooht.UI.Cmd;


@SuppressWarnings("serial")
public class MainView extends View {
		
	public MainView(UI ui){
		super(ui);
		//JFrame.setDefaultLookAndFeelDecorated(true);
		this.setup();
	}
	
	private ImageView iview=null;
		
	
	public void setup(){
		this.setTitle("Main");		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setFocusable(true);		
		
		Dimension d = m_ui.tk.getScreenSize();
		this.iview = new ImageView(this.m_ui);
		this.getContentPane().setLayout(new BorderLayout());			
		
		JScrollPane scroll = new JScrollPane(centeredPanel(iview));
		scroll.setAlignmentX(CENTER_ALIGNMENT);
		Dimension isize = m_ui.getImg().dimensions();
		
		this.getContentPane().add(scroll);
		//FIXME magic numbers account for borders + scrollbars
		this.setBounds((
				d.width/2)-(int)(isize.width*iview.zoomlevel())/2, 
				(d.height/2)-(int)(isize.height*iview.zoomlevel())/2, 
				(int)(isize.getWidth()*iview.zoomlevel())+8+10, 
				(int)(isize.getHeight()*iview.zoomlevel())+32+10);		
		this.setMinimumSize(this.getContentPane().getMinimumSize());
		this.setCustomTitle();
		this.setVisible(true);
		this.validate();		
	}
	
	public void newImage() {
		iview.imgbuf = m_ui.getImg().data();
		//fill background:
		ooht.tool.Fill fill = new ooht.tool.Fill();
		fill.setColor(m_ui.getCurrentColor());
		iview.imgbuf = fill.apply(iview.imgbuf);
		Dimension d = m_ui.tk.getScreenSize();
		Dimension isize = m_ui.getImg().dimensions();
		this.setBounds((
				d.width/2)-(int)(isize.width*iview.zoomlevel())/2, 
				(d.height/2)-(int)(isize.height*iview.zoomlevel())/2, 
				(int)(isize.getWidth()*iview.zoomlevel())+8+10, 
				(int)(isize.getHeight()*iview.zoomlevel())+32+10);
	}

	private void setCustomTitle(){
		this.setTitle(String.format("Main: Zoomlevel %.1f", iview.zoomlevel()));
	}
	
	public JPanel centeredPanel(JPanel content){		
		JPanel frame_outer = new JPanel();
		frame_outer.setLayout(new BoxLayout(frame_outer, BoxLayout.LINE_AXIS));
		frame_outer.add(Box.createHorizontalGlue());
		JPanel frame = new JPanel();
		frame.setLayout(new BoxLayout(frame, BoxLayout.PAGE_AXIS));		
		frame.add(Box.createVerticalGlue());
		frame.add(content);
		frame_outer.add(frame);
						
		addBindings(frame_outer);
		return frame_outer;				
	}
	
	public void addBindings(JPanel panel) {
		InputMap input = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actions = panel.getActionMap();
		
			input.put(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0), Cmd.ZOOM_IN );
			input.put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0),Cmd.ZOOM_OUT );
			input.put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0), Cmd.ZOOM_IN );
			input.put(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, 0),Cmd.ZOOM_OUT );
			
			input.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), Cmd.SAVE);
			input.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK), Cmd.LOAD);
			input.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), Cmd.PEN);
			input.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), Cmd.FILL);
			
			input.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), Cmd.UNDO);
			
			input.put(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK), Cmd.ADD_LAYER);
			
			actions.put(Cmd.ZOOM_IN, new KeyAction(Cmd.ZOOM_IN));		
			actions.put(Cmd.ZOOM_OUT, new KeyAction(Cmd.ZOOM_OUT));
			actions.put(Cmd.LOAD, new KeyAction(Cmd.LOAD));
			actions.put(Cmd.SAVE, new KeyAction(Cmd.SAVE));
			actions.put(Cmd.PEN, new KeyAction(Cmd.PEN));
			actions.put(Cmd.FILL, new KeyAction(Cmd.FILL));
			actions.put(Cmd.UNDO, new KeyAction(Cmd.UNDO));
			actions.put(Cmd.ADD_LAYER, new KeyAction(Cmd.ADD_LAYER));
		
	}
	class KeyAction extends AbstractAction {
		
		private UI.Cmd m_action;
		public KeyAction(UI.Cmd cmd)
		{
			m_action = cmd;			
		}
		
		public void actionPerformed(ActionEvent arg0) {
			
			switch (m_action) {
			case PEN:
				m_ui.toolCommand(UI.Cmd.PEN);
				break;
			case FILL:
				m_ui.toolCommand(UI.Cmd.FILL);
				break;
			case LOAD:
				LoadDialog ld = new LoadDialog(m_ui);								
				m_ui.command(m_action);
			default:
				m_ui.command(m_action);
				break;			
			}
		}
			
	}
	
	
	public boolean command(UI.Cmd command){
		switch (command){
		case ZOOM_IN:
			iview.setZoom(iview.zoomlevel()+0.1);
			this.setCustomTitle();
			break;
		case ZOOM_OUT:
			iview.setZoom(iview.zoomlevel()-0.1);
			this.setCustomTitle();
			break;
		case LOAD:
			try{				
				m_ui.getImg().load();
				iview.imgbuf=m_ui.getImg().data();
			} catch (Exception e){
				JDialog d = new JDialog(new JFrame(), "Error: Could not load " + 
						m_ui.getImg().getName());
				
			}
			break;
		case SAVE:		
			try {
			//m_ui.getImg().save(iview.imgbuf);
				m_ui.getImg().save();
			} catch (Exception e) {
				SaveDialog sd = new SaveDialog(m_ui);				
			}
			break;
		case NEW:
			newImage();
			break;
		case UNDO:
			iview.imgbuf = m_ui.getImg().Undo();
			break;
		case ADD_LAYER:
			String layername = String.format("New Layer %d", m_ui.getImg().getLayerCount());
			m_ui.getImg().addLayer(layername);			
			
			break;
		default:
			break;
		}
		iview.update();
		return true;
	}
	
	

	
}
