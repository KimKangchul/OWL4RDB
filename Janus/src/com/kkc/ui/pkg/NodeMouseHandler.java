package com.kkc.ui.pkg;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import com.kkc.handler.pkg.AllHandleFunctions;
import com.kkc.model.pkg.Node;


public class NodeMouseHandler extends MouseAdapter implements MouseMotionListener{

	int x, y;
	MyMindMapPanel mp = null;
	
	public void mousePressed(MouseEvent e)
	{
		NodeView p = (NodeView)e.getSource();
		 p.mouseClickedLocation.x = e.getX();
	     p.mouseClickedLocation.y = e.getY();
	}
	public void mouseDragged(MouseEvent e)
	{
		
		NodeView v = (NodeView)e.getSource();
		//x = e.getX();
		//y = e.getY();
		//v.setLocation(x, y);
		
		x = e.getLocationOnScreen().x - v.mouseClickedLocation.x - 680;
		y = e.getLocationOnScreen().y - v.mouseClickedLocation.y - 300;
		v.setLocation(x , y);
		
		MyAttrPanel p = null;
		
		Component[] c =  v.getParent().getParent().getComponents();
		for(int i=0;i<c.length;i++)		// ²Ä¼ö
		{
			if(c[i] instanceof MyAttrPanel) {
				p = (MyAttrPanel)c[i];
			} else if(c[i] instanceof MyMindMapPanel) {
				mp = (MyMindMapPanel)c[i];
				mp.repaint();
			}
		}
		
		p.setTargetNodeID(v.getNodeID());
		
		p.tf_text.setText(v.getNodeInfo().getText());
		p.tf_xPos.setText(v.getX()+"");
		p.tf_yPos.setText(v.getY()+"");
		p.tf_width.setText(v.getWidth()+"");
		p.tf_height.setText(v.getHeight()+"");
		
	}
	
	public void mouseReleased(MouseEvent e)
	{
		NodeView v = (NodeView)e.getSource();
		Component[] c =  v.getParent().getParent().getComponents();
		for(int i=0;i<c.length;i++)		// ²Ä¼ö
		{
			if(c[i] instanceof MyMindMapPanel) {
				mp = (MyMindMapPanel)c[i];
			}
		}
		
		mp.drawLine();
	}
	
	public void mouseClicked(MouseEvent e)
	{
		MyAttrPanel p = null;
		NodeView v = (NodeView)e.getSource();
		
		Component[] c =  v.getParent().getParent().getComponents();
		for(int i=0;i<c.length;i++)		// ²Ä¼ö
		{
			if(c[i] instanceof MyAttrPanel) {
				p = (MyAttrPanel)c[i];
			} else if(c[i] instanceof MyMindMapPanel) {
				mp = (MyMindMapPanel)c[i];
			}
		}
		
		p.setTargetNodeID(v.getNodeID());
		
		p.tf_text.setText(v.getNodeInfo().getText());
		p.tf_xPos.setText(v.getX()+"");
		p.tf_yPos.setText(v.getY()+"");
		p.tf_width.setText(v.getWidth()+"");
		p.tf_height.setText(v.getHeight()+"");
		
		Vector<NodeView> vt = AllHandleFunctions.getVt_nodeView();
		
		for(int i=0;i<vt.size();i++)
		{
			NodeView temp = vt.get(i);
			if(temp.getNodeID() == p.getTargetNodeID())
			{
				vt.get(i).setBackground(Color.RED);
			} else {
				vt.get(i).setBackground(Color.YELLOW);
			}
		}
		
		//vÀÇ ¿¬°áµÈ³ðµé Âï¾îº¸ÀÚ.
		for(int i=0;i<vt.size();i++)
		{
			NodeView temp = vt.get(i);
			if(temp.getList().size() > 0)
			{
				for(int j=0;j<temp.getList().size();j++)
				{
					System.out.println("³ëµå " + temp.getNodeID() + temp.getText() + "¿Í ¿¬°áµÈ ºÎ¸ðµé : " + temp.getList().get(j).getNodeID());
				}
			}
		}
		
		mp.drawLine();
	}
}
