package stor.ensa.stor.projetws.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

import stor.ensa.stor.projetws.R;
import stor.ensa.stor.projetws.beans.Etudiant;

public class EtudiantAdapter extends ArrayAdapter<Etudiant> {
    private List<Etudiant> etudiantList;
    private Context context;

    public EtudiantAdapter(Context context, List<Etudiant> etudiantList) {
        super(context, 0, etudiantList);
        this.etudiantList = etudiantList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Etudiant etudiant = etudiantList.get(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }

        // Lookup view for data population
        TextView nom_prenom = convertView.findViewById(R.id.nom_prenom);
        TextView prenom = convertView.findViewById(R.id.prenom);
        TextView ville = convertView.findViewById(R.id.ville);
        TextView sexe = convertView.findViewById(R.id.sexe);

        // Populate the data into the template view using the data object
        nom_prenom.setText(etudiant.getNom()+" "+etudiant.getPrenom());
        ville.setText("City: " + etudiant.getVille());
        sexe.setText("Sexe: " + etudiant.getSexe());

        // Return the completed view to render on screen
        return convertView;
    }
}