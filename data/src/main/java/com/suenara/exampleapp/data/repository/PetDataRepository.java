package com.suenara.exampleapp.data.repository;

import com.suenara.exampleapp.data.entity.mapper.CatEntityDataMapper;
import com.suenara.exampleapp.data.entity.mapper.DogEntityDataMapper;
import com.suenara.exampleapp.data.repository.datasourse.PetDataStore;
import com.suenara.exampleapp.data.repository.datasourse.PetDataStoreFactory;
import com.suenara.exampleapp.domain.entity.Cat;
import com.suenara.exampleapp.domain.entity.Dog;
import com.suenara.exampleapp.domain.repository.PetRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PetDataRepository implements PetRepository {

    private final PetDataStoreFactory petDataStoreFactory;
    private final CatEntityDataMapper catEntityDataMapper;
    private final DogEntityDataMapper dogEntityDataMapper;

    @Inject
    public PetDataRepository(PetDataStoreFactory dataStoreFactory,
                             CatEntityDataMapper catEntityDataMapper,
                             DogEntityDataMapper dogEntityDataMapper) {

        this.petDataStoreFactory = dataStoreFactory;
        this.catEntityDataMapper = catEntityDataMapper;
        this.dogEntityDataMapper = dogEntityDataMapper;
    }

    @Override
    public Observable<List<Dog>> dogs() {
        PetDataStore petDataStore = petDataStoreFactory.create();
        return petDataStore.dogEntityList().map(dogEntityDataMapper::map);
    }

    @Override
    public Observable<List<Cat>> cats() {
        PetDataStore petDataStore = petDataStoreFactory.create();
        return petDataStore.catEntityList().map(catEntityDataMapper::map);
    }
}
