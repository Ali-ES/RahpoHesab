package ir.Rahpo.RahpoHesab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView settings = findViewById(R.id.settings);
        setFragmentOpenOn(settings, new SettingsFragment());

        ImageView defineCategory = findViewById(R.id.define_category);
        setFragmentOpenOn(defineCategory, new DefineCategoryFragment());




        init();

    }


    private void init() {
        SharedPreferences sp = getSharedPreferences(Constants.PREF_CURRENCY, MODE_PRIVATE);
        if(sp.getString(Constants.KEY_CURRENCY, "").isEmpty()) {
            SharedPreferences.Editor editor = sp.edit();
            String defaultCurrency = getString(R.string.rial);
            editor.putString(Constants.KEY_CURRENCY, defaultCurrency);
            editor.apply();
        }

    }

    private void setFragmentOpenOn(View view, Fragment fragment) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount() < 1) {
            finishAffinity();
        }
    }
}