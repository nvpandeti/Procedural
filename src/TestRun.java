import javax.swing.JFrame;

public class TestRun {
	public static void main(String[] args){
		JFrame f = new JFrame();
		//Fractals cg = new Fractals();
		DualFractals cg = new DualFractals();
		//ColorGenerator cg = new ColorGenerator();
		
		f.setSize(Fractals.sizeX, Fractals.sizeY);
		f.setLocationRelativeTo(null);
		f.add(cg);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Thread t = new Thread(cg);
		t.start();
		
		f.setVisible(true);
	}
}
