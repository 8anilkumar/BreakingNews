package com.anilkumarnishad.breakingnews.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.anilkumarnishad.breakingnews.Activity.FullNews;
import com.anilkumarnishad.breakingnews.Article;
import com.anilkumarnishad.breakingnews.R;
import com.anilkumarnishad.breakingnews.UtilMethods;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class TapnewsAdapter extends RecyclerView.Adapter<TapnewsAdapter.viewHolder> implements Filterable {

    private Context context;
    private List<Article> itemModels;
    private List<Article> exampleListFull;
    public TapnewsAdapter(Context context, List<Article> addressModels) {
        this.context = context;
        this.itemModels = addressModels;
        exampleListFull = new ArrayList<>(itemModels);
    }

    @NonNull
    @Override
    public TapnewsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview, parent, false);
        return  new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TapnewsAdapter.viewHolder viewHolder, final int position) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(UtilMethods.getRandomDrawbleColor());
        requestOptions.error(UtilMethods.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        final Article model = itemModels.get(position);
        String date = UtilMethods.DateFormat(model.getPublishedAt());
        String title = itemModels.get(position).getTitle();
        String detail = itemModels.get(position).getDescription();
        String hoster =  itemModels.get(position).getSource().getName();
        String url = itemModels.get(position).getUrl();
        String content =  itemModels.get(position).getContent();

        Glide.with(context)
                .load(model.getUrlToImage())
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(viewHolder.imageView);

        viewHolder.setData(date,title,detail,hoster);
        viewHolder.grid_view_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, FullNews.class);
                intent.putExtra("news detail",url);
                context.startActivity(intent);
            }
        });

        viewHolder.shareimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                context.startActivity(Intent.createChooser(shareIntent, "choose one"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView date;
        TextView title;
        TextView detail;
        TextView hoster;
        ImageView shareimage;
        TextView readMore;
        CardView grid_view_items;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            date = itemView.findViewById(R.id.dateFormet);
            title = itemView.findViewById(R.id.title);
            detail = itemView.findViewById(R.id.detail);
            hoster = itemView.findViewById(R.id.hoster);
            shareimage = itemView.findViewById(R.id.shareImage);
            readMore = itemView.findViewById(R.id.readMore);
            grid_view_items = itemView.findViewById(R.id.contectLinearLayou);
        }

        private void setData(String currentDate, String newstitle, String newsdetail, String newshoster) {
            date.setText(currentDate);
            title.setText(newstitle);
            detail.setText(newsdetail);
            hoster.setText(newshoster);
        }
    }

    public void  dismissItem (int position){
        itemModels.remove(position);
        this.notifyItemRemoved(position);
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Article> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Article item : exampleListFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemModels.clear();
            itemModels.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
