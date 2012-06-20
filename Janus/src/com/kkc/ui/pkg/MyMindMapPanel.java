package com.kkc.ui.pkg;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

import com.kkc.handler.pkg.AllHandleFunctions;


public class MyMindMapPanel extends JPanel{
	
	
	public MyMindMapPanel()
	{
		init();
		start();
	}
	
	private void init()
	{
		setSize(800,500);
		setLayout(null);
		this.setBackground(Color.GRAY);
	}
	
	private void start()
	{
		this.addMouseListener(new MindMapPanelMouseHandler());
		setVisible(true);
		MindMapThread th = new MindMapThread();
		th.start();
	}
	/*
	public void paintComponent(Graphics g)
	{
		System.out.println("jj");
		//AllHandleFunctions.drawLine(g);
		//drawLine(g);
	}*/
	
	private class MindMapThread extends Thread
	{
		public void run()
		{
			while(true)
			{	
				drawLine();
				try {
					Thread.sleep(1000);
				}catch(Exception e){}
			}
		}
	}
	
	public void drawLine()
	{
		Graphics g = this.getGraphics();
		Vector<NodeView> vt = AllHandleFunctions.getVt_nodeView();

		getPointPosition();
		
		for(int i=0;i<vt.size();i++)
		{
			NodeView temp = vt.get(i);
			if(temp.getList().size()==0) { 
				continue; }
			else {
				NodeView parent = temp.getList().get(0);
				
				//System.out.println(temp.getNodeID() + " 자식과 " + parent.getNodeID() + "부모 연결!");
				drawNearestLine(temp, parent);		// 두 노드간의 최단 지점 찾음.
			}
			
		}
	}
	
	private void drawNearestLine(NodeView child, NodeView parent)
	{
		//x y 좌표 합해서 가장 작은값으로 그려보자.
		int pflag=-1, cflag=-1;
		int pflag2=-1, cflag2=-1;
		int nearestX = 9999;
		int nearestY = 9999;
		
		int plastflag=-1, clastflag=-1;
		
		int fartestX = 0;
		int fartestY = 0;
		
		int tmpi_x, tmpi_y;
		int tmpj_x, tmpj_y;
		int sum;
		
		int childX, childY;
		int parentX, parentY;
		
		for(int i=0;i<4;i++)
		{
			tmpi_x = child.drawingPoint[i][0];
			tmpi_y = child.drawingPoint[i][1];
			for(int j=0;j<4;j++)
			{
				tmpj_x = parent.drawingPoint[j][0];
				tmpj_y = parent.drawingPoint[j][1];
				if(Math.abs(tmpi_x-tmpj_x) <= nearestX && Math.abs(tmpi_y - tmpj_y) <= nearestY)
				{
					nearestX = Math.abs(tmpi_x - tmpj_x);
					nearestY = Math.abs(tmpi_y - tmpj_y);
					cflag = i;
					pflag = j;
				}
			}
		}
		
		//System.out.println("child : " + cflag + "parent : "+ pflag);
		
		childX = child.drawingPoint[cflag][0];
		childY = child.drawingPoint[cflag][1];
		
		parentX = parent.drawingPoint[pflag][0];
		parentY = parent.drawingPoint[pflag][1];
		
		Graphics g = this.getGraphics();
		g.drawLine(childX, childY, parentX, parentY);
	}
	
	private void getPointPosition()
	{
		Vector<NodeView> vt = AllHandleFunctions.getVt_nodeView();
		for(int i=0;i<vt.size();i++)
		{
			vt.get(i).getMyPoint();
		}
	}
}
