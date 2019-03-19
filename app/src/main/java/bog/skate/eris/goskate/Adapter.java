package bog.skate.eris.goskate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static bog.skate.eris.goskate.R.layout.item_post;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    ArrayList<Model> models;

    public Adapter(Context c , ArrayList<Model> p)
    {
        context = c;
        models = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_post, viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
       myViewHolder.rider.setText(models.get(i).getRider());
       myViewHolder.truco.setText(models.get(i).getTruco());
       myViewHolder.spot.setText(models.get(i).getSpot());
       Picasso.get().load(models.get(i).getImage()).into(myViewHolder.imagepost);

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView rider, truco, spot;
        ImageView imagepost;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rider = (TextView) itemView.findViewById(R.id.riderpublic);
            truco = (TextView) itemView.findViewById(R.id.trucopublic);
            spot = (TextView) itemView.findViewById(R.id.spotpublic);
            imagepost = (ImageView) itemView.findViewById(R.id.imgpublic);
        }
    }

}
