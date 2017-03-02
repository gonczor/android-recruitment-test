package dog.snow.androidrecruittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import dog.snow.androidrecruittest.model.Item;
import dog.snow.androidrecruittest.rest.ApiClient;
import dog.snow.androidrecruittest.rest.ApiInterface;
import dog.snow.androidrecruittest.rest.ItemsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Item>> call = apiService.getItems();

        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                TextView textView = (TextView)findViewById(R.id.empty_list_tv);
                textView.setText(response.toString());
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e("TAG", t.getMessage());
            }
        });
    }
}
