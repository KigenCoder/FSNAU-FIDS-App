package utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton singletonInstance;
    private RequestQueue requestQueue;
    private Context context;

    private VolleySingleton(Context singletonContext){
      context = singletonContext;
      requestQueue = getRequestQueue();
    }


    public static synchronized VolleySingleton getInstance(Context context){
        if(singletonInstance == null){
            singletonInstance = new VolleySingleton(context);

        }

        return  singletonInstance;
    }


    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);

    }


}
