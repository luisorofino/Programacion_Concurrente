package servidor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorServidor {
	private int nr;
	private int nw;
	private final Lock lock;
    private final Condition oktoread;
    private final Condition oktowrite;
    
    public MonitorServidor() {
    	this.nr = 0;
    	this.nw = 0;
    	this.lock = new ReentrantLock(true);
    	this.oktoread = lock.newCondition();
    	this.oktowrite = lock.newCondition();
    }
    
    public void requestRead() {
    	lock.lock();
    	
    	while(nw>0) {
			try {
				oktoread.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	nr++;
    	
    	lock.unlock();
    }
    
    public void releaseRead() {
    	lock.lock();
    	
    	nr--;
    	if(nr==0) oktowrite.signal();
    	
    	lock.unlock();
    }
    
    public void requestWrite() {
    	lock.lock();
    	
    	while(nr>0 || nw >0)
			try {
				oktowrite.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	nw++;
    	
    	lock.unlock();
    }
    
    public void releaseWrite() {
    	lock.lock();
    	
    	nw--;
    	oktowrite.signal();
    	oktoread.signalAll();
    	
    	lock.unlock();
    }
}
