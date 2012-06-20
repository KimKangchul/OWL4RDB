package com.kkc.handler.pkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseHandler implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		AllHandleFunctions.close();
	}
}
