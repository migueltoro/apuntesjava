package us.lsi.beans;

import us.lsi.math.Math2;

public class ListenableRunnablePunto extends ListenablePunto implements Runnable {

	private boolean fin = false;
	private Thread thread = new Thread(this);
	
	public ListenableRunnablePunto() {
		// TODO Auto-generated constructor stub
	}

	public ListenableRunnablePunto(Double x, Double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		double r = 0.;
		while(!fin){
			setX(r+1);
			try {
				Thread.sleep(Math2.escogeEntre(0., 2000.));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setY(r*r);
			r=r+2;
		}		
		
	}

	public boolean isFin() {
		return fin;
	}

	public void setFin(boolean fin) {
		this.fin = fin;
	}

	public void start() {
		thread.start();
	}

	public final void stop() {
		fin = true;
	}
	

}
