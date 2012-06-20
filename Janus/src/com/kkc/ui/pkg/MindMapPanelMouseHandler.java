package com.kkc.ui.pkg;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.kkc.handler.pkg.AllHandleFunctions;
import com.kkc.model.pkg.Node;

public class MindMapPanelMouseHandler implements MouseListener {
	
	private boolean isParentSelected()
	{
		boolean flag = false;
		
		Vector<NodeView> vt = AllHandleFunctions.getVt_nodeView();
		
		if(vt.size() == 0) { return true; }
		for(int i=0;i<vt.size();i++)
		{
			if(vt.get(i).getBackground() == Color.RED)
			{
				flag = true;
				break;
			}
		}
		return flag;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		MyMindMapPanel p = (MyMindMapPanel)e.getSource();
		// TODO Auto-generated method stub
		/*
		if(AllHandleFunctions.isAddEnabled())
		{
			MyMindMapPanel mapPanel = (MyMindMapPanel)e.getSource();
			mapPanel.setBackground(Color.GREEN);
			int xPos = e.getX();
			int yPos = e.getY();
			JLabel lb_new = new JLabel("테스트", JLabel.CENTER);
			lb_new.setBounds(100, 20, xPos, yPos);
			BevelBorder br = new BevelBorder(BevelBorder.RAISED);
			lb_new.setBorder(br);
			lb_new.setOpaque(true);
			lb_new.setBackground(Color.YELLOW);
			mapPanel.add(lb_new);
			
			AllHandleFunctions.setAddEnabled(false);
		}*/
		
		if(e.getClickCount() == 2)
		{
			
			if(!isParentSelected())
			{
				JOptionPane.showMessageDialog(null, "연결할 부모 노드를 선택해야 합니다.");
				return;
			}
			
			Vector<NodeView> vt = AllHandleFunctions.getVt_nodeView();
			int xPos = e.getX();
			int yPos = e.getY();
			
			NodeView parent = null;
			for(int i=0;i<vt.size();i++)
			{
				if(vt.get(i).getBackground() == Color.RED) 
				{
					parent = vt.get(i);
				}
			}
			
			Node newNode = new Node(xPos, yPos);
			NodeView v = new NodeView(newNode, AllHandleFunctions.getUniqueID(), parent);

			p.setBackground(Color.YELLOW);
			p.setBackground(Color.WHITE);
			
			vt.add(v);
			p.add(v);
			
		}
		p.drawLine();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
