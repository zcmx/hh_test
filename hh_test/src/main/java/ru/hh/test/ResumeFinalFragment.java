package ru.hh.test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ResumeFinalFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View resumeFinal = inflater.inflate(R.layout.resume_final_fragment, container, false);

        return  resumeFinal;
    }
}
