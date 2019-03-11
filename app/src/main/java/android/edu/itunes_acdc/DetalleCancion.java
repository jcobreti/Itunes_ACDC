package android.edu.itunes_acdc;
import android.edu.itunes_acdc.Utils.Cancion;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.TextView;

public class DetalleCancion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cancion);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);


        TextView textView=findViewById(R.id.texto);
        Bundle loadInfo = getIntent().getExtras();
        Cancion cancion= (Cancion) loadInfo.getSerializable("cancion");
        String id=cancion.getTrackId();
        textView.setText("ID cancion Seleccionada: "+id);
    }
}
