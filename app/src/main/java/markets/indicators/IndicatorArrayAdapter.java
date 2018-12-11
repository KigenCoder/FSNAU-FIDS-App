package markets.indicators;

import android.content.Context;

import database.DatabaseHandler;
import fsnau.org.R;
import model.Indicator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class IndicatorArrayAdapter extends BaseAdapter {
    private static class ViewHolder {
        TextView indicatorName;
        EditText indicatorPrice;
        TextView categoryName;
    }


    ArrayList<Object> indicatorsList;
    DatabaseHandler databaseHandler;
    Context context;
    static final int ROW = 0;
    static final int HEADER = 1;

    //Constructor
    public IndicatorArrayAdapter(Context context, ArrayList<Object> indicators, DatabaseHandler dbHandler) {
        this.context = context;
        this.indicatorsList = indicators;
        this.databaseHandler = dbHandler;

    }

    //GET THE SIZE OF THE LIST TO DISPLAY
    @Override
    public int getCount() {
        return indicatorsList.size();
    }

    //GET SINGLE ITEM FROM THE ARRAY LIST
    @Override
    public Object getItem(int position) {
        return indicatorsList.get(position);
    }

    //GET ITEM IDENTIFIER
    @Override
    public long getItemId(int position) {
        return position;
    }



    /*
      CHECK IF  CURRENT ITEM IS A HEADER OR ROW ITEM
     */

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Indicator) {
            //Row
            return ROW;
        } else {
            //Header
            return HEADER;
        }

    }


    @Override
    public int getViewTypeCount() {
        //We have 2 sets of views headers and rows
        return 2;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        //Is it Header or Normal Row
        int type = getItemViewType(position);


        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(context);

            switch (type) {
                case ROW:
                    convertView = inflater.inflate(R.layout.indicator_row, null, true);
                    viewHolder.indicatorName = convertView.findViewById(R.id.indicatorName);
                    viewHolder.indicatorPrice = convertView.findViewById(R.id.indicatorPrice);
                    break;

                case HEADER:
                    convertView = inflater.inflate(R.layout.indicator_category, null, true);
                    viewHolder.categoryName = convertView.findViewById(R.id.categoryName);
                    break;

            }

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        //OTHERWISE SET DATA FOR HEADER/ROW ACCORDINGLY
        switch (type) {
            case ROW:
                final Indicator indicator = (Indicator) getItem(position);
                viewHolder.indicatorName.setText(indicator.getIndicatorBusinessName());
                String savedPrice = databaseHandler.getIndicatorPrice(indicator);

                if (savedPrice.trim().length() > 0) {
                    viewHolder.indicatorPrice.setText(savedPrice);
                }else{
                    viewHolder.indicatorPrice.getText().clear();
                }




                final ViewHolder placeHolder = viewHolder;

                viewHolder.indicatorPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        //When Focus is over / Editing is done save
                        if(!hasFocus){

                            try{
                                String indicatorPrice = placeHolder.indicatorPrice.getText().toString();
                                String indicatorId = indicator.getIndicatorId();
                                if(indicatorPrice.trim().length()>0){
                                    databaseHandler.updateIndicatorPrice(indicatorId, indicatorPrice);

                                    Log.d(indicator.getIndicatorBusinessName() +" :" + indicatorId,
                                            indicatorPrice);
                                }


                            }catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    }
                });



                if (savedPrice.trim().length() > 0) {
                    viewHolder.indicatorPrice.setText(savedPrice);
                }

                break;
            case HEADER:
                String categoryTitle = (String) getItem(position);
                viewHolder.categoryName.setText(categoryTitle);
                break;

        }

        return convertView;
    }


}
