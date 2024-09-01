package ir.Rahpo.RahpoHesab.Fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.Rahpo.RahpoHesab.Category;
import ir.Rahpo.RahpoHesab.CategoryDatabase;
import ir.Rahpo.RahpoHesab.R;
import ir.Rahpo.RahpoHesab.Utility.CurrencyFormatter;
import ir.Rahpo.RahpoHesab.ViewHelper;

public class ChangeCategoryFragment extends Fragment {
    public static final String TAG = "ChangeCategory";
    private Context context;
    private TextView selectCategoryTextView;
    private EditText categoryName;
    private EditText commissionPercent;
    private EditText processPrice;
    private TextView processPriceCurrency;
    private Button delete;
    private Button edit;
    private Category selectedCategory = null;
    private ArrayList<Category> categories = new ArrayList<>();
    private AlertDialog.Builder builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_category, container, false);
        context = requireContext();
        selectCategoryTextView = v.findViewById(R.id.select_category_tv);
        categoryName = v.findViewById(R.id.category_name_et);
        commissionPercent = v.findViewById(R.id.commission_percent_et);
        processPrice = v.findViewById(R.id.process_price_et);
        processPriceCurrency = v.findViewById(R.id.process_price_currency);
        delete = v.findViewById(R.id.delete_b);
        edit = v.findViewById(R.id.edit_b);

        categories.clear();
        selectedCategory = null;

        ViewHelper.setPercentCheckerForEditText(commissionPercent);
        ViewHelper.setThousandSeparatorForEditText(processPrice);
        ViewHelper.setUpCurrencyText(context, processPriceCurrency);

        loadCategories();


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
        if(selectedCategory == null) {
            Toast.makeText(context, R.string.error_category_not_selected, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            SQLiteOpenHelper helper = new CategoryDatabase(context);
            SQLiteDatabase db = helper.getWritableDatabase();


            int status = db.delete("CATEGORY", "NAME = ?", new String[] {selectedCategory.getName()});
            if(status > 0) {
                Toast.makeText(context, R.string.success_deleting_category, Toast.LENGTH_SHORT).show();
                selectCategoryTextView.setText("");
                categoryName.setText("");
                commissionPercent.setText("");
                processPrice.setText("");


                categories.remove(selectedCategory);
                selectedCategory = null;
                setUpCategorySelection();
            } else {
                Toast.makeText(context, R.string.error_category_not_found, Toast.LENGTH_SHORT).show();
            }
            db.close();
            helper.close();

        } catch(SQLException e) {
            Toast.makeText(context, R.string.error_deleting_category, Toast.LENGTH_SHORT).show();
        }

    }

    private void handleEdit() {
        if(selectedCategory == null) {
            Toast.makeText(context, R.string.error_category_not_selected, Toast.LENGTH_SHORT).show();
            return;
        }

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

            Category newCategory = new Category();

            ContentValues cv = new ContentValues();
            if(!categoryNameText.isEmpty()) {
                cv.put("NAME", categoryNameText);
                newCategory.setName(categoryNameText);
            }
            if(!commissionPercentText.isEmpty()) {
                cv.put("COMMISSION_PERCENT", commissionPercentText);
                newCategory.setCommissionPercent(commissionPercentText);
            }
            if(!processPriceText.isEmpty()) {
                cv.put("PROCESS_PRICE", formatter.parse(processPriceText));
                newCategory.setProcessPrice(processPriceText);
            }

            int status = db.update("CATEGORY", cv, "NAME = ?", new String[] {selectedCategory.getName()});
            if(status > 0) {
                Toast.makeText(context, R.string.success_editing_category, Toast.LENGTH_SHORT).show();
                selectCategoryTextView.setText("");
                categoryName.setText("");
                commissionPercent.setText("");
                processPrice.setText("");

                categories.set(categories.indexOf(selectedCategory), newCategory);
                setUpCategorySelection();
            } else {
                Toast.makeText(context, R.string.error_category_not_found, Toast.LENGTH_SHORT).show();
            }

            db.close();
            helper.close();

        } catch(SQLException e) {
            Toast.makeText(context, R.string.error_deleting_category, Toast.LENGTH_SHORT).show();
        }

    }

    private void loadCategories() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SQLiteOpenHelper helper = new CategoryDatabase(context);
                    SQLiteDatabase db = helper.getReadableDatabase();
                    Cursor cursor = db.query("CATEGORY", new String[] {"NAME", "COMMISSION_PERCENT", "PROCESS_PRICE"}, null, null, null, null, null);
                    if(cursor.moveToFirst()) {
                        do {
                            Category category = new Category();
                            category.setName(cursor.getString(0));
                            category.setCommissionPercent(cursor.getString(1));
                            category.setProcessPrice(cursor.getString(2));

                            categories.add(category);
                        } while (cursor.moveToNext());
                    }

                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setUpCategorySelection();
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

    private void setUpCategorySelection() {
        ArrayList<String> categoryNames = new ArrayList<>();
        for(Category category : categories) {
            categoryNames.add(category.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, categoryNames);
        builder = new AlertDialog.Builder(context);
        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                selectedCategory = categories.get(which);

                selectCategoryTextView.setText(selectedCategory.getName());
                categoryName.setText(selectedCategory.getName());
                commissionPercent.setText(selectedCategory.getCommissionPercent());
                processPrice.setText(selectedCategory.getProcessPrice());
            }
        });

        selectCategoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();
            }
        });
    }
}