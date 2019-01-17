package com.suenara.exampleapp.domain.interactor;

import com.suenara.exampleapp.domain.entity.Cat;
import com.suenara.exampleapp.domain.executor.PostExecutionThread;
import com.suenara.exampleapp.domain.executor.ThreadExecutor;
import com.suenara.exampleapp.domain.repository.PetRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetCatList extends UseCase<List<Cat>, Void> {

    private final PetRepository petRepository;

    @Inject
    GetCatList(PetRepository petRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.petRepository = petRepository;
    }

    @Override
    Observable<List<Cat>> buildUseCaseObservable(Void aVoid) {
        return petRepository.cats();
    }
}
