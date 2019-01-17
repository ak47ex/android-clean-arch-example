package com.suenara.exampleapp.domain.interactor;

import com.suenara.exampleapp.domain.entity.Dog;
import com.suenara.exampleapp.domain.executor.PostExecutionThread;
import com.suenara.exampleapp.domain.executor.ThreadExecutor;
import com.suenara.exampleapp.domain.repository.PetRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetDogList extends UseCase<List<Dog>, Void> {

    private final PetRepository petRepository;

    @Inject
    GetDogList(PetRepository petRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.petRepository = petRepository;
    }

    @Override
    Observable<List<Dog>> buildUseCaseObservable(Void aVoid) {
        return petRepository.dogs();
    }
}
