package com.moviesdb.moviesdbmvvm.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.CLASS)//in time of compilation
public @interface MovieDBApplicationScope {
}
