package dev.davidvega.rpgame;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dev.davidvega.rpgame.data.viewmodel.GameViewModel;
import dev.davidvega.rpgame.data.viewmodel.LoginViewModel;
import dev.davidvega.rpgame.utils.ImageUtils;

public class MainActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;
    GameViewModel gameViewModel;
    public static String fistPngPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        String fileName =  "WEAPON_" + "puños" + ".png";

        ImageUtils.saveImageFromUrl(this, "https://api.taxistahosting.com/image/tile095.png", fileName);

        String localImagePath = this.getDir("images", Context.MODE_PRIVATE).getAbsolutePath() + "/" + fileName;
        fistPngPath = localImagePath;







    }



}