package com.codekul.uithread.login;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by aniruddha on 19/5/17.
 */

public class LoginInteractor {

    private LoginPresenter presenter;

    public LoginInteractor(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    PublishSubject<UserDetails> sub = PublishSubject.create();
    public Observable<UserDetails> login(final String userName, final String password) {
        return Observable.create(em -> {
            if (userName == null) {
                em.onError(new Throwable("Invalid UserName"));
            } else if (password == null) {
                em.onError(new Throwable("Invalid Password"));
            } else {
                em.onNext(new UserDetails(userName, password));
                em.onComplete();
            }
        });
    }
}
