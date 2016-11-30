package com.karol.vimos.gym;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Vimos on 22/11/16.
 */

public class Pager extends FragmentStatePagerAdapter{


        //integer to count number of tabs
        int tabCount;

        //Constructor to the class
        public Pager(FragmentManager fm, int tabCount) {
            super(fm);
            //Initializing tab count
            this.tabCount= tabCount;
        }

        //Overriding method getItem
        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    Monday monday = new Monday();
                    return monday;
                case 1:
                    Tuesday tuesday = new Tuesday();
                    return tuesday;
                case 2:
                    Wednesday wednesday = new Wednesday();
                    return wednesday;
                case 3:
                    Thursday thursday = new Thursday();
                    return thursday;
                case 4:
                    Friday friday = new Friday();
                    return friday;
                case 5:
                    Saturday saturday = new Saturday();
                    return saturday;
                default:
                    return null;
            }
        }

        //Overriden method getCount to get the number of tabs
        @Override
        public int getCount() {
            return tabCount;
        }

}
