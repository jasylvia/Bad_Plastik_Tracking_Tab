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
    static private int ARRAY_SIZE = 5;
    private String[] balance;
    private String balances;

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
        balance = new String[ARRAY_SIZE];
        balances = settings.getString("balances", "$0.00,$0.00,$0.00,$0.00,$0.00,"); // restores old values, or returns a zero set if null
        balance = balances.split(","); // restore old values to balance array
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getPreferences(0);
        SharedPreferences.Editor editor = settings.edit();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < balance.length; i++)
            sb.append(balance[i]).append(","); // append all balances to one string with a comma delimiter
        editor.putString("balances", sb.toString());
        editor.apply();
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
                break;
            case R.id.change5:
                totes += 5.00f * multiplier;
                break;
            case R.id.change10:
                totes += 10.00f * multiplier;
                break;
            case R.id.changePenn:
                totes += 0.01f * multiplier;
                break;
            case R.id.changeNick:
                totes += 0.05f * multiplier;
                break;
            case R.id.changeDime:
                totes += 0.10f * multiplier;
                break;
        }

        setTotal(v, totes);
        balance[4] = balance[3];
        balance[3] = balance[2];
        balance[2] = balance[1];
        balance[1] = balance[0];
        balance[0] = cashView.getText().toString();
    }

    public void setTotal(View v, float total) {
        // Add amount textviews, with all the unoptimization you want!
        TextView cashView = (TextView) findViewById(R.id.cardTotal1);
        TextView oldView1 = (TextView) findViewById(R.id.oldTotal1);
        TextView oldView2 = (TextView) findViewById(R.id.oldTotal2);
        TextView oldView3 = (TextView) findViewById(R.id.oldTotal3);
        TextView oldView4 = (TextView) findViewById(R.id.oldTotal4);
        // Sub amount textviews
        TextView cashView2 = (TextView) findViewById(R.id.cardTotal2);
        TextView olderView1 = (TextView) findViewById(R.id.oldTotal01);
        TextView olderView2 = (TextView) findViewById(R.id.oldTotal02);
        TextView olderView3 = (TextView) findViewById(R.id.oldTotal03);
        TextView olderView4 = (TextView) findViewById(R.id.oldTotal04);
        // Hidden textview for easy total changes
        TextView tempView = (TextView) findViewById(R.id.tempTotal);

        tempView.setText("$");
        tempView.append(String.format("%.2f", total));
        oldView4.setText(oldView3.getText()); // 4th history item pair
        olderView4.setText(oldView3.getText());
        oldView3.setText(oldView2.getText()); // 3rd history item pair
        olderView3.setText(oldView2.getText());
        oldView2.setText(oldView1.getText()); // 2nd history item pair
        olderView2.setText(oldView1.getText());
        oldView1.setText(cashView.getText()); // 1st history item pair
        olderView1.setText(cashView.getText());
        cashView.setText(tempView.getText()); // current total pair
        cashView2.setText(tempView.getText());
    }
}