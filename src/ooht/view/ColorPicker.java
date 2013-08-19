package ooht.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import ooht.UI;
import ooht.tool.Pen;
import ooht.tool.Tool;

@SuppressWarnings("serial")
public class ColorPicker extends View {
	JPanel container = null;
	JPanel colorview = null;
	JLabel numeric = null;
	Color color = null;
	
	List<Color> palette = null; //TODO implement palette
 	enum Elems{RED (0), GREEN (1), BLUE (2), ALPHA (3), LABEL (1), COLOR (0);
		private final int index;
		Elems(int index){
			this.index = index;
		}
		int index(){
			return this.index;
		}
	};
	
	public Color getColor() {
		return this.color;
	}
	
	public ColorPicker(UI ui){
		super(ui);		
		this.setTitle("Color");
		this.setBounds(0, 200, 100, 300);
		this.
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		colorview = new JPanel();
		colorview.setMinimumSize(new Dimension(128, 128));
		colorview.setBounds(0,0,128,128);
		
		numeric = new JLabel();		
		numeric.setAlignmentX(LEFT_ALIGNMENT);
		container.setAlignmentX(LEFT_ALIGNMENT);
		container.add(colorview, Elems.COLOR);
		container.add(numeric, Elems.LABEL);		
		container.add(new cpScrollBar(Elems.RED));
		container.add(new cpScrollBar(Elems.GREEN));
		container.add(new cpScrollBar(Elems.BLUE));
		container.add(new cpScrollBar(Elems.ALPHA));
		this.getContentPane().add(container);
		this.setMinimumSize(this.getContentPane().getMinimumSize());
		this.pack();
		this.setVisible(true);
		displayCurrentColor();		
		this.validate();
	}
	public void repaint(){
		this.displayCurrentColor();
		super.repaint();		
	}
	public void update(){
		this.repaint();
	}
	
	/** DisplayCurrentColor
	 * Sets the scrollbars and the colorview panel to display the 
	 * color of the currently selected tool  
	 */
	public void displayCurrentColor(){
		Tool t = m_ui.getCurrentTool();
		if (t instanceof Pen){			
			color = ((Pen)t).getColor();
			numeric.setText("R: " + color.getRed() +
					"\nG: " + color.getGreen() + 
					"\nB: " + color.getBlue() +
					"\nA: " + color.getAlpha() );
			for(Component comp : container.getComponents()){
				if (comp instanceof cpScrollBar){
					switch(((cpScrollBar)comp).getElemID()){
					case ALPHA:
						((cpScrollBar)comp).setValue(color.getAlpha());
						break;
					case RED:
						((cpScrollBar)comp).setValue(color.getRed());
						break;
					case GREEN:
						((cpScrollBar)comp).setValue(color.getGreen());
						break;
					case BLUE:
						((cpScrollBar)comp).setValue(color.getBlue());
						break;
						default:
							break;
					}
				}
			}
			colorview.setBackground(color);
			colorview.repaint();
		}					
	}
	
	/** cpScrollBar
	 * JScrollBar with identity!
	 */
	private class cpScrollBar extends JScrollBar{
		private Elems id;
		cpScrollBar(Elems id){
			this.id = id;
			this.setOrientation(JScrollBar.HORIZONTAL);
			this.setBlockIncrement(1);		
			this.setMinimum(0);
			this.setMaximum(265);// hack since can't adjust... :/
			this.addAdjustmentListener(new AdjustColor());		
		}
		public Elems getElemID(){
			return this.id;
		}
	}	
	private class AdjustColor implements AdjustmentListener{
		@Override
		public void adjustmentValueChanged(AdjustmentEvent event) {			
			int val = event.getValue();
			cpScrollBar cur = (cpScrollBar)event.getSource();
			int r = color.getRed(); 
			int g = color.getGreen();
			int b = color.getBlue();
			int a = color.getAlpha();
			numeric.setText("R: "+r+"\nG: "+g+"\nB: "+b+"\nA: "+a);
			switch (cur.getElemID()){
			case RED:
				r=val;
				break;
			case GREEN:
				g=val;
				break;
			case BLUE:
				b=val;
				break;
			case ALPHA:
				a=val;
				break;			
			}
			color = new Color(r, g, b, a);
			Tool t = m_ui.getCurrentTool();
			if (t instanceof Pen){
				((Pen) t).setColor(color);
			}
			colorview.setBackground(color);
			colorview.repaint();
		
			//System.err.println(event.getID() + " adjust: " + val);
		}
		
	}
}
