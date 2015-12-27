package datFace;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
public class detectFace extends JPanel implements Runnable,ActionListener {
private static final long serialVersionUID=1L;
BufferedImage image;
VideoCapture capture;
JButton screenshot;
CascadeClassifier faceDetector;
MatOfRect faceDetections;

detectFace()  {
	screenshot = new JButton("take it baby");
	screenshot.addActionListener(this);
	add(screenshot);
	
}
@Override
public void run()
{
System.loadLibrary("opencv_java310");
capture = new VideoCapture(0);
Mat camImage = new Mat();

faceDetector = new CascadeClassifier("C:\\Users\\Mert\\Desktop\\Screens\\opencv\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
faceDetections = new MatOfRect();
if(capture.isOpened())
{
	while(true)
	{
		capture.read(camImage);
		if(!camImage.empty())
		{
			JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
			topFrame.setSize(camImage.width()+40,camImage.height()+40);
			matToBufferedImage(camImage);
			faceDetector.detectMultiScale(camImage, faceDetections);
			repaint();
			
		}
		
	}
}

}
	
public void paintComponent(Graphics g)
{
super.paintComponent(g);
if(image==null) return;
g.drawImage(image, 10, 40, image.getWidth(),image.getHeight(),null);
g.setColor(Color.MAGENTA);
for(Rect rect:faceDetections.toArray())
{
	g.drawRect(rect.x+10, rect.y+40, rect.width, rect.height);
}

}

public void matToBufferedImage(Mat matBGR)
{
	int width=matBGR.width();
	int height = matBGR.height();
	int channels=matBGR.channels();
	byte[] source = new byte[width*height*channels];
	matBGR.get(0, 0,source);
	image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	final byte[] target=((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	System.arraycopy(source, 0, target, 0,	 source.length);
}
@Override
public void actionPerformed(ActionEvent e)
{
File output = new File("screenshot1.png");
int i=0;
while(output.exists())
{
i++;
output = new File("screenshot"+i+".png");

}
try{
ImageIO.write(image, "png", output);	

}catch(IOException e1){e1.printStackTrace();}
}
}
