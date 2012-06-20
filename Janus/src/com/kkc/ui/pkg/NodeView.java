package com.kkc.ui.pkg;

import java.awt.Color;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JLabel;

import com.kkc.model.pkg.Node;

public class NodeView extends JLabel {
	private Node node;
	private int nodeID;
	private Vector<NodeView> list;
	private int parentID;
	
	Point mouseClickedLocation = new Point(0, 0);
	
	public int[][] drawingPoint;		// 시계방향순으로 0위 1오른쪽 2 아래 3왼쪽
	
	public NodeView(Node n, int id)
	{
		super(n.getText(), JLabel.CENTER);
		drawingPoint = new int[4][2];
		list = new Vector<NodeView>();
		node = n;
		nodeID = id;
		setBounds(n.getxPos(), n.getyPos(), n.getWidth(), n.getHeight());
		setOpaque(true);
		setBackground(Color.YELLOW);

		start();
	}
	
	public NodeView(Node n, int id, NodeView parent)
	{
		this(n, id);
		if(parent!=null) { list.add(parent); }
	}
	
	private void start()
	{
		NodeMouseHandler handle = new NodeMouseHandler();
		addMouseListener(handle);
		addMouseMotionListener(handle);
		
	}
	
	public void setNodeInfo(Node n)
	{
		node = n;
		setBounds(n.getxPos(), n.getyPos(), n.getWidth(), n.getHeight());
		setOpaque(true);
		setText(n.getText());
		setBackground(Color.YELLOW);
	}
	
	public Node getNodeInfo()
	{
		return node;
	}
	
	public void setNodeID(int id)
	{
		nodeID = id;
	}
	
	public int getNodeID()
	{
		return nodeID;
	}

	public Vector<NodeView> getList() {
		return list;
	}

	public void setList(Vector<NodeView> list) {
		this.list = list;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	
	public void getMyPoint()
	{
		drawingPoint[0][0] = this.getX() + (this.getWidth()/2);
		drawingPoint[0][1] = this.getY();
		
		drawingPoint[1][0] = this.getX() + this.getWidth();
		drawingPoint[1][1] = this.getY() + (this.getHeight()/2);
		
		drawingPoint[2][0] = this.getX() + (this.getWidth()/2);
		drawingPoint[2][1] = this.getY() + this.getHeight();
		
		drawingPoint[3][0] = this.getX();
		drawingPoint[3][1] = this.getY() + (this.getHeight()/2);
		
			}
}
