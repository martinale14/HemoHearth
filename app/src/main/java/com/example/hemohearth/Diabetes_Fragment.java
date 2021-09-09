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
 * Use the {@link Diabetes_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  Diabetes_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean canGo;
    Spinner spinner;
    private EditText[] textInputs;
    private EditText etName;
    private EditText etLastName;
    private EditText etID;
    private ImageView btnContinuar;
    private View.OnClickListener disabled;

    public Diabetes_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment diabetesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Diabetes_Fragment newInstance(String param1, String param2) {
        Diabetes_Fragment fragment = new Diabetes_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_diabetes, container, false);
        this.spinner = (Spinner) view.findViewById(R.id.spinnerEPS);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.opciones, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        this.spinner.setAdapter(adapter);

        ImageView goBack = (ImageView)getActivity().findViewById(R.id.imageViewGoBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Home.class);
                startActivity(intent);
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
        this.etID = (EditText)view.findViewById(R.id.editTextTextId);

        Bundle datosR = getArguments();

        this.textInputs = new EditText[3];

        this.textInputs[0] = etName;
        this.textInputs[1] = etLastName;
        this.textInputs[2] = etID;

        if(datosR != null){
            this.etName.setText(datosR.getString("name"));
            this.etLastName.setText(datosR.getString("lastName"));
            this.etID.setText(datosR.getString("id"));
            this.spinner.setSelection(datosR.getInt("epsId"));

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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkInputs();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    public void checkInputs(){

        View.OnClickListener active = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle datos = new Bundle();

                datos.putString("name", etName.getText().toString());
                datos.putString("lastName", etLastName.getText().toString());
                datos.putString("id", etID.getText().toString());
                datos.putString("eps", spinner.getSelectedItem().toString());
                datos.putInt("epsId", spinner.getSelectedItemPosition());

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

        if(this.spinner.getSelectedItem().toString().equalsIgnoreCase("")){
            this.canGo = false;
        }
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