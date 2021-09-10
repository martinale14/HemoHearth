package com.example.hemohearth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Anemia_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Anemia_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean canGo;
    private EditText[] textInputs;
    private EditText etName;
    private EditText etLastName;
    private EditText etEmail;
    private ImageView btnContinuar;
    private View.OnClickListener disabled;

    public Anemia_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Anemia_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Anemia_Fragment newInstance(String param1, String param2) {
        Anemia_Fragment fragment = new Anemia_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_anemia_, container, false);

        ImageView goBack = (ImageView)getActivity().findViewById(R.id.imageViewGoBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        this.disabled = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDisable();
            }
        };

        this.btnContinuar = (ImageView)view.findViewById(R.id.btnContinuar);
        this.btnContinuar.setOnClickListener(disabled);

        this.etName = (EditText)view.findViewById(R.id.editTextTextName);
        this.etLastName = (EditText)view.findViewById(R.id.editTextTextLastName);
        this.etEmail = (EditText)view.findViewById(R.id.editTextTextEmail);

        Bundle datosR = getArguments();

        this.textInputs = new EditText[3];

        this.textInputs[0] = etName;
        this.textInputs[1] = etLastName;
        this.textInputs[2] = etEmail;

        if(datosR != null){
            this.etName.setText(datosR.getString("name"));
            this.etLastName.setText(datosR.getString("lastName"));
            this.etEmail.setText(datosR.getString("email"));

            this.checkInputs();
        }

        for(int i = 0; i < this.textInputs.length; i++){
            this.textInputs[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    checkInputs();
                }
            });

        }

        return view;
    }

    public void checkInputs(){

        View.OnClickListener active = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle datos;

                if(getArguments() != null){
                    datos = getArguments();
                }else{
                    datos = new Bundle();
                }

                datos.putString("name", etName.getText().toString());
                datos.putString("lastName", etLastName.getText().toString());
                datos.putString("email", etEmail.getText().toString());

                Fragment diagnostico = new Diagnostico_Fragment();
                diagnostico.setArguments(datos);

                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentContainerView2, diagnostico, null);
                transaction.commit();
            }
        };

        this.canGo = true;

        for(int j = 0; j < this.textInputs.length; j++){
            if(this.textInputs[j].getText().length() <= 0){
                this.canGo = false;
            }
        }

        if(this.canGo){
            this.btnContinuar.setImageResource(R.drawable.ic_btncontinuaractive);
            this.btnContinuar.setOnClickListener(active);
        }else{
            this.btnContinuar.setImageResource(R.drawable.ic_btncontinuardisable);
            this.btnContinuar.setOnClickListener(this.disabled);
        }
    }

    public void onClickDisable(){
        Toast toast = Toast.makeText(this.getActivity(), "Por favor complete todos los campos", Toast.LENGTH_SHORT);
        toast.show();
    }
}