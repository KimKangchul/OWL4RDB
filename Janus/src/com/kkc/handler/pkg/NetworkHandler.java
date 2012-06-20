package com.kkc.handler.pkg;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JOptionPane;

import com.kkc.ui.pkg.MainJFrame;

public class NetworkHandler implements ActionListener {
	MainJFrame p;
	public NetworkHandler(MainJFrame p)
	{
		this.p = p;
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == p.bt_network_in || e.getSource() ==p.m_more_network_in)
		{
			ReceiveThread recv = new ReceiveThread();
			recv.start();
		} 
		else if(e.getSource() == p.bt_network_out || p.m_more_network_out == e.getSource()) {
			SendThread send = new SendThread();
			send.start();
		}
	}
	
	private class ReceiveThread extends Thread
	{
		public void run()
		{
			try {
				InetAddress ia = InetAddress.getLocalHost();
				
				DatagramSocket soc = new DatagramSocket(7777);
				JOptionPane.showMessageDialog(p, "수신 대기중입니다. (IP : " + ia.getHostAddress() + ")");
				File file = null;
				DataOutputStream dos = null;
				
				while(true) {
					DatagramPacket dp = new DatagramPacket(new byte[65508], 65508);
					soc.receive(dp);
					String str = new String(dp.getData()).trim();
					if(str.equalsIgnoreCase("start")) {
						JOptionPane.showMessageDialog(p, "파일 수신을 시작합니다. 저장할 파일 이름을 지정해주세요.");
						
						FileDialog fd = new FileDialog(p , "파일저장", FileDialog.SAVE);
						fd.setVisible(true);
						
						if(fd.getFile() == null) { return; }
						
						file = new File(fd.getFile());	// 여기에 파일 수신이름 정해야함.
						dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
					} else if(str.equalsIgnoreCase("end")) {
						JOptionPane.showMessageDialog(p, "파일 송신을 완료하였습니다.");
						dos.close();
						break;
					} else if(file != null) {
						dos.write(str.getBytes(), 0, str.getBytes().length);
					}
				}
			}catch(IOException e) {}
		}
	}
	
	private class SendThread extends Thread
	{
		public void run()
		{
			String ip = JOptionPane.showInputDialog("전송할 IP를 입력하세요.");
			if(ip==null) { return; }
			
			FileDialog fd = new FileDialog(p , "전송파일 선택", FileDialog.LOAD);
			fd.setVisible(true);
			if(fd.getFile() == null) { return; } 
			
			String filename = fd.getFile();
			try {
				DatagramSocket ds = new DatagramSocket();
				InetAddress ia = InetAddress.getByName(ip);
				String str = "start";
				DatagramPacket dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ia, 7777);
				ds.send(dp);
				
				DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(filename))));
				byte[] by = new byte[65508];
				
				while(true)
				{
					int xx = dis.read(by, 0, by.length);
					if(xx == -1) break;
					dp = new DatagramPacket(by, xx, ia, 7777);
					ds.send(dp);
				}
				
				str = "end";
				dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ia, 7777);
				
				ds.send(dp);
				ds.close();
			}catch(IOException e) {}
			
			JOptionPane.showMessageDialog(p, "xml file 송신 완료.");
			
		}
	}
}
