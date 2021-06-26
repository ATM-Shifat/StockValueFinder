package android.example.stockvaluefinder;

import android.media.session.MediaSession;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface myApi {
    public final String ap="";
    @GET("stable/stock/{id}/quote?token=pk_79eb67d745014cc184e0a6c8ffc96ab6")
    Call<model> getmodels(@Path("id") String symbol);
}
