package ar.com.yoaprendo;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.appcompat.app.AppCompatActivity;
import ar.com.yoaprendo.activities.SignInActivity;
import ar.com.yoaprendo.beans.Ubicacion;
import ar.com.yoaprendo.placepicker.ConfirmAlert;
import ar.com.yoaprendo.placepicker.TransparentProgressDialog;

public class Utils {

    public static void loadScreen(TransparentProgressDialog tp){

        if (tp.isShowing())
            tp.dismiss();
        //mProgressDialog.setTitle(getResources().getString(R.string.title_progress_dialog));
        tp.setCancelable(false);
        tp.show();

    }

    public static void disableLoadScreen(TransparentProgressDialog tp){

        if (tp.isShowing())
            tp.dismiss();

    }

    public static double distanciaEnMetros(LatLng startLatLng,LatLng endLatLng){
        return SphericalUtil.computeDistanceBetween(startLatLng, endLatLng);
    }

    public static String hash(String text) {

        try{

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(text.getBytes(StandardCharsets.UTF_8));

            byte[] digest = md.digest();

            return String.format("%064x", new BigInteger(1, digest));

        }catch(Exception e){

            return text;

        }

    }

    public static void popup(String msg, AppCompatActivity context){

        FragmentTransaction ft = context.getFragmentManager().beginTransaction();
        Fragment prev = context.getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment dialogFragment = new ConfirmAlert();

        Bundle args = new Bundle();
        args.putString("msg", msg);
        dialogFragment.setArguments(args);
        dialogFragment.show(ft, "dialog");

    }

    public static float calcularDistancia(Ubicacion ubicacionFrom, Ubicacion ubicacionTo){

        Location locationA = new Location("point A");

        locationA.setLatitude(ubicacionFrom.latitud);
        locationA.setLongitude(ubicacionFrom.longitud);

        Location locationB = new Location("point B");

        locationB.setLatitude(ubicacionTo.latitud);
        locationB.setLongitude(ubicacionTo.longitud);

        return locationA.distanceTo(locationB);

    }

}
