package br.com.marcoapps.requisicoeshttp.api;

import java.util.List;

import br.com.marcoapps.requisicoeshttp.model.Foto;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ConsumoDeDados {

    @GET("/photos")
    Call <List<Foto>> obterListaDeFotos();


}
