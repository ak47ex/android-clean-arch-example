package com.suenara.exampleapp.data.entity.mapper;

import com.suenara.exampleapp.data.entity.CatEntity;
import com.suenara.exampleapp.domain.entity.Cat;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CatEntityDataMapper {

    @Inject
    public CatEntityDataMapper() {
        super();
    }

    public Cat map(CatEntity catEntity) {
        if (catEntity == null) return null;

        return new Cat(catEntity.getTitle(), catEntity.getImageUrl());
    }

    public List<Cat> map(Iterable<CatEntity> catCollection) {
        final List<Cat> catList = new LinkedList<>();
        for (CatEntity entity : catCollection) {
            Cat cat = map(entity);
            if (cat != null) {
                catList.add(cat);
            }
        }

        return catList;
    }
}
