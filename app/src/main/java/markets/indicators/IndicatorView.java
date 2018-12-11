package markets.indicators;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import fsnau.org.R;

public class IndicatorView extends ConstraintLayout{


    public IndicatorView(Context context) {
        super(context);
    }




    public static IndicatorView inflate(ViewGroup parent) {
        IndicatorView indicatorView = (IndicatorView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.indicator_row, parent, false);
        return indicatorView ;
    }
}
