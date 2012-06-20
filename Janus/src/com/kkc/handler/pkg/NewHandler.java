package com.kkc.handler.pkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import com.kkc.ui.pkg.MainJFrame;
import com.kkc.ui.pkg.NodeView;

public class NewHandler implements ActionListener{

	MainJFrame p;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		p.pAttr.initField();
		
		AllHandleFunctions.setUniqueID(0);
		Vector<NodeView> vt = AllHandleFunctions.getVt_nodeView();
		
		for(int i=0;i<vt.size();i++)
		{
			NodeView v = vt.get(i);
			v.setVisible(false);
		}
		vt.removeAllElements();
		vt.clear();
	}
	
	public NewHandler(MainJFrame p)
	{
		this.p = p;
	}

}
