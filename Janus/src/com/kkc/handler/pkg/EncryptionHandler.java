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
			JOptionPane.showMessageDialog(p, "�� ������ ��ȣȭ �� �� �����ϴ�.");
			return;
		}
		
		String password = JOptionPane.showInputDialog("��ȣ ����� ������ ��ȣ�� �Է��ϼ���.");
		if(password==null) { return; }
		
		FileDialog fd = new FileDialog(p , "��������", FileDialog.SAVE);
		fd.setVisible(true);
		
		AllHandleFunctions.saveToXML(fd.getFile());
		
		AllHandleFunctions.savePassword(fd.getFile(), password);
		
		JOptionPane.showMessageDialog(p, "��ȣ ������� ���� �Ϸ�.");
		
	}
}
