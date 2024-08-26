package ir.Rahpo.RahpoHesab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }


    private void init() {

        SharedPreferences sp = getSharedPreferences(Constants.PREF_CURRENCY, MODE_PRIVATE);
        if(sp.getString(Constants.KEY_CURRENCY, "").isEmpty()) {
            SharedPreferences.Editor editor = sp.edit();
            String defaultCurrency = getString(R.string.rial);
            editor.putString(Constants.KEY_CURRENCY, defaultCurrency);
        }

    }
}