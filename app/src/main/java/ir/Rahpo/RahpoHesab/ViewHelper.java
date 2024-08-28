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
import ir.Rahpo.RahpoHesab.Utility.PercentChecker;

public class ViewHelper {
    public static final String TAG = "ViewHelper";
    public static boolean isNull(Context c, String msg, EditText editText) {
        if(editText.getText().toString().isBlank()) {
            Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
    public static boolean isNull(Context c, String[] messages, EditText...editTexts) {
        for(int i = 0; i < editTexts.length; i++) {
            if(editTexts[i].getText().toString().isBlank()) {
                Toast.makeText(c, messages[i], Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }
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
                try {
                    editText.setSelection(formattedPrice.length());
                } catch (NullPointerException e) {
                    Log.v(TAG, e.toString());
                }
                editText.addTextChangedListener(this);
            }
        });
    }

    public static void setPercentCheckerForEditText(EditText editText) {
        PercentChecker percentChecker = new PercentChecker();
        editText.addTextChangedListener(new TextWatcher() {
            String previousText = "";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.removeTextChangedListener(this);
                String percentText = editText.getText().toString();
                if(percentChecker.matches(percentText)) {
                    previousText = percentText;
                } else {
                    editText.setText(previousText);
                    editText.setSelection(previousText.length());
                }
                editText.addTextChangedListener(this);
            }
        });
    }
}
