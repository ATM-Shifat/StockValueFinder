package android.example.stockvaluefinder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Adapter_for_user_profile extends RecyclerView.Adapter<Adapter_for_user_profile.ViewHolder> {
    public List<stocks>user_stocks;
    Context context;
    String com;
    double price;
    int quntity;

    public Adapter_for_user_profile(List<stocks> user_stocks, Context context) {
        this.user_stocks = user_stocks;
        this.context = context;
    }

    @NotNull
    @Override
    public Adapter_for_user_profile.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.userprofile_recycle,parent,false);
        return new Adapter_for_user_profile.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NotNull Adapter_for_user_profile.ViewHolder holder, int position) {
        com=user_stocks.get(position).getCompany();
        price=user_stocks.get(position).getBuy_price();
        quntity=user_stocks.get(position).getCount();
        holder.setData(com,price,quntity);
        Button button=holder.button11;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Selling_Page.class);
                intent.putExtra("cname",user_stocks.get(position).getCompany());
                intent.putExtra("ticker",user_stocks.get(position).getTicker());
                intent.putExtra("count",user_stocks.get(position).getCount());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return user_stocks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView, textView1, textView3;
        public Button button11 = itemView.findViewById(R.id.sell_button);

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.usercname);
            textView1 = itemView.findViewById(R.id.userprice);
            textView3 = itemView.findViewById(R.id.usercount);
        }

        public void setData(String com, double price, int quantity) {
            textView.setText("" + com);
            textView1.setText("$" + price);
            textView3.setText(""+quantity);
        }
    }
}
