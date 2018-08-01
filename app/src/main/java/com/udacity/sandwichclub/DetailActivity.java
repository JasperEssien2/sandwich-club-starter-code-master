package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private final String TAG = DetailActivity.class.getSimpleName();
    private ActivityDetailBinding mActivityDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail);

        setUpDetailActivity();

    }

    private void setUpDetailActivity() {
        showProgressBar();
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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        loadImage(sandwich);
        setTitle(sandwich.getMainName());
        Log.e(TAG, "************************** " + sandwich.getMainName() + " ************ ");
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        mActivityDetailBinding.alsoKnownTv.setText(prettyPrintList(sandwich.getAlsoKnownAs()));
        mActivityDetailBinding.ingredientsTv.setText(prettyPrintList(sandwich.getIngredients()));
        mActivityDetailBinding.descriptionTv.setText(sandwich.getDescription());
        mActivityDetailBinding.originTv.setText(sandwich.getPlaceOfOrigin());
    }

    private void loadImage(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.image_url_broken)
                .into(mActivityDetailBinding.imageIv, new Callback() {
                    @Override
                    public void onSuccess() {
                        hideProgressBar();
                    }

                    @Override
                    public void onError() {
                        hideProgressBar();
                    }
                });
    }

    private void hideProgressBar() {
        mActivityDetailBinding.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        mActivityDetailBinding.imageLoadingProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * This methods takes in a list of string and pretty prints it
     *
     * @param stringList the list of string to pretty print
     * @return
     */
    private String prettyPrintList(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        if (stringList.isEmpty()) {
            return " ------------ ";
        }
        int count = 0;
        for (String text : stringList) {
            String txt = null;
            if (count != stringList.size() - 1)
                txt = text + ", \n";
            else txt = text + ". \n";
            stringBuilder.append(txt);
            count++;
        }
        return stringBuilder.toString();
    }
}
