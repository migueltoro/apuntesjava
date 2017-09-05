package us.lsi.concurrent.ejemplos;

public class InduceLockOrder {

	private static final Object tieLock = new Object();

	public void transferMoney(final Account fromAcct, final Account toAcct,
			final DollarAmount amount) throws InsufficientFundsException {
		
		int fromHash = System.identityHashCode(fromAcct);
		int toHash = System.identityHashCode(toAcct);

		if (fromHash < toHash) {
			synchronized (fromAcct) {
				synchronized (toAcct) {
					new Helper().transfer(fromAcct,toAcct,amount);
				}
			}
		} else if (fromHash > toHash) {
			synchronized (toAcct) {
				synchronized (fromAcct) {
					new Helper().transfer(fromAcct,toAcct,amount);
				}
			}
		} else {
			synchronized (tieLock) {
				synchronized (fromAcct) {
					synchronized (toAcct) {
						new Helper().transfer(fromAcct,toAcct,amount);
					}
				}
			}
		}
	}

	interface DollarAmount extends Comparable<DollarAmount> {
	}

	interface Account {
		void debit(DollarAmount d);

		void credit(DollarAmount d);

		DollarAmount getBalance();

		int getAcctNo();
	}

	class InsufficientFundsException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}

	class Helper {
		
		public void transfer(final Account fromAcct, final Account toAcct,
				final DollarAmount amount) throws InsufficientFundsException {
			
			if (fromAcct.getBalance().compareTo(amount) < 0)
				throw new InsufficientFundsException();
			else {
				fromAcct.debit(amount);
				toAcct.credit(amount);
			}
		}

	}
	
}
