package mx.test.android.gonet.main.ui.extensions

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.applySchedulers( funOnSubscribe: () -> Unit = {} ): Observable<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { funOnSubscribe.invoke() }
}

fun <T> Single<T>.applySchedulers(funOnSubscribe: () -> Unit = {}): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}