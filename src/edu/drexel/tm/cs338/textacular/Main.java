package edu.drexel.tm.cs338.textacular;

import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        final ProjectFrame frame = new ProjectFrame();
        
        EventQueue.invokeLater(new Runnable() {
        	public void run() {
        		frame.setVisible(true);
        	}
        });
    }
}
