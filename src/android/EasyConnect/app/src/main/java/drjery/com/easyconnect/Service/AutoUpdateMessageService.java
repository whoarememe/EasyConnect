package drjery.com.easyconnect.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import drjery.com.easyconnect.pojo.Message;
import drjery.com.easyconnect.util.ConstUtil;
import drjery.com.easyconnect.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateMessageService extends Service {

    private LocalBroadcastManager localBroadcastManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        updateMessage();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int timeSplit = 5*1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + timeSplit;
        Intent i = new Intent(this,AutoUpdateMessageService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    public void updateMessage(){
        SharedPreferences pref = this.getSharedPreferences("data",MODE_PRIVATE);
        int userId = pref.getInt("id",0);
        String url = ConstUtil.url+"";
        List<String> data = new ArrayList<String>();
        data.add("userId");
        data.add(String.valueOf(userId));
        HttpUtil.sendOkHttpRequest(url, data, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                try {
                    JSONObject resultObject = new JSONObject(responseText);
                    String status = resultObject.getString("success");
                    if(status.equals("true"))
                    {
                        JSONObject messageObjects = resultObject.getJSONObject("data");
                        Gson gson = new Gson();
                        List<Message> messages = gson.fromJson(messageObjects.toString(), new TypeToken<List<Message>>(){}.getType());
                        if(messages.size()!=0)
                        {
                            for(Message message : messages)
                            {
                                message.save();
                            }
                            Intent intent = new Intent(ConstUtil.MESSAGE_RECEIVE);
                            intent.putExtra("infor",ConstUtil.MESSAGE_RECEIVE);
                            localBroadcastManager.sendBroadcast(intent);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
