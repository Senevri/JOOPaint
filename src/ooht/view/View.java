package ooht.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import ooht.UI;

@SuppressWarnings("serial")
public abstract class View extends JFrame{
	@SuppressWarnings("unused")
	protected UI m_ui;
	public View(UI ui){
		m_ui = ui;
		this.setFocusable(true);
	}
	public boolean command(UI.Cmd command){
		return false;		
	}
	
	@SuppressWarnings("unused")
	protected JButton tButton(String title, String command, ActionListener al){
		JButton b = new JButton(title);
		b.setAlignmentX(JButton.RIGHT_ALIGNMENT);
		b.setActionCommand(command);
		b.addActionListener(al);
		return b;
	}
}
