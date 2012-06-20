package com.kkc.handler.pkg;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.kkc.ui.pkg.MainJFrame;
import com.kkc.ui.pkg.NodeView;

public class SaveHandler implements ActionListener {
	MainJFrame p;
	private String fileName = null;
	public SaveHandler(MainJFrame p)
	{
		this.p = p;
	}
	
	public void actionPerformed(ActionEvent e){
		Vector<NodeView> vt = AllHandleFunctions.getVt_nodeView();
		
		if(vt.size()==0)
		{
			JOptionPane.showMessageDialog(p, "빈 파일은 저장할 수 없습니다.");
			return;
		}
		if(e.getSource() == p.bt_save || e.getSource() == p.m_item_save)
		{
			if(fileName == null)
			{
	
				FileDialog fd = null; 
				fd = new FileDialog(p , "파일저장", FileDialog.SAVE);
				fd.setVisible(true);
				
				if(fd.getFile() != null)
				{
					AllHandleFunctions.saveToXML(fd.getFile());
					fileName = fd.getFile();
				}
			} else {
				AllHandleFunctions.saveToXML(fileName);
			}
		} else {
			FileDialog fd = new FileDialog(p , "파일저장", FileDialog.SAVE);
			fd.setVisible(true);
			
			if(fd.getFile() != null)
			{
				AllHandleFunctions.saveToXML(fd.getFile());
				fileName = fd.getFile();
			}
		}
	}
}
