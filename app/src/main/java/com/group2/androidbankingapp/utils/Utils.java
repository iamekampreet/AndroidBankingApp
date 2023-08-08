package com.group2.androidbankingapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group2.androidbankingapp.LoginActivity;
import com.group2.androidbankingapp.MainActivity;
import com.group2.androidbankingapp.api.ApiError;
import com.group2.androidbankingapp.authorization.LoginResponseModel;
import com.group2.androidbankingapp.models.PayeeModel;
import com.group2.androidbankingapp.models.UserModel;
import com.group2.androidbankingapp.paybill.PayBillModel;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Date;

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

    public static void handleUnauthorizedError(Activity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
        Toast.makeText(activity,
                "Error: " + "Token expired. Please login.",
                Toast.LENGTH_LONG).show();
        //clear expired token
        SessionManager sessionManager = new SessionManager(activity.getApplicationContext());
        sessionManager.saveAuthToken(null);
        activity.finish();
    }

    public static ApiError parseError(Response<?> response, Context context) {
        Converter<ResponseBody, ApiError> converter =
                Singleton.getRetrofitInstance(context)
                        .responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ApiError();
        }

        return error;
    }

    public static void dummyLogin() {
        String userString = "{\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NGQwNGQ2MzAxMDJhYWNlMzI2OGE5OTYiLCJlbWFpbCI6InNhbnRvc2guZGhha2FsMDdAZ21haWwuY29tIiwiaWF0IjoxNjkxNTAyNzI5LCJleHAiOjE2OTE1MDYzMjl9.DDlETH1MYGnNabqgo0I4tOiYzX7ffWNm0LLkEFEGThk\",\"user\":{\"_id\":\"64d04d630102aace3268a996\",\"firstName\":\"Santosh\",\"lastName\":\"Dhakal\",\"email\":\"santosh.dhakal07@gmail.com\",\"password\":\"$2a$12$Vkr0Q6h8aR3qy578E4mRFO7x1PUQuOCezB3TI8LjyLYg8TcYy5Y5C\",\"address\":\"1234 Bloor St, Mississauga, ON, Canada\",\"phone\":\"+16478365807\",\"sinNumber\":\"123456789\",\"accountType\":0,\"displayName\":\"Santosh Dhakal\",\"accounts\":[{\"accountNumber\":1,\"accountType\":1,\"status\":0,\"accountBalance\":19000,\"_id\":\"64d04d630102aace3268a997\"},{\"accountNumber\":2,\"accountType\":0,\"status\":0,\"accountBalance\":98545.43,\"_id\":\"64d04d630102aace3268a998\"}],\"cards\":[{\"cardType\":0,\"cardNumber\":\"1111222233334444\",\"expiryDate\":\"2025-02-01T05:00:00.000Z\",\"securityCode\":\"$2a$12$cOCNJwxMiN2HyVHo7SDLke2eYwyUlPFt2lZROaGE4TaxrVz.yFVv2\",\"status\":0,\"_id\":\"64d04d630102aace3268a999\"},{\"cardType\":1,\"cardNumber\":\"0000111122223333\",\"expiryDate\":\"2025-02-01T05:00:00.000Z\",\"securityCode\":\"$2a$12$cOCNJwxMiN2HyVHo7SDLke2eYwyUlPFt2lZROaGE4TaxrVz.yFVv2\",\"maxLimit\":1000,\"accountBalance\":300,\"status\":0,\"_id\":\"64d04d630102aace3268a99a\"}],\"payee\":[{\"payeeId\":\"64d04d630102aace3268a9aa\",\"displayName\":\"CRA(Revenue) Tax Amount Owing\",\"description\":\"CRA tax payments\",\"accountNumber\":10,\"_id\":\"64d04d640102aace3268a9c0\"},{\"payeeId\":\"64d04d630102aace3268a9a5\",\"displayName\":\"Humber College\",\"description\":\"Tuition Payment\",\"accountNumber\":8,\"_id\":\"64d04d640102aace3268a9c1\"},{\"payeeId\":\"64cea15e69bb6d8cf2d015dc\",\"displayName\":\"Bell Internet\",\"description\":\"Bell Internet payments\",\"accountNumber\":12,\"_id\":\"64d04dbe0102aace3268a9cc\"},{\"payeeId\":\"64d04d630102aace3268a9af\",\"displayName\":\"Bell Internet\",\"description\":\"desc\",\"accountNumber\":12,\"_id\":\"64d05e41959725ab72788992\"}],\"__v\":0}}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LoginResponseModel loginResponseModel = objectMapper.readValue(userString, LoginResponseModel.class);
            Singleton.getInstance().user = loginResponseModel.getUser();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void testJackson() {
        String userString = "{\"_id\":\"64d04d630102aace3268a996\",\"firstName\":\"Santosh\",\"lastName\":\"Dhakal\",\"email\":\"santosh.dhakal07@gmail.com\",\"password\":\"$2a$12$Vkr0Q6h8aR3qy578E4mRFO7x1PUQuOCezB3TI8LjyLYg8TcYy5Y5C\",\"address\":\"1234 Bloor St, Mississauga, ON, Canada\",\"phone\":\"+16478365807\",\"sinNumber\":\"123456789\",\"accountType\":0,\"displayName\":\"Santosh Dhakal\",\"accounts\":[{\"accountNumber\":1,\"accountType\":1,\"status\":0,\"accountBalance\":20000,\"_id\":\"64d04d630102aace3268a997\"},{\"accountNumber\":2,\"accountType\":0,\"status\":0,\"accountBalance\":99877.43,\"_id\":\"64d04d630102aace3268a998\"}],\"cards\":[{\"cardType\":0,\"cardNumber\":\"1111222233334444\",\"expiryDate\":\"2025-02-01T05:00:00.000Z\",\"securityCode\":\"$2a$12$cOCNJwxMiN2HyVHo7SDLke2eYwyUlPFt2lZROaGE4TaxrVz.yFVv2\",\"status\":0,\"_id\":\"64d04d630102aace3268a999\"},{\"cardType\":1,\"cardNumber\":\"0000111122223333\",\"expiryDate\":\"2025-02-01T05:00:00.000Z\",\"securityCode\":\"$2a$12$cOCNJwxMiN2HyVHo7SDLke2eYwyUlPFt2lZROaGE4TaxrVz.yFVv2\",\"maxLimit\":1000,\"accountBalance\":300,\"status\":0,\"_id\":\"64d04d630102aace3268a99a\"}],\"payee\":[{\"payeeId\":\"64d04d630102aace3268a9aa\",\"displayName\":\"CRA(Revenue) Tax Amount Owing\",\"description\":\"CRA tax payments\",\"accountNumber\":10,\"_id\":\"64d04d640102aace3268a9c0\"},{\"payeeId\":\"64d04d630102aace3268a9a5\",\"displayName\":\"Humber College\",\"description\":\"Tuition Payment\",\"accountNumber\":8,\"_id\":\"64d04d640102aace3268a9c1\"},{\"payeeId\":\"64cea15e69bb6d8cf2d015dc\",\"displayName\":\"Bell Internet\",\"description\":\"Bell Internet payments\",\"accountNumber\":12,\"_id\":\"64d04dbe0102aace3268a9cc\"}],\"__v\":0}";
        String payeeString = "{\"_id\":\"64cea15e69bb6d8cf2d015da\",\"payeeId\":\"64cea15e69bb6d8cf2d015c5\",\"displayName\":\"Humber College\",\"accountNumber\":\"8\",\"__v\":0}";
        String payBillString = "{\"accountTo\":[{\"_id\":\"64cfc3d04021138fee18e73b\",\"payeeId\":\"64cfc3d04021138fee18e72a\",\"displayName\":\"CRA(Revenue) Tax Amount Owing\",\"accountNumber\":\"10\",\"__v\":0}],\"amount\":19000,\"when\":\"2023-08-06T18:03:45.810Z\"}";

//        Gson gson = new Gson();
        ObjectMapper objectMapper = new ObjectMapper();
        UserModel userModel = null;
        PayeeModel payeeModel = null;
        PayBillModel payBillModel1 = null;

        try {
            userModel = objectMapper.readValue(userString, UserModel.class);
            payeeModel = objectMapper.readValue(payeeString, PayeeModel.class);
            payBillModel1 = objectMapper.readValue(payBillString, PayBillModel.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Log.d("MainActivity", payeeModel.toString());

        PayBillModel payBillModel = new PayBillModel();
        payBillModel.setAccountFrom(userModel.getAccounts().get(1));
        payBillModel.setAccountTo(userModel.getPayee().get(0));
        payBillModel.setAmount(123.0);
        payBillModel.setWhen(new Date());
        payBillModel.setFrequency(0);
        try {
            String payBillModelString = objectMapper.writeValueAsString(payBillModel);
            Log.d(MainActivity.class.getSimpleName(), payBillModelString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Singleton.getInstance().user = userModel;
    }
}
