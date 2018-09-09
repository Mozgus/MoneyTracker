package com.berryjam.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class AddActivity extends AppCompatActivity {
    public static final String KEY_TYPE = "type";
    public static final String KEY_ITEM = "item";
    private EditText nameInput;
    private EditText priceInput;
    private Button addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameInput = findViewById(R.id.name);
        priceInput = findViewById(R.id.price);
        addBtn = findViewById(R.id.addBtn);

        nameInput.addTextChangedListener(watcher);
        priceInput.addTextChangedListener(watcher);

        final String type = Objects.requireNonNull(getIntent().getExtras()).getString(KEY_TYPE);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = nameInput.getText().toString();
                int itemPrice = Integer.parseInt(priceInput.getText().toString());

                Intent intent = new Intent();
                intent.putExtra(KEY_ITEM, new Item(itemName, itemPrice, type));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            addBtn.setEnabled(!TextUtils.isEmpty(nameInput.getText().toString().trim())
                    && !TextUtils.isEmpty(priceInput.getText()));
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

    };

}
