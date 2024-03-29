package br.com.marcoapps.requisicoeshttp.api;

import br.com.marcoapps.requisicoeshttp.model.CEP;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CEPService {

    @GET("{cep}/json/")
    Call<CEP> pegarDadosDoCEP(@Path("cep") String cep);
}
