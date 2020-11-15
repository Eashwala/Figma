package com.ipl.user.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ipl.user.screens.JourneyFragment;
import com.ipl.user.screens.StatisticsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager supportFragmentManager) {
        super(supportFragmentManager);

    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new StatisticsFragment();
        }
        else if (position == 1)
        {
            fragment = new JourneyFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "SATISTICS";
        }
        else if (position == 1)
        {
            title = "JOURNEY";
        }
        return title;
    }

}
