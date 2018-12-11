package markets.indicators;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import database.DatabaseHandler;
import fsnau.org.R;
import model.IndicatorPrice;
import model.UserData;
import utilities.Global;
import utilities.VolleySingleton;

public class UploadMarketData extends Fragment implements View.OnClickListener {
    TextView txtMarketName;
    EditText txtYear;
    Spinner monthSpinner;
    Spinner weekSpinner;
    String month_id;
    String week;
    Button uploadButton;


    DatabaseHandler databaseHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View uploadView = inflater.inflate(R.layout.upload_market_data, container, false);
        databaseHandler = DatabaseHandler.getInstance(getContext());
        init(uploadView);
        return uploadView;
    }

    @Override
    public void onClick(View view) {
        String buttonTag = view.getTag().toString();

        if (buttonTag.equalsIgnoreCase("btnUploadToServer")) {
            prepUpload();

        }
    }

    private void prepUpload() {
        String yearName = txtYear.getText().toString();
        JSONObject uploadJSON = new JSONObject();
        if (yearName.length() > 0) {
            JSONObject metaDataJSON = new JSONObject();
            JSONObject dataJSON = new JSONObject();
            try {
                UserData userData = databaseHandler.getUserData();
                metaDataJSON.put("yearName", yearName);
                metaDataJSON.put("month_id", month_id);
                metaDataJSON.put("week", week);
                metaDataJSON.put("marketId", userData.getMarketId());

                ArrayList<IndicatorPrice> priceList = databaseHandler.getSavedPriceList();

                for (IndicatorPrice indicatorPrice : priceList) {

                    dataJSON.put(indicatorPrice.getIndicatorId(), indicatorPrice.getIndicatorPrice());
                }

                uploadJSON.put("metaData", metaDataJSON);
                uploadJSON.put("marketData", dataJSON);

                if (uploadJSON != null) {
                    upload(uploadJSON);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getContext(), "Enter Year, Month, Week", Toast.LENGTH_LONG).show();
        }

        System.out.println("Upload JSON: " + uploadJSON);
    }

    private void upload(JSONObject uploadJSON) {

        String upload_url = Global.getServerUrl() + "/api/auth/upload";
        final String requestBody = uploadJSON.toString();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("VOLLEY Success", response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                String errorMessage = "";

                                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                    errorMessage = "Network timeout error check connection";

                                } else if (error instanceof AuthFailureError) {
                                    errorMessage = "Authentication failure error";
                                    /*
                                      Logout user and acquire new token
                                     */
                                } else if (error instanceof ServerError) {
                                    errorMessage = "Server connection error";
                                } else if (error instanceof NetworkError) {
                                    errorMessage = "Network error";
                                } else if (error instanceof ParseError) {
                                    errorMessage = "Network response parse error";
                                }

                                final String finalErrorMessage = errorMessage;

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), finalErrorMessage, Toast.LENGTH_LONG).show();
                                    }
                                });


                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
            ) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }


                @Override
                public byte[] getBody() {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding: bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);

                        String message = "No new records to added";

                        try {
                            Log.e("Response", "" + response.data.length);
                            if (response.data.length > 0) {
                                JSONObject responseBody = new JSONObject(new String(response.data, "UTF-8"));

                                int numberOfRecords = Integer.parseInt(responseBody.getString("numberOfRecords"));

                                if (numberOfRecords > 0) {
                                    message = responseBody.getString("message") + ", " + numberOfRecords + " saved";

                                }

                            }

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        } finally {
                            informUser(message);
                        }

                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }


                @Override
                public Map<String, String> getHeaders() {
                    String bearerToken = databaseHandler.getUserData().getAccessToken();
                    HashMap<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json; charset=utf-8");
                    params.put("Authorization", "Bearer " + bearerToken);
                    return params;
                }
            };

            VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    private void informUser(final String message) {
        try {
            //Clear the current data
            ArrayList<IndicatorPrice> priceArrayList = databaseHandler.getSavedPriceList();
            for (IndicatorPrice indicatorPrice : priceArrayList) {
                databaseHandler.updateIndicatorPrice(indicatorPrice.getIndicatorId(), "");
            }

            Log.d("Response message: ", message);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                }
            });
            //Move to Indicator Activity
            Intent indicatorIntent = new Intent(getContext(), IndicatorActivity.class);
            startActivity(indicatorIntent);

        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }


    private void init(View uploadView) {
        txtMarketName = uploadView.findViewById(R.id.txtUploadMarketName);
        String marketName = "Market: " + databaseHandler.getUserData().getMarketName();
        txtMarketName.setText(marketName);
        txtYear = uploadView.findViewById(R.id.txtYear);
        monthSpinner = uploadView.findViewById(R.id.monthSpinner);
        weekSpinner = uploadView.findViewById(R.id.weeksSpinner);
        uploadButton = uploadView.findViewById(R.id.btnUploadMarketData);
        uploadButton.setOnClickListener(this);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                month_id = "" + (position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        weekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                week = "" + (position + 1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


}
