package net.muststudio.musicbox.util;

public class Decounter extends Waitter{
	public Decounter() {
		this(1);
	}
	public Decounter(int i) {
		super(i);
	}
	
	public void count(){
		if (++currentStep >= waitLength) 
			currentStep = waitLength;
	}
}
