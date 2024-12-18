package dev.davidvega.rpgame.game;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import dev.davidvega.rpgame.R;
import dev.davidvega.rpgame.data.viewmodel.GameViewModel;
import dev.davidvega.rpgame.data.viewmodel.LoginViewModel;
import dev.davidvega.rpgame.databinding.FragmentMainGameBinding;
import dev.davidvega.rpgame.game.encounter.Encounter;
import dev.davidvega.rpgame.game.model.Item;
import dev.davidvega.rpgame.game.model.PlayerCharacter;


/**
 * The type Main game fragment.
 */
public class MainGameFragment extends Fragment {
    /**
     * The Binding.
     */
    FragmentMainGameBinding binding;

    /**
     * The Game view model.
     */
    GameViewModel gameViewModel;
    /**
     * The Login view model.
     */
    LoginViewModel loginViewModel;

    /**
     * The Player character.
     */
    PlayerCharacter playerCharacter;
    /**
     * The Current encounter.
     */
    Encounter currentEncounter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        gameViewModel.getPlayerDead().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isDead) {
                if ( isDead ) {
                    Log.d("DEBUG_GAME", "Player is dead, maingamefragment");
                    gameViewModel.getPlayerDead().postValue(false);
                    gameViewModel.emptyInventory();

                    loginViewModel.getHasDied().postValue(true);
                    binding.layoutCombat.setVisibility(View.GONE);
                    binding.layoutTrap.setVisibility(View.GONE);
                    binding.layoutForward.setVisibility(View.GONE);

                    binding.mainGamePlayerHp.setProgress( 0 );
                    // Crear un temporizador de 3 segundos
                    binding.deadText.setVisibility(View.VISIBLE);
                    new CountDownTimer(3000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            binding.deadText.setText("¡Has sido derrotado!\nVolverás al creador de personajes en:\n" + millisUntilFinished / 1000);
                        }

                        public void onFinish() {
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                            navController.navigate(R.id.characterCreatorFragment);
                            binding.deadText.setVisibility(View.INVISIBLE);
                            navController.clearBackStack(R.id.mainGameFragment);
                        }
                    }.start();

                }
            }
        });
        return (binding = FragmentMainGameBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if ( gameViewModel.getUser().isInitialized() && gameViewModel.getEncounter().isInitialized() ) {
            playerCharacter = gameViewModel.getUser().getValue().getPlayerCharacter();
            currentEncounter = gameViewModel.getEncounter().getValue();
        }

        // Gestiona la visibilidad del botón para que si se destruye el fragmento que se mantenga oculto
        gameViewModel.getButtonVisible().observe(getViewLifecycleOwner(), isVisible -> {
            if (isVisible != null) {
                if (isVisible) {
                    binding.layoutForward.setVisibility(View.VISIBLE);
                    binding.layoutCombat.setVisibility(View.GONE);
                    binding.layoutTrap.setVisibility(View.GONE);
                } else {
                    binding.layoutForward.setVisibility(View.GONE);
                }
            }
        });

        // Botón para avanzar en la dungeon
        binding.mainGameForwardButton.setOnClickListener( item -> {
            binding.layoutForward.setVisibility(View.GONE);
            binding.layoutCombat.setVisibility(View.GONE);
            binding.layoutTrap.setVisibility(View.GONE);
            gameViewModel.getEncounterFromApi();
            gameViewModel.setButtonVisible(false);
            gameViewModel.setEncounterLocked(true);
        });

        // Botón para hacer un ataque básico
        binding.mainGameAttack.setOnClickListener(viewAttack -> {
            gameViewModel.makeAttack();
        });

        gameViewModel.getUser().getValue().getPlayerdataLiveData().observe(getViewLifecycleOwner(), new Observer<PlayerCharacter>() {
            @Override
            public void onChanged(PlayerCharacter user) {
                if ( user != null ) {
                    binding.mainGamePlayerHp.setProgress( user.getHp() );
                    binding.mainGamePlayerHp.setMax( user.getMaxHp() );
                }
            }
        });

        // Para actualizar los datos del jugador y del enemigo cuando combatan
        gameViewModel.getCalculateDamage().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean calculatedDamage) {
                if (calculatedDamage) {
                    playerCharacter = gameViewModel.getUser().getValue().getPlayerdataLiveData().getValue();
                    currentEncounter = gameViewModel.getEncounter().getValue();
                }
            }
        });
        gameViewModel.getAttackLock().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean attackLock) {
                if ( !attackLock ) {
                    if ( currentEncounter.getEnemy() != null ) {
                        binding.mainGameEnemyHp.setProgress(currentEncounter.getEnemy().getHp());
                        binding.mainGameText.setText(currentEncounter.getDescription());
                    } else {
                        binding.mainGameEnemyHp.setProgress(0);
                    }
                }
            }
        });

        gameViewModel.getWonEncounter().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean wonEncounter) {
                if ( wonEncounter ) {
                    gameViewModel.getWonEncounter().setValue(false);
                    giveReward();
                    loginViewModel.updateUserFromGame( gameViewModel.getUser().getValue() );
                }
            }
        });

        // Por cada encuentro que se genere o si se cambia el fragmento para que se quede guardado
        gameViewModel.getEncounter().observe(getViewLifecycleOwner(), new Observer<Encounter>() {
            @Override
            public void onChanged(Encounter encounter) {
                if ( encounter != null ) {
                    currentEncounter = encounter;
                    newEncounter(encounter);
                    Log.d("DEBUG", encounter.toString());
                }
            }
        });
        gameViewModel.getEncounterLocked().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean encounterLocked) {
                if ( !encounterLocked ){
                    gameViewModel.setButtonVisible(true);

                    binding.layoutCombat.setVisibility(View.GONE);
                    binding.layoutTrap.setVisibility(View.GONE);
                    binding.mainGameEnemyOverlay.setVisibility(View.GONE);

                }
            }
        });

    }

    private void giveReward() {
        Item item = gameViewModel.rewardPlayer();
        Snackbar.make(getView() , "Has conseguido un/a " + item.getItemName() + " !", Snackbar.LENGTH_SHORT)
                .setAnchorView(R.id.layoutForward)
                .show();

    }


    /**
     * New encounter.
     *
     * @param encounter the encounter
     */
    public void newEncounter(Encounter encounter){
        binding.mainGameText.setText(encounter.getDescription());

        switch ( encounter.getEncounterType() ) {
            case COMBAT:

                binding.layoutCombat.setVisibility(View.VISIBLE);
                binding.mainGameEnemyHp.setMax(encounter.getEnemy().getMaxhp());
                binding.mainGameEnemyName.setText(encounter.getEnemy().getName());
                binding.mainGameEnemyOverlay.setVisibility(View.VISIBLE);
                binding.mainGameEnemyHp.setProgress(encounter.getEnemy().getHp());


                break;
            case TRAP:
                binding.layoutTrap.setVisibility(View.VISIBLE);

                break;
        }




    }


    /**
     * Sets button icon tint.
     *
     * @param button     the button
     * @param colorResId the color res id
     */
    public void setButtonIconTint(Button button, int colorResId) {
        Drawable[] drawables = button.getCompoundDrawables();
        Drawable leftDrawable = drawables[1];
        if (leftDrawable != null) {
            Drawable tintedDrawable = leftDrawable.mutate();
            tintedDrawable.setColorFilter(ContextCompat.getColor(button.getContext(), colorResId), PorterDuff.Mode.SRC_IN);
        }
    }

}