package sahil.clickclean.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import sahil.clickclean.R;
import sahil.clickclean.interfaces.RCVItemClickListener;
import sahil.clickclean.model.OrderList;
import java.util.ArrayList;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {

    private ArrayList<OrderList> listOrderLists;
    private RCVItemClickListener rcvItemClickListener;
    Context context1;
    public OrderListAdapter(Context context, ArrayList<OrderList> listOrderLists) {
        context1 = context;
        this.listOrderLists = listOrderLists;
    }

    public void setRcvItemClickListener(RCVItemClickListener rcvItemClickListener) {
        this.rcvItemClickListener = rcvItemClickListener;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_orders, parent, false);
        return new OrderListViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListViewHolder holder, final int position) {
        final OrderList current = listOrderLists.get(position);



    }


    @Override
    public int getItemCount() {
        return listOrderLists.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView name, count;

        private OrderListViewHolder(View itemView) {
            super(itemView);



        }

        @Override
        public void onClick(View v) {
            if (rcvItemClickListener != null) {
                rcvItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }



}