package kztproject.com.retrofitpractice;

import kztproject.com.retrofitpractice.models.GourmetData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by k-seito on 2016/01/28.
 */
public interface HotPepperService {
    @GET("/hotpepper/gourmet/v1/")
    Call<GourmetData> getGourmetData(@Query("key") String key, @Query("large_area") String area, @Query("format") String format);
}
