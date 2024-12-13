package dev.davidvega.rpgame.game;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import dev.davidvega.rpgame.R;
import dev.davidvega.rpgame.data.viewmodel.GameViewModel;
import dev.davidvega.rpgame.databinding.FragmentCharacterBinding;
import dev.davidvega.rpgame.game.manager.ExperienceManager;
import dev.davidvega.rpgame.game.model.PlayerCharacter;

/**
 * The type Character fragment.
 */
public class CharacterFragment extends Fragment {

    /**
     * The Binding.
     */
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
                    Log.d("DEBUG_CHARACTER", user.toString());
                    binding.characterName.setText("Nombre: " + user.getName());
                    binding.characterClass.setText("Clase: " + user.getPlayerClass().getName());
                    binding.characterLevel.setText("Nivel: " + user.getLevel());
                    binding.characterHP.setMax(user.getMaxHp());
                    binding.characterHP.setProgress(user.getHp());
                    binding.characterMana.setMax(user.getMaxMana());
                    binding.characterMana.setProgress(user.getMana());
                    binding.characterExperience.setMax(ExperienceManager.getXpForNextLevel(user.getLevel()));
                    binding.characterExperience.setProgress(user.getXp());


                    binding.characterManaCurrentAndMax.setText(
                            user.getMana() + " / " + user.getMaxMana()
                    );
                    binding.characterHPCurrentAndMax.setText(
                            user.getHp() + " / " + user.getMaxHp()
                    );
                    binding.characterStrength.setText("Fuerza: " + user.getStrength());
                    binding.characterDexterity.setText("Destreza: " + user.getDexterity());
                    binding.characterIntelligence.setText("Inteligencia: " + user.getIntelligence());
                    binding.characterWeapon.setText("Arma: " + user.getCurrentWeapon().getItemName());

                    Glide.with(getContext())
                            .load(user.getCurrentWeapon().getImage()) // Ruta local
                            .placeholder(R.drawable.arrow_up)// Imagen por defecto mientras se carga
                            .into(binding.characterWeaponIcon);
                }

            }
        });









    }




}