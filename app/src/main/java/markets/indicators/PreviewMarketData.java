package markets.indicators;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import auth.LoginActivity;
import database.DatabaseHandler;
import fsnau.org.R;
import model.IndicatorPrice;

public class PreviewMarketData extends Fragment implements View.OnClickListener {
    TextView txtMarketName;
    ListView listView;
    Button btnPreview;
    DatabaseHandler databaseHandler;

    public PreviewMarketData() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View preview = inflater.inflate(R.layout.preview_market_data, container, false);
        databaseHandler = DatabaseHandler.getInstance(getContext());
        txtMarketName = preview.findViewById(R.id.txtPreviewMarket);
        String marketName = "Market: " + databaseHandler.getUserData().getMarketName();
        txtMarketName.setText(marketName);
        btnPreview = preview.findViewById(R.id.btnPreviewMarketPrices);
        btnPreview.setOnClickListener(this);
        populateListView(preview);
        return preview;
    }


    private void populateListView(View view) {
        try {
            ArrayList<IndicatorPrice> indicators = databaseHandler.getSavedPriceList();
            PreviewArrayAdapter indicatorArrayAdapter = new PreviewArrayAdapter(getContext(), indicators, databaseHandler);
            listView = view.findViewById(R.id.previewDataList);
            listView.setAdapter(indicatorArrayAdapter);
            //listView.setOnItemClickListener(this);
            //
        } catch (Exception exception) {
            Log.e("Markets OnCreateView", exception.getLocalizedMessage());
        }

    }

    @Override
    public void onClick(View view) {
        String buttonTag = view.getTag().toString();

        if (buttonTag.equalsIgnoreCase("btnToUpload")) {

            Intent uploadIntent = new Intent(getContext(), UploadDataActivity.class);
            startActivity(uploadIntent);
        }
    }
}
