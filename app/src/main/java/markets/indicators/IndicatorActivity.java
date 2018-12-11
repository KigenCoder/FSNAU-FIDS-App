package markets.indicators;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import database.DatabaseHandler;
import fsnau.org.R;
import model.Indicator;
import model.IndicatorPrice;


public class IndicatorActivity extends AppCompatActivity implements OnSelectedListener {


    DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indicator_activity);
        databaseHandler = DatabaseHandler.getInstance(this);
        addFragment();
        savePriceList();
    }



    @Override
    protected void onResume() {
        super.onResume();
         addFragment();
    }


    @Override
    public void indicatorSelected(Indicator indicator) {
        Toast.makeText(getApplicationContext(), "Row: " + indicator.getIndicatorBusinessName(), Toast.LENGTH_LONG).show();

    }


    private void savePriceList() {

        try{
            ArrayList<IndicatorPrice>  savedPriceList = databaseHandler.getPriceList();
            if(savedPriceList.size()<1){
                //Save Price list
                ArrayList<IndicatorPrice> priceList = PriceList.priceList();

                for(IndicatorPrice price : priceList ){

                    databaseHandler.addIndicatorPrice(price);
                    //Log.d(price.getIndicatorName(), ": " + price.getIndicatorPrice());
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }


    }

    private void addFragment() {
        int marketTypeId = Integer.parseInt(databaseHandler.getUserData().getMarketTypeId());

        Fragment fragment;

        if (marketTypeId == 1) {
            //Main market
            fragment = new MainMarkets();
        } else {
            //SLIM market
            fragment = new SLIMSContainerFragment();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainContainer, fragment)
                .addToBackStack(null)
                .commit();
    }




}
