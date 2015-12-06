package com.really.badplastiktracking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Boolean add;
    private String balance;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        SharedPreferences settings = getPreferences(0);
        balance = settings.getString("cashBal", "$0.00"); // restore previous balance, or $0.00 if there is none
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getPreferences(0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("cashBal", balance);
        editor.commit();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new AddAmountFragment();
                case 1:
                    return new SubAmountFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "ADD AMOUNT";
                case 1:
                    return "SUBTRACT AMOUNT";
            }
            return null;
        }
    }

    public void addCash(View v) {
        add = true;
        changeCash(v);
    }

    public void subCash(View v) {
        add = false;
        changeCash(v);
    }

    public void changeCash(View v) {
        TextView cashView = (TextView) findViewById(R.id.cardTotal1);
        TextView cashView2 = (TextView) findViewById(R.id.cardTotal2);
        float totes = Float.valueOf(cashView.getText().toString().substring(1));
        float multiplier;

        if (add)
            multiplier = 1.00f;
        else
            multiplier = -1.00f;

        switch (v.getId()) {
            case R.id.change1:
                totes += 1.00f * multiplier;
                cashView.setText("$");
                cashView.append(String.format("%.2f", totes));
                cashView2.setText("$");
                cashView2.append(String.format("%.2f", totes));
                break;
            case R.id.change5:
                totes += 5.00f * multiplier;
                cashView.setText("$");
                cashView.append(String.format("%.2f", totes));
                cashView2.setText("$");
                cashView2.append(String.format("%.2f", totes));
                break;
            case R.id.change10:
                totes += 10.00f * multiplier;
                cashView.setText("$");
                cashView.append(String.format("%.2f", totes));
                cashView2.setText("$");
                cashView2.append(String.format("%.2f", totes));
                break;
            case R.id.changePenn:
                totes += 0.01f * multiplier;
                cashView.setText("$");
                cashView.append(String.format("%.2f", totes));
                cashView2.setText("$");
                cashView2.append(String.format("%.2f", totes));
                break;
            case R.id.changeNick:
                totes += 0.05f * multiplier;
                cashView.setText("$");
                cashView.append(String.format("%.2f", totes));
                cashView2.setText("$");
                cashView2.append(String.format("%.2f", totes));
                break;
            case R.id.changeDime:
                totes += 0.10f * multiplier;
                cashView.setText("$");
                cashView.append(String.format("%.2f", totes));
                cashView2.setText("$");
                cashView2.append(String.format("%.2f", totes));
                break;
        }
        balance = cashView.getText().toString();
    }
}
