package android.edu.itunes_acdc;

import android.content.Intent;
import android.edu.itunes_acdc.Utils.BaseDatosCanciones;
import android.edu.itunes_acdc.Utils.ConexionInternet;
import android.edu.itunes_acdc.Utils.DescargarCanciones;
import android.edu.itunes_acdc.Utils.RecyclerAdapter;
import android.edu.itunes_acdc.Utils.ResultadoCanciones;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.LinearLayoutManager;

import com.squareup.picasso.Picasso;

public class Inicio extends AppCompatActivity {
    RecyclerAdapter adapter;
    android.support.v7.widget.RecyclerView recyclerView;
    Bundle bundle;
    private static ImageView playButton;
    private static MediaPlayer reproductor;
    private static boolean play;
    private static ProgressBar progressBar;
    private static TextView sinConexion;
    private static BaseDatosCanciones baseDatosCanciones;

    public static ImageView getPlayButton() {return playButton;}

    public static void setPlayButton(ImageView playButton) {Inicio.playButton = playButton;}

    public static MediaPlayer getReproductor() {return reproductor;}

    public static void setReproductor(MediaPlayer reproductor) {Inicio.reproductor = reproductor;}

    public static boolean isPlay() {return play; }

    public static void setPlay(boolean play) { Inicio.play = play; }

    public static BaseDatosCanciones getBaseDatosCanciones() {return baseDatosCanciones;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);

        progressBar = findViewById(R.id.progressBar);
        sinConexion = findViewById(R.id.sinConexionTxt);
        sinConexion.setVisibility(View.INVISIBLE);

        if (ConexionInternet.hayInternet(getApplicationContext())) {

            bundle=getIntent().getExtras();
            String artista=bundle.getString("KEY");
            if (artista.compareTo("")==0) artista="ACDC";

            String contador=bundle.getString("CONTADOR","20");
            setReproductor(new MediaPlayer());
            setPlay(false);
            reproductor.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //Salta cuando finaliza la cancion
                    //Lo usamos para cambiar el icono de play
                    Picasso.with(getApplicationContext()).load(android.R.drawable.ic_media_play).into(playButton);
                }
            });
            //El execute llama a doInBackground de la clase de DescargarCanciones
            new DescargarCanciones(this).execute(contador,artista);

        } else {//Toast.makeText(getApplicationContext(),"NO HAY CONEXION A INTERNET",Toast.LENGTH_LONG).show();
            sinConexion.setVisibility(View.VISIBLE);

            progressBar.setVisibility(View.GONE);

        }
    }
    public void mostrarResultados(ResultadoCanciones rc) {   //Aqui quitamos el cargador

        progressBar.setVisibility(View.GONE);

        //Toast.makeText(getApplicationContext(), "DESCARGA COMPLETA", Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new RecyclerAdapter(this, rc.getResults());
        int m=rc.getResults().size();
        if (m==0) {
            Intent intent = new Intent(this, BusquedaSinResultados.class);
            startActivity(intent);
        }
        else
        {  baseDatosCanciones = new BaseDatosCanciones(this,"ITUNESBD",null,1);
            recyclerView.setAdapter(adapter);
        }
    }
}

