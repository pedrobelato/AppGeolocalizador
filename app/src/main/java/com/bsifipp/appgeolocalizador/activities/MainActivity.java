package com.bsifipp.appgeolocalizador.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bsifipp.appgeolocalizador.R;
import com.bsifipp.appgeolocalizador.adapters.ViaCepAdapter;
import com.bsifipp.appgeolocalizador.enums.APIAddress;
import com.bsifipp.appgeolocalizador.models.Cidade;
import com.bsifipp.appgeolocalizador.models.GoogleApiModel;
import com.bsifipp.appgeolocalizador.models.UF;
import com.bsifipp.appgeolocalizador.models.ViaCep;
import com.bsifipp.appgeolocalizador.services.PermissionsService;
import com.bsifipp.appgeolocalizador.utils.Permissions;
import com.bsifipp.appgeolocalizador.utils.RetrofitConfig;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    PermissionsService permissionsService = new PermissionsService(this);
    private Spinner spEstado;
    private Spinner spCidade;
    private EditText etComplemento;
    private Button botaoPesquisar;
    private UF uf;
    private Cidade cidade;
    private ListView lvListagem;
    private ViaCepAdapter lvAdapter;
    private ViaCep viaCep;
    private GoogleApiModel googleApiModel;


    private void setOnItemClickListenerEstado()
    {
        spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                uf = (UF)adapterView.getAdapter().getItem(i);
                Call<ArrayList<Cidade>> call = new RetrofitConfig(APIAddress.IBGE).getCidadeService().buscarCidade(uf.id);
                call.enqueue(new Callback<ArrayList<Cidade>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Cidade>> call, Response<ArrayList<Cidade>> response) {
                        ArrayList<Cidade> cidades = response.body();
                        populateSpinnerCidade(cidades);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Cidade>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setOnItemClickListenerCidade()
    {
        spCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cidade = (Cidade)adapterView.getAdapter().getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setOnClickListenerBotaoPesquisar()
    {
        botaoPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etComplemento.getText().toString().length() > 3)
                {
                    Call<ArrayList<ViaCep>> call = new RetrofitConfig(APIAddress.VIACEP).getViaCepService().buscarLogradouros(uf.sigla,cidade.nome,etComplemento.getText().toString());
                    call.enqueue(new Callback<ArrayList<ViaCep>>() {
                        @Override
                        public void onResponse(Call<ArrayList<ViaCep>> call, Response<ArrayList<ViaCep>> response) {
                            ArrayList<ViaCep> viaCeps = response.body();
                            lvAdapter = new ViaCepAdapter(MainActivity.this,R.layout.layout_logradouros,viaCeps);
                            lvListagem.setAdapter(lvAdapter);
                            lvListagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    viaCep = (ViaCep) adapterView.getAdapter().getItem(i);
                                    obterLongitudeLatitude(viaCep);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<ArrayList<ViaCep>> call, Throwable t) {

                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this,"Você deve digitar mais de 3 caracteres para pesquisar",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionsService.CheckPermissionGranted();
        spEstado = findViewById(R.id.spEstado);
        spCidade = findViewById(R.id.spCidade);
        etComplemento = findViewById(R.id.etComplemento);
        botaoPesquisar = findViewById(R.id.btnPesquisar);
        lvListagem = findViewById(R.id.lvEnderecos);
        this.CarregarEstados(spEstado);
        this.setOnItemClickListenerEstado();
        this.setOnItemClickListenerCidade();
        this.setOnClickListenerBotaoPesquisar();
    }


    private void obterLongitudeLatitude(ViaCep viaCep)
    {
        Call<GoogleApiModel> call = new RetrofitConfig(APIAddress.GOOGLEAPI).getGoogleApiService().BuscarPorCEP(viaCep.cep,"AIzaSyAHpg-1KXM7DF2WT-bpkb1KKEaaIjIfmTo");
        call.enqueue(new Callback<GoogleApiModel>() {
            @Override
            public void onResponse(Call<GoogleApiModel> call, Response<GoogleApiModel> response) {
                googleApiModel = (GoogleApiModel) response.body();
                if(googleApiModel.status.equals("OK") && googleApiModel.results.size() > 0)
                {
                    Intent intent = new Intent(MainActivity.this,ActivityMaps.class);
                    intent.putExtra("lng",googleApiModel.results.get(0).geometry.location.lng);
                    intent.putExtra("lat",googleApiModel.results.get(0).geometry.location.lat);

                    intent.putExtra("bairro", viaCep.bairro);
                    intent.putExtra("rua", viaCep.logradouro);
                    intent.putExtra("cidade", viaCep.localidade);
                    intent.putExtra("siglaEstado", viaCep.uf);

                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<GoogleApiModel> call, Throwable t) {

            }
        });
    }

    private void populateSpinnerCidade(ArrayList<Cidade> cidades)
    {
        cidades.sort(new Comparator<Cidade>() {
            @Override
            public int compare(Cidade cid, Cidade t1) {
                return cid.nome.compareTo(t1.nome);
            }
        });
        ArrayAdapter<Cidade> adapter = new ArrayAdapter<Cidade>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,cidades);
        spCidade.setAdapter(adapter);
    }


    private void pouplateSpinnerUF(ArrayList<UF> ufs)
    {
        ufs.sort(new Comparator<UF>() {
            @Override
            public int compare(UF uf, UF t1) {
                return uf.nome.compareTo(t1.nome);
            }
        });
        ArrayAdapter<UF> adapter = new ArrayAdapter<UF>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,ufs);
        spEstado.setAdapter(adapter);
    }

    private void CarregarEstados(Spinner spEstado)
    {
        try{
            Call<ArrayList<UF>> call = new RetrofitConfig(APIAddress.IBGE).getEstadoService().buscarEstados();
            call.enqueue(new Callback<ArrayList<UF>>() {
                @Override
                public void onResponse(Call<ArrayList<UF>> call, Response<ArrayList<UF>> response) {
                    ArrayList<UF> ufs = response.body();
                    pouplateSpinnerUF(ufs);
                }

                @Override
                public void onFailure(Call<ArrayList<UF>> call, Throwable t) {

                }
            });
        }catch (Exception e)
        {
            Log.i("ERRO" , e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.itFechar:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}