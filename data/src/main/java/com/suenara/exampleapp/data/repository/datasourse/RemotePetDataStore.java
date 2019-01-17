package com.suenara.exampleapp.data.repository.datasourse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suenara.exampleapp.data.entity.CatEntity;
import com.suenara.exampleapp.data.entity.DogEntity;
import com.suenara.exampleapp.data.exception.NetworkRequestException;
import com.suenara.exampleapp.data.net.RestApi;
import com.suenara.exampleapp.data.net.response.RestApiResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RemotePetDataStore implements PetDataStore {

    private final RestApi restApi;

    public RemotePetDataStore(ObjectMapper objectMapper) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApi.API_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        restApi = retrofit.create(RestApi.class);
    }

    @Override
    public Observable<List<CatEntity>> catEntityList() {
        return Observable.create(emitter -> {
            restApi.catEntitiesList().enqueue(new Callback<RestApiResponse<CatEntity>>() {
                @Override
                public void onResponse(Call<RestApiResponse<CatEntity>> call, Response<RestApiResponse<CatEntity>> response) {
                    if (response.body() != null) {
                        emitter.onNext(response.body().getData());
                    } else {
                        emitter.onError(new NetworkRequestException(response.errorBody()));
                    }
                    emitter.onComplete();
                }

                @Override
                public void onFailure(Call<RestApiResponse<CatEntity>> call, Throwable t) {
                    emitter.onError(t);
                }
            });
        });
    }

    @Override
    public Observable<List<DogEntity>> dogEntityList() {
        return Observable.create(emitter -> {
            restApi.dogEntitiesList().enqueue(new Callback<RestApiResponse<DogEntity>>() {
                @Override
                public void onResponse(Call<RestApiResponse<DogEntity>> call, Response<RestApiResponse<DogEntity>> response) {
                    if (response.body() != null) {
                        emitter.onNext(response.body().getData());
                    } else {
                        emitter.onError(new NetworkRequestException(response.errorBody()));
                    }
                    emitter.onComplete();
                }

                @Override
                public void onFailure(Call<RestApiResponse<DogEntity>> call, Throwable t) {
                    emitter.onError(t);
                }
            });
        });
    }
}
