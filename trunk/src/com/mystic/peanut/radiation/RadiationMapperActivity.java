package com.mystic.peanut.radiation;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RadiationMapperActivity extends Activity {
    /** Called when the activity is first created. */
	
	public String RM_TAG = "RadiationMapper";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        List<NeighboringCellInfo> myList = telephonyManager.getNeighboringCellInfo();
        
        List<String> cellStrList = new ArrayList<String>();
        
        if (myList.size()==0)
        {
        	Log.d(RM_TAG, "getNeighboringCellInfo returns empty list");
        	//TextView textView = (TextView) findViewById(R.id.textview);
        	//textView.setText("No Cells Available!");
        	
        	String cellStr = "No Cells Available!";
        	cellStrList.add(cellStr);
        }
        else
        {
	        for (int i=0; i< myList.size(); i++)
	        {
	        	String cellStr = myList.get(i).toString();
	        	cellStrList.add(cellStr);
	        }
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cellStrList);
        
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        
        
        // for api 17 example value of first element
        //CellInfoGsm cellinfogsm = (CellInfoGsm)telephonyManager.getAllCellInfo().get(0);
        //CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
        //cellSignalStrengthGsm.getDbm();
    }
}