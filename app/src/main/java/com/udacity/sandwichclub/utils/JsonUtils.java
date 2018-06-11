package com.udacity.sandwichclub.utils;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwich_details_data = new JSONObject(json);
            JSONObject name = sandwich_details_data.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            List<String> otherNamesList = new ArrayList<>();
            for (int k = 0; k < alsoKnownAs.length(); k++) {
                otherNamesList.add(alsoKnownAs.getString(k));
            }
            String placeOfOrigin = sandwich_details_data.getString("placeOfOrigin");
            String description = sandwich_details_data.getString("description");
            String image = sandwich_details_data.getString("image");
            JSONArray ingredients = sandwich_details_data.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for (int k = 0; k < ingredients.length(); k++) {
                ingredientsList.add(ingredients.getString(k));
            }
            Sandwich sandwich = new Sandwich(mainName, otherNamesList, placeOfOrigin, description, image, ingredientsList);
            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
