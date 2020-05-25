package ar.com.yoaprendo;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog alertDialog;

    public LoadingDialog(Activity activity){
        this.activity=activity;
    }

    public void startLoadingDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.loadingdialog,null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();

    }

    public void dismissDialog(){

        alertDialog.dismiss();

    }


}
