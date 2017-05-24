package com.codekul.uithread.login;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aniruddha on 19/5/17.
 */

public class LoginPresenter {

    LoginInteractor loginInteractor = new LoginInteractor(this);

    public void login(String userName, String userPassword) {
        loginInteractor.login(userName, userPassword)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(userDetails -> {
                })
                .doOnError(err -> {

                })
                .doOnComplete(() -> {
                })
                .subscribe();
    }
}
