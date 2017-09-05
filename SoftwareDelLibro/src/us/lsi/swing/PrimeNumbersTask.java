package us.lsi.swing;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.google.common.collect.Lists;


class PrimeNumbersTask extends SwingWorker<List<Long>, Long> {	
	
	private int numbersToFind;
	private JTextArea textArea;	
	private JButton mensajeButton;
	private List<Long> numbers = Lists.newArrayList();
	

	PrimeNumbersTask(JTextArea textArea, JButton mensajeButton, int numbersToFind) {
		// TODO Auto-generated constructor stub
		 this.textArea = textArea;
		 this.numbersToFind = numbersToFind;
		 this.mensajeButton = mensajeButton;
	}

	@Override
	public List<Long> doInBackground() {
		Long number = 1L;	

		numbers.add(number);
		publish(number);
		while (numbers.size() < numbersToFind && ! isCancelled()) {
			number = nextPrimeNumber(number,numbers);
			publish(number);
			numbers.add(number);
			setProgress(100 * numbers.size() / numbersToFind);
		}
		return numbers;
	}

	private Long nextPrimeNumber(Long n, List<Long> primes) {
		// TODO Auto-generated method stub
		Long r = null;
		for(Long i=n+1;true;i++){
			if(isPrime(i,primes)){
				r = i;
				break;
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		return r;
	}
	
	private boolean isPrime(Long nn, List<Long> primes){
		boolean r = true;
		for(Long e: primes){
			if(e==1){
				continue;
			}
			if(nn%e==0){
				r = false;
				break;
			}
		}
		return r;
	}

	@Override
	protected void process(List<Long> chunks) {
		for (Long number : chunks) {
			textArea.append(number + "\n");
		}
	}
	
	@Override
	protected void done() {		
		List<Long> r = null;
		try {
			r = get();
			System.out.println(r);
		} catch (Exception e) {
			mensajeButton.setText("Tarea Interumpida");
		} 
	   
	}

}
	