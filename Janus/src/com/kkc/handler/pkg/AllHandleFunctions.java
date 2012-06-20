package com.kkc.handler.pkg;

import java.awt.Graphics;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.kkc.ui.pkg.MyMindMapPanel;
import com.kkc.ui.pkg.NodeView;

public class AllHandleFunctions {
	
	private static int uniqueID;
	
	private static Vector<NodeView> vt_nodeView;
	
	//private static Graphics gs;
	
	public static MyMindMapPanel mp;
	
	static 
	{
		vt_nodeView = new Vector<NodeView>();
		uniqueID = 0; 
	}
	
	public static Vector<NodeView> getVt_nodeView()
	{
		return vt_nodeView;
	}
	
	public static void close()
	{
		System.exit(0);
	}
	
	public synchronized static int getUniqueID()
	{
		return uniqueID++;
	}
	
	public static void setUniqueID(int val)
	{
		uniqueID = val;
	}
	
	public static void saveToXML(String fileName)
	{
		Vector<NodeView> vt = AllHandleFunctions.getVt_nodeView();
		try{        
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();        
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// 루트 엘리먼트        
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("NodeList");
			doc.appendChild(rootElement);
			// Node 엘리먼트        
			for(int i=0;i<vt.size();i++)
			{
				NodeView temp = vt.get(i);
				Element node = doc.createElement("node");
				rootElement.appendChild(node);
				
				// id 엘리먼트
				Element id = doc.createElement("id");
				id.appendChild(doc.createTextNode(temp.getNodeID()+""));
				node.appendChild(id);
	    
				// xpos 엘리먼트        
				Element xPos = doc.createElement("xPos");
				xPos.appendChild(doc.createTextNode(temp.getX()+""));
				node.appendChild(xPos);
				// ypos 엘리먼트        
				Element yPos = doc.createElement("yPos"); 
				yPos.appendChild(doc.createTextNode(temp.getY()+"")); 
				node.appendChild(yPos);        
				// width 엘리먼트        
				Element width = doc.createElement("width");        
				width.appendChild(doc.createTextNode(temp.getWidth()+""));   
				node.appendChild(width);        
				// height 엘리먼트       
				Element height = doc.createElement("height");     
				height.appendChild(doc.createTextNode(temp.getHeight()+""));      
				node.appendChild(height);  
				// text 엘리먼트       
				Element text = doc.createElement("text");     
				text.appendChild(doc.createTextNode(temp.getText()));      
				node.appendChild(text);  
				Element link = doc.createElement("link");
				Vector<NodeView> subVt = temp.getList();
				if(subVt.size()>0)
				{
					for(int j=0;j<subVt.size();j++)
					{
						link.appendChild(doc.createTextNode(subVt.get(j).getNodeID()+""));      
					}
					node.appendChild(link);
				} else {
					link.appendChild(doc.createTextNode("-1"));
					node.appendChild(link);
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();     
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = null;
			try {
				result = new StreamResult(new FileOutputStream(new File(fileName)));
			}catch(IOException ee) {}
			// 파일로 쓰지 않고 콘솔에 찍어보고 싶을 경우 다음을 사용 (디버깅용)        
			// StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);    
			System.out.println("File saved!");
		}
		catch (ParserConfigurationException pce) {      
			pce.printStackTrace();
		}
		catch (TransformerException tfe) {        
			tfe.printStackTrace();
		}
	}
	
	public static void savePassword(String fileName, String password)
	{
		File f = new File("private.dat");
		try {
			
			FileWriter fw = new FileWriter(f, true);
			if(!f.exists()) {
					f.createNewFile();
			}
			
			fw.write(fileName + "\t" + password + "\r\n");
			fw.close();
		}catch(IOException e){}
	}
	
	public static void connectMindMapPanel(MyMindMapPanel p)
	{
		mp = p;
	}
	
	public void drawLine()
	{
	//	Graphics g = mp.getGraphics();
		Vector<NodeView> vt = AllHandleFunctions.getVt_nodeView();

		getPointPosition();
		
		for(int i=0;i<vt.size();i++)
		{
			NodeView temp = vt.get(i);
			if(temp.getList().size()==0) { 
				continue; }
			else {
				NodeView parent = temp.getList().get(0);
				
				//System.out.println(temp.getNodeID() + " 자식과 " + parent.getNodeID() + "부모 연결!");
				drawNearestLine(temp, parent);		// 두 노드간의 최단 지점 찾음.
			}
			
		}
	}
	
	private void drawNearestLine(NodeView child, NodeView parent)
	{
		//x y 좌표 합해서 가장 작은값으로 그려보자.
		int pflag=-1, cflag=-1;
		//int pflag2=-1, cflag2=-1;
		int nearestX = 9999;
		int nearestY = 9999;
		
		int tmpi_x, tmpi_y;
		int tmpj_x, tmpj_y;
		
		int childX, childY;
		int parentX, parentY;
		
		for(int i=0;i<4;i++)
		{
			tmpi_x = child.drawingPoint[i][0];
			tmpi_y = child.drawingPoint[i][1];
			for(int j=0;j<4;j++)
			{
				tmpj_x = parent.drawingPoint[j][0];
				tmpj_y = parent.drawingPoint[j][1];
				if(Math.abs(tmpi_x-tmpj_x) <= nearestX && Math.abs(tmpi_y - tmpj_y) <= nearestY)
				{
					nearestX = Math.abs(tmpi_x - tmpj_x);
					nearestY = Math.abs(tmpi_y - tmpj_y);
					cflag = i;
					pflag = j;
				}
			}
		}
		
		//System.out.println("child : " + cflag + "parent : "+ pflag);
		
		childX = child.drawingPoint[cflag][0];
		childY = child.drawingPoint[cflag][1];
		
		parentX = parent.drawingPoint[pflag][0];
		parentY = parent.drawingPoint[pflag][1];
		
		Graphics g = mp.getGraphics();
		g.drawLine(childX, childY, parentX, parentY);
	}
	
	private void getPointPosition()
	{
		Vector<NodeView> vt = AllHandleFunctions.getVt_nodeView();
		for(int i=0;i<vt.size();i++)
		{
			vt.get(i).getMyPoint();
		}
	}
}
