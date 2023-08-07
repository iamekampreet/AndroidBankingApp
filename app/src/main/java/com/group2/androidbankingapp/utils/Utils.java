package com.group2.androidbankingapp.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.group2.androidbankingapp.api.ApiError;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class Utils {

    public static String getAccounttype(int accountType) {
        String type = "";
        if (accountType == 0) {
            type = "SAVING";
        } else if (accountType == 1) {
            type = "CHECKING";
        }
        return type;
    }

    public static String getFrequencyString(int frequencyInt) throws Exception {
        switch (frequencyInt) {
            case 0:
                return "Once";
            case 1:
                return "Weekly";
            case 2:
                return "Monthly";
        }
        throw new Exception("Unknown frequency number " + frequencyInt);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static ApiError parseError(Response<?> response) {
        Converter<ResponseBody, ApiError> converter =
                Singleton.getRetrofitInstance()
                        .responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ApiError();
        }

        return error;
    }
}
