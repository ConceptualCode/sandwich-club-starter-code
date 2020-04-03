package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView name, nameTextView4;
    TextView alsoKnownAs5, alsoKnownAsTextView6;
    TextView originTv7, originTextView8;
    TextView ingredientsTv9, ingredientseTxtView10;
    TextView descriptionTv11, descriptioTtextView12;
    ImageView sandwichImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        sandwichImageView = findViewById(R.id.image_iv);
        name = findViewById(R.id.nameTv);
        nameTextView4 = findViewById(R.id.textView4);
        alsoKnownAs5 = findViewById(R.id.textView5);
        alsoKnownAsTextView6 = findViewById(R.id.textView6);
        originTv7 = findViewById(R.id.textView7);
        originTextView8 = findViewById(R.id.textView8);
        ingredientsTv9 = findViewById(R.id.textView9);
        ingredientseTxtView10 = findViewById(R.id.textView10);
        descriptionTv11 = findViewById(R.id.textView11);
        descriptioTtextView12 = findViewById(R.id.textView12);


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

        loadSandwich(position);
        if (!loadSandwich(position)) {
            closeOnError();
        }


    }

    public boolean loadSandwich(int position) {
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            closeOnError();
        } else
            populateUI(sandwich);
        // Sandwich data unavailable

        return true;

    }

//        Picasso.with(this)
//                .load(sandwich.getImage())
//                .into(ingredientsIv);
//
//        setTitle(sandwich.getMainName());


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    public void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichImageView);
        nameTextView4.setText(sandwich.getMainName());

        String alsoKnownAs = sandwich.getAlsoKnownAs();
        alsoKnownAsTextView6.setVisibility(alsoKnownAs.isEmpty() ? View.GONE : View.VISIBLE);
        alsoKnownAsTextView6.setText(alsoKnownAs);

        String sandwichDescription = sandwich.getDescription();
        descriptioTtextView12.setVisibility(sandwichDescription.isEmpty() ? View.VISIBLE : View.VISIBLE);
        descriptioTtextView12.setText(sandwichDescription);

        String sandwichPlaceOfOrigin = sandwich.getPlaceOfOrigin();
        originTextView8.setVisibility(sandwichPlaceOfOrigin.isEmpty() ? View.GONE : View.VISIBLE);
        originTextView8.setText(sandwichPlaceOfOrigin);

        String sandwichIngredients = sandwich.getIngredients();
        ingredientseTxtView10.setVisibility(sandwichIngredients.isEmpty() ? View.GONE : View.VISIBLE);
        ingredientseTxtView10.setText(sandwichIngredients);
    }
}
