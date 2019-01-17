package com.suenara.exampleapp.data.repository.datasourse;

import android.content.Context;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PetDataStoreFactory {

    private final Context context;

    @Inject
    public PetDataStoreFactory(@NonNull Context context) {
        this.context = context;
    }

    public PetDataStore create() {
        return createRemoteDataStore();
    }

    public PetDataStore createRemoteDataStore() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

        return new RemotePetDataStore(mapper);
    }
}
