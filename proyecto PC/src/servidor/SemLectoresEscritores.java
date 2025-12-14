package servidor;

import java.util.concurrent.Semaphore;

public class SemLectoresEscritores {
	private Semaphore e;
	private Semaphore r;
	private Semaphore w;
	volatile int nr;//lectores activos
	volatile int nw;//escritores activos
	volatile int dr;//delayed readers
	volatile int dw;//delayed writers
	
	public SemLectoresEscritores() {
		this.e = new Semaphore(1);
		this.r = new Semaphore(0);
		this.w = new Semaphore(0);
		this.nr = 0;
		this.nw = 0;
		this.dr = 0;
		this.dw = 0;
	}
	
	public void requestRead() {
		try {
			e.acquire();
		}catch(Exception e) {}
		if(nw > 0) {
			dr++;
			e.release();
			try {
				r.acquire();
			}catch(Exception e) {}
		}
		nr++;
		if(dr>0) {
			dr--;
			r.release();
		}
		else e.release();
	}
	
	public void releaseRead() {
		try {
			e.acquire();
		}catch(Exception e) {}
		nr--;
		if(nr==0 && dw>0) {
			dw--;
			w.release();
		}else e.release();
	}
	
	public void requestWrite() {
		try {
			e.acquire();
		}catch(Exception e) {}
		if(nr > 0 || nw > 0) {
			dw++;
			e.release();
			try {
				w.acquire();
			}catch(Exception e) {}
		}
		nw++;
		e.release();
	}
	
	public void releaseWrite() {
		try {
			e.acquire();
		}catch(Exception e) {}
		nw--;
		if(dr>0) {
			dr--;
			r.release();
		}else if(dw>0) {
			dw--;
			w.release();
		}
		else e.release();
	}
}
