package drjery.com.easyconnect.util;


import android.util.Log;

import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by DRJ on 2017/7/5.
 */

public class HttpUtil {

    public static void sendOkHttpRequest(String url,  List<String> data, okhttp3.Callback callback)
    {
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        for(int i = 0; i < (data.size()/2); i++)
        {
            builder.add(data.get(i*2),data.get(i*2+1));
        }

        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();

        client.newCall(request).enqueue(callback);
    }


}
