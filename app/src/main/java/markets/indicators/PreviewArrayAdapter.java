package markets.indicators;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import database.DatabaseHandler;
import fsnau.org.R;

import model.IndicatorPrice;


public class PreviewArrayAdapter extends BaseAdapter {
    private static class ViewHolder {
        TextView indicatorName;
        TextView indicatorPrice;
    }


    ArrayList<IndicatorPrice> indicatorsList;
    DatabaseHandler databaseHandler;
    Context context;


    //Constructor
    public PreviewArrayAdapter(Context context, ArrayList<IndicatorPrice> indicators, DatabaseHandler dbHandler) {
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


    @Override
    public View getView(final int position, View rowView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (rowView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.indicator_preview_row, null, true);
            viewHolder.indicatorName = rowView.findViewById(R.id.indicatorPreviewName);
            viewHolder.indicatorPrice = rowView.findViewById(R.id.indicatorPreviewPrice);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) rowView.getTag();

        }

        //OTHERWISE SET DATA FOR ROW ACCORDINGLY
        IndicatorPrice indicatorPrice = (IndicatorPrice) getItem(position);
        viewHolder.indicatorName.setText(indicatorPrice.getIndicatorName());
        viewHolder.indicatorPrice.setText(indicatorPrice.getIndicatorPrice());

        return rowView;
    }


}
