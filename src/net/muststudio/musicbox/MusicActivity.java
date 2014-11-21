package net.muststudio.musicbox;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static com.ti.sensortag.models.Devices.LOST_DEVICE_;
import static com.ti.sensortag.models.Devices.NEW_DEVICE_;
import static com.ti.sensortag.models.Devices.State.CONNECTED;
import static com.ti.sensortag.models.Measurements.PROPERTY_ACCELEROMETER;
import static com.ti.sensortag.models.Measurements.PROPERTY_AMBIENT_TEMPERATURE;
import static com.ti.sensortag.models.Measurements.PROPERTY_GYROSCOPE;
import static com.ti.sensortag.models.Measurements.PROPERTY_IR_TEMPERATURE;
import static com.ti.sensortag.models.Measurements.PROPERTY_MAGNETOMETER;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import net.muststudio.musicbox.R;

import com.ti.sensortag.ble.LeController;
import com.ti.sensortag.models.Devices;
import com.ti.sensortag.models.Measurements;
import com.ti.sensortag.models.Point3D;

import android.app.Activity;
import android.app.AlarmManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MusicActivity extends Activity implements PropertyChangeListener {
	public class AlarmReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, "This the time", Toast.LENGTH_LONG).show();
		}

	}

	protected static final float EPSILON = 0.0002f;
	protected static final float MIN_V = 0.0001f;
	protected static final double MIN_A = 0.5f;
	public SensorManager sm;
	public Sensor aSensor;
	public Sensor mSensor;

	public AlarmManager alarmManager;

	public boolean bleEnabled = false;

	public final static double windowLength = 10;
	public final float[] phoneV = new float[] { 0, 0 };
	public final float[] phoneP = new float[] { 0, 0 };
	public final float[] phoneAccelerometerValues = new float[3];
	public long lastUpdatePositionSensor;
	public final float[] phoneMagneticFieldValues = new float[3];
	public final float[] phoneOrientationValues = new float[3];
	public final float[] phoneGroundBasedAccelerationValues = new float[3];
	public final float[] phoneRotate = new float[9];
	public boolean needAdjustment = false;

	public Point3D sensorAccelerometerValues = new Point3D(0, 0, 0);
	public double sensorAmbientTemperature = 0;
	public double sensorIrTemperature = 0;
	public Point3D sensorMagnetometerValues = new Point3D(0, 0, 0);
	public Point3D sensorGyroscopeValues = new Point3D(0, 0, 0);
	public final float[] sensorAccelerometerValuesf = new float[3];
	public final float[] sensorMagnetometerValuesf = new float[3];
	public final float[] sensorOrientationValuesf = new float[3];
	public final float[] sensorRotate = new float[9];

	private static MusicActivity act;

	public final ArrayDeque<Double> realtimeTemperatures = new ArrayDeque<Double>();

	public static MusicActivity getActivity() {
		return act;
	}

	private void updatePosition() {
		long dt = System.currentTimeMillis() - lastUpdatePositionSensor;
		lastUpdatePositionSensor = System.currentTimeMillis();

		// if (Math.abs(phoneOrientationValues[1]) > 0.3 ||
		// Math.abs(phoneOrientationValues[2]) > 0.3)
		if (phoneAccelerometerValues[2] < 9.6)
			needAdjustment = true;
		else if (needAdjustment)
			adjustment();

		if (!needAdjustment) {
			double ax = (phoneAccelerometerValues[0] * cos(phoneOrientationValues[0]) + phoneAccelerometerValues[1]
					* sin(phoneOrientationValues[0]));
			if (Math.abs(ax) < MIN_A)
				ax = 0;
			phoneP[0] += 0.5 * dt / 1000 * dt / 1000 * ax + dt / 1000. * phoneV[0];
			phoneV[0] += dt / 1000. * ax;
			phoneV[0] *= 0.995;
			if (Math.abs(phoneV[0]) < MIN_V)
				phoneV[0] = 0;
			double ay = (phoneAccelerometerValues[1] * cos(phoneOrientationValues[0]) - phoneAccelerometerValues[0]
					* sin(phoneOrientationValues[0]));
			if (Math.abs(ay) < MIN_A)
				ay = 0;
			phoneP[1] += 0.5 * dt / 1000 * dt / 1000 * ay + dt / 1000. * phoneV[1];
			phoneV[1] += dt / 1000. * ay;
			phoneV[1] *= 0.995;
			if (Math.abs(phoneV[1]) < MIN_V)
				phoneV[1] = 0;
		}
	}

	public void adjustment() {
		phoneV[0] = phoneV[1] = 0;
		needAdjustment = false;
	}

	private void caculateGroundBasedAccelerationValues() {
		float alpha = phoneOrientationValues[0];
		float beta = phoneOrientationValues[1];
		float garma = phoneOrientationValues[2];
		float x = phoneAccelerometerValues[0];
		float y = phoneAccelerometerValues[1];
		float z = phoneAccelerometerValues[2];
		phoneGroundBasedAccelerationValues[0] = (float) (cos(alpha) * cos(garma) * x + sin(alpha)
				* cos(garma) * y - sin(garma) * z);
		phoneGroundBasedAccelerationValues[1] = (float) ((-sin(alpha) * cos(beta) - cos(alpha)
				* sin(beta) * sin(garma))
				* x + (-sin(alpha) * sin(beta) * sin(garma) + cos(alpha) * cos(beta)) * y - sin(beta)
				* cos(garma) * z);
		phoneGroundBasedAccelerationValues[2] = (float) ((-sin(alpha) * sin(beta) + cos(alpha)
				* cos(beta) * sin(garma))
				* x + (sin(alpha) * cos(beta) * sin(garma) + cos(alpha) * sin(beta)) * y + cos(beta)
				* cos(garma) * z);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Use this check to determine whether BLE is supported on the device.
		// Then
		// you can selectively disable BLE-related features.
		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
			Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_LONG).show();
		else
			bleEnabled = true;

		// Initializes a Bluetooth deviceListAdapter. For API level 18 and
		// above, get a
		// reference to BluetoothAdapter through BluetoothManager.
		BluetoothManager bluetoothManager = (BluetoothManager) this
				.getSystemService(Context.BLUETOOTH_SERVICE);
		if (bluetoothManager == null) {
			Toast.makeText(this, "System Error! Code:0x01", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
		mBluetoothAdapter = bluetoothManager.getAdapter();

		// Checks if Bluetooth is supported on the device.
		if (mBluetoothAdapter == null)
			Toast.makeText(this, R.string.bt_not_supported, Toast.LENGTH_LONG).show();

		active = false;
		try {
			PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		} catch (Throwable e) {
			Toast.makeText(this, "System Error! Code:0x02", Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		// Register the BroadcastReceiver
		try {
			mFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
		} catch (Throwable e) {
			Toast.makeText(this, "System Error! Code:0x03", Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		// Initialization
		act = this;
		SoundMaker.init(this);
		Settings.getSettings(this);
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		aSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		sm.registerListener(myListener, aSensor, SensorManager.SENSOR_DELAY_GAME);
		sm.registerListener(myListener, mSensor, SensorManager.SENSOR_DELAY_GAME);
		lastUpdatePositionSensor = System.currentTimeMillis();

		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		adjustment();

		// Window initialization.
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new MainView(this));

		initApp();
	}

	@Override
	protected void onDestroy() {
		sm.unregisterListener(myListener);

		// Stop listening for broadcasts
		unregisterReceiver(mReceiver);
		mFilter = null;
		active = false;

		// Stop listening for updates
		Devices.INSTANCE.removePropertyChangeListener(this);

		// Stop BLE activities
		LeController.INSTANCE.onDestroy();
		mBluetoothAdapter = null;
		super.onDestroy();
	}

	final SensorEventListener myListener = new SensorEventListener() {
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				float sum = 0;
				for (int i = 0; i < 3; ++i) {
					sum += (phoneAccelerometerValues[i] - event.values[i])
							* (phoneAccelerometerValues[i] - event.values[i]);
					phoneAccelerometerValues[i] = (float) ((1 - Math.pow(Math.E, -1 / windowLength))
							* event.values[i] + phoneAccelerometerValues[i]
							* Math.pow(Math.E, -1 / windowLength));
				}
				if (sum > 200)
					SoundMaker.playSound((int) sum);
			}
			if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
				for (int i = 0; i < 3; ++i)
					phoneMagneticFieldValues[i] = (float) ((1 - Math.pow(Math.E, -1 / windowLength))
							* event.values[i] + phoneMagneticFieldValues[i]
							* Math.pow(Math.E, -1 / windowLength));

			if (phoneAccelerometerValues[0] * phoneAccelerometerValues[0]
					+ phoneAccelerometerValues[1] * phoneAccelerometerValues[1]
					+ phoneAccelerometerValues[2] * phoneAccelerometerValues[2] < 100) {
				SensorManager.getRotationMatrix(phoneRotate, null, phoneAccelerometerValues,
						phoneMagneticFieldValues);
				SensorManager.getOrientation(phoneRotate, phoneOrientationValues);
				caculateGroundBasedAccelerationValues();
			}
			updatePosition();
		}
	};

	private static final int REQ_ENABLE_BT = 0;
	static volatile boolean active = false;
	private BluetoothAdapter mBluetoothAdapter = null;
	private IntentFilter mFilter;

	private void startBle() {
		Devices.INSTANCE.addPropertyChangeListener(this);

		// Start scanning
		LeController.INSTANCE.run(this);
	}

	private void initApp() {
		// Broadcast receiver
		try {
			registerReceiver(mReceiver, mFilter);
		} catch (Throwable e) {
			Toast.makeText(this, "System Error! Code:0x04", Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		if (mBluetoothAdapter.isEnabled()) {
			// Start straight away
			startBle();
			LeController.INSTANCE.startScan();
		} else {
			// Request BT deviceListAdapter to be turned on
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQ_ENABLE_BT);
		}
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();

			// Adapter state changed
			if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action))
				switch (mBluetoothAdapter.getState()) {
				case BluetoothAdapter.STATE_ON:
					startBle();
					break;
				case BluetoothAdapter.STATE_OFF:
					mBluetoothAdapter.enable();
					break;
				default:
					break;
				}
		}
	};

	private void refreshSensorOrientation() {
		SensorManager.getRotationMatrix(sensorRotate, null, sensorAccelerometerValuesf,
				sensorMagnetometerValuesf);
		SensorManager.getOrientation(sensorRotate, sensorOrientationValuesf);
	}

	List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();

	@Override
	public void propertyChange(final PropertyChangeEvent event) {
		// Check if a new device has been discovered
		runOnUiThread(new Runnable() {
			public void run() {
				String eventName = event.getPropertyName();
				if (eventName.equals("NEW_DEVICE_ADVERTISING")) {
					// A device started advertising.
					// In this version, we will directly set the device to the
					// listening device.
					BluetoothDevice device = (BluetoothDevice) event.getNewValue();

					// Connect to the device.
					Toast.makeText(getActivity(), "Connecting...", Toast.LENGTH_SHORT).show();
					LeController.INSTANCE.connect(device);

					Measurements.INSTANCE.addPropertyChangeListener(MusicActivity.this);
				}
			}
		});

		final String property = event.getPropertyName();
		if (property.equals(PROPERTY_ACCELEROMETER)) {
			float sum = 0;

			// A change in accelerometer data has occurred.
			sensorAccelerometerValues = (Point3D) event.getNewValue();
			sum += (sensorAccelerometerValuesf[0] - sensorAccelerometerValues.x)
					* (sensorAccelerometerValuesf[0] - sensorAccelerometerValues.x);
			sensorAccelerometerValuesf[0] = (float) sensorAccelerometerValues.x;
			sum += (sensorAccelerometerValuesf[1] - sensorAccelerometerValues.y)
					* (sensorAccelerometerValuesf[1] - sensorAccelerometerValues.y);
			sensorAccelerometerValuesf[1] = (float) sensorAccelerometerValues.y;
			sum += (sensorAccelerometerValuesf[2] - sensorAccelerometerValues.z)
					* (sensorAccelerometerValuesf[2] - sensorAccelerometerValues.z);
			sensorAccelerometerValuesf[2] = (float) sensorAccelerometerValues.z;

			if (sum > 1.5)
				SoundMaker.playSound((int) (sum * 100));

			refreshSensorOrientation();
		} else if (property.equals(PROPERTY_AMBIENT_TEMPERATURE)) {
			sensorAmbientTemperature = (Double) event.getNewValue();
			synchronized (realtimeTemperatures) {
				realtimeTemperatures.addLast(sensorAmbientTemperature);
				if (realtimeTemperatures.size() > 100)
					realtimeTemperatures.pollFirst();
			}
		} else if (property.equals(PROPERTY_IR_TEMPERATURE)) {
			sensorIrTemperature = (Double) event.getNewValue();
		} else if (property.equals(PROPERTY_MAGNETOMETER)) {
			sensorMagnetometerValues = (Point3D) event.getNewValue();
			sensorMagnetometerValuesf[0] = (float) sensorMagnetometerValues.x;
			sensorMagnetometerValuesf[1] = (float) sensorMagnetometerValues.y;
			sensorMagnetometerValuesf[2] = (float) sensorMagnetometerValues.z;
			refreshSensorOrientation();
		} else if (property.equals(PROPERTY_GYROSCOPE)) {
			sensorGyroscopeValues = (Point3D) event.getNewValue();
		} else if (property.equals(LOST_DEVICE_ + CONNECTED)) {
			// A device has been disconnected
			// We notify the user with an alarm
			SoundMaker.startAlarm();
		} else if (property.equals(NEW_DEVICE_ + CONNECTED)) {
			SoundMaker.stopAlarm();
		}
	}
}
