package net.muststudio.musicbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent ootStartIntent = new Intent(context, AlarmActivity.class);
		ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ootStartIntent.putExtra("source", "BootBroadcast");
		context.startActivity(ootStartIntent);
	}
}
