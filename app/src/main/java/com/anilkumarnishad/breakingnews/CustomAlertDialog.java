package com.anilkumarnishad.breakingnews;

import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE;

public class CustomAlertDialog {

    private Context context;
    private SweetAlertDialog alertDialog;
    boolean isScreenOpen;

    public CustomAlertDialog(Context context, boolean isScreenOpen) {
        try {
            this.context = context;
            this.isScreenOpen = isScreenOpen;
            alertDialog = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                    TextView text = (TextView) alertDialog.findViewById(R.id.content_text);
                    text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    text.setSingleLine(false);
                }
            });
       /* TextView text = (TextView) alertDialog.findViewById(R.id.content_text);
        text.setGravity(Gravity.CENTER);
        //text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text.setSingleLine(false);
        text.setMaxLines(10);
        text.setLines(6);*/
        } catch (IllegalStateException ise) {

        } catch (Exception e) {

        }

    }


    public void Error(final String message) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(ERROR_TYPE);
                alertDialog.setContentText(message);
                // alertDialog.setCustomImage(R.drawable.ic_error_red_24dp);
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

}
