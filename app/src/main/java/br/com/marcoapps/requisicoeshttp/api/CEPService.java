package br.com.marcoapps.requisicoeshttp.api;

import br.com.marcoapps.requisicoeshttp.model.CEP;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CEPService {

    @GET("01310100/json/")
    Call<CEP> pegarDadosDoCEP();
}
