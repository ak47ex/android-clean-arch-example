package com.suenara.exampleapp.presentation.mapper;

import com.suenara.exampleapp.domain.entity.Dog;
import com.suenara.exampleapp.presentation.internal.di.PerActivity;
import com.suenara.exampleapp.presentation.model.DogModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class DogDataMapper {

    @Inject
    public DogDataMapper() {}

    public DogModel map(Dog dog) {
        if (dog == null) {
            throw new IllegalArgumentException("Cannot map a null value");
        }

        return new DogModel(dog.getTitle(), dog.getUrl());
    }

    public Collection<DogModel> map(Collection<Dog> dogCollection) {
        if (dogCollection == null || dogCollection.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<DogModel> modelList = new ArrayList<>(dogCollection.size());
        for (Dog dog : dogCollection) {
            modelList.add(map(dog));
        }

        return modelList;
    }
}
