package com.suenara.exampleapp.data.cache;

import com.suenara.exampleapp.data.entity.CatEntity;
import com.suenara.exampleapp.data.entity.DogEntity;

import java.util.List;

import io.reactivex.Observable;

public interface CatCache {

    Observable<List<CatEntity>> getAll();

    void putAll(Iterable<CatEntity> entities);

    boolean isExpired();

    void invalidateAll();

}
