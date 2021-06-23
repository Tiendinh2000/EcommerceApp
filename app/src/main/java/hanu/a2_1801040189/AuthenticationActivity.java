package hanu.a2_1801040189;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import hanu.a2_1801040189.ui.LoginFragment;

public class AuthenticationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = new LoginFragment();

        manager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

    }
}