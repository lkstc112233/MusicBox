package net.muststudio.musicbox;

import net.muststudio.musicbox.gui.TemperatureViewer.TemperatureSource;

public class BluetoothTemperatureSource implements TemperatureSource {
	private boolean Ir;

	public BluetoothTemperatureSource() {
		this(false);
	}

	public BluetoothTemperatureSource(boolean Ir) {
		this.Ir = Ir;
	}

	@Override
	public double getTemperature() {
		if (Ir)
			return MusicActivity.getActivity().sensorIrTemperature;
		else
			return MusicActivity.getActivity().sensorAmbientTemperature + 3;
	}
}