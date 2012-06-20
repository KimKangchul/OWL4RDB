package com.kkc.model.pkg;

public class Node {
	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private String text;
	
	public Node()
	{
		
	}
	
	public Node(int x, int y)
	{
		xPos = x;
		yPos = y;
		
		width = 100;		//default.
		height = 30;
		text = "New node";
	}
	
	public Node(int x, int y, String s)
	{
		this(x,y);
		text = s;
		
		width = 100;		//default.
		height = 30;
	}
	
	public Node(int x, int y, int w, int h, String s)
	{
		this(x,y,s);
		width = w;
		height = h;
	}
	
	public int getxPos() {
		return xPos;
	}
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public int getyPos() {
		return yPos;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
