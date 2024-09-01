package ir.Rahpo.RahpoHesab.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import ir.Rahpo.RahpoHesab.CategoryDatabase;
import ir.Rahpo.RahpoHesab.Constants;
import ir.Rahpo.RahpoHesab.R;

public class SettingsFragment extends Fragment {
    private static final String TAG = "Settings";
    private ToggleButton currency;
    private boolean isCurrentCurrencyTooman;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        currency = v.findViewById(R.id.currency_toggle);

        SharedPreferences sp = getActivity().getSharedPreferences(Constants.PREF_CURRENCY, Context.MODE_PRIVATE);
        if(sp.getString(Constants.KEY_CURRENCY, "").equals(getString(R.string.tooman))) {
            currency.setChecked(true);
            isCurrentCurrencyTooman = true;
        } else {
            isCurrentCurrencyTooman = false;
        }
        Log.v(TAG, sp.getString(Constants.KEY_CURRENCY, ""));
        SharedPreferences.Editor editor = sp.edit();


        currency.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String newCurrency;
                if(isChecked) {
                    newCurrency = currency.getTextOn().toString();
                } else {
                    newCurrency = currency.getTextOff().toString();
                }
                editor.putString(Constants.KEY_CURRENCY, newCurrency);
                editor.apply();

                if(isCurrentCurrencyTooman != isChecked) {
                    currency.setClickable(false);
                    isCurrentCurrencyTooman = !isCurrentCurrencyTooman;
                    CategoryDatabase dbHelper = new CategoryDatabase(getActivity());
                    dbHelper.changeCurrency(isCurrentCurrencyTooman);
                    dbHelper.close();
                    currency.setClickable(true);
                }
            }
        });




        return v;
    }
}