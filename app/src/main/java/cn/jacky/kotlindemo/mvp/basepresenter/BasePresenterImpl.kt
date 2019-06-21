package cn.jacky.kotlindemo.mvp.basepresenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.util.Log
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl.Constant.TAG
import cn.jacky.kotlindemo.mvp.baseview.IView
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
    object Constant {
        const val TAG = "BasePresenter"
    }

    private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
    protected var mView: V? = null

    constructor(mView: V?) {
        this.mView = mView
    }

    override fun onCreate(owner: LifecycleOwner) {
        //do Something
        Log.d(TAG, "onCreate")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.d(TAG, "onDestroy")
        mView = null
        releaseDisposable()
    }

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
        Log.d(TAG, "onLifecycleChanged:" + event.name)
    }

    override fun getToken(): String {
        //do getToken
        return ""
    }

    override fun onApiGrantRefuse() {
        mView?.let {
            it.hideProgress()
            it.onApiGrantRefuse()
        }
    }

    override fun onApiFailure(err_msg: String?) {
        err_msg?.let {
            mView!!.hideProgress()
            mView!!.onApiFailure(err_msg)
        }
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