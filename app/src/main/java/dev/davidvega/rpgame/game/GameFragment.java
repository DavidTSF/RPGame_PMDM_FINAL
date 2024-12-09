package dev.davidvega.rpgame.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import dev.davidvega.rpgame.R;
import dev.davidvega.rpgame.databinding.FragmentGameBinding;


public class GameFragment extends Fragment {
    FragmentGameBinding binding;

    private Fragment mainGameFragment = new MainGameFragment();
    private Fragment inventoryFragment = new InventoryFragment();
    private Fragment characterFragment = new CharacterFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGameBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavHostFragment navHostFragment =
                (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment_game);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
        /*

        binding.bottomNavGame.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.MainGameFragment:
                    selectedFragment = mainGameFragment;
                    break;
                case R.id.inventoryFragment:
                    selectedFragment = inventoryFragment;
                    break;
                case R.id.characterFragment:
                    selectedFragment = characterFragment;
                    break;
            }

            if (selectedFragment != null) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_game, selectedFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

            return true;
        });

        binding.bottomNavGame.setSelectedItemId(R.id.MainGameFragment);
        */



    }

}