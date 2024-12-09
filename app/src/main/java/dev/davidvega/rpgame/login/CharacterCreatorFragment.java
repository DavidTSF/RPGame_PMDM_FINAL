package dev.davidvega.rpgame.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import dev.davidvega.rpgame.R;
import dev.davidvega.rpgame.data.viewmodel.GameViewModel;
import dev.davidvega.rpgame.data.viewmodel.LoginViewModel;
import dev.davidvega.rpgame.databinding.FragmentCharacterCreatorBinding;


public class CharacterCreatorFragment extends Fragment {
    FragmentCharacterCreatorBinding binding;
    Clase selectedClass;

    LoginViewModel loginViewModel;
    GameViewModel gameViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        return (binding = FragmentCharacterCreatorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Carga los valores del array definido en strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.character_classes, android.R.layout.simple_spinner_item);
        // Define el estilo del menú desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerClases.setAdapter(adapter);

        binding.spinnerClases.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                try {
                    selectedClass = Clase.fromString(selectedItem);

                } catch (IllegalArgumentException e) {
                    Toast.makeText(getContext(), "Clase no válida", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String selectedItem = parent.getItemAtPosition(0).toString();
                try {
                    selectedClass = Clase.fromString(selectedItem);

                } catch (IllegalArgumentException e) {
                    Toast.makeText(getContext(), "Clase no válida", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.characterCreatorButtonCreate.setEnabled(true);  // Asegúrate de que el botón esté habilitado inicialmente.


        loginViewModel.getCurrentUser().observe(getViewLifecycleOwner(), userStatus -> {
            if (userStatus != null) {
                if (userStatus.user.getPlayerCharacter() != null) {
                    binding.characterCreatorButtonCreate.setEnabled(true);
                } else {
                    binding.characterCreatorButtonCreate.setEnabled(false);
                }
            }
        });

        // Cuando se de click al boton de crear se va a crear un nuevo usuario con datos y con unas estadisticas según la clase elegida
        binding.characterCreatorButtonCreate.setOnClickListener(v -> {
                binding.characterCreatorButtonCreate.setEnabled(false);

                loginViewModel.createCharacter(
                    selectedClass, binding.creatorCharacterName.getText().toString()
                );
        });



        loginViewModel.getPassToGame().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean passToGame) {
                if ( passToGame ) {
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                        gameViewModel.getUser().setValue(loginViewModel.getCurrentUser().getValue().user);
                        navController.navigate(R.id.gameFragment);
                    }
                }
        });





    }
}