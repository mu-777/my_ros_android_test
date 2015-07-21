package com.murata.ryosuke.my_sample.talker;

import android.util.Log;

import org.ros.namespace.GraphName;
import org.ros.node.NodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.topic.Publisher;


/**
 * Created by ryosuke on 15/07/21.
 */
public class Talker implements NodeMain {

    private static final String TAG = "Talker";
    private ConnectedNode node;
    private Publisher<std_msgs.String> publisher;
    private std_msgs.String std_msg;
    private int cnt = 0;

    public void sendMessage(String msg) {
        try {
            Log.d(TAG, "publish!");
            this.cnt += 1;
            this.std_msg.setData(msg + " from Android " + String.valueOf(this.cnt));
            this.publisher.publish(this.std_msg);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            if (node != null) {
                node.getLog().fatal(e);
            } else {
                e.printStackTrace();
            }
        }
    }

    //@Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("android_ros_sample/talker");
    }

    //@Override
    public void onError(Node node, Throwable throwable) {
    }

    //@Override
    public void onStart(ConnectedNode node) {
        this.publisher = node.newPublisher("android/chatter", "std_msgs/String");
        this.node = node;
        this.std_msg = this.publisher.newMessage();
    }

    //@Override
    public void onShutdown(Node arg0) {
    }

    //@Override
    public void onShutdownComplete(Node arg0) {
    }

}
