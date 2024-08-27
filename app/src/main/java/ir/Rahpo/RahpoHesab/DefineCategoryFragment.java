package ir.Rahpo.RahpoHesab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static ir.Rahpo.RahpoHesab.ViewHelper.isNull;

import ir.Rahpo.RahpoHesab.Utility.CurrencyFormatter;

public class DefineCategoryFragment extends Fragment {
    private Context context;
    private EditText categoryName;
    private EditText commissionPercent;
    private EditText processPrice;
    private TextView processPriceCurrency;
    private Button submit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_define_category, container, false);
        context = requireContext();

        categoryName = v.findViewById(R.id.category_name_et);
        commissionPercent = v.findViewById(R.id.commission_percent_et);
        processPrice = v.findViewById(R.id.process_price_et);
        submit = v.findViewById(R.id.submit_b);
        processPriceCurrency = v.findViewById(R.id.process_price_currency);

        ViewHelper.setUpCurrencyText(context, processPriceCurrency);
        ViewHelper.setPercentCheckerForEditText(commissionPercent);
        ViewHelper.setThousandSeparatorForEditText(processPrice);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmit();
            }
        });

        return v;
    }

    private void handleSubmit() {
        String[] errorMessages = {getString(R.string.warn_empty_category_name), getString(R.string.warn_empty_commission_percent), getString(R.string.process_price)};
        if (!ViewHelper.isNull(context, errorMessages, categoryName, commissionPercent, processPrice)) {
            if(!queryDuplicateName()) {
                String categoryNameText = categoryName.getText().toString().strip();
                String commissionPercentText = commissionPercent.getText().toString();
                String processPriceText = new CurrencyFormatter().parse(processPrice.getText().toString());

                ContentValues cv = new ContentValues();
                cv.put("NAME", categoryNameText);
                cv.put("COMMISSION_PERCENT", commissionPercentText);
                cv.put("PROCESS_PRICE", processPriceText);
                try {

                    SQLiteOpenHelper helper = new CategoryDatabase(context);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    db.insert("CATEGORY", null, cv);
                    db.close();
                    helper.close();

                    Toast.makeText(context, getString(R.string.success_defining_category), Toast.LENGTH_SHORT).show();
                    categoryName.setText("");
                    commissionPercent.setText("");
                    processPrice.setText("");
                } catch (SQLException e) {
                    Toast.makeText(context, getString(R.string.error_defining_category), Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
    private boolean queryDuplicateName() {
        boolean result;
        try {
            String categoryNameText = categoryName.getText().toString().strip();

            SQLiteOpenHelper helper = new CategoryDatabase(context);
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query("CATEGORY", new String[] {"NAME"} , "NAME = ?", new String[] {categoryNameText}, null, null, null);
            if(cursor.moveToFirst()) {
                result = true;
                Toast.makeText(context, getString(R.string.error_duplicate_category_name), Toast.LENGTH_SHORT).show();
            } else {
                result = false;
            }
            cursor.close();
            db.close();
            helper.close();

        } catch (SQLException e) {
            result = true;
            Toast.makeText(context, getString(R.string.error_defining_category), Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}