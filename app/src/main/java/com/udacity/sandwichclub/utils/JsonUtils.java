package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String NAME_KEY = "name";
    private static final String MAIN_NAME_KEY = "mainName";
    private static final String ALSO_KNOWN_AS_KEY = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    private static final String DESCRIPTION_KEY = "description";
    private static final String IMAGE_URL_KEY = "image";
    private static final String INGREDIENTS_KEY = "ingredients";
    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        JSONObject jsonObject = getJsonObject(json);
        String mainName = null;
        List<String> alsoKnownAs = null;
        List<String> ingredients = null;
        String placeOfOrigin = null;
        String description = null;
        String imageUrl = null;

        if(jsonObject != null){
            JSONObject name = getJsonObject(jsonObject, NAME_KEY);
            if(name != null){
                mainName = getJsonString(name, MAIN_NAME_KEY);
                alsoKnownAs = getJsonArray(name, ALSO_KNOWN_AS_KEY);
                Log.e(TAG, "************************** " + mainName + " ************ ");
            }else{
                mainName = "Name";
                alsoKnownAs = new ArrayList<String>();
            }

            placeOfOrigin = getJsonString(jsonObject, PLACE_OF_ORIGIN_KEY);
            description = getJsonString(jsonObject, DESCRIPTION_KEY);
            imageUrl = getJsonString(jsonObject, IMAGE_URL_KEY);
            ingredients = getJsonArray(jsonObject, INGREDIENTS_KEY);
            Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description,
                    imageUrl, ingredients);
            return sandwich;
        }else return null;

    }

    /**
     * This helper method returns a json object
     * @param json the json string containing the json object needed to parse
     * @return
     */
    private static JSONObject getJsonObject(String json){
        try{
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONObject getJsonObject(JSONObject object, String key){
        try{
            JSONObject jsonObject = object.getJSONObject(key);
            return jsonObject;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This helper method returns an ArrayList depending on the argument passed in
     * @param json the json object to parse
     * @param key the json key
     * @return
     */
    private static ArrayList getJsonArray(JSONObject json, String key){
        ArrayList<String> list = new ArrayList<>();
        try{
            JSONArray jsonArray = json.getJSONArray(key);
            for(int i = 0; i < jsonArray.length(); i++){
                list.add((String) jsonArray.get(i));
            }
            return list;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    /**
     * Takes in a jsonObject and searchrd the key then returns a string
     * @param json the jsonObject
     * @param key the key to search for
     * @return
     */
    private static String getJsonString(JSONObject json, String key){
        try{
            String string = json.getString(key);
            return string + ".";
        }catch (JSONException e) {
            e.printStackTrace();
            //Log.e("JsonUtils", e.p)
        }
        return "------------";
    }
}
