package com.mystic.peanut.radiation;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
//import android.telephony.CellInfoGsm;
//import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RadiationMapperActivity extends Activity {
    /** Called when the activity is first created. */
	
	public String RM_TAG = "RadiationMapper";
	
	public SignalStrength mSignalStrength = null;
	public CellLocation mCellLocation = null;
    List<String> mCellStrList = new ArrayList<String>();
    	
	// Listener for signal strength.
    final PhoneStateListener mListener = new PhoneStateListener()
    {
            @Override
            public void onCellLocationChanged(CellLocation mLocation)
            {
                    mCellLocation = mLocation;
                    String slocation = mCellLocation.toString();
                    Log.d(RM_TAG, "Cell location obtained: " + slocation);
                    mCellStrList.add("Location: " + slocation);
                    //update();
            }
            
            @Override
            public void onSignalStrengthsChanged(SignalStrength sStrength)
            {
                    mSignalStrength = sStrength;
                    String ssignal = mSignalStrength.toString();
                    Log.d(RM_TAG, "Signal strength obtained: " + ssignal + ", CdmaDbm: " + mSignalStrength.getCdmaDbm());
                    
                    
                    mCellStrList.add("Signal: " + ssignal);
                    //String[] parts = ssignal.split(" ");
                    //update();
            }
    };
    	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        List<NeighboringCellInfo> myList = telephonyManager.getNeighboringCellInfo();
        telephonyManager.listen(mListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS | PhoneStateListener.LISTEN_CELL_LOCATION);

        if (myList.size()==0)
        {
        	Log.d(RM_TAG, "getNeighboringCellInfo returns empty list");
        	//TextView textView = (TextView) findViewById(R.id.textview);
        	//textView.setText("No Cells Available!");
        	
        	String cellStr = "No Cells Available!";
        	mCellStrList.add(cellStr);
        }
        else
        {
	        for (int i=0; i< myList.size(); i++)
	        {
		        // for api 17 example value of first element
		        //CellInfoGsm cellinfogsm = (CellInfoGsm)telephonyManager.getAllCellInfo().get(i);
		        //CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
		        //int dbm = cellSignalStrengthGsm.getDbm();
		        //int level = cellSignalStrengthGsm.getLevel();
		        //int asuLevel = cellSignalStrengthGsm.getAsuLevel();
		        
		        int rssi = myList.get(i).getRssi();
		        int psc = myList.get(i).getPsc();
		        int cid = myList.get(i).getCid();

		        //String cellStr = myList.get(i).toString() + "dbm: " + dbm + ", level: " + level + ", asu level: " + asuLevel;
		        String cellStr = myList.get(i).toString() + " rssi: " + rssi + ", psc: " + psc + ", cid: " + cid;
	        	mCellStrList.add(cellStr);
	        }
	        
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mCellStrList);
        
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        
        
        
    }
}