package dog.snow.androidrecruittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import dog.snow.androidrecruittest.database.ItemDatabaseHelper;
import dog.snow.androidrecruittest.model.Item;
import dog.snow.androidrecruittest.model.error.ErrorResponse;
import dog.snow.androidrecruittest.rest.ApiClient;
import dog.snow.androidrecruittest.rest.ApiInterface;
import dog.snow.androidrecruittest.views.ErrorAdapter;
import dog.snow.androidrecruittest.views.ItemAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ItemDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new ItemDatabaseHelper.Builder()
                .setContext(this)
                .build();
        downloadItems();
        showItemsFromDatabase();
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
        helper.openDatabase();

        for(Item i : response.body()){
            helper.insertItem(i);
        }

        helper.closeDatabase();
    }

    private void onDownloadItemsHandleUnsuccessfulResponse(Response<List<Item>> response){
        try {
            Gson gson = new Gson();
            ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(),
                    ErrorResponse.class);

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.response_rv);
            ErrorAdapter adapter = new ErrorAdapter(errorResponse);
            setupRecyclerView(recyclerView, adapter);

        } catch (IOException e) {
            Log.e("UNSUCCESSFUL RESPONSE", e.getMessage());
        }
    }

    private void showItemsFromDatabase(){
        List<Item> items = helper.selectAllItems();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.response_rv);
        ItemAdapter itemAdapter = new ItemAdapter(items);
        setupRecyclerView(recyclerView, itemAdapter);
    }

    private void setupRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}