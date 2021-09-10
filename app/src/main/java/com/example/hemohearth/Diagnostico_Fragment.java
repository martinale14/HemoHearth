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
 * Use the {@link Diagnostico_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Diagnostico_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean canGo;
    Spinner spinnerSexo;
    Spinner spinnerEdad;
    private EditText[] textInputs;
    private EditText etEdad;
    private EditText etHemo;
    private ImageView btnEnviar;
    private View.OnClickListener disabled;

    public Diagnostico_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Diagnostico_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Diagnostico_Fragment newInstance(String param1, String param2) {
        Diagnostico_Fragment fragment = new Diagnostico_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_diagnostico, container, false);

        this.spinnerSexo = (Spinner) view.findViewById(R.id.spinnerSexo);
        ArrayAdapter<CharSequence> sexoAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.sexo, R.layout.spinner_item);
        sexoAdapter.setDropDownViewResource(R.layout.spinner_item);
        this.spinnerSexo.setAdapter(sexoAdapter);
        
        this.spinnerEdad = (Spinner) view.findViewById(R.id.spinnerEdad);
        ArrayAdapter<CharSequence> edadAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.edad, R.layout.spinner_item);
        edadAdapter.setDropDownViewResource(R.layout.spinner_item);
        this.spinnerEdad.setAdapter(edadAdapter);

        this.disabled = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDisable();
            }
        };

        this.btnEnviar = (ImageView)view.findViewById(R.id.btnEnviar);
        this.btnEnviar.setOnClickListener(disabled);

        this.etEdad = (EditText)view.findViewById(R.id.editTextTextEdad);
        this.etHemo = (EditText)view.findViewById(R.id.editTextTextHemo);

        Bundle datosR = getArguments();

        this.textInputs = new EditText[2];

        this.textInputs[0] = etEdad;
        this.textInputs[1] = etHemo;

        if(datosR != null){
            this.etEdad.setText(datosR.getString("edad"));
            this.etHemo.setText(datosR.getString("hemo"));
            this.spinnerSexo.setSelection(datosR.getInt("sexoId"));
            this.spinnerEdad.setSelection(datosR.getInt("edadId"));

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

        spinnerSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkInputs();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerEdad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkInputs();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ImageView goBack = (ImageView)getActivity().findViewById(R.id.imageViewGoBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle datos;

                if(getArguments() != null){
                    datos = getArguments();
                }else{
                    datos = new Bundle();
                }

                datos.putString("edad", etEdad.getText().toString());
                datos.putString("hemo", etHemo.getText().toString());
                datos.putInt("sexoId", spinnerSexo.getSelectedItemPosition());
                datos.putInt("edadId", spinnerEdad.getSelectedItemPosition());

                Fragment anemia = new Anemia_Fragment();
                anemia.setArguments(datosR);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentContainerView2, anemia, null);
                transaction.commit();
            }
        });

        return view;
    }

    public void checkInputs(){

        View.OnClickListener active = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle datos = new Bundle();

                datos.putString("edad", etEdad.getText().toString());
                datos.putString("hemo", etHemo.getText().toString());
                datos.putInt("sexoId", spinnerSexo.getSelectedItemPosition());
                datos.putInt("edadId", spinnerEdad.getSelectedItemPosition());

                Bundle datosR = getArguments();

                if(datosR != null){
                    if(datosR.getBooleanArray("checked") != null){
                        datos.putBooleanArray("checked", datosR.getBooleanArray("checked"));
                    }
                }

                Fragment sintomas = new SintomasDiabetes_Fragment();
                sintomas.setArguments(datos);

                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentContainerView2, sintomas, null);
                transaction.commit();
            }
        };

        this.canGo = true;

        if(this.spinnerSexo.getSelectedItem().toString().equalsIgnoreCase("")){
            this.canGo = false;
        }
        if(this.spinnerEdad.getSelectedItem().toString().equalsIgnoreCase("")){
            this.canGo = false;
        }
        for(int j = 0; j < this.textInputs.length; j++){
            if(this.textInputs[j].getText().length() <= 0){
                this.canGo = false;
            }
        }

        if(this.canGo){
            this.btnEnviar.setImageResource(R.drawable.ic_btn_send_active);
            this.btnEnviar.setOnClickListener(active);
        }else{
            this.btnEnviar.setImageResource(R.drawable.ic_btn_send_disable);
            this.btnEnviar.setOnClickListener(this.disabled);
        }
    }

    public void onClickDisable(){
        Toast toast = Toast.makeText(this.getActivity(), "Por favor complete todos los campos", Toast.LENGTH_SHORT);
        toast.show();
    }
}