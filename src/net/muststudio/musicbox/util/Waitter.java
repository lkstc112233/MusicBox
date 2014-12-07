package net.muststudio.musicbox.util;

public class Waitter {
	public Waitter() {
		this(1);
	}

	protected int waitLength;
	protected int currentStep;

	public Waitter(int i) {
		currentStep = 0;
		waitLength = i;
	}

	public boolean isOk() {
		if (++currentStep >= waitLength) {
			currentStep = 0;
			return true;
		}
		return false;
	}
}
