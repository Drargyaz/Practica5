package net.iessochoa.juanmartinez.practica5.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.iessochoa.juanmartinez.practica5.R;
import net.iessochoa.juanmartinez.practica5.modelo.DiaDiario;
import net.iessochoa.juanmartinez.practica5.modelo.DiarioContract;
import net.iessochoa.juanmartinez.practica5.modelo.DiarioDB;

public class MainActivity extends AppCompatActivity {

    private DiarioDB diarioDB;
    TextView tvDiario;
    DiarioDB db;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case  R.id.itemAdd:
                
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvDiario = findViewById(R.id.tvListadoDia);
        diarioDB = new DiarioDB(getApplicationContext());
        diarioDB.open();

       // mostrarDatos(DiarioContract.DiaDiarioEntry.FECHA);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
  /*  public void mostrarDatos(String ordenadoPor){
        Cursor c = db.getCursorDiario(ordenadoPor);
        DiaDiario dia;
        tvDiario.setText("");//limpiamos el campo de texto
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                dia = DiarioDB.deCursorADiario(c);
                //podéis sobrecargar toString en DiaDiario para mostrar los datos
                tvDiario.append(dia.toString() + "/n");
            } while (c.moveToNext());
        }

    } */
}
