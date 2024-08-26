package ir.Rahpo.RahpoHesab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class SettingsFragment extends Fragment {
    private static final String TAG = "Settings";
    private ToggleButton currency;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        SharedPreferences sp = getActivity().getSharedPreferences(Constants.PREF_CURRENCY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        currency = v.findViewById(R.id.currency_toggle);
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
            }
        });


        return v;
    }
}