package ir.Rahpo.RahpoHesab;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ir.Rahpo.RahpoHesab.Utility.CurrencyFormatter;

public class ViewHelper {
    public static void setUpCurrencyText(Context c, TextView currencyTextView) {
        SharedPreferences sp = c.getSharedPreferences(Constants.PREF_CURRENCY, Context.MODE_PRIVATE);
        currencyTextView.setText(sp.getString(Constants.KEY_CURRENCY, ""));
    }
    public static void setThousandSeparatorForEditText(EditText editText) {
        CurrencyFormatter currencyFormatter = new CurrencyFormatter();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.removeTextChangedListener(this);
                String formattedPrice = currencyFormatter.format(editText.getText().toString());
                editText.setText(formattedPrice);
                editText.setSelection(formattedPrice.length());
                editText.addTextChangedListener(this);
            }
        });
    }
}
