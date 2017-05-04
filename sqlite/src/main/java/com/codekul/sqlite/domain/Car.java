package com.codekul.sqlite.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by aniruddha on 4/5/17.
 */

@Entity
public class Car {

    @Id
    private Long id;

    private String nm;

    @Generated(hash = 783209412)
    public Car(Long id, String nm) {
        this.id = id;
        this.nm = nm;
    }

    @Generated(hash = 625572433)
    public Car() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }
}
