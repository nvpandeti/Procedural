import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ColorGenerator extends JPanel implements Runnable, KeyListener
{
	public static int sizeX, sizeY;
	public boolean[] keys;
	public int[] pos;
	public double animation;
	public static int seed = 2;
	public ColorGenerator()
	{
		sizeX = 400;
		sizeY = 400;
		
		setSize(sizeX, sizeY);
		keys = new boolean[10];
		
		pos = new int[2];
		pos[0] = 0;
		pos[1] = -10;
		
		try
		{
			seed = Integer.parseInt(JOptionPane.showInputDialog("Enter a seed number!"));
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Invalid number");
			System.exit(0);
		}
		JOptionPane.showMessageDialog(null, "Press P to print the image");
		animation = 0;
		
		addKeyListener(this);
		setVisible(true);
		setFocusable(true);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			long temp = System.currentTimeMillis();
			repaint();
			long current = System.currentTimeMillis();
			//System.out.println(current +" "+ temp);
		}
	}
	
	public void paintComponent(Graphics g2) 
	{
		BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = img.createGraphics();
		
		if(keys[0] || keys[4])
			pos[0] -= 1;
		if(keys[1] || keys[5])
			pos[0] += 1;
		if(keys[2] || keys[6])
			pos[1] += 1;
		if(keys[3] || keys[7])
			pos[1] -= 1;
		//System.out.println(Arrays.toString(pos));
		//g.setColor(Color.BLACK);
		//g.fillRect(0,0,getWidth(), getHeight());
		double[] rgb = new double[3];
		Random r = new Random(seed);
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				//Color c = new Color((int) (Math.floorMod(pos[0]+i, getWidth())/(double)getWidth()*255), (int) (Math.floorMod(pos[1]+j, getHeight())/(double)getHeight()*255), (int) ((getWidth()-Math.floorMod(pos[0]+i, getWidth()))/(double)getWidth()*255));
				//Color c = new Color((int) ((Math.cos((pos[0]+i)/(double)getWidth()*2*Math.PI)*.5+.5)*255), (int) ((Math.cos((pos[1]+j)/(double)getHeight()*2*Math.PI)*.5+.5)*255), (int) ((Math.cos(Math.pow(pos[0]+i, 2) + Math.pow(pos[1]+j, 2))*.5+.5)*255));
				//Color c = new Color((int) ((Math.cos((pos[0]+i)/(double)getWidth()*2*Math.PI)*.5+.5)*255), (int) ((Math.cos((pos[1]+j)/(double)getHeight()*2*Math.PI)*.5+.5)*255), (int) ((Math.cos((Math.pow(pos[0]+i+getWidth()/2, 2) + Math.pow(pos[1]+j+getHeight()/2, 2))/(double)getWidth()*128*Math.PI)*.5+.5)*255));
				//Color c = new Color((int) ((Math.cos((pos[1]+j)/(double)getHeight()*2*Math.PI)*.5+.5)*255), (int) ((Math.cos((pos[0]+i)/(double)getWidth()*2*Math.PI)*.5+.5)*255), (int) ((Math.cos((Math.pow(pos[0]+i+getWidth()/2, 2) + Math.pow(pos[1]+j+getHeight()/2, 2))/(double)getWidth()*animation*Math.PI)*.5+.5)*255));
				
				//int rgb = (int) ((Math.cos((Math.pow(pos[0]+i+getWidth()/2, 2) + Math.pow(pos[1]+j+getHeight()/2, 2))/(double)getWidth()*animation*Math.PI)*.5+.5)*255);
				//rgb += ((int) ((Math.cos((pos[0]+i)/(double)getWidth()*2*Math.PI)*.5+.5)*255)) << 8;
				//rgb += ((int) ((Math.cos((pos[1]+j)/(double)getHeight()*2*Math.PI)*.5+.5)*255)) << 16;
				//img.setRGB(i, j, rgb);
				
				Color c = new Color((int) rgb[0], (int) rgb[1], (int) rgb[2]);
				for (int k = 0; k < rgb.length; k++) {
					rgb[k] += r.nextDouble()*2-1;//Math.random()*2-1;
					rgb[k] = Math.max(0, Math.min(255, rgb[k]));
				}
				g.setColor(c);
				g.drawRect(i, j, 1, 1);
			}
		}
		animation+= .001;
		//System.out.println(animation);
		//g.setColor(Color.black);
		//g.fillOval((int)(getWidth()/2.0-2),(int) (getHeight()/2.0-2), 4, 4);
		if(keys[8])
		{
			keys[8] = false;
			try
			{
				System.out.println("Saving...");
				ImageIO.write(img, "BMP", new File(JOptionPane.showInputDialog("Enter a name for your print!")+".bmp"));
				System.out.println("Saved!");
				JOptionPane.showMessageDialog(null, "Saved!");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		if(keys[9])
		{
			seed++;
			keys[9] = false;
		}
		g.dispose();
	    g2.drawImage(img, getX(), getY(), this);
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
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			keys[9] = true;
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
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}