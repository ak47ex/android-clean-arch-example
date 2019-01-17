package com.suenara.exampleapp.data.repository.datasourse;

import com.suenara.exampleapp.data.entity.CatEntity;
import com.suenara.exampleapp.data.entity.DogEntity;

import java.util.List;

import io.reactivex.Observable;

public interface PetDataStore {

    Observable<List<CatEntity>> catEntityList();

    Observable<List<DogEntity>> dogEntityList();

}
