package ir.Rahpo.RahpoHesab.Fragments;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.Rahpo.RahpoHesab.Calculations;
import ir.Rahpo.RahpoHesab.Category;
import ir.Rahpo.RahpoHesab.CategoryDatabase;
import ir.Rahpo.RahpoHesab.R;
import ir.Rahpo.RahpoHesab.Utility.CurrencyFormatter;
import ir.Rahpo.RahpoHesab.Utility.PercentChecker;
import ir.Rahpo.RahpoHesab.ViewHelper;

public class CalculatePriceFragment extends Fragment {
    private static final String TAG = "CalculatePrice";
    private Context context;
    private EditText buyPriceEdit;
    private EditText listPriceEdit;
    private TextView categoryTextView;
    private TextView finalBuyCostTextView;
    private TextView finalListCostTextView;
    private EditText sellPriceEdit;
    private TextView netReceiveTextView;
    private int selectedCategory = -1;
    private ArrayList<Category> categories = new ArrayList<>();
    private CurrencyFormatter currencyFormatter = new CurrencyFormatter();
    private Calculations calc = new Calculations();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calculate_price, container, false);
        context = requireContext();
        buyPriceEdit = v.findViewById(R.id.buy_price_et);
        listPriceEdit = v.findViewById(R.id.list_price_et);
        categoryTextView = v.findViewById(R.id.category_tv);
        finalBuyCostTextView = v.findViewById(R.id.final_buy_cost_tv);
        finalListCostTextView = v.findViewById(R.id.final_list_cost_tv);
        sellPriceEdit = v.findViewById(R.id.sell_price_et);
        netReceiveTextView = v.findViewById(R.id.net_receive_tv);

        ViewHelper.setUpCurrencyText(context, v.findViewById(R.id.buy_price_currency));
        ViewHelper.setUpCurrencyText(context, v.findViewById(R.id.list_price_currency));
        ViewHelper.setUpCurrencyText(context, v.findViewById(R.id.final_buy_cost_currency));
        ViewHelper.setUpCurrencyText(context, v.findViewById(R.id.final_list_cost_currency));
        ViewHelper.setUpCurrencyText(context, v.findViewById(R.id.sell_price_currency));
        ViewHelper.setUpCurrencyText(context, v.findViewById(R.id.net_receive_currency));

        ViewHelper.setThousandSeparatorForEditText(buyPriceEdit);
        ViewHelper.setThousandSeparatorForEditText(listPriceEdit);
        ViewHelper.setThousandSeparatorForEditText(sellPriceEdit);

        loadCategoryNames();

        v.findViewById(R.id.calculate_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCalculate();
            }
        });

        categoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }


    private void loadCategoryNames() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    SQLiteOpenHelper helper = new CategoryDatabase(context);
                    SQLiteDatabase db = helper.getReadableDatabase();
                    Cursor cursor = db.query("CATEGORY", new String[] {"NAME", "COMMISSION_PERCENT", "PROCESS_PRICE"}, null, null, null, null, null);
                    ArrayList<String> categoryNames = new ArrayList<>();
                    if(cursor.moveToFirst()) {
                        do {
                            Category cat = new Category();
                            cat.setName(cursor.getString(0));
                            cat.setCommissionPercent(cursor.getString(1));
                            cat.setProcessPrice(cursor.getString(2));
                            categories.add(cat);

                            categoryNames.add(cursor.getString(0));

                        } while (cursor.moveToNext());
                    }


                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, categoryNames);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    selectedCategory = which;
                                    categoryTextView.setText(categories.get(selectedCategory).getName());
                                }
                            });
                            AlertDialog dialog = builder.create();

                            categoryTextView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.show();
                                }
                            });

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

    private void handleCalculate() {

        String buyPrice = buyPriceEdit.getText().toString();
        String listPrice = listPriceEdit.getText().toString();

        Category category = null;


        if(selectedCategory == -1) {
            Toast.makeText(context, R.string.error_category_not_selected, Toast.LENGTH_SHORT).show();
            return;
        } else {
            category = categories.get(selectedCategory);
        }

        int emptyInputCount = 0;

        if(!buyPrice.isEmpty()) {
            finalBuyCostTextView.setText(calc.getFinalBuyListCost(currencyFormatter.parse(buyPrice), category.getProcessPrice(), category.getCommissionPercent()));
        } else emptyInputCount++;
        if(!listPrice.isEmpty()) {
            finalListCostTextView.setText(calc.getFinalBuyListCost(currencyFormatter.parse(listPrice), category.getProcessPrice(), category.getCommissionPercent()));
        } else emptyInputCount++;

        String sellPrice = sellPriceEdit.getText().toString();
        if(sellPrice.isEmpty()) {
            if(emptyInputCount == 2) Toast.makeText(context, R.string.warn_empty_input_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        netReceiveTextView.setText(calc.getNetReceive(currencyFormatter.parse(sellPrice), category.getCommissionPercent(), category.getProcessPrice()));


    }

}