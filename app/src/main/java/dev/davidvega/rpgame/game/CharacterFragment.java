package dev.davidvega.rpgame.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import dev.davidvega.rpgame.R;
import dev.davidvega.rpgame.data.viewmodel.GameViewModel;
import dev.davidvega.rpgame.data.viewmodel.LoginViewModel;
import dev.davidvega.rpgame.databinding.FragmentCharacterBinding;
import dev.davidvega.rpgame.game.model.PlayerCharacter;

public class CharacterFragment extends Fragment {

    FragmentCharacterBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentCharacterBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final GameViewModel gameViewModel = new ViewModelProvider(getActivity()).get(GameViewModel.class);

        gameViewModel.getUser().getValue().getPlayerdataLiveData().observe(getViewLifecycleOwner(), new Observer<PlayerCharacter>() {
            @Override
            public void onChanged(PlayerCharacter user) {
                if ( user != null ) {
                    binding.characterName.setText("Nombre: " + user.getName());
                    binding.characterClass.setText("Clase: " + user.getPlayerClass().getName());
                    binding.characterLevel.setText("Nivel: " + user.getLevel());
                    binding.characterHP.setMax(user.getMaxhp());
                    binding.characterHP.setProgress(user.getHp());
                    binding.characterMana.setProgress(user.getMana());
                    binding.characterHPCurrentAndMax.setText(
                            user.getHp() + " / " + user.getMaxhp()
                    );
                    binding.characterStrength.setText("Fuerza: " + user.getStrength());
                    binding.characterDexterity.setText("Destreza: " + user.getDexterity());
                    binding.characterIntelligence.setText("Inteligencia: " + user.getIntelligence());
                    binding.characterWeapon.setText("Arma: " + user.getCurrentWeapon().name);
                }

            }
        });









    }




}