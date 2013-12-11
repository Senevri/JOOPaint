package ooht.view;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import ooht.UI;
import ooht.tool.Tool;

public class ToolOptionsPanel extends View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tool m_tool;
	private UI m_ui;
	private JPanel m_container;
	private HashMap<String, JLabel> m_datalabels;
	public ToolOptionsPanel(UI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
		//this.setBounds(0, 100, 200, -1);
		m_ui = ui;
		m_tool = ui.getCurrentTool();
		m_container = new JPanel();
		m_datalabels = new HashMap<String, JLabel>();
		m_container.setLayout(new BoxLayout(m_container, BoxLayout.PAGE_AXIS));
		this.setTitle("Options");
		update();
		this.getContentPane().add(m_container);
		//this.setMinimumSize(this.getContentPane().getMinimumSize());
		this.pack();
		this.setVisible(true);
	}
	
	public void repaint() {
		super.repaint();
	}
	
	public JLabel getDataLabel(String name) {
		return (JLabel)m_datalabels.get(name);
		
	}
	
	public void update() {		
		m_tool = m_ui.getCurrentTool();
		m_container.removeAll();
		m_datalabels.clear();
		switch (m_tool.type) {
		case PEN: 
			m_datalabels.put("size", new JLabel(String.valueOf(m_tool.getSize())));
			m_container.add(new JLabel("size"));
			m_container.add(m_datalabels.get("size"));
			m_container.add(new topScrollBar(1));
			break;
		case FILL:
			break;
		default:
			break;		
		}
		this.pack();
		this.validate();
		this.repaint();
	}
	private class topScrollBar extends JScrollBar{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int id;
		
		topScrollBar(int id){
			this.id = id;
			this.setSize(100, this.getWidth());
			this.setOrientation(JScrollBar.HORIZONTAL);
			this.setValue(m_tool.getOptionValue(id));
			this.setBlockIncrement(1);				
			this.setMinimum(0);
			this.setMaximum(265);// hack since can't adjust... :/
			this.addAdjustmentListener(new AdjustTool());		
		}
		public int getElemID(){
			return this.id;
		}
	}
	
	private class AdjustTool implements AdjustmentListener{
		@Override
		public void adjustmentValueChanged(AdjustmentEvent event) {			
			int val = event.getValue();
			topScrollBar cur = (topScrollBar)event.getSource();
			m_tool = m_ui.getCurrentTool();
			m_tool.updateOptions(cur.id, val);
			m_ui.setCurrentTool(m_tool);
			switch(m_tool.type) {
			case PEN:				
				m_ui.getToolOptionsPanel().getDataLabel("size").setText(String.valueOf(val/10));
			break;
			default: 
				break;
			}
		}
	}
}
