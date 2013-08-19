package ooht.tool;

import java.awt.image.BufferedImage;

public abstract class Tool  {
	public enum Type{PEN, FILL}
	
	public Type type;
	public abstract BufferedImage apply(BufferedImage img);
	public abstract void updateOptions(int id, int value);
	public abstract int getOptionValue(int id);
}
