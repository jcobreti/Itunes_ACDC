package android.edu.itunes_acdc;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
  private EditText ed;
  private Spinner sp;
  private ArrayAdapter<CharSequence> adapter;
  private String textoBusqueda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);

        ed=findViewById(R.id.editText);
        cargarSpinner();

    }

    public void irPrincipal(View view) {
        Intent intent=new Intent (this, Inicio.class);
        textoBusqueda=ed.getText().toString();

        intent.putExtra("KEY",textoBusqueda);
        intent.putExtra("CONTADOR",textoSpinner());
        startActivity(intent);

    }
    private void cargarSpinner()
    {
        sp =  findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.contador, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setSelection(2);
    }
    private String textoSpinner()
    {   TextView tv;
        tv=(TextView)sp.getSelectedView();
        return tv.getText().toString();
    }
}
