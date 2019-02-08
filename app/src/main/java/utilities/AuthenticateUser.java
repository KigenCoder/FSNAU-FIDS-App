package utilities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import database.DatabaseHandler;
import model.UserData;

public class AuthenticateUser {

    public void verify(Context context){
        DatabaseHandler databaseHandler  = DatabaseHandler.getInstance(context);
        final UserData userData = databaseHandler.getUserData();
        String server_url = Global.getServerUrl() + "/api/auth/user";

        if (userData != null) {
            //User already authenticated
            final String oauth_token = userData.getAccessToken();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, server_url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String userID = response.getString("id");
                        //Compare userID with locally saved one
                        String savedUserID = userData.getUserId();

                        if (userID.equalsIgnoreCase(savedUserID)) {

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            })

            {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    String auth = "Bearer " + oauth_token;
                    headers.put("Authorization", auth);
                    return headers;
                }
            };

            VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        }
    }
}
