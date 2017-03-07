package dog.snow.androidrecruittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import dog.snow.androidrecruittest.model.Item;
import dog.snow.androidrecruittest.rest.ApiClient;
import dog.snow.androidrecruittest.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadItems();
    }

    private void downloadItems(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        apiService.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    onDownloadItemsHandleSuccessfulResponse(response);
                } else {
                    onDownloadItemsHandleUnsuccessfulResponse(response);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e("MainActivity", t.getMessage());
            }
        });
    }

    private void onDownloadItemsHandleSuccessfulResponse(Response<List<Item>> response){
        TextView tv = (TextView) findViewById(R.id.empty_list_tv);
        tv.setText(response.body().toString());
    }

    private void onDownloadItemsHandleUnsuccessfulResponse(Response<List<Item>> response){
        TextView tv = (TextView) findViewById(R.id.empty_list_tv);
        try {
            tv.setText(response.errorBody().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}