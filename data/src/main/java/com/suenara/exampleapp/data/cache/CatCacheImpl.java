package com.suenara.exampleapp.data.cache;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.suenara.exampleapp.data.cache.serializer.Serializer;
import com.suenara.exampleapp.data.entity.CatEntity;
import com.suenara.exampleapp.data.exception.CacheNotFoundException;
import com.suenara.exampleapp.domain.executor.ThreadExecutor;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class CatCacheImpl implements CatCache {

    private static final String DEFAULT_FILE_NAME = "cats";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;


    private final Context context;
    private final File cacheDir;
    private final Serializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    @Inject
    public CatCacheImpl(Context context, Serializer serializer, FileManager fileManager, ThreadExecutor executor) {
        if (context == null || serializer == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.serializer = serializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    @Override
    public Observable<List<CatEntity>> getAll() {
        return Observable.create(emitter -> {
            final File catEntityFile = CatCacheImpl.this.buildFile();
            final String fileContent = CatCacheImpl.this.fileManager.readFileToString(catEntityFile);
            final List<CatEntity> catEntities =
                    CatCacheImpl.this.serializer.deserialize(fileContent, new TypeReference<List<CatEntity>>(){});

            if (catEntities != null) {
                emitter.onNext(catEntities);
                emitter.onComplete();
            } else {
                emitter.onError(new CacheNotFoundException());
            }
        });
    }

    @Override
    public void putAll(Iterable<CatEntity> entities) {
        if (entities == null) return;

        final File catCacheFile;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public void invalidateAll() {

    }

    private File buildFile() {
        final StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);

        return new File(fileNameBuilder.toString());
    }
}
