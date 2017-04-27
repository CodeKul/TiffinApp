package com.codekul.internalstorage;

import android.app.Application;

/**
 * Created by aniruddha on 27/4/17.
 */

public class App extends Application {

    private Car car;

    public Car car() {
        return car == null ? car = new Car() : car;
    }
}
