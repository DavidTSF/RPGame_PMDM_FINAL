package dev.davidvega.rpgame.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import dev.davidvega.rpgame.R;
import dev.davidvega.rpgame.data.model.User;
import dev.davidvega.rpgame.data.viewmodel.GameViewModel;
import dev.davidvega.rpgame.data.viewmodel.LoginViewModel;
import dev.davidvega.rpgame.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;

    LoginViewModel loginViewModel;
    GameViewModel gameViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);

        return  (binding = FragmentLoginBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        gameViewModel.getAllItemsFromDatabase(getContext());


        TextView userEntry = binding.loginUsername;
        Button button = binding.loginButton;

        button.setOnClickListener(view1 -> {
            if ( binding.loginUsername.length() == 0 ) {
                binding.loginStatus.setVisibility(View.VISIBLE);
                binding.loginStatus.setText("Error: no puedes dejarlo vacio");

            } else {
                binding.loginStatus.setVisibility(View.GONE);
                binding.loginStatus.setText("");

                loginViewModel.userLogin(userEntry.getText().toString());
            }

        });


        loginViewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<LoginViewModel.UserStatus>() {
            @Override
            public void onChanged(LoginViewModel.UserStatus userStatus) {

                if (userStatus != null && userStatus.loggedIn) {
                    User user = userStatus.user;
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

                    if (userStatus.hasToCreateCharacter) {

                        navController.navigate(R.id.characterCreatorFragment);

                    } else {

                        gameViewModel.getUser().postValue(user);
                        navController.navigate(R.id.gameFragment);
                    }
                }
            }
        });
    }
}
