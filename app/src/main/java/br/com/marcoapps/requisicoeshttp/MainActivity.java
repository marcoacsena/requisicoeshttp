package br.com.marcoapps.requisicoeshttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.marcoapps.requisicoeshttp.api.CEPService;
import br.com.marcoapps.requisicoeshttp.model.CEP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private Button btnRecuperarDados;
    private TextView tvResultado;
    private Retrofit consumoDeServicosWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRecuperarDados = findViewById(R.id.btnRecuperarDados);
        tvResultado = findViewById(R.id.tvResultado);

        consumoDeServicosWeb = new Retrofit.Builder().
                baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnRecuperarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                consultarCep();

                /*MyTask tarefa = new MyTask();

                String urlApi = "https://blockchain.info/ticker";
                String urlApi = "https://viacep.com.br/ws/01001000/json/";
                tarefa.execute(urlApi);*/

            }
        });
    }

    private void consultarCep() {

        CEPService cepService = consumoDeServicosWeb.create(CEPService.class);
        Call<CEP> call = cepService.pegarDadosDoCEP();

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {

                if(response.isSuccessful()){

                    CEP cep = response.body();
                    tvResultado.setText(cep.getLogradouro() +", " +cep.getComplemento()
                            +". "+cep.getBairro() +".");

                }

            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

            }
        });

    }

    class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {
                URL url = new URL(stringUrl);

                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                //Recupera os dados em bytes:
                inputStream = conexao.getInputStream();

                //Lê os dados em bytes e os codifica para caracteres:
                inputStreamReader = new InputStreamReader(inputStream);

                //Transforma caracteres em String:

                BufferedReader reader = new BufferedReader(inputStreamReader);

                buffer = new StringBuffer();
                String linha = "";

                while ((linha = reader.readLine()) != null){

                    buffer.append(linha);
                };



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

            tvResultado.setText(resultado);
        }
    }
}