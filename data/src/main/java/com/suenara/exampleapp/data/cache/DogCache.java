package com.suenara.exampleapp.data.cache;

import com.suenara.exampleapp.data.entity.DogEntity;

import java.util.List;

import io.reactivex.Observable;

public interface DogCache {

    Observable<List<DogEntity>> getAll();

    void putAll(Iterable<DogEntity> entities);

    boolean isExpired();

    void invalidateAll();

}
