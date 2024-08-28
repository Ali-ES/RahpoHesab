package ir.Rahpo.RahpoHesab.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import ir.Rahpo.RahpoHesab.Calculations;
import ir.Rahpo.RahpoHesab.R;
import ir.Rahpo.RahpoHesab.Utility.CurrencyFormatter;
import ir.Rahpo.RahpoHesab.Utility.PercentChecker;
import ir.Rahpo.RahpoHesab.ViewHelper;

public class SourcePriceFragment extends Fragment {
    private static final String TAG = "SourcePriceFragment";
    private Context context;
    private EditText sourcePriceEdit;
    private EditText tolerancePercentEdit;
    private TextView minAllowedPriceTextView;
    private TextView maxAllowedPriceTextView;
    private Calculations calculations = new Calculations();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_source_price, container, false);
        context = requireContext();
        sourcePriceEdit = v.findViewById(R.id.source_price_et);
        tolerancePercentEdit = v.findViewById(R.id.tolerance_percent_et);
        minAllowedPriceTextView = v.findViewById(R.id.min_price_input);
        maxAllowedPriceTextView = v.findViewById(R.id.max_price_input);

        ViewHelper.setUpCurrencyText(context, v.findViewById(R.id.source_price_currency));
        ViewHelper.setUpCurrencyText(context, v.findViewById(R.id.min_price_currency));
        ViewHelper.setUpCurrencyText(context, v.findViewById(R.id.max_price_currency));
        ViewHelper.setPercentCheckerForEditText(tolerancePercentEdit);
        ViewHelper.setThousandSeparatorForEditText(sourcePriceEdit);

        handleModifications();

        return v;
    }

    private void handleModifications() {
        TextWatcher changeWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String sourcePrice = sourcePriceEdit.getText().toString();
                String tolerancePercent = tolerancePercentEdit.getText().toString();
                if(sourcePrice.isEmpty() || tolerancePercent.isEmpty()) {
                    return;
                }
                calcMinMaxAllowedText(sourcePrice, tolerancePercent);
            }
        };
        sourcePriceEdit.addTextChangedListener(changeWatcher);
        tolerancePercentEdit.addTextChangedListener(changeWatcher);
    }

    private void calcMinMaxAllowedText(String sourcePrice, String tolerancePercent) {
        sourcePrice = new CurrencyFormatter().parse(sourcePrice);
        tolerancePercent =  PercentChecker.getPercentValue(tolerancePercent);
        minAllowedPriceTextView.setText(calculations.getMinAllowedPrice(sourcePrice, tolerancePercent));
        maxAllowedPriceTextView.setText(calculations.getMaxAllowedPrice(sourcePrice, tolerancePercent));
    }
}