package com.kkc.ui.pkg;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kkc.handler.pkg.AllHandleFunctions;
import com.kkc.model.pkg.Node;

public class MyAttrPanel extends JPanel{
	JLabel l_xPos, l_yPos;
	JLabel l_width,l_height;
	JLabel l_text;
	
	JTextField tf_xPos, tf_yPos;
	JTextField tf_width, tf_height;
	JTextField tf_text;

	JPanel p_bt;		//아래쪽 버튼 추가용 패널.
	JButton bt_modify;
	
	private int targetNodeID; 
	
	JPanel p;
	
	public MyAttrPanel()
	{
		createComponents();
		init();
		start();
	}
	
	private void createComponents()
	{
		l_xPos = new JLabel("X 좌표 : ", JLabel.CENTER);
		l_yPos = new JLabel("Y 좌표 : ", JLabel.CENTER);
		l_width = new JLabel("넓이 : ", JLabel.CENTER);
		l_height = new JLabel("높이 : ", JLabel.CENTER);
		l_text = new JLabel("텍스트 : ", JLabel.CENTER);
		
		tf_xPos = new JTextField(4);
		tf_yPos = new JTextField(4);
		tf_width = new JTextField(4);
		tf_height = new JTextField(4);
		tf_text = new JTextField(20);
		
		bt_modify = new JButton("변경");
		
		p_bt = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p = new JPanel(new GridLayout(5,2, 20, 50));
	}

	public void initField()
	{
		tf_xPos.setText("");
		tf_yPos.setText("");
		tf_width.setText("");
		tf_height.setText("");
		tf_text.setText("");
	}
	
	private void init()
	{
		setLayout(new BorderLayout());
		setSize(200,300);
		
		p.add(l_xPos);	p.add(tf_xPos);
		p.add(l_yPos);	p.add(tf_yPos);
		p.add(l_width);	p.add(tf_width);
		p.add(l_height);	p.add(tf_height);
		p.add(l_text);	p.add(tf_text);
		
		add(p, BorderLayout.CENTER);
		
		p_bt.add(bt_modify);
		
		add(p_bt, BorderLayout.SOUTH);
	}
	
	private void start()
	{
		bt_modify.addActionListener(new ModifyHandler());
		setVisible(true);
	}

	public int getTargetNodeID() {
		return targetNodeID;
	}

	public void setTargetNodeID(int targetNodeID) {
		this.targetNodeID = targetNodeID;
	}
	
	private class ModifyHandler implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) {
			Vector<NodeView> vt = AllHandleFunctions.getVt_nodeView();
			Node newInfo = new Node();
			newInfo.setText(tf_text.getText());
			newInfo.setxPos(Integer.parseInt(tf_xPos.getText()));
			newInfo.setyPos(Integer.parseInt(tf_yPos.getText()));
			newInfo.setWidth(Integer.parseInt(tf_width.getText()));
			newInfo.setHeight(Integer.parseInt(tf_height.getText()));
			
			for(int i=0;i<vt.size();i++)
			{
				NodeView v = vt.get(i);
				if(v.getNodeID() == targetNodeID)
				{
					v.setNodeInfo(newInfo);
				}
			}
			
			AllHandleFunctions.mp.repaint();
		}
	}
}
