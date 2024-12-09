package dev.davidvega.rpgame.game;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


public class GameFragment extends Fragment {
    FragmentGameBinding binding;

    GameViewModel gameViewModel;


    //private Fragment mainGameFragment = new MainGameFragment();
    //private Fragment inventoryFragment = new InventoryFragment();
    //private Fragment characterFragment = new CharacterFragment();

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

        gameViewModel.getPlayerDead().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isDead) {
                if ( isDead ) {
                    Log.d("DEBUG_GAME", "Player is dead");
                    gameViewModel.getPlayerDead().postValue(false);

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.characterCreatorFragment);

                    //NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

                    //FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    //transaction.replace(R.id.nav_host_fragment, new LoginFragment());
                    //transaction.commit();

                    //FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    //transaction.replace(R.id.nav_host_fragment, new LoginFragment());
                    //transaction.addToBackStack(null);


                    //NavController navControll = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    //navControll.popBackStack(R.id.loginFragment, true);

                }
            }
        });



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