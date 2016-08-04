package com.example.tallesrodrigues.medicament11;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Horarios_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Horarios_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Horarios_fragment extends Fragment {


    public Horarios_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_horarios_fragment, container, false);
    }

}
