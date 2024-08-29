package ir.Rahpo.RahpoHesab.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.Rahpo.RahpoHesab.CategoryDatabase;
import ir.Rahpo.RahpoHesab.R;
import ir.Rahpo.RahpoHesab.Utility.CurrencyFormatter;
import ir.Rahpo.RahpoHesab.ViewHelper;

public class ChangeCategoryFragment extends Fragment {
    public static final String TAG = "ChangeCategory";
    private Context context;
    private AutoCompleteTextView selectCategory;
    private EditText categoryName;
    private EditText commissionPercent;
    private EditText processPrice;
    private TextView processPriceCurrency;
    private Button delete;
    private Button edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_category, container, false);
        context = requireContext();
        selectCategory = v.findViewById(R.id.select_category_auto_com);
        categoryName = v.findViewById(R.id.category_name_et);
        commissionPercent = v.findViewById(R.id.commission_percent_et);
        processPrice = v.findViewById(R.id.process_price_et);
        processPriceCurrency = v.findViewById(R.id.process_price_currency);
        delete = v.findViewById(R.id.delete_b);
        edit = v.findViewById(R.id.edit_b);


        ViewHelper.setPercentCheckerForEditText(commissionPercent);
        ViewHelper.setThousandSeparatorForEditText(processPrice);
        ViewHelper.setUpCurrencyText(context, processPriceCurrency);

        loadCategoryNames();


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDelete();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEdit();
            }
        });

        return v;
    }

    private void handleDelete() {
        if(!ViewHelper.isNull(context, getString(R.string.warn_empty_select_category), selectCategory)) {
            try {
                SQLiteOpenHelper helper = new CategoryDatabase(context);
                SQLiteDatabase db = helper.getWritableDatabase();

                String selectCategoryText = selectCategory.getText().toString().strip();

                int status = db.delete("CATEGORY", "NAME = ?", new String[] {selectCategoryText});
                if(status > 0) {
                    Toast.makeText(context, R.string.success_deleting_category, Toast.LENGTH_SHORT).show();
                    selectCategory.setText("");
                    loadCategoryNames();
                } else {
                    Toast.makeText(context, R.string.error_category_not_found, Toast.LENGTH_SHORT).show();
                }
                db.close();
                helper.close();

            } catch(SQLException e) {
                Toast.makeText(context, R.string.error_deleting_category, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleEdit() {
        if(!ViewHelper.isNull(context, getString(R.string.warn_empty_select_category), selectCategory)) {
            String selectCategoryText = selectCategory.getText().toString().strip();
            String categoryNameText = categoryName.getText().toString().strip();
            String commissionPercentText = commissionPercent.getText().toString();
            String processPriceText = processPrice.getText().toString();
            if(categoryNameText.isEmpty() && commissionPercentText.isEmpty() && processPriceText.isEmpty()) {
                Toast.makeText(context, getString(R.string.warn_empty_category_values), Toast.LENGTH_SHORT).show();
                return;
            }

            CurrencyFormatter formatter = new CurrencyFormatter();
            try {
                SQLiteOpenHelper helper = new CategoryDatabase(context);
                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues cv = new ContentValues();
                if(!categoryNameText.isEmpty()) cv.put("NAME", categoryNameText);
                if(!commissionPercentText.isEmpty()) cv.put("COMMISSION_PERCENT", commissionPercentText);
                if(!processPriceText.isEmpty()) cv.put("PROCESS_PRICE", formatter.parse(processPriceText));

                int status = db.update("CATEGORY", cv, "NAME = ?", new String[] {selectCategoryText});
                if(status > 0) {
                    Toast.makeText(context, R.string.success_editing_category, Toast.LENGTH_SHORT).show();
                    selectCategory.setText("");
                    categoryName.setText("");
                    commissionPercent.setText("");
                    processPrice.setText("");
                    loadCategoryNames();
                } else {
                    Toast.makeText(context, R.string.error_category_not_found, Toast.LENGTH_SHORT).show();
                }

                db.close();
                helper.close();

            } catch(SQLException e) {
                Toast.makeText(context, R.string.error_deleting_category, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadCategoryNames() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> categoryNames = new ArrayList<>();
                try {
                    SQLiteOpenHelper helper = new CategoryDatabase(context);
                    SQLiteDatabase db = helper.getReadableDatabase();
                    Cursor cursor = db.query("CATEGORY", new String[] {"NAME"}, null, null, null, null, null);
                    if(cursor.moveToFirst()) {
                        do {
                            categoryNames.add(cursor.getString(0));
                        } while (cursor.moveToNext());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, categoryNames);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            selectCategory.setAdapter(adapter);
                        }
                    });

                    cursor.close();
                    db.close();
                    helper.close();
                } catch (SQLException e) {
                    Log.v(TAG, e.toString());
                }
            }
        }).start();
    }
}