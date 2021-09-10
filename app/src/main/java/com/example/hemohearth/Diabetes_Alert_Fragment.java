package com.example.hemohearth;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
            case 4:
                title = "El paciente tiene anemia";
                break;
            case 5:
                title = "El paciente no tiene anemia";
                break;

        }

        TextView titleTv = (TextView)view.findViewById(R.id.textViewTittle);
        TextView messageTv = (TextView)view.findViewById(R.id.textViewMessage);
        TextView aceptarBtn = (TextView)view.findViewById(R.id.btnAceptar);
        titleTv.setText(title);
        messageTv.setText(msg);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "pacientes", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();

        registro.put("nombre", datosR.getString("name"));
        registro.put("apellido", datosR.getString("lastName"));

        if(level >= 4){

            registro.put("correo", datosR.getString("email"));
            registro.put("sexo", datosR.getInt("sexoId") == 1 ? "Hombre" : "Mujer");
            registro.put("edad", datosR.getDouble("edad") + " " + (datosR.getInt("edadId") == 1 ? "meses" : "años"));
            registro.put("hemoglobina", datosR.getDouble("hemo"));
            registro.put("tieneAnemia", level == 4 ? 1 : 0);

            String toastMsg = "";

            try {
                System.out.println("LLegue aqui");
                bd.insertOrThrow("pacientesAnemia", null, registro);
                toastMsg = "Paciente Guardado Correctamente";

            }catch (Exception exception){
                System.out.println(exception);
                bd.update("pacientesAnemia", registro, "correo='" + datosR.getString("email") + "'", null);
                toastMsg = "Paciente Actualizado con Exito";
            }

            Cursor row = bd.rawQuery("SELECT * FROM pacientesAnemia WHERE correo = '" + datosR.getString("email") + "'", null);

            if(row.moveToFirst()) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                Map<String, Object> fireData = new HashMap<>();
                fireData.put("correo", row.getString(0));
                fireData.put("nombre", row.getString(1));
                fireData.put("apellido", row.getString(2));
                fireData.put("sexo", row.getString(3));
                fireData.put("edad", row.getString(4));
                fireData.put("hemoglobina", row.getDouble(5));
                fireData.put("tieneAnemia", row.getInt(6)==1 ? "si" : "no");

                db.collection("pacientes").document(row.getString(0)).set(fireData);

                toastMsg = "Paciente Guardado Correctamente en la nube";
            }

            Toast toast = Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_SHORT);
            toast.show();

        }else{

            registro.put("cedula", datosR.getString("id"));
            registro.put("eps", datosR.getString("eps"));
            registro.put("tieneDiabetes", (datosR.getInt("level") == 0 ? 0 : 1));

            String toastMsg = "";

            try {
                bd.insertOrThrow("pacientesDiabetes", null, registro);
                toastMsg = "Paciente Guardado Correctamente";
            }catch (SQLiteConstraintException exception){
                bd.update("pacientesDiabetes", registro, "cedula=" + datosR.getString("id"), null);
                toastMsg = "Paciente Actualizado con Exito";
            }

            Toast toast = Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_SHORT);
            toast.show();
        }

        bd.close();

        aceptarBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                getActivity().finish();

            }
        });




        return view;
    }

}