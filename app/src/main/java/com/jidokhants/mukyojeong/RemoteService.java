package com.jidokhants.mukyojeong;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RemoteService {
    @GET("muk")
    Call<person> getName();
}
