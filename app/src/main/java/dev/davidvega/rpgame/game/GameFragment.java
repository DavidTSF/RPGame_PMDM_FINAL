package dev.davidvega.rpgame.game;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import dev.davidvega.rpgame.R;
import dev.davidvega.rpgame.data.viewmodel.GameViewModel;
import dev.davidvega.rpgame.databinding.FragmentGameBinding;
import dev.davidvega.rpgame.login.LoginFragment;


/**
 * The type Game fragment.
 */
public class GameFragment extends Fragment {
    /**
     * The Binding.
     */
    FragmentGameBinding binding;

    /**
     * The Game view model.
     */
    GameViewModel gameViewModel;


    //private Fragment mainGameFragment = new MainGameFragment();
    //private Fragment inventoryFragment = new InventoryFragment();
    //private Fragment characterFragment = new CharacterFragment();


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        gameViewModel.updateUser();


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        NavHostFragment navHostFragment =
                (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment_game);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),binding.drawerLayout,R.string.nav_open,R.string.nav_close);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();




        /*
        gameViewModel.getPlayerDead().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isDead) {
                if ( isDead ) {
                    Log.d("DEBUG_GAME", "Player is dead");


                    NavHostFragment navHostFragment =
                            (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    NavController navController = navHostFragment.getNavController();
                    gameViewModel.getPlayerDead().postValue(false);
                }
            }
        });
*/

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