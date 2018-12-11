package markets.indicators;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class IndicatorPageAdapter extends FragmentPagerAdapter {


    public IndicatorPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }


    private String[] tabs = new String[]{"SLIMS I", "SLIMS II"};


    //Return the Fragment associated with a specified position. @param position

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        try {
            // Small Market
            switch (position) {
                case 0:
                    fragment = new SlimsPartOne();
                    break;
                case 1:
                    fragment = new SlimsPartTwo();
                    break;
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }


        return fragment;

    }

    //Return the number of views available.
    @Override
    public int getCount() {

        return tabs.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }


}
