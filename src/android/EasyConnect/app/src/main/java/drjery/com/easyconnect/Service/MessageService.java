package drjery.com.easyconnect.Service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import drjery.com.easyconnect.pojo.Message;
import drjery.com.easyconnect.util.ConstUtil;

public class MessageService extends Service {

    private LocalBroadcastManager localBroadcastManager;

    private SharedPreferences prf;

    private InetAddress addr;

    private int port;

    private Thread heart;

    private Thread msg;

    private HeartBeatThread heartBeatThread;

    private ReceiveDataThread receiveDataThread;

    public MessageService() {
    }

    @Override
    public void onCreate() {
        Log.e("Message Service:","Start");
        if(ConstUtil.mySocket==null)
        {
            try {
                ConstUtil.mySocket = new DatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        prf = this.getSharedPreferences("data",MODE_PRIVATE);
        try {
            addr = InetAddress.getByName(ConstUtil.addr);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        port = ConstUtil.port;
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        heartBeatThread = new HeartBeatThread();
        heart = new Thread(heartBeatThread);
        heart.start();
        receiveDataThread = new ReceiveDataThread();
        msg = new Thread(receiveDataThread);
        msg.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        heartBeatThread.isRun = false;
        receiveDataThread.isRun = false;
        super.onDestroy();
        Log.e("Message Service:","Stop");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    class HeartBeatThread implements Runnable{
        public boolean isRun = true;
        @Override
        public void run() {
            try {
                JSONObject heartBeat = new JSONObject();
                heartBeat.put("code",1);
                heartBeat.put("direction",1);
                heartBeat.put("userId",String.valueOf(prf.getInt("userId",0)));
                byte[] heartBeatBuf = heartBeat.toString().getBytes();
                final DatagramPacket heartBeatPacket = new DatagramPacket(heartBeatBuf,heartBeatBuf.length,addr,port);
                while(isRun)
                {
                    ConstUtil.mySocket.send(heartBeatPacket);
                    Thread.sleep(30000);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    class ReceiveDataThread implements Runnable{
        public boolean isRun = true;
        @Override
        public void run() {
            byte recvBuf[] = new byte[1024];
            DatagramPacket recvPacket = new DatagramPacket(recvBuf,recvBuf.length);
            while(isRun)
            {
                try {
                    ConstUtil.mySocket.receive(recvPacket);
                    String recvData = new String(recvPacket.getData(), 0, recvPacket.getLength());
                    Log.e("RecvData",recvData);
                    JSONObject messageObject = new JSONObject(recvData);
                    int code = messageObject.getInt("code");
                    switch (code)
                    {
                        case 2:
                            Log.e("RecvData","this is a new message");
                            Message message = new Message(messageObject.getInt("direction"),messageObject.getInt("userId")
                            ,messageObject.getInt("deviceId"),messageObject.getInt("msgType"),messageObject.getLong("time")
                            ,messageObject.getString("msg"),messageObject.getInt("level"),0);
                            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                            String current = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
                            Log.e("equals",current);
                            if(current.contains("CommunicateActivity"))
                            {
                                int currentId = prf.getInt("communitDevId",0);
                                if(currentId == message.getDevId())
                                {
                                    message.setLevel(0);
                                    message.setHasRead(1);
                                }
                            }
                            message.save();
                            Log.e("MessgRcv",String.valueOf(message.getDevId()));
                            Intent intent = new Intent(ConstUtil.MESSAGE_RECEIVE);
                            intent.putExtra("NewMessage",message);
                            localBroadcastManager.sendBroadcast(intent);
                            List<Message> messages = DataSupport.findAll(Message.class);
                            Log.e("NowNum", String.valueOf(messages.size()));
                            break;
                        default:
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
