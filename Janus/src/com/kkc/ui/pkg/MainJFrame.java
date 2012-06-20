package com.kkc.ui.pkg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import com.kkc.handler.pkg.AllHandleFunctions;
import com.kkc.handler.pkg.CloseHandler;
import com.kkc.handler.pkg.EncryptionHandler;
import com.kkc.handler.pkg.NetworkHandler;
import com.kkc.handler.pkg.NewHandler;
import com.kkc.handler.pkg.OpenHandler;
import com.kkc.handler.pkg.SaveHandler;

public class MainJFrame extends JFrame {
	
	public MyAttrPanel pAttr;
	public MyMindMapPanel pMindMap;
	
	//splash
	Splash mySplash;
	
	//menu declaration
	JMenuBar menubar;
	JMenu m_file;
	JMenuItem m_item_newfile;
	JMenuItem m_item_open;
	public JMenuItem m_item_save;
	public JMenuItem m_item_saveas;
	JMenuItem m_item_close;
	
	JMenu m_more;
	JMenuItem m_more_encryption;
	public JMenuItem m_more_network_in;
	public JMenuItem m_more_network_out;
	//end of menu declaration
	JToolBar toolbar;
	JButton bt_newfile;
	JButton bt_open;
	public JButton bt_save;
	public JButton bt_saveas;
	JButton bt_close;
	
	JButton bt_encryption;
	public JButton bt_network_in;
	public JButton bt_network_out;
	//toolbar declaration
	
	//end of toolbar declaration
	
	public MainJFrame(String title)
	{
		super(title);
		showSplash();
		createComponents();
		init();
		start();
		
		AllHandleFunctions.connectMindMapPanel(pMindMap);
	}
	
	private void showSplash()
	{
		//splash
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);		}});
		mySplash = new Splash(this, "splash.gif");
		
		try {
			Thread.sleep(5000);
		}catch(Exception e) {}
	}
	
	private void createComponents()
	{
		
		toolbar = new JToolBar();
		pAttr = new MyAttrPanel();
		pMindMap = new MyMindMapPanel();
		
		{		// menu
			menubar = new JMenuBar();
			m_file = new JMenu("파일(F)");
			m_item_newfile = new JMenuItem("새파일(N)");
			m_item_open = new JMenuItem("열기(O)");
			m_item_save = new JMenuItem("저장(S)");
			m_item_saveas = new JMenuItem("다른 이름으로 저장(A)");
			m_item_close = new JMenuItem("닫기(C)");
			
			m_more = new JMenu("부가기능");
			m_more_encryption = new JMenuItem("암호 잠금");
			m_more_network_in = new JMenuItem("네트워크 수신");
			m_more_network_out = new JMenuItem("네트워크 송신");
		}		// end of menu
		
		{		// toolbar
			bt_newfile = new JButton(new ImageIcon("new.gif"));
			bt_open = new JButton(new ImageIcon("open.gif"));
			bt_save = new JButton(new ImageIcon("save.gif"));
			bt_saveas = new JButton(new ImageIcon("saveas.gif"));
			bt_close = new JButton(new ImageIcon("close.gif"));
			
			bt_encryption = new JButton(new ImageIcon("encryption.gif"));
			bt_network_in = new JButton(new ImageIcon("network_in.gif"));
			bt_network_out = new JButton(new ImageIcon("network_out.gif"));
		}		// end of toolbar
	}
	
	private void init()
	{
		setLayout(new BorderLayout());
		setLocation(200, 200);
		setSize(1300,500);

		add(pAttr, BorderLayout.WEST);
		add(pMindMap, BorderLayout.CENTER);
		
		{		// menu init
			m_item_newfile.setMnemonic('N');
			m_item_newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_MASK));
			m_file.add(m_item_newfile);
			
			
			m_item_open.setMnemonic('O');
			m_item_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_MASK));
			m_file.add(m_item_open);
			
			m_item_save.setMnemonic('S');
			m_item_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_MASK));
			m_file.add(m_item_save);
			
			m_item_saveas.setMnemonic('A');
			m_item_saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,KeyEvent.CTRL_MASK));
			m_file.add(m_item_saveas);
			
			m_item_close.setMnemonic('C');
			m_item_close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.CTRL_MASK));
			m_file.add(m_item_close);
			
			m_file.setMnemonic('F');
			//m_file.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,KeyEvent.CTRL_MASK));
			menubar.add(m_file);
			
			m_more.add(m_more_encryption);
			m_more.add(m_more_network_in);
			m_more.add(m_more_network_out);
			
			menubar.add(m_more);
			
			setJMenuBar(menubar);
		}		// end of menu init
		
		{		// toolbar init
			toolbar.add(bt_newfile); toolbar.addSeparator();
			toolbar.add(bt_open); toolbar.addSeparator();
			toolbar.add(bt_save); toolbar.addSeparator();
			toolbar.add(bt_saveas); toolbar.addSeparator();
			toolbar.add(bt_close); toolbar.addSeparator();
			
			toolbar.addSeparator();	toolbar.addSeparator();
			
			toolbar.add(bt_encryption); toolbar.addSeparator();
			toolbar.add(bt_network_in); toolbar.addSeparator(); 
			toolbar.add(bt_network_out); toolbar.addSeparator();
			
			
			add(toolbar, BorderLayout.NORTH);
		}		// end of tolbar init
	}
	
	private void start()
	{
		NewHandler newHandler = new NewHandler(this);
		SaveHandler saveHandler = new SaveHandler(this);
		OpenHandler openHandler = new OpenHandler(this);
		EncryptionHandler encryptionHandler = new EncryptionHandler(this);
		NetworkHandler networkHandler = new NetworkHandler(this);
		
		m_item_close.addActionListener(new CloseHandler());
		bt_close.addActionListener(new CloseHandler());
		
		//new
		m_item_newfile.addActionListener(newHandler);
		bt_newfile.addActionListener(newHandler);
		
		//save
		m_item_save.addActionListener(saveHandler);
		bt_save.addActionListener(saveHandler);
		
		//save as
		m_item_saveas.addActionListener(saveHandler);
		bt_saveas.addActionListener(saveHandler);
		
		//open
		m_item_open.addActionListener(openHandler);
		bt_open.addActionListener(openHandler);
		
		//encryption
		m_more_encryption.addActionListener(encryptionHandler);
		bt_encryption.addActionListener(encryptionHandler);
		
		//network
		
		//network-in
		m_more_network_in.addActionListener(networkHandler);
		bt_network_in.addActionListener(networkHandler);
		//network-out
		m_more_network_out.addActionListener(networkHandler);
		bt_network_out.addActionListener(networkHandler);
		
		setVisible(true);
	}
}
