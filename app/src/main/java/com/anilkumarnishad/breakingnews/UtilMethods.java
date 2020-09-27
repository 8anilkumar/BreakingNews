package com.anilkumarnishad.breakingnews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import com.google.android.gms.common.internal.Constants;
import com.google.gson.Gson;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.ocpsoft.prettytime.PrettyTime;

import java.security.AccessControlContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilMethods {

    public static void ChangeFragmemt(Fragment fragment, FragmentManager fm, Boolean addtobackstag) {
        FragmentTransaction tr = fm.beginTransaction();
        tr.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        if (addtobackstag) {
            tr.replace(R.id.frame, fragment).addToBackStack("a").commit();
        } else {
            tr.replace(R.id.frame, fragment).commit();
        }
    }


    public static ColorDrawable[] vibrantLightColorList =
    {
        new ColorDrawable(Color.parseColor("#ffeead")),
                new ColorDrawable(Color.parseColor("#93cfb3")),
                new ColorDrawable(Color.parseColor("#fd7a7a")),
                new ColorDrawable(Color.parseColor("#faca5f")),
                new ColorDrawable(Color.parseColor("#1ba798")),
                new ColorDrawable(Color.parseColor("#6aa9ae")),
                new ColorDrawable(Color.parseColor("#ffbf27")),
                new ColorDrawable(Color.parseColor("#d93947"))
    };

    public static ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

    public static String DateToTimeFormat(String oldstringDate){
        PrettyTime p = new PrettyTime(new Locale(getCountry()));
        String isTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
            Date date = sdf.parse(oldstringDate);
            isTime = p.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isTime;
    }

    public static String DateFormat(String oldstringDate){
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", new Locale(getCountry()));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldstringDate);
            newDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldstringDate;
        }
        return newDate;
    }

    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(Locale.getDefault().getDisplayLanguage());
        return country.toLowerCase();
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


    public static void Error(Context context, String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context, true);
        customAlertDialog.Error(message);
    }




}
