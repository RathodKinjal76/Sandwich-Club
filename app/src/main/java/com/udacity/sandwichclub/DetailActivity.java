package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    Sandwich sandwich;

    @BindView(R.id.image_iv)
    ImageView iv_Image;
    @BindView(R.id.mainName_tv)
    TextView tv_Main_name;
    @BindView(R.id.description_tv)
    TextView tv_Description;
    @BindView(R.id.origin_tv)
    TextView tv_Place_of_origin;
    @BindView(R.id.also_known_tv)
    TextView tv_Also_known_as;
    @BindView(R.id.ingredients_tv)
    TextView tv_Ingredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.camera)
                .into(iv_Image);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        if (sandwich.getMainName() != null) {
            tv_Main_name.setText(sandwich.getMainName());
        } else {
            tv_Main_name.setText(R.string.no_data_available);
        }
        if (sandwich.getDescription() != null) {
            tv_Description.setText(sandwich.getDescription());
        } else {
            tv_Description.setText(R.string.no_data_available);
        }
        List<String> also_known_as = sandwich.getAlsoKnownAs();
        if (also_known_as.size() > 0) {
            for (String string : also_known_as) {
                tv_Also_known_as.append(string + "\n");
            }
        } else {
            tv_Also_known_as.setText(R.string.no_data_available);
        }
        if (sandwich.getPlaceOfOrigin() != null) {
            tv_Place_of_origin.setText(sandwich.getPlaceOfOrigin());
        } else {
            tv_Place_of_origin.setText(R.string.no_data_available);
        }
        List<String> ingredients = sandwich.getIngredients();
        if (ingredients.size() > 0) {
            for (String string : ingredients) {
                tv_Ingredients.append(string + "\n");
            }
        } else {
            tv_Ingredients.setText(R.string.no_data_available);
        }

    }

}