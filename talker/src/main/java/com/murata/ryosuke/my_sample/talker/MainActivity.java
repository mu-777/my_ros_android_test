package com.murata.ryosuke.my_sample.talker;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.ros.android.RosActivity;
import org.ros.node.NodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.address.InetAddressFactory;

public class MainActivity extends RosActivity {

    private static final String TAG = "MainActivity";
    private Talker mTalker;
    private IMUPublisher mIMUPub;
    private SensorManager mSensorManager;
    private Button mMsgButton;

    public MainActivity() {
        super("notificationTicker", "notificationTitle");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mMsgButton = (Button) findViewById(R.id.MsgButton);
        mMsgButton.setOnClickListener(setBtnClickedCallback);
    }

    private View.OnClickListener setBtnClickedCallback = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mTalker.sendMessage("test");
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {

        Log.d(TAG, "init");
        NodeConfiguration nodeConfigurator = NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress());
        nodeConfigurator.setMasterUri(getMasterUri());
        nodeConfigurator.setNodeName("android_talker");

//        this.mTalker = new Talker();
//        nodeMainExecutor.execute(this.mTalker, nodeConfigurator);

        this.mIMUPub = new IMUPublisher(mSensorManager);
        nodeMainExecutor.execute(this.mIMUPub, nodeConfigurator);
    }
}
