package android.example.stockvaluefinder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Modelclassfor_recycle> userlist;
    public String com;
    double price;
    Context context;


    public  Adapter(List<Modelclassfor_recycle>userlist, Context cont){this.userlist=userlist;this.context=cont;}
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.ViewHolder holder, int position) {
        com=userlist.get(position).getCompany_name();
         price=userlist.get(position).getPrice();
        Double change= userlist.get(position).getChangePercent();
        holder.setData(com,price,change);
        Button button=holder.button;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,buying_page.class);
                intent.putExtra("cname",userlist.get(position).getCompany_name());
                intent.putExtra("price",userlist.get(position).getPrice());
                intent.putExtra("ticker",userlist.get(position).getTicker());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView,textView1,textView3;
        public Button button=itemView.findViewById(R.id.buy_button);

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            Context context=itemView.getContext();
            textView=itemView.findViewById(R.id.textview1);
            textView1=itemView.findViewById(R.id.textview2);
            textView3=itemView.findViewById(R.id.textView3);
        }


        public void setData(String com, double price, double change ) {
            textView.setText(""+com);
            textView1.setText("$"+price);
            if (change>0.00)
            {
                textView3.setBackgroundColor(Color.GREEN);
            }
            else if(change==0.0)
            {
                textView3.setBackgroundColor(Color.GRAY);
            }
            else
            {
                textView3.setBackgroundColor(Color.RED);
            }
            textView3.setText(""+change+"%");
        }

    }

}

