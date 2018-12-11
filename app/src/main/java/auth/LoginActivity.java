package auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import database.DatabaseHandler;
import fsnau.org.R;
import markets.indicators.IndicatorActivity;
import model.UserData;
import utilities.Global;
import utilities.VolleySingleton;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPassword;
    private DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        databaseHandler = DatabaseHandler.getInstance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Check if user has already been authenticated
        try {
            UserData userData = databaseHandler.getUserData();
            if (userData != null) {
                changeScreen();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected void btnLogin(View view) {
        final String userEmail = txtEmail.getText().toString().trim();
        final String userPassword = txtPassword.getText().toString().trim();

        if (userEmail.length() < 1 || userPassword.length() < 1) {
            Toast.makeText(getApplicationContext(), "Enter Email & Password", Toast.LENGTH_LONG).show();
        } else {

            authenticateUser(userEmail, userPassword);
        }


    }


    public void authenticateUser(String email, String password) {
        String api_url = Global.getServerUrl() + "/api/auth/login";
        JSONObject userCredentials = new JSONObject();
        try {
            userCredentials.put("email", email);
            userCredentials.put("password", password);
        } catch (JSONException exception) {
            Log.e("Auth JSON exception: ", exception.getLocalizedMessage());
        }

        if (userCredentials != null) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, api_url, userCredentials, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String accessToken = response.getString("access_token");
                        String tokenType = response.getString("token_type");
                        String expiresAt = response.getString("expires_at");
                        //User data
                        JSONArray jsonArray = response.getJSONArray("user_data");
                        JSONObject userData = jsonArray.getJSONObject(0);
                        String userID = userData.getString("id");
                        String userName = userData.getString("name");
                        String marketId = userData.getString("market_id");
                        String marketName = userData.getString("market_name");
                        String marketTypeId = userData.getString("market_type_id");
                        UserData currentUser = databaseHandler.getUserData();

                        if (currentUser != null) {//Delete current user
                            databaseHandler.deleteUserData();
                        }


                        //Save new user credentials in the database
                        UserData userDataModel = new UserData(
                                userID,
                                userName,
                                marketId,
                                marketName,
                                marketTypeId,
                                accessToken,
                                tokenType,
                                expiresAt
                        );

                        databaseHandler.addUserData(userDataModel);


                    } catch (JSONException exception) {
                        Log.e("Auth JSON Parse error", exception.getLocalizedMessage());
                        exception.printStackTrace();
                    } finally {
                        changeScreen();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    try {
                        String errorMessage = "";

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                            errorMessage = "Network timeout error check connection";

                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            errorMessage = "Authentication failure error";
                        } else if (error instanceof ServerError) {
                            //TODO
                            errorMessage = "Server connection error";
                        } else if (error instanceof NetworkError) {
                            //TODO
                            errorMessage = "Network error";
                        } else if (error instanceof ParseError) {
                            //TODO
                            errorMessage = "Network response parse error";
                        }

                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                }
            });

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

        }
    }

    private void changeScreen() {
        // Bring up Market data form(s) - if user has been authenticated
        Intent indicatorActivityIntent = new Intent(LoginActivity.this, IndicatorActivity.class);
        startActivity(indicatorActivityIntent);
    }
}

