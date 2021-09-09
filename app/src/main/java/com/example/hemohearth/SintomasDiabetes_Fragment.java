package com.example.hemohearth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SintomasDiabetes_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SintomasDiabetes_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CheckBox[] checkBoxes;

    public SintomasDiabetes_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SintomasDiabetes.
     */
    // TODO: Rename and change types and number of parameters
    public static SintomasDiabetes_Fragment newInstance(String param1, String param2) {
        SintomasDiabetes_Fragment fragment = new SintomasDiabetes_Fragment();
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

        View view = inflater.inflate(R.layout.fragment_sintomas_diabetes, container, false);

        this.checkBoxes = new CheckBox[5];

        this.checkBoxes[0] = (CheckBox)view.findViewById(R.id.checkBox1);
        this.checkBoxes[1] = (CheckBox)view.findViewById(R.id.checkBox2);
        this.checkBoxes[2] = (CheckBox)view.findViewById(R.id.checkBox3);
        this.checkBoxes[3] = (CheckBox)view.findViewById(R.id.checkBox4);
        this.checkBoxes[4] = (CheckBox)view.findViewById(R.id.checkBox5);

        if(datosR != null){
            if(datosR.getBooleanArray("checked") != null){
                boolean[] checked = datosR.getBooleanArray("checked");
                for(int i = 0; i < checked.length; i++){
                    this.checkBoxes[i].setChecked(checked[i]);
                }
            }
        }

        ImageView continueBt = (ImageView)view.findViewById(R.id.btnContinuarSin);
        continueBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean[] checked = new boolean[5];

                for(int i = 0; i < checkBoxes.length; i++){
                    checked[i] = checkBoxes[i].isChecked();
                }


                datosR.putBooleanArray("checked" ,checked);

                Fragment glucemia = new Glucemia_Fragment();
                glucemia.setArguments(datosR);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentContainerView2, glucemia, null);
                transaction.commit();
            }
        });

        ImageView goBack = (ImageView)getActivity().findViewById(R.id.imageViewGoBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean[] checked = new boolean[5];

                for(int i = 0; i < checkBoxes.length; i++){
                    checked[i] = checkBoxes[i].isChecked();
                }

                datosR.putBooleanArray("checked", checked);

                Fragment diabetes = new Diabetes_Fragment();
                diabetes.setArguments(datosR);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentContainerView2, diabetes, null);
                transaction.commit();
            }
        });


        return view;
    }
}