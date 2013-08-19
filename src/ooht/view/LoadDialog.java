package ooht.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;

import ooht.UI;

@SuppressWarnings("serial")
public class LoadDialog extends View implements ActionListener{
	JTextField filepath = null;
	JFileChooser filechooser = null;
	public LoadDialog(UI ui){
		super(ui);		
		this.setName("Load File");
		//filepath = new JTextField(System.getProperty("user.dir"));
		//filepath.setSize(300, this.getMinimumSize().height);
		//filepath.addKeyListener(new LoadCommand(this))
		filechooser = new JFileChooser();
		int returnVal = filechooser.showOpenDialog(null); 
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		 //Open File
		} 
		//filechooser.addKeyListener(new LoadCommand(this));
		JPanel container = new JPanel(new FlowLayout());
		//container.add(filechooser);
		//container.add(filepath);
		//container.add(tButton("Load", "load", new LoadCommand(this)));
		this.getContentPane().add(filechooser);
		this.pack();
		this.setVisible(true);

	}
	
	public void actionPerformed(ActionEvent e) {
		filechooser = new JFileChooser();
		int returnVal = filechooser.showOpenDialog(null); 
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		 //Open File
		} 
		//filechooser.addKeyListener(new LoadCommand(this));
		JPanel container = new JPanel(new FlowLayout());
		//container.add(filechooser);
		//container.add(filepath);
		//container.add(tButton("Load", "load", new LoadCommand(this)));
		this.getContentPane().add(filechooser);
		this.pack();
		this.setVisible(true);
		
	}

	private class LoadCommand implements ActionListener, KeyListener{

		private View view;
		public LoadCommand(View v){
			view = v;
		}
		private void load(){
			//filepath.setText(filechooser.getSelectedFile.GetName();)

			m_ui.setImg(new ooht.model.Image(filechooser.getSelectedFile().getName()));
			m_ui.command(UI.Cmd.LOAD);
			view.setVisible(false);			
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
