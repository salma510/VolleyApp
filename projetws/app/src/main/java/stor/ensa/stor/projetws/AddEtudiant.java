package stor.ensa.stor.projetws;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import stor.ensa.stor.projetws.beans.ListEtudiantActivity;

public class AddEtudiant extends AppCompatActivity implements View.OnClickListener {
    private EditText nom;
    private EditText prenom;
    private Spinner ville;
    private RadioButton m;
    private RadioButton f;
    private Button add;
    private Button all; // Ajout du bouton "Show List"
    RequestQueue requestQueue;
    String insertUrl = "http://10.0.2.2/projet/ws/createEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);

        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        ville = (Spinner) findViewById(R.id.ville);
        add = (Button) findViewById(R.id.add);
        m = (RadioButton) findViewById(R.id.m);
        f = (RadioButton) findViewById(R.id.f);
        all = (Button) findViewById(R.id.all); // Initialisation du bouton "Show List"

        add.setOnClickListener(this);
        all.setOnClickListener(this); // Définir le listener pour le bouton "Show List"

        // Initialize request queue
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onClick(View v) {
        if (v == add) {
            if (validateInputs()) {
                sendRequest();
                Toast.makeText(getApplicationContext(), "Student Created Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddEtudiant.this, ListEtudiantActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        } else if (v == all) {  // Gérer le clic sur le bouton "Show List"
            Intent intent = new Intent(AddEtudiant.this, ListEtudiantActivity.class);
            startActivity(intent); // Rediriger vers l'activité qui affiche la liste
        }
    }

    private void sendRequest() {
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        Toast.makeText(AddEtudiant.this, "Student added successfully!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        Toast.makeText(AddEtudiant.this, "Error adding student", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String sexe = m.isChecked() ? "homme" : "femme";
                HashMap<String, String> params = new HashMap<>();
                params.put("nom", nom.getText().toString());
                params.put("prenom", prenom.getText().toString());
                params.put("ville", ville.getSelectedItem().toString());
                params.put("sexe", sexe);
                return params;
            }
        };

        requestQueue.add(request);
    }

    private boolean validateInputs() {
        return !nom.getText().toString().isEmpty() &&
                !prenom.getText().toString().isEmpty() &&
                ville.getSelectedItem() != null;
    }
}
