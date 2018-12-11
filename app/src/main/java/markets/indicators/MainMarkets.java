package markets.indicators;


import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import database.DatabaseHandler;
import fsnau.org.R;
import model.Indicator;
import model.IndicatorPrice;
import model.MainMarketIndicators;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainMarkets extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    public MainMarkets() {
    }

    ListView listView;
    Button saveButton;
    OnSelectedListener callback;
    DatabaseHandler databaseHandler;
    TextView txtMarketName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_markets, container, false);
        databaseHandler = DatabaseHandler.getInstance(getContext());
        populateListView(view);
        saveButton = view.findViewById(R.id.btnSaveMainMarketPrices);
        saveButton.setOnClickListener(this);
        txtMarketName = view.findViewById(R.id.txtMainMarketName);
        String marketName = databaseHandler.getUserData().getMarketName();
        txtMarketName.setText(marketName);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                return true;
            }
        });
        return view;
    }

    private void populateListView(View view) {
        try {
            final ArrayList<Object> indicators = MainMarketIndicators.listOfIndicators();
            final IndicatorArrayAdapter indicatorArrayAdapter = new IndicatorArrayAdapter(getContext(), indicators, databaseHandler);
            listView = view.findViewById(R.id.mainMarketsListView);
            listView.setAdapter(indicatorArrayAdapter);
            listView.setOnItemClickListener(this);
            //
        } catch (Exception exception) {
            Log.e("Markets OnCreateView", exception.getLocalizedMessage());
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity;

        if (context instanceof Activity) {
            activity = (Activity) context;
            callback = (OnSelectedListener) activity;
        }
    }

    @Override
    public void onClick(View view) {

        String buttonTag = view.getTag().toString();

        if (buttonTag.equalsIgnoreCase("btnPreview")) {
            // Save
            ArrayList<IndicatorPrice> priceList = databaseHandler.getPriceList();

            ArrayList<Integer> savedPrices = new ArrayList<>();

            for (IndicatorPrice indicatorPrice : priceList) {
                try {
                    String savedPrice = indicatorPrice.getIndicatorPrice().trim();

                    if (savedPrice.length() > 0) {
                        int price = Integer.parseInt(savedPrice);
                        savedPrices.add(price);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (savedPrices.size() ==0) {
                //No data to save
                Toast.makeText(getContext(), "No data to preview", Toast.LENGTH_LONG).show();
            } else {
                //Preview and save
                PreviewMarketData previewMarketData = new PreviewMarketData();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, previewMarketData);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }


        }
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Object clickedObject = adapterView.getItemAtPosition(position);

        if (clickedObject instanceof Indicator) {
            Indicator selectedIndicator = (Indicator) clickedObject;

            callback.indicatorSelected(selectedIndicator);

        }
    }


}
