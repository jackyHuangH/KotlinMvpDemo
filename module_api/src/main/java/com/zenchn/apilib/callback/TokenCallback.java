package com.zenchn.apilib.callback;


import com.zenchn.apilib.entity.LoginInfoEntity;
import com.zenchn.apilib.entity.TokenEntity;

/**
 * 作    者：wangr on 2017/6/19 12:07
 * 描    述：
 * 修订记录：
 */

public interface TokenCallback extends FailureCallback {

    void onGetTokenSuccess(LoginInfoEntity loginInfo, TokenEntity tokenInfo);

    void onRefreshTokenSuccess(TokenEntity tokenInfo);
}
