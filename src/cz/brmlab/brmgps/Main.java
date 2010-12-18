package cz.brmlab.brmgps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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

  public void onClick(View src) {
    switch (src.getId()) {
    case R.id.buttonStart:
      Log.d(TAG, "onClick: starting srvice");
      
      LocationManager locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if(!locationMgr.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER )) {
			Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS ); 
			startActivity(myIntent);
		}

      startService(new Intent(this, GPSService.class));
      break;
    case R.id.buttonStop:
      Log.d(TAG, "onClick: stopping srvice");
      stopService(new Intent(this, GPSService.class));
      break;
    }
  }
}