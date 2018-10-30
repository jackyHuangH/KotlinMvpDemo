package com.zenchn.apilib.retrofit.converter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * 作    者：wangr on 2017/7/21 23:15
 * 描    述：
 * 修订记录：
 */

final class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

  private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
  private SerializeConfig serializeConfig;
  private SerializerFeature[] serializerFeatures;

  FastJsonRequestBodyConverter(SerializeConfig config, SerializerFeature... features) {
    serializeConfig = config;
    serializerFeatures = features;
  }

  @Override
  public RequestBody convert(T value) throws IOException {
    byte[] content;
    if (serializeConfig != null) {
      if (serializerFeatures != null) {
        content = JSON.toJSONBytes(value, serializeConfig, serializerFeatures);
      } else {
        content = JSON.toJSONBytes(value, serializeConfig);
      }
    } else {
      if (serializerFeatures != null) {
        content = JSON.toJSONBytes(value, serializerFeatures);
      } else {
        content = JSON.toJSONBytes(value);
      }
    }
    return RequestBody.create(MEDIA_TYPE, content);
  }
}
