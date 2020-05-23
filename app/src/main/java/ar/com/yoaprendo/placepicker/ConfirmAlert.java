package ar.com.yoaprendo.placepicker;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ar.com.yoaprendo.R;
import ar.com.yoaprendo.activities.PlacePickerActivity;

public class ConfirmAlert extends DialogFragment implements View.OnClickListener {

        Button aceptarBoton;
        String msg;

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            msg = getArguments().getString("msg");

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.message_alert, container, false);

            aceptarBoton = (Button) v.findViewById(R.id.btnAceptarAlert);
            TextView mensaje = (TextView) v.findViewById(R.id.messageAlertText);
            mensaje.setText(msg);

            aceptarBoton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });


            getDialog().setCanceledOnTouchOutside(true);

            return v;

        }

        @Override
        public void onCancel(DialogInterface dialog) {
            super.onCancel(dialog);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            dismiss();
        }

        @Override
        public void onClick(View v) {

        }


}
