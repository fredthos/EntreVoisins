package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

public class NeighbourProfilActivity extends AppCompatActivity {


    private NeighbourApiService mApiService;

    private ImageView imgProfil;
    private ImageButton retourButton;
    private TextView Nameprofile;
    private FloatingActionButton floatingActionButton;
    private TextView NameUsers;
    private TextView addreseUsers;
    private TextView tellUsers;
    private TextView mailUsers;
    private TextView Aproposdemoi;
    private TextView aboutMe;
    private Neighbour profil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_profil);

        imgProfil = findViewById(R.id.imgProfil);
        Nameprofile = findViewById(R.id.Nameprofile);
        NameUsers = findViewById(R.id.NameUsers);
        addreseUsers = findViewById(R.id.addreseUsers);
        tellUsers = findViewById(R.id.tellUsers);
        mailUsers = findViewById(R.id.mailUsers);
        Aproposdemoi = findViewById(R.id.Aproposdemoi);
        aboutMe = findViewById(R.id.aboutMe);

        mApiService = DI.getNeighbourApiService();
        profil = getIntent().getParcelableExtra("profil");

        Glide.with(this).load(profil.getAvatarUrl()).into(imgProfil);

        Nameprofile.setText(profil.getName());
        NameUsers.setText(profil.getName());
        mailUsers.setText(mailUsers.getText() + profil.getName());

        retourButton = findViewById(R.id.retourButton);
        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        floatingActionButton = findViewById(R.id.floatingActionButton);
        if (profil.isFavoris() == false){
            floatingActionButton.setImageResource(R.drawable.ic_star_white_24dp);
        }
        else floatingActionButton.setImageResource(R.drawable.ic_baseline_star_24);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            /**
             * utilisation de ma methode de services changeFavoris
             * @param v
             */
            @Override
            public void onClick(View v) {

                if (profil.isFavoris() == false){
                    mApiService.changeFavoris(profil);
                    floatingActionButton.setImageResource(R.drawable.ic_star_white_24dp);
                }
                else if (profil.isFavoris() == true) {
                    mApiService.changeFavoris(profil);
                    floatingActionButton.setImageResource(R.drawable.ic_baseline_star_24);
                }


            }
        });

    }
}
