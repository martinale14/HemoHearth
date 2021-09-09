package com.example.hemohearth;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Glucemia_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Glucemia_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView btnEnviar;
    private EditText glucemiaTb;
    private View.OnClickListener sendEnabled;
    private View.OnClickListener sendDisabled;

    public Glucemia_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Glucemia_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Glucemia_Fragment newInstance(String param1, String param2) {
        Glucemia_Fragment fragment = new Glucemia_Fragment();
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
        // Inflate the layout for this fragment
        Bundle datosR = getArguments();

        View view = inflater.inflate(R.layout.fragment_glucemia, container, false);

        ImageView goBack = (ImageView)getActivity().findViewById(R.id.imageViewGoBack);
        this.glucemiaTb = (EditText)view.findViewById(R.id.editTextTextGlucemia);
        this.btnEnviar = (ImageView)view.findViewById(R.id.btnEnviar);

        if(datosR != null){
            if(datosR.getString("glucemia") != null){
                glucemiaTb.setText(datosR.getString("glucemia"));
                this.checkInput();
            }
        }

        this.sendDisabled = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getActivity(), "Por favor llene los resultados del exámen", Toast.LENGTH_SHORT);
                toast.show();
            }
        };

        this.sendEnabled = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double glucemia = Double.parseDouble(glucemiaTb.getText().toString());
                int level = 0;

                if(glucemia > 7 && glucemia < 13.8){
                    level = 1;
                }else if(glucemia >= 13.8 && glucemia <= 33) {
                    level = 2;
                }else if(glucemia > 33){
                    level = 3;
                }

                Bundle datos = new Bundle();

                datos.putInt("level", level);
                datos.putDouble("glucemia", glucemia);

                DialogFragment dialog = new Diabetes_Alert_Fragment();
                dialog.setArguments(datos);
                dialog.show(getActivity().getSupportFragmentManager(), "dialog");
            }
        };

        this.btnEnviar.setOnClickListener(this.sendDisabled);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datosR.putString("glucemia", glucemiaTb.getText().toString());

                Fragment sintomas = new SintomasDiabetes_Fragment();
                sintomas.setArguments(datosR);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentContainerView2, sintomas, null);
                transaction.commit();

            }
        });

        glucemiaTb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkInput();
            }
        });


        return view;
    }

    public void checkInput(){
        if(this.glucemiaTb.getText().toString().equalsIgnoreCase("")){
            this.btnEnviar.setImageResource(R.drawable.ic_btn_send_disable);
            this.btnEnviar.setOnClickListener(this.sendDisabled);
        }else{
            this.btnEnviar.setImageResource(R.drawable.ic_btn_send_active);
            this.btnEnviar.setOnClickListener(this.sendEnabled);
        }
    }
}