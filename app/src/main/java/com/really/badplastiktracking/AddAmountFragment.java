package com.really.badplastiktracking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AddAmountFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_amount, container, false);
        TextView cashView = (TextView) rootView.findViewById(R.id.cardTotal1);
        TextView oldView1 = (TextView) rootView.findViewById(R.id.oldTotal1);
        TextView oldView2 = (TextView) rootView.findViewById(R.id.oldTotal2);
        TextView oldView3 = (TextView) rootView.findViewById(R.id.oldTotal3);
        TextView oldView4 = (TextView) rootView.findViewById(R.id.oldTotal4);
        SharedPreferences settings = getActivity().getPreferences(0);
        String balances = settings.getString("balances", "$0.00,$0.00,$0.00,$0.00,$0.00,"); // restores old values, or returns a zero set if null
        String[] balance = balances.split(",");
        cashView.setText(balance[0]);
        oldView1.setText(balance[1]);
        oldView2.setText(balance[2]);
        oldView3.setText(balance[3]);
        oldView4.setText(balance[4]);
        return rootView;
    }
}
