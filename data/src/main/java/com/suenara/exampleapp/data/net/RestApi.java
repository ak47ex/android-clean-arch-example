package com.suenara.exampleapp.data.net;

import com.suenara.exampleapp.data.entity.CatEntity;
import com.suenara.exampleapp.data.entity.DogEntity;
import com.suenara.exampleapp.data.net.response.RestApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApi {

    String API_URL = "https://kot3.com/xim/";

    @GET("api.php?query=cat")
    Call<RestApiResponse<CatEntity>> catEntitiesList();

    @GET("api.php?query=dog")
    Call<RestApiResponse<DogEntity>> dogEntitiesList();
}
