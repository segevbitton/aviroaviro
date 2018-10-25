package com.labrisch.shaked.sex;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.Set;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

        private Button btnBluetooth;
        private ListView blueList;
        private BluetoothAdapter blueAdapter ;
        private Set<BluetoothDevice> pairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBluetooth = (Button) findViewById(R.id.button3);
        blueList = (ListView) findViewById(R.id.blueDevices);

        blueAdapter = BluetoothAdapter.getDefaultAdapter();

        if (blueAdapter == null)
        {
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
            finish();
        }
        else
        {
            if (blueAdapter.isEnabled())
            {}
            else {

                Intent turnBTon = new Intent (BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon,1);
            }

            btnBluetooth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pairedDevicesList();
                }
            });

        }


    }

    private void pairedDevicesList()
    {
        pairedDevices = blueAdapter.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size()>0)
        {
            for(BluetoothDevice bt : pairedDevices)
            {
                list.add(bt.getName() + "\n" + bt.getAddress());
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        blueList.setAdapter(adapter);
        blueList.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked

    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView av, View v, int arg2, long arg3)
        {
            // Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            // Make an intent to start next activity.
            Intent i = new Intent(MainActivity.this, MotorControl.class);
            //Change the activity.
            i.putExtra("SEX_ADDRESS", address); //this will be received at ledControl (class) Activity
            startActivity(i);
        }
    };

}

