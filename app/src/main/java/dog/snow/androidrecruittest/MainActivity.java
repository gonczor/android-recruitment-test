package dog.snow.androidrecruittest;

import java.io.IOException;
import java.util.List;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import dog.snow.androidrecruittest.database.ItemDatabaseHelper;
import dog.snow.androidrecruittest.model.Item;
import dog.snow.androidrecruittest.model.error.ErrorResponse;
import dog.snow.androidrecruittest.rest.ApiClient;
import dog.snow.androidrecruittest.rest.ApiInterface;
import dog.snow.androidrecruittest.views.ErrorAdapter;
import dog.snow.androidrecruittest.views.ItemAdapter;


public class MainActivity extends AppCompatActivity {
    private ItemDatabaseHelper helper;
    private SwipeRefreshLayout swipeContainer;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // helper MUST be created at the beginning since both downloadItems() and
        // setupEditorAction() use database
        helper = new ItemDatabaseHelper.Builder()
                .setContext(this)
                .build();
        downloadItems();
        showAllItems();
        setupSwipeContainer();
        setupEditorAction();
    }

    private void setupSwipeContainer(){
        swipeContainer =  (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadItems();
                showAllItems();
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void setupEditorAction(){
        final EditText search = (EditText) findViewById(R.id.search_et);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction()==KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)){
                    String toSearch = search.getText().toString();
                    List<Item> items = helper.selectMatchingItems(toSearch);
                    showItems(items);
                    return true;
                }
                return false;
            }
        });
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
        helper.insertItems(response.body());
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

    private void showAllItems(){
        List<Item> items = helper.selectAllItems();
        showItems(items);
    }

    private void showItems(List<Item> items){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.response_rv);
        if(itemAdapter == null)
            itemAdapter = new ItemAdapter();
        itemAdapter.clear();
        itemAdapter.addAll(items);
        setupRecyclerView(recyclerView, itemAdapter);
    }

    private void setupRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}