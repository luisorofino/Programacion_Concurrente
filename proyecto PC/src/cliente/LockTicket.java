package cliente;

import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket extends Lock{

	private int N;
	private volatile int next;
	private volatile AtomicInteger num;

	public LockTicket(int N) {
		this.N = N;
		num = new AtomicInteger(1);
		next = 1;
	}

	public void takeLock() {
		int turn = num.getAndAdd(1);
		if(turn==N) num.getAndAdd(-N);
		else if (turn > N) turn -= N;
		while(turn != next);
	}
	
	public void releaseLock() {
		next = next % N + 1;
	}

}