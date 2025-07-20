package com.dfrobot.angelo.blunobasicdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;
import android.widget.CheckBox;
import java.util.List;
//import java.util.logging.Handler;

public class MainActivity  extends BlunoLibrary {
	private Button buttonScan;
	private int peakValue = 0;
	//	private Handler handler = new Handler();
	private Runnable sensorRunnable;
	private int samplingIntervalMs = 500;  // sample every 500 ms
	private long samplingDurationMs = 5000; // amount of time to blow
	private Button buttonSerialSend;
	private Button startSensorButton;
	private EditText serialSendText;
	private TextView serialReceivedText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
					checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

				requestPermissions(
						new String[]{
								Manifest.permission.BLUETOOTH_SCAN,
								Manifest.permission.BLUETOOTH_CONNECT
						},
						1001
				);
			}
		}
		request(1000, new OnPermissionsResult() {
			@Override
			public void OnSuccess() {
				Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void OnFail(List<String> noPermissions) {
				Toast.makeText(MainActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
			}
		});

		onCreateProcess();                                                        //onCreate Process by BlunoLibrary


		serialBegin(115200);                                                    //set the Uart Baudrate on BLE chip to 115200

		serialReceivedText = (TextView) findViewById(R.id.serialReveicedText);    //initial the EditText of the received data
		//  serialSendText=(EditText) findViewById(R.id.serialSendText);			//initial the EditText of the sending data

		//    buttonSerialSend = (Button) findViewById(R.id.buttonSerialSend);		//initial the button for sending the data
//        buttonSerialSend.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				serialSend(serialSendText.getText().toString());				//send the data to the BLUNO
//			}
//		});

		buttonScan = (Button) findViewById(R.id.buttonScan);                    //initial the button for scanning the BLE device
		buttonScan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					buttonScanOnClickProcess();  // Alert Dialog for selecting the BLE device
				} catch (Exception e) {
					Log.e("ScanError", "Scan button crashed", e);
					Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});


		startSensorButton = findViewById(R.id.startSensorButton);                    //initial the button for scanning the BLE device
		startSensorButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				serialSend("START\n");  // Sends trigger
				Toast.makeText(MainActivity.this, "Started 10s scan", Toast.LENGTH_SHORT).show();
			}
		});
		startSensor();

	}

	protected void onResume() {
		super.onResume();
		System.out.println("BlUNOActivity onResume");
		onResumeProcess();                                                        //onResume Process by BlunoLibrary
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		onActivityResultProcess(requestCode, resultCode, data);                    //onActivityResult Process by BlunoLibrary
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onPause() {
		super.onPause();
		onPauseProcess();                                                        //onPause Process by BlunoLibrary
	}

	protected void onStop() {
		super.onStop();
		onStopProcess();                                                        //onStop Process by BlunoLibrary
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		onDestroyProcess();                                                        //onDestroy Process by BlunoLibrary
	}

	@Override
	public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
		switch (theConnectionState) {                                            //Four connection state
			case isConnected:
				buttonScan.setText("Connected");
				break;
			case isConnecting:
				buttonScan.setText("Connecting");
				break;
			case isToScan:
				buttonScan.setText("Scan");
				break;
			case isScanning:
				buttonScan.setText("Scanning");
				break;
			case isDisconnecting:
				buttonScan.setText("isDisconnecting");
				break;
			default:
				break;
		}
	}

	@Override
	public void onSerialReceived(String theString) {
		serialReceivedText.setText(""); // Clear previous value
		serialReceivedText.append(theString);  // Should show "Peak: XXXX"
		((ScrollView) serialReceivedText.getParent()).fullScroll(View.FOCUS_DOWN);
	}

	@Override
	public <T extends View> T findViewById(int id) {
		return super.findViewById(id);
	}

	private void startSensor() {
//
//		sensorRunnable = new Runnable() {
//			long startTime = System.currentTimeMillis();
//			@Override
//			public void run() {
//				// check if 5 seconds have passed
//				if (System.currentTimeMillis() - startTime < samplingDurationMs) {
//					handler.postDelayed(this, samplingIntervalMs);
//				} else {
//					// finished sampling, go to details page
//					goToDetailsPage();
//				}
//			}
//		};
//
//		handler.post(sensorRunnable);
//	}
//
//	private void goToDetailsPage() {
//		Intent intent = new Intent(this, detailsPage.class);
//		//intent.putExtra("PEAK_VALUE", peakValue);
//		startActivity(intent);
//		finish();
//	}
		Button startSensorButton = findViewById(R.id.startSensorButton);

		startSensorButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 0. Send "START" to Arduino via Bluetooth
				serialSend("START\n");

				// 1. Show toast
				Toast.makeText(MainActivity.this, "Sensor started!", Toast.LENGTH_SHORT).show();

				// 2. Delay 10 seconds then switch activity
				new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(MainActivity.this, detailsPage.class);
						startActivity(intent);
					}
				}, 20_000); // 10 seconds
			}
		});
	}
}
