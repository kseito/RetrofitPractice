package kztproject.com.retrofitpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kztproject.com.retrofitpractice.models.GourmetData;
import kztproject.com.retrofitpractice.models.Shop;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://webservice.recruit.co.jp")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HotPepperService service = retrofit.create(HotPepperService.class);

        Call<GourmetData> call = service.getGourmetData(Environment.API_KEY, "Z012", "json");
        call.enqueue(new Callback<GourmetData>() {
            @Override
            public void onResponse(Response<GourmetData> response) {
                List<Shop> shopList = response.body().results.getShop();
                StringBuilder builder = new StringBuilder();

                for (Shop shop : shopList) {
                    builder.append(shop.getName() + "\n");
                }
                        ((TextView) findViewById(R.id.textview)).setText(new String(builder));
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
