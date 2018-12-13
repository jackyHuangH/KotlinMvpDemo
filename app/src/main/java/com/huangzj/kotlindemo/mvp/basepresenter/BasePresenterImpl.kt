package com.huangzj.kotlindemo.mvp.basepresenter

import com.huangzj.kotlindemo.mvp.baseview.IView
import com.zenchn.apilib.callback.rx.RxApiCallback
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
abstract class BasePresenterImpl<V : IView> : IPresenter, RxApiCallback {

    private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
    protected var mView: V? = null

    constructor(mView: V?) {
        this.mView = mView
    }

    override fun onStart() {
        //do Something
    }

    override fun onDestroy() {
        mView = null
        releaseDisposable()
    }

    override fun getToken(): String {
        //do getToken
        return ""
    }

    override fun onApiGrantRefuse() {
        mView?.let { mView!!.onApiGrantRefuse() }
    }

    override fun onApiFailure(err_msg: String?) {
        err_msg?.let { mView!!.showMessage(err_msg) }
    }

    override fun onRegisterObserver(disposable: Disposable?) {
        disposable?.let { mCompositeDisposable.add(it) }
    }

    private fun releaseDisposable() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.clear()
        }
    }
}