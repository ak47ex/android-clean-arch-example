package com.suenara.exampleapp.presentation.mapper;

import com.suenara.exampleapp.domain.entity.Cat;
import com.suenara.exampleapp.presentation.internal.di.PerActivity;
import com.suenara.exampleapp.presentation.model.CatModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class CatDataMapper {

    @Inject
    public CatDataMapper() {}

    public CatModel map(Cat cat) {
        if (cat == null) {
            throw new IllegalArgumentException("Cannot map a null value");
        }

        return new CatModel(cat.getTitle(), cat.getUrl());
    }

    public Collection<CatModel> map(Collection<Cat> catCollection) {
        if (catCollection == null || catCollection.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<CatModel> modelList = new ArrayList<>(catCollection.size());
        for (Cat cat : catCollection) {
            modelList.add(map(cat));
        }

        return modelList;
    }
}
