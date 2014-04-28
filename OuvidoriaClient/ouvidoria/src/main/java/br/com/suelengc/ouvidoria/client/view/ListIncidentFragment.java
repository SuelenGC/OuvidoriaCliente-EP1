package br.com.suelengc.ouvidoria.client.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.suelengc.ouvidoria.client.R;
import br.com.suelengc.ouvidoria.client.dao.IncidentDAO;
import br.com.suelengc.ouvidoria.client.model.Incident;

public class ListIncidentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_incident, container, false);

        List<Incident> incidents = new IncidentDAO(getActivity()).getAll();
        ArrayAdapter<Incident> adapter = new ArrayAdapter<Incident>(getActivity(), android.R.layout.simple_list_item_1, incidents);
        ListView list = (ListView) view.findViewById(R.id.list_incident);
        list.setAdapter(adapter);

        return view;
    }
}
