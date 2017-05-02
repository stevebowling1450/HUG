package com.interapt.togglit.data.remote;

import com.interapt.togglit.common.ISharedPreferencesManager;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by Matthew.Watson on 3/1/17.
 */

public class SessionRequestInterceptor implements Interceptor {

    private final ISharedPreferencesManager mSharedPreferencesManager;

    public SessionRequestInterceptor(ISharedPreferencesManager mSharedPreferencesManager) {
        this.mSharedPreferencesManager = mSharedPreferencesManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (mSharedPreferencesManager.getToken() != null) {
            Request.Builder builder = request.newBuilder();
            builder.header("Authorization",
                    mSharedPreferencesManager.getToken());
            request = builder.build();
        } else {
            Timber.e("Intercept FAILED");
        }

        return chain.proceed(request);
    }
}
