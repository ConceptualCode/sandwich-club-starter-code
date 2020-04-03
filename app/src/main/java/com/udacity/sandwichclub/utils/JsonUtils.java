package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();


    public static final String NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String IMAGE = "image";
    public static final String DESCRIPTION = "description";
    public static final String ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String INGREDIENTS = "ingredients";
    public static final String PLACE_OF_ORIGIN = "placeOfOrigin";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject sandwichJson = new JSONObject(json);
            sandwich = new Sandwich(
                    sandwichJson.getJSONObject(NAME).getString(MAIN_NAME),
                    getAlsoKnownAs(sandwichJson),
                    sandwichJson.optString(PLACE_OF_ORIGIN),
                    sandwichJson.optString(DESCRIPTION),
                    sandwichJson.optString(IMAGE),
                    getIngredients(sandwichJson)
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    private static List<String> getAlsoKnownAs(JSONObject sandwichJson) throws JSONException {
        JSONArray aliasesJson = sandwichJson.getJSONObject(NAME).getJSONArray(ALSO_KNOWN_AS);
        return jsonArrayToList(aliasesJson);
    }

    private static List<String> getIngredients(JSONObject sandwichJson) throws JSONException {
        JSONArray ingredientsJson = sandwichJson.getJSONArray(INGREDIENTS);
        return jsonArrayToList(ingredientsJson);
    }

    private static List<String> jsonArrayToList(JSONArray array) {
        List<String> jsonList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            jsonList.add(array.optString(i));
        }
        return jsonList;
    }
}