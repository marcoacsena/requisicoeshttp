package br.com.marcoapps.requisicoeshttp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.marcoapps.requisicoeshttp.R;
import br.com.marcoapps.requisicoeshttp.api.CEPService;
import br.com.marcoapps.requisicoeshttp.api.ConsumoDeDados;
import br.com.marcoapps.requisicoeshttp.model.CEP;
import br.com.marcoapps.requisicoeshttp.model.Foto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private EditText editCEP;
    private Button btnRecuperarDados;
    private TextView tvResultado;
    private Retrofit consumoDeServicosWeb;
    private List<Foto> listaDeFotosRecuperada = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCEP = findViewById(R.id.editCEP);
        btnRecuperarDados = findViewById(R.id.btnRecuperarDados);
        tvResultado = findViewById(R.id.tvResultado);

        consumoDeServicosWeb = new Retrofit.Builder().
                //baseUrl("https://viacep.com.br/ws/")
                baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnRecuperarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cepDigitado = obterDadosDoFormulario();
                //consultarCep(cepDigitado);

                obterListaDeFotos();

                /*MyTask tarefa = new MyTask();

                String urlApi = "https://blockchain.info/ticker";
                String urlApi = "https://viacep.com.br/ws/01001000/json/";
                tarefa.execute(urlApi);*/

            }
        });
    }

    public void obterListaDeFotos() {

        ConsumoDeDados listaDeFotos = consumoDeServicosWeb.create(ConsumoDeDados.class);
        Call<List<Foto>> call = listaDeFotos.obterListaDeFotos();

        call.enqueue(new Callback<List<Foto>>() {
            @Override
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {

                if(response.isSuccessful()){

                    listaDeFotosRecuperada = response.body();

                    for(int i = 0; i < listaDeFotosRecuperada.size(); i++){

                        Foto foto = listaDeFotosRecuperada.get(i);
                        Log.d("Resultado", "resultado: " +foto.getId() +"/" +foto.getTitle() );

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Foto>> call, Throwable t) {

            }
        });

    }

    public String obterDadosDoFormulario(){

        String cep = editCEP.getText().toString();
        return cep;
    }

    private void consultarCep(String cepDigitado) {

        CEPService cepService = consumoDeServicosWeb.create(CEPService.class);
        Call<CEP> call = cepService.pegarDadosDoCEP(cepDigitado);

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {

                if(response.isSuccessful()){

                    CEP cep = response.body();
                    tvResultado.setText("Avenida: " +cep.getLogradouro() +"\n"
                            +"Complemento: " +cep.getComplemento() +"\n"
                            +"Bairro: "+cep.getBairro() +".");

                    editCEP.setText("");

                }

            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Não foi possível pesquisar o Cep" +
                                " fornecido",Toast.LENGTH_SHORT).show();
            }
        });

    }

}



//    class MyTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            String stringUrl = strings[0];
//            InputStream inputStream = null;
//            InputStreamReader inputStreamReader = null;
//            StringBuffer buffer = null;
//
//            try {
//                URL url = new URL(stringUrl);
//
//                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
//
//                //Recupera os dados em bytes:
//                inputStream = conexao.getInputStream();
//
//                //Lê os dados em bytes e os codifica para caracteres:
//                inputStreamReader = new InputStreamReader(inputStream);
//
//                //Transforma caracteres em String:
//
//                BufferedReader reader = new BufferedReader(inputStreamReader);
//
//                buffer = new StringBuffer();
//                String linha = "";
//
//                while ((linha = reader.readLine()) != null){
//
//                    buffer.append(linha);
//                };
//
//
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return buffer.toString();
//        }
//
//        @Override
//        protected void onPostExecute(String resultado) {
//            super.onPostExecute(resultado);
//
//            tvResultado.setText(resultado);
//        }
//    }
