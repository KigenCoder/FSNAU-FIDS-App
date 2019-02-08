package markets.indicators;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;
import database.DatabaseHandler;
import fsnau.org.R;


public class SLIMSContainerFragment extends Fragment {
    Context fragmentContext;
    public SLIMSContainerFragment() { }

    ViewPager viewPager = null;
    PagerTabStrip pagerTabStrip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.container_fragment,container, false);
        viewPager = view.findViewById(R.id.indicatorViewPager);
        pagerTabStrip = view.findViewById(R.id.pagerTabStrip);
        if (viewPager != null) {
            initViewPager();
        }

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




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.fragmentContext = context;
    }

    private void initViewPager() {
        IndicatorPageAdapter pageAdapter = new IndicatorPageAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        pagerTabStrip.setTabIndicatorColor(Color.BLACK);
        //pagerTabStrip.setDrawFullUnderline(false);
        //pagerTabStrip.setTextColor(Color.WHITE);
        //pagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        //pagerTabStrip.setText;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (viewPager != null) {
            initViewPager();
        }
    }


}
