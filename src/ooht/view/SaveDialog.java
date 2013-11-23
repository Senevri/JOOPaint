package ooht.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import ooht.UI;

public class SaveDialog extends View {
	String filepath = null;
	UI m_ui = null;
	protected JFileChooser filechooser;
	public SaveDialog(UI ui){
		super(ui);		
		m_ui = ui;
		this.setName("Save File");		
		//filepath = new JTextField(System.getProperty("user.dir"));
		//filepath.setSize(300, this.getMinimumSize().height);
		//filepath.addKeyListener(new LoadCommand(this))
		filechooser = new JFileChooser();
		filechooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
		int returnVal = filechooser.showSaveDialog(null); 
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			java.io.File file = filechooser.getSelectedFile();
			try {
				filepath=file.getCanonicalPath();
				SaveCommand sc = new SaveCommand(this);
				sc.save();
			} catch (IOException ioe) {
				JDialog jd = new JDialog(new JFrame(), "Error" + ioe.getMessage());
			}			
		} 	
		
	}		
	
	public void actionPerformed(ActionEvent e) {
		
	}

	
	private class SaveCommand implements ActionListener, KeyListener{

		private View view;
		public SaveCommand(View v){
			view = v;
		}
		
		private void save(){
			if (filepath.equals(null)) {
				filechooser = new JFileChooser();
				int returnVal = filechooser.showSaveDialog(null); 
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					java.io.File file = filechooser.getSelectedFile();
					try {
						filepath=file.getCanonicalPath();
					} catch (IOException ioe) {
						JDialog jd = new JDialog(new JFrame(), "Error" + ioe.getMessage());
						return;
					}											
				} else {					
					view.setVisible(false);
					return;
				}						
			}
			if (!filepath.endsWith(".png") && null != filepath) {
				filepath += ".png";							
			}
			// FIXME problem if filepath remains null? 			
			m_ui.getImg().setName(filepath);
			m_ui.command(UI.Cmd.SAVE);
			view.setVisible(false);			
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			// save file
			String action = event.getActionCommand();
			if(action.equals("save")){
				save();
			}
		}
		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyReleased(KeyEvent event) {
			if (KeyEvent.VK_ENTER==event.getKeyCode()){
				save();
			}
			
		}
		@Override
		public void keyTyped(KeyEvent event) {
			
			
		}	
		
	}
}
