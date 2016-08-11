import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DualFractals extends JPanel implements Runnable, KeyListener, MouseWheelListener
{
	public static int sizeX, sizeY;
	public static double[] bounds = new double[4];
	public boolean[] keys;
	public double[] pos;
	public double animation, scale;
	public int quality;
	public ArrayList<Color> colorWheel;
	public ComplexCoord c;
	public DualFractals()
	{
		sizeX = 800;
		sizeY = 400;
		scale = 5;
		quality = 4;
		bounds[0] = -10;
		bounds[1] = 10;
		bounds[2] = -10;
		bounds[3] = 10;
		
		
		keys = new boolean[20];
		
		pos = new double[2];
		pos[0] = -1.7857383626485748;
		pos[1] = 6.973507051902347E-9;
		scale = 9.303535670983853E-15;
		quality = 1524;
		/*
		pos[0] = -1.2528056372963303;
		pos[1] = -0.3440360928970174;
		scale = 2.3083214051208173E-11;
		quality = 2005;
		keys[8] = true;
		//*/
		//pos[0] = -0.36574438600381676;
		//pos[1] = -0.7035087560858729;
		//[-0.36574438600381676, -0.7035087560858729]
		
		animation = 0;
		
		colorWheel = new ArrayList<Color>();
		/*
		colorWheel.add(new Color(255,0,0));
		colorWheel.add(new Color(255,128,0));
		colorWheel.add(new Color(255,255,0));
		colorWheel.add(new Color(128,255,0));
		colorWheel.add(new Color(0,255,0));
		colorWheel.add(new Color(0,255,128));
		colorWheel.add(new Color(0,255,255));
		colorWheel.add(new Color(0,128,255));
		colorWheel.add(new Color(0,0,255));
		colorWheel.add(new Color(128,0,255));
		colorWheel.add(new Color(255,0,255));
		colorWheel.add(new Color(255,0,128));
		*/
		/*
		int[] nums = new int[3];
		nums[0] = 255;
		for (int i = 0; i < 3; i++) {
			
			for (int j = 0; j < 255; j++) {
				colorWheel.add(new Color(nums[0],nums[1],nums[2]));
				nums[(i+1)%3]++;
			}
			for (int j = 0; j < 255; j++) {
				System.out.println(Arrays.toString(nums));
				colorWheel.add(new Color(nums[0],nums[1],nums[2]));
				nums[i]--;
			}
		}
		*/
		
		float[] nums = new float[3];
		nums[0] = 1;
		for (int i = 0; i < 3; i++) {
			
			for (int j = 0; j < 20; j++) {
				colorWheel.add(new Color(bound(nums[0]), bound(nums[1]), bound(nums[2])));
				nums[(i+1)%3] += .05;
			}
			for (int j = 0; j < 20; j++) { 
				System.out.println(Arrays.toString(nums));
				colorWheel.add(new Color(bound(nums[0]), bound(nums[1]), bound(nums[2])));
				nums[i] -= .05;
			}
		}
		
		//c = new ComplexCoord(0.7269, 0.1889);
		//c  = new ComplexCoord(0, .84);  
		c  = new ComplexCoord(.4, 0);
		addKeyListener(this);
		addMouseWheelListener(this);
		setVisible(true);
		setFocusable(true);
	}
	
	public float bound(float f)
	{
		return f>1?1:(f<0?0:f);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long tempTime = System.currentTimeMillis();
		while(true)
		{
			long diff = System.currentTimeMillis() - tempTime;
			 
			//while(diff<100)
			//	diff = System.currentTimeMillis() - tempTime; 
			//System.out.println("    "+diff);
			//Thread.currentThread().sleep(Math.max(0, 130 - diff));
			//System.out.println("Yay");
			tempTime = System.currentTimeMillis();
			repaint();
		}
	}
	
	public void paintComponent(Graphics g2) 
	{
		long tempTime = System.currentTimeMillis();
		BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		//BufferedImage img = new BufferedImage(5312,2988, BufferedImage.TYPE_INT_RGB);
		//img.setAccelerationPriority((float) .99);
		Graphics2D g = img.createGraphics();
		
		if(keys[0] || keys[4])
			pos[0] -= scale/20;
		if(keys[1] || keys[5])
			pos[0] += scale/20;
		if(keys[2] || keys[6])
			pos[1] -= scale/20;
		if(keys[3] || keys[7])
			pos[1] += scale/20;
		//System.out.println(Arrays.toString(pos));
		//scale *= .8;
		//c.add(new ComplexCoord(0, .001));
		//System.out.println(c); 
		double halfW = img.getWidth()/4.0;
		double halfH = img.getHeight()/2.0;
		
		int counter, qualityCheck=0;
		for (int i = 0; i < img.getWidth()/2; i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				//ComplexCoord point = new ComplexCoord(pos[0]+(i-halfW)/halfW*scale, pos[1]-(j-halfH)/halfH*scale);
				ComplexCoord point = new ComplexCoord(0,0);
				c = new ComplexCoord(pos[0]+(i-halfW)/halfW*scale, pos[1]-(j-halfH)/halfH*scale);
				counter = 0;
				//System.out.println(i+" "+j+" "+point);
				while(point.inBounds() && counter<quality)
				{
					point.multiply(point);
					//point.pow(3);
					point.add(c);
					//System.out.println("inside "+counter+point);
					++counter;
				}
				//System.out.println("go "+counter+point);
				
				if(counter==quality)
				{
					g.setColor(Color.black);
					++qualityCheck;
				}
				else
					g.setColor(colorWheel.get((counter+8)%colorWheel.size()));
				g.drawRect(i, j, 1, 1);
				//g2.drawImage(img, getX(), getY(), this);
			}
		}
		/*
		double percentCoverage = 1.0*qualityCheck/(getWidth()*getHeight());
		if(percentCoverage < .00005) 
			quality--;
		
		else if(percentCoverage >.0002)
			quality+=1;
		//*/
		if(keys[8])
		{
			
			try
			{
				System.out.println("Saving...");
				ImageIO.write(img, "BMP", new File("wallpaper2.bmp"));
				System.out.println("Saved!");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			keys[8] = false;
		}
		if(keys[9])
		{
			scale *= .9;
			quality += 5;
		}
		if(keys[10])
		{
			keys[10] = false;
			System.out.println(Arrays.toString(pos)+" "+scale+" "+quality);
		}
		g.dispose();
	    g2.drawImage(img, getX(), getY(), this);
	    System.out.println(System.currentTimeMillis() - tempTime); 
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT && !keys[0])
		{
			keys[0] = true;
			//repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && !keys[1])
		{
			keys[1] = true;
			//repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && !keys[2])
		{
			keys[2] = true;
			//repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_UP && !keys[3])
		{
			keys[3] = true;
			//repaint();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_A && !keys[4])
		{
			keys[4] = true;
			//repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_D && !keys[5])
		{
			keys[5] = true;
			//repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_S && !keys[6])
		{
			keys[6] = true;
			//repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_W && !keys[7])
		{
			keys[7] = true;
			//repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_P)
		{
			keys[8] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			keys[9] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_I)
		{
			keys[10] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			quality+=10;
		}
		if (e.getKeyCode() == KeyEvent.VK_QUOTE)
		{
			quality -= 10;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			keys[0] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			keys[1] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			keys[2] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			keys[3] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			keys[4] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			keys[5] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_S)
		{
			keys[6] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_W)
		{
			keys[7] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			keys[9] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		if(e.getPreciseWheelRotation()<0)
		{
			scale *= .8;
			quality+=10;
		}
		else
		{
			scale /= .8;
			quality-=10;
		}
		System.out.println(quality +" "+ scale);
	}
	
	private class ComplexCoord
	{
		public double real, i;
		public ComplexCoord(double real, double i)
		{
			this.real = real;
			this.i = i;
		}
		
		public void add(ComplexCoord other)
		{
			real += other.real;
			i += other.i;
		}
		
		public void multiply(ComplexCoord other)
		{
			double tempReal = real*other.real + -1*i*other.i;
			i = real * other.i + i * other.real;
			real = tempReal;
		}
		
		public void pow(int pow)
		{
			ComplexCoord temp = new ComplexCoord(real, i);
			for (int i = 1; i < pow; i++) {
				multiply(temp);
			}
		}
		
		public boolean inBounds()
		{
			return bounds[0]<real && bounds[1]>real && bounds[2]<i && bounds[3]>i;
			//return (Math.pow(real, 2) + Math.pow(i,2))<4;
		}
		
		public String toString()
		{
			return " "+real+" "+i;
		}
	}
}

