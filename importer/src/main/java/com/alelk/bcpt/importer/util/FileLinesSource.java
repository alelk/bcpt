package com.alelk.bcpt.importer.util;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * File Lines Source
 *
 * Created by Alex Elkin on 05.10.2017.
 */
public class FileLinesSource implements ObservableSource<String> {

    private String file;

    public FileLinesSource(String file) {
        this.file = file;
    }

    @Override
    public void subscribe(@NonNull Observer<? super String> observer) {
        try {
            Files.lines(Paths.get(file)).forEach(observer::onNext);
            observer.onComplete();
        } catch (IOException exc) {
            observer.onError(exc);
        }
    }
}
