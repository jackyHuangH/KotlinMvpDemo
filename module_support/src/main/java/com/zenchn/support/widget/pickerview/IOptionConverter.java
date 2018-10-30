package com.zenchn.support.widget.pickerview;

import java.util.List;

/**
 * 作    者：wangr on 2017/9/5 22:56
 * 描    述：
 * 修订记录：
 */

public interface IOptionConverter {

    List<String> convertOptions1(String source);

    List<List<String>> convertOptions2(String source);

    List<List<List<String>>> convertOptions3(String source);


}
