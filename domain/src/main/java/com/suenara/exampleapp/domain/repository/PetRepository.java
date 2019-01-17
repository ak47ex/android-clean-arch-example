package com.suenara.exampleapp.domain.repository;

import com.suenara.exampleapp.domain.entity.Cat;
import com.suenara.exampleapp.domain.entity.Dog;

import java.util.List;

import io.reactivex.Observable;

public interface PetRepository {

    Observable<List<Dog>> dogs();

    Observable<List<Cat>> cats();

}
