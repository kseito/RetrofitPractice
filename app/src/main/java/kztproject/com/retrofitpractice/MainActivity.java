package kztproject.com.retrofitpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import kztproject.com.retrofitpractice.models.GourmetData;
import kztproject.com.retrofitpractice.models.Shop;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://webservice.recruit.co.jp")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HotPepperService service = retrofit.create(HotPepperService.class);

        Observable<GourmetData> observable = service.getGourmetData(Environment.API_KEY, "Z012", "json");
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<GourmetData, List<Shop>>() {
                    @Override
                    public List<Shop> call(GourmetData gourmetData) {
                        return gourmetData.results.getShop();
                    }
                })
                .subscribe(new Subscriber<List<Shop>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Completed!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Error:" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Shop> shops) {
                        StringBuilder builder = new StringBuilder();

                        for (Shop shop : shops) {
                            builder.append(shop.getName() + "\n");
                        }
                        ((TextView) findViewById(R.id.textview)).setText(new String(builder));
                    }
                });

    }
}
