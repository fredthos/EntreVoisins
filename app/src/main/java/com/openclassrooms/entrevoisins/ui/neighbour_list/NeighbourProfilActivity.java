package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import static com.openclassrooms.entrevoisins.ui.neighbour_list.MyNeighbourRecyclerViewAdapter.SELECTED_NEIGHBOUR;

public class NeighbourProfilActivity extends AppCompatActivity {


    private NeighbourApiService mApiService;

    private ImageView neighbourImg;
    private Toolbar mToolbar;
    private TextView TBneighbourName;
    private FloatingActionButton favorisButton;
    private TextView neighbourName;
    private TextView neighbourAddress;
    private TextView neighbourPhone;
    private TextView neighbourFB;
    private TextView neighbourAboutMe;
    private Neighbour neighbour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_profil);

        //Wire Widgets
        neighbourImg = findViewById(R.id.neighbourImg);
        neighbourName = findViewById(R.id.neighbourName);
        TBneighbourName = findViewById(R.id.TBneighbourName);
        neighbourAddress = findViewById(R.id.neighbourAddress);
        neighbourPhone = findViewById(R.id.neighbourPhone);
        neighbourFB = findViewById(R.id.neighbourFB);
        neighbourAboutMe = findViewById(R.id.neighbourAboutMe);
        favorisButton = findViewById(R.id.favoris_Button);
        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mApiService = DI.getNeighbourApiService();
        neighbour = getIntent().getParcelableExtra(SELECTED_NEIGHBOUR);

        Glide.with(this).load(neighbour.getAvatarUrl()).into(neighbourImg);

        neighbourName.setText(neighbour.getName());
        TBneighbourName.setText(neighbour.getName());
        neighbourFB.setText(neighbourFB.getText() + neighbour.getName());

        if (neighbour.isFavori()) {
            favorisButton.setImageDrawable(getDrawable(R.drawable.ic_yellow_star_24));
        } else favorisButton.setImageDrawable(getDrawable(R.drawable.ic_grey_star_24));

        favorisButton.setOnClickListener(new View.OnClickListener() {
            /**
             * utilisation de l'ajout de voisin dans favoris
             * @param view
             */
            @Override
            public void onClick(View view) {
                if (!neighbour.isFavori()) {
                    neighbour.setFavori(true);
                    favorisButton.setImageDrawable(getDrawable(R.drawable.ic_yellow_star_24));
                    mApiService.addFavNeighbour(neighbour);
                } else {
                    neighbour.setFavori(false);
                    favorisButton.setImageDrawable(getDrawable(R.drawable.ic_grey_star_24));
                    mApiService.removeFavNeighbour(neighbour);
                }
            }
        });
    }
}
