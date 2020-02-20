package com.example.myapplication.http;

import io.reactivex.observers.DisposableObserver;


/**
 * @author devel
 */
public abstract class HttpDisposable<T> extends DisposableObserver<T> {

    public HttpDisposable() {
    }

    @Override
    protected void onStart() {
    }

    @Override
    public void onNext(T value) {
        success(value);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public abstract void success(T t);
}
