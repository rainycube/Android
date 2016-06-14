package com.rainycube.petbuddy;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by SBKim on 2016-06-14.
 */
public class JsonStringConverterFactory extends Converter.Factory {
    private final Converter.Factory delegateFactory;

    @Retention(RUNTIME)
        @interface Json {
    }

    public JsonStringConverterFactory(Converter.Factory delegateFactory) {
        this.delegateFactory = delegateFactory;
    }

    @Override public Converter<?, String> stringConverter(Type type, Annotation[] annotations,
                                                          Retrofit retrofit) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof Json) {
                // NOTE: If you also have a JSON converter factory installed in addition to this factory,
                // you can call retrofit.requestBodyConverter(type, annotations) instead of having a
                // reference to it explicitly as a field.
                Converter<?, RequestBody> delegate =
                        delegateFactory.requestBodyConverter(type, annotations, new Annotation[0], retrofit);
                return new DelegateToStringConverter<>(delegate);
            }
        }
        return null;
    }

    static class DelegateToStringConverter<T> implements Converter<T, String> {
        private final Converter<T, RequestBody> delegate;

        DelegateToStringConverter(Converter<T, RequestBody> delegate) {
            this.delegate = delegate;
        }

        @Override public String convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            delegate.convert(value).writeTo(buffer);
            return buffer.readUtf8();
        }
    }
}
