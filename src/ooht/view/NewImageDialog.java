package ooht.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ooht.UI;
import ooht.model.Image;

public class NewImageDialog extends View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField m_widthText;
	private JTextField m_heightText;
	
	public NewImageDialog(UI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
		JPanel container = new JPanel(new FlowLayout());
		this.setTitle("Create New Image");
		CreateImage ci = new CreateImage();
		m_widthText = new JTextField("128");
		m_heightText = new JTextField("128");
		container.add(new JLabel("width"));
		container.add(m_widthText);
		container.add(new JLabel("height"));
		container.add(m_heightText);
		container.add(tButton("Create", "create", ci));	
		this.getContentPane().add(container);
		this.pack();
		this.setVisible(true);
	} 
	
	private class CreateImage implements ActionListener {
		
		public int width = 320;
		public int height = 240;
		@Override
		public void actionPerformed(ActionEvent event) {
			this.width = Integer.parseInt(m_widthText.getText());
			this.height = Integer.parseInt(m_heightText.getText());
			Image img = new Image(this.width, this.height);
			img.create();
			m_ui.setImg(img);
			m_ui.command(UI.Cmd.NEW);
		}
	}
	
	

}
