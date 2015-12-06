package com.really.badplastiktracking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SubAmountFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sub_amount, container, false);
        TextView cashView = (TextView) rootView.findViewById(R.id.cardTotal2);
        SharedPreferences settings = getActivity().getPreferences(0);
        String balance = settings.getString("cashBal", "$0.00"); // restore previous balance, or $0.00 if there is none
        cashView.setText(balance);
        return rootView;
    }
}