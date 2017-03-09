package com.maksimov.transformers;

/**
 * Created on 03.03.17.
 */
public interface ViewTransformer<T, S> {

    T transform(S object);

}
