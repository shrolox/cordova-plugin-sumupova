package cordovapluginsumupova;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sumup.merchant.Models.TransactionInfo;
import com.sumup.merchant.api.SumUpAPI;
import com.sumup.merchant.api.SumUpState;
import com.sumup.merchant.api.SumUpLogin;
import com.sumup.merchant.api.SumUpPayment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import java.math.BigDecimal;

public class Sumupova extends CordovaPlugin {

    String key = "";
    CallbackContext paymentCallbackContext = null;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("initPlugin")) {
            this.key = args.getString(0);
            this.cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    SumUpState.init(cordova.getActivity());
                }
            });
            return true;
        }
        else if (action.equals("login")) {
            this.login(this.key, callbackContext);
        }
        else if (action.equals("paySumup")) {
            // params:[price, title, currencyCode]
            this.pay(this.key, args, callbackContext);
            return true;
        }
        return false;
    }

    private void login(String key, CallbackContext callbackContext) {
        SumUpLogin sumupLogin = SumUpLogin.builder(key).build();
        SumUpAPI.openLoginActivity(cordova.getActivity(), sumupLogin, 1);
    }

    private void pay(String key, JSONArray args, CallbackContext callbackContext) {
        this.paymentCallbackContext = callbackContext;
        try {
            this.cordova.setActivityResultCallback(this);
            SumUpPayment payment = SumUpPayment.builder()
                    // mandatory parameters
                    // Please go to https://me.sumup.com/developers to retrieve your Affiliate Key by entering the application ID of your app. (e.g. com.sumup.sdksampleapp)
                    .total(new BigDecimal(args.get(0).toString()))
                    .currency(SumUpPayment.Currency.valueOf(args.get(2).toString()))
                    .title(args.get(1).toString())
                    // optional: skip the success screen
                    //.skipSuccessScreen()
                    .build();
            SumUpAPI.checkout(cordova.getActivity(), payment, 2);
        }
        catch (JSONException e) {
            callbackContext.error("Unknown error");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("PAYMENT CALLBACK", "0");
        if (requestCode == 2 && data != null && this.paymentCallbackContext != null) {
            // Handle the response here
            Log.i("PAYMENT CALLBACK", "1");
            if (resultCode == 1) {
                this.paymentCallbackContext.success();
            }
            else if (resultCode == 2) {
                this.paymentCallbackContext.error("Transaction failed");
            }
            else if (resultCode == 3) {
                this.paymentCallbackContext.error("Geolocation must be enabled on your phone");
            }
            else {
                this.paymentCallbackContext.error("Unknown error, code : " + resultCode);
            }
        }
    }

}
