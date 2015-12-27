package datFace;

import javax.swing.*;
import org.opencv.videoio.VideoCapture;
public class mainGUI extends JFrame {

	detectFace cp;
	JButton screenshot2;
	mainGUI()
	{
		cp=new detectFace();
		Thread thread = new Thread(cp);
		thread.start();
		add(cp);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,400);
		setVisible(true);
		
		
	}
	public static void main(String args[])
	{
		mainGUI cf=new mainGUI();
		
	}
}
