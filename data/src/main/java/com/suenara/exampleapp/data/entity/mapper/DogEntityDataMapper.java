package com.suenara.exampleapp.data.entity.mapper;

import com.suenara.exampleapp.data.entity.DogEntity;
import com.suenara.exampleapp.domain.entity.Dog;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DogEntityDataMapper {

    @Inject
    public DogEntityDataMapper() {
        super();
    }

    public Dog map(DogEntity dogEntity) {
        if (dogEntity == null) return null;

        return new Dog(dogEntity.getTitle(), dogEntity.getImageUrl());
    }

    public List<Dog> map(Iterable<DogEntity> dogCollection) {
        final List<Dog> dogList = new LinkedList<>();
        for (DogEntity entity : dogCollection) {
            Dog dog = map(entity);
            if (dog != null) {
                dogList.add(dog);
            }
        }

        return dogList;
    }
}
