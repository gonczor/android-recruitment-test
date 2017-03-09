package dog.snow.androidrecruittest.views;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dog.snow.androidrecruittest.R;
import dog.snow.androidrecruittest.model.error.ErrorResponse;

public class ErrorAdapter extends RecyclerView.Adapter<ErrorAdapter.ViewHolder>{
    private ErrorResponse errorResponse;

    public ErrorAdapter(ErrorResponse response){
        this.errorResponse = response;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View errorView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.error, parent, false);
        return new ErrorAdapter.ViewHolder(errorView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.message.setText(errorResponse.getError().getMessage());
        holder.status.setText(errorResponse.getError().getErrorStatus().getStatus().toString());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView message, status;
        ViewHolder(View errorView) {
            super(errorView);
            message = (TextView) errorView.findViewById(R.id.error_message);
            status = (TextView) errorView.findViewById(R.id.error_code);
        }
    }
}
