package com.kkc.handler.pkg;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.kkc.ui.pkg.MainJFrame;
import com.kkc.ui.pkg.NodeView;

public class EncryptionHandler implements ActionListener {
	MainJFrame p;
	
	public EncryptionHandler(MainJFrame p)
	{
		this.p = p;
	}
	
	public void actionPerformed(ActionEvent e) {
		Vector<NodeView> vt = AllHandleFunctions.getVt_nodeView();
		
		if(vt.size()==0)
		{
			JOptionPane.showMessageDialog(p, "빈 파일은 암호화 할 수 없습니다.");
			return;
		}
		
		String password = JOptionPane.showInputDialog("보호 기능을 설정할 암호를 입력하세요.");
		if(password==null) { return; }
		
		FileDialog fd = new FileDialog(p , "파일저장", FileDialog.SAVE);
		fd.setVisible(true);
		
		AllHandleFunctions.saveToXML(fd.getFile());
		
		AllHandleFunctions.savePassword(fd.getFile(), password);
		
		JOptionPane.showMessageDialog(p, "보호 기능으로 저장 완료.");
		
	}
}
