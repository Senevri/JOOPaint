package ooht.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import ooht.UI;

@SuppressWarnings("serial")
public class LoadDialog extends View implements ActionListener{
	JTextField filepath = null;
	JFileChooser filechooser = null;
	public LoadDialog(UI ui){
		super(ui);		
		this.setName("Load File");
		filechooser = new JFileChooser();
		filechooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "gif", "png"));
		int returnVal = filechooser.showOpenDialog(null); 
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			LoadCommand lc = new LoadCommand(this);
			lc.load();
		} 		
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.toString());
	}

	private class LoadCommand implements ActionListener, KeyListener{

		private View view;
		public LoadCommand(View v){
			view = v;
		}
		private void load(){
			//filepath.setText(filechooser.getSelectedFile.GetName();)
			try {
			m_ui.setImg(new ooht.model.Image(filechooser.getSelectedFile().getCanonicalPath()));
			m_ui.command(UI.Cmd.LOAD);
			view.setVisible(false);
			} catch (IOException ioe) {
				JDialog jd = new JDialog(new JFrame(), ioe.getMessage());				
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			// load file
			if((event.getActionCommand()).equals("load")){
				load();
			}
		}
		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyReleased(KeyEvent event) {
			if (KeyEvent.VK_ENTER==event.getKeyCode()){
				load();
			}
			
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}	
		
	}
}
