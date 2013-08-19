package ooht.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import ooht.UI;

public class SaveDialog extends View {
	JTextField filepath = null;
	public SaveDialog(UI ui){
		super(ui);		
		this.setName("Save File");
		filepath = new JTextField(m_ui.getImg().getName());
		filepath.setSize(300, this.getMinimumSize().height);
		filepath.setMinimumSize(new Dimension(300, 10));
		filepath.addKeyListener(new SaveCommand(this));
		JPanel container = new JPanel(new FlowLayout());
		container.setMinimumSize(new Dimension(360, 100));
		container.add(filepath);
		container.add(tButton("Save", "save", new SaveCommand(this)));
		this.getContentPane().add(container);
		this.pack();
		this.setVisible(true);
	
	}		
	private class SaveCommand implements ActionListener, KeyListener{

		private View view;
		public SaveCommand(View v){
			view = v;
		}
		private void save(){
			m_ui.getImg().setName(filepath.getText());
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
