package com.example.hemohearth;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.text.LineBreaker;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Diabetes_Alert_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Diabetes_Alert_Fragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Diabetes_Alert_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Diabetes_Alert_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Diabetes_Alert_Fragment newInstance(String param1, String param2) {
        Diabetes_Alert_Fragment fragment = new Diabetes_Alert_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_diabetes, container, false);
        // Set transparent background and no title

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        Bundle datosR = getArguments();

        int level = datosR.getInt("level");
        String title = "Diabetes identificada";
        String msg = "";

        switch (level){

            case 0:
                title = "El paciente no padece de diabetes";
                break;
            case 1:
                msg = "Indicar glucemia en ayunas y TGP en pacientes sin diagnóstico. - Si " +
                        "deshidratación, rehidratación oral o EV según las demandas. - " +
                        "Reevaluar conducta terapéutica en diabéticos y cumplimiento de los " +
                        "pilares. - Reevaluar dosis de hipoglucemiantes.";
                break;
            case 2:
                msg = "Coordinar traslado y comenzar tratamiento. - Hidratación con Solución " +
                        "salina 40 ml/Kg en las primeras 4 horas. 1-2 L la primera hora. - " +
                        "Administrar potasio al restituirse la diuresis o signos de " +
                        "hipopotasemia (depresión del ST, Onda U ≤ 1mv, ondas U≤ T). - Evitar " +
                        "insulina hasta desaparecer signos de hipopotasemia. - Administrar " +
                        "insulina simple 0,1 U/kg EV después de hidratar";
                break;
            case 3:
                msg = "Coordinar traslado y comenzar tratamiento. - Hidratación con Solución " +
                        "Salina 10-15 ml/Kg/h hasta conseguir estabilidad hemodinámica. - " +
                        "Administrar potasio al restituirse la diuresis o signos de " +
                        "hipopotasemia (depresión del ST, Onda U ≤ 1mv, ondas U≤ T).";
                break;

        }

        TextView titleTv = (TextView)view.findViewById(R.id.textViewTittle);
        TextView messageTv = (TextView)view.findViewById(R.id.textViewMessage);
        TextView aceptarBtn = (TextView)view.findViewById(R.id.btnAceptar);
        titleTv.setText(title);
        messageTv.setText(msg);

        aceptarBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return view;
    }

}