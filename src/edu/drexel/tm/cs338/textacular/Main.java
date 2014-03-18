package edu.drexel.tm.cs338.textacular;

import java.awt.EventQueue;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The class Main.
 *
 * The main driver of the application
 *
 * @author Trevor Maglione <tm@cs.drexel.edu>
 */
public class Main {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        final ProjectFrame frame = new ProjectFrame();

        try {
            UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame.setVisible(true);
            }
        });
    }
}
