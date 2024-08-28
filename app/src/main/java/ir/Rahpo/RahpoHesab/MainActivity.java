package ir.Rahpo.RahpoHesab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import ir.Rahpo.RahpoHesab.Fragments.CalculatePriceFragment;
import ir.Rahpo.RahpoHesab.Fragments.ChangeCategoryFragment;
import ir.Rahpo.RahpoHesab.Fragments.DefineCategoryFragment;
import ir.Rahpo.RahpoHesab.Fragments.SettingsFragment;
import ir.Rahpo.RahpoHesab.Fragments.SourcePriceFragment;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ImageView settings = findViewById(R.id.settings);
        setFragmentOpenOn(settings, new SettingsFragment());

        ImageView defineCategory = findViewById(R.id.define_category);
        setFragmentOpenOn(defineCategory, new DefineCategoryFragment());

        ImageView changeCategory = findViewById(R.id.change_category);
        setFragmentOpenOn(changeCategory, new ChangeCategoryFragment());

        ImageView sourcePrice = findViewById(R.id.source_price);
        setFragmentOpenOn(sourcePrice, new SourcePriceFragment());

        ImageView calculatePrice = findViewById(R.id.calculate_price);
        setFragmentOpenOn(calculatePrice, new CalculatePriceFragment());

        openFragment(new CalculatePriceFragment());

        init();

    }


    private void init() {
        SharedPreferences sp = getSharedPreferences(Constants.PREF_CURRENCY, MODE_PRIVATE);
        if(sp.getString(Constants.KEY_CURRENCY, "").isEmpty()) {
            SharedPreferences.Editor editor = sp.edit();
            String defaultCurrency = getString(R.string.rial);
            editor.putString(Constants.KEY_CURRENCY, defaultCurrency);
            editor.apply();
            Log.v(TAG, "condition met");
        }

    }

    private void setFragmentOpenOn(View view, Fragment fragment) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(fragment);
            }
        });
    }
    private void openFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount() < 1) {
            finishAffinity();
        }
    }
}