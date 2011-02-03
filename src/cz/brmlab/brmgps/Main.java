package cz.brmlab.brmgps;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener {
  private static final String TAG = "brmGPS";
  Button buttonStart, buttonStop;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    buttonStart = (Button) findViewById(R.id.buttonStart);
    buttonStop = (Button) findViewById(R.id.buttonStop);

    buttonStart.setOnClickListener(this);
    buttonStop.setOnClickListener(this);
  }
  
  protected void sendnotification (String title, String message) {
	   String ns = Context.NOTIFICATION_SERVICE;
	   NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
	 
	   int icon = R.drawable.icon;
	   CharSequence tickerText = message;
	   long when = System.currentTimeMillis();

	   Notification notification = new Notification(icon, tickerText, when);

	   Context context = getApplicationContext();
	   CharSequence contentTitle = title;
	   CharSequence contentText = message;
	   Intent notificationIntent = new Intent(this, Main.class);
	   PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

	   notification.flags = Notification.FLAG_ONGOING_EVENT;
	   notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	   mNotificationManager.notify(1, notification);
	}
  
  protected void clearnotification () {
	  String ns = Context.NOTIFICATION_SERVICE;
      NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
      mNotificationManager.cancelAll();
	}

  // Menu Create
  public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.menu, menu);
      return true;
  }
  // Menu click
  public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    
	    case R.id.Illution:
	    	Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.illution.dk"));
	    	startActivity(browserIntent);
	        return true;
	    
	    case R.id.brmlab:
	    	Intent browserIntent2 = new Intent("android.intent.action.VIEW", Uri.parse("http://brmlab.cz"));
	    	startActivity(browserIntent2);
	        return true;	        
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

  public void onClick(View src) {
    switch (src.getId()) {
    case R.id.buttonStart:
    sendnotification("brmgps is active","brmgps is active");  
    Log.d(TAG, "onClick: starting srvice");
      
      LocationManager locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if(!locationMgr.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER )) {
			Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS ); 
			startActivity(myIntent);
		}

      startService(new Intent(this, GPSService.class));
      break;
    case R.id.buttonStop:
      clearnotification();
      Log.d(TAG, "onClick: stopping srvice");
      stopService(new Intent(this, GPSService.class));
      break;
    }
  }
}