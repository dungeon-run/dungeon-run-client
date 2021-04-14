package edu.cnm.deepdive.dungeonrunclient.controller;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.dungeonrunclient.R;
import edu.cnm.deepdive.dungeonrunclient.databinding.FragmentPlayBinding;

public class PlayFragment extends Fragment {

    FragmentPlayBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        binding = FragmentPlayBinding.inflate(inflater, container, false);
//        binding.startGameButton.setOnClickListener((v) -> {
//            Navigation.findNavController(binding.getRoot()).navigate(GameFragment);
//        });
        return binding.getRoot();
    }
}