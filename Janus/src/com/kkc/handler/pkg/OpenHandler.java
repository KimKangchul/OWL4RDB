package com.kkc.handler.pkg;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.kkc.model.pkg.Node;
import com.kkc.ui.pkg.MainJFrame;
import com.kkc.ui.pkg.NodeView;

public class OpenHandler implements ActionListener {

	MainJFrame p;
	public OpenHandler(MainJFrame p)
	{
		this.p = p;
	}
	
	private boolean isFileEncrypted(String fileName)
	{
		StringTokenizer stk;
		try {
			Scanner sc = new Scanner(new File("private.dat"));
			String line;
			String name;
			while(sc.hasNext())
			{
				line = sc.nextLine();
				stk = new StringTokenizer(line, "\t");
				name = stk.nextToken();
				if(name.equals(fileName)) { return true; }
			}
		}catch(IOException e){}
		return false;
	}
	
	private boolean checkPassword(String fileName, String password)
	{
		StringTokenizer stk;
		try {
			Scanner sc = new Scanner(new File("private.dat"));
			String line;
			String name;
			String pass;
			while(sc.hasNext())
			{
				line = sc.nextLine();
				stk = new StringTokenizer(line, "\t");
				name = stk.nextToken();
				pass = stk.nextToken();
				if(name.equals(fileName) && pass.equals(password)) { return true; }
			}
		}catch(IOException e){}
		return false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Vector<NodeView> vt = AllHandleFunctions.getVt_nodeView();
		
		//new 한번 해주고.
		new NewHandler(p).actionPerformed(e);
		
		FileDialog fd = new FileDialog(p , "파일열기", FileDialog.LOAD);
		fd.setVisible(true);
		
		if(fd.getFile() == null) { return; }
		
		if(isFileEncrypted(fd.getFile()))
		{
			String password = JOptionPane.showInputDialog("보호 기능 적용된 파일입니다. 암호를 입력하세요.");
			if(!checkPassword(fd.getFile(), password)) {
				JOptionPane.showMessageDialog(p, "암호가 맞지않아 열기 실패.");
				return;
			}
		}
		
		
		
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = dbf.newDocumentBuilder();
			Document doc = parser.parse(fd.getFile());
			Element root = doc.getDocumentElement();
			
			NodeList node_list = root.getElementsByTagName("node");
			int len = node_list.getLength();
			for(int i=0;i<len;i++)
			{
				node_list = root.getElementsByTagName("id");
				String id = node_list.item(i).getFirstChild().getNodeValue();
				node_list = root.getElementsByTagName("xPos");
				int x = Integer.parseInt(node_list.item(i).getFirstChild().getNodeValue());
				node_list = root.getElementsByTagName("yPos");
				int y = Integer.parseInt(node_list.item(i).getFirstChild().getNodeValue());
				node_list = root.getElementsByTagName("width");
				int w = Integer.parseInt(node_list.item(i).getFirstChild().getNodeValue());
				node_list = root.getElementsByTagName("height");
				int h = Integer.parseInt(node_list.item(i).getFirstChild().getNodeValue());
				node_list = root.getElementsByTagName("text");
				String t = node_list.item(i).getFirstChild().getNodeValue();
				node_list = root.getElementsByTagName("link");
				String link = null;
				link = node_list.item(i).getFirstChild().getNodeValue();
				
				Node newNode = new Node(x,y,w,h,t);
				NodeView v = new NodeView(newNode, Integer.parseInt(id));
				
				v.setParentID(Integer.parseInt(link));		// 부모 ID 기록해둠.
				
				p.pMindMap.setBackground(Color.YELLOW);
				p.pMindMap.setBackground(Color.WHITE);
				
				vt.add(v);
				p.pMindMap.add(v);
			}
		}catch(Exception ee) {
			ee.printStackTrace();
		}
		
		//부모정보 노드로 연결해주어야 함.
		for(int i=0;i<vt.size();i++)
		{
			NodeView v = vt.get(i);
			int pid = v.getParentID();
			System.out.println(v.getText() + v.getParentID());
			Vector<NodeView> parentList = v.getList();
			if(pid != -1)
			{
				for(int j=0;j<vt.size();j++)
				{
					NodeView candidate = vt.get(j);
					if(pid == candidate.getNodeID())		// 부모 노드 찾아서 리스트에 등록해주어야한다.
					{
						parentList.add(candidate);
					}
				}
			}
		}
		
		//NodeID 생성기 정보 변경.
		AllHandleFunctions.setUniqueID(vt.size());
	}
}
