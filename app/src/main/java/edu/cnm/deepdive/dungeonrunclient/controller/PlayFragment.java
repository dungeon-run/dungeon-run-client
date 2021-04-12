package edu.cnm.deepdive.dungeonrunclient.controller;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.cnm.deepdive.dungeonrunclient.R;
import edu.cnm.deepdive.dungeonrunclient.databinding.FragmentPlayBinding;

public class PlayFragment extends Fragment {

    FragmentPlayBinding binding;

    public PlayFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        binding = FragmentPlayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}