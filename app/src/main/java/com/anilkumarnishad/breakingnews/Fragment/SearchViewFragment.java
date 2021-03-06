package com.anilkumarnishad.breakingnews.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anilkumarnishad.breakingnews.Activity.MainActivity;
import com.anilkumarnishad.breakingnews.Adapters.TapnewsAdapter;
import com.anilkumarnishad.breakingnews.Article;
import com.anilkumarnishad.breakingnews.Constants;
import com.anilkumarnishad.breakingnews.CustomLoader;
import com.anilkumarnishad.breakingnews.GetDataService;
import com.anilkumarnishad.breakingnews.News;
import com.anilkumarnishad.breakingnews.R;
import com.anilkumarnishad.breakingnews.RetrofitClientInstance;
import com.anilkumarnishad.breakingnews.SwipeHelper;
import com.anilkumarnishad.breakingnews.UtilMethods;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


public class SearchViewFragment extends Fragment {
    TapnewsAdapter adapter;
    RecyclerView recyclerView;
    private List<Article> articles = new ArrayList<>();
    String strtext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_view, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        strtext = getArguments().getString("query");
        loadData();
        return view;
    }
    public void loadData() {
        final CustomLoader customLoader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        customLoader.show();
        GetDataService apiInterface = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        String country = UtilMethods.getCountry();
        int page = 99;
        Call<News> call;
        call = apiInterface.getNews(strtext, Constants.API_KEY,page);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                customLoader.dismiss();
                if (response.isSuccessful() && response.body().getArticles().size() != 0) {
                    articles = response.body().getArticles();
                    adapter = new TapnewsAdapter(getActivity(),articles);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    UtilMethods.hideKeyboard(getActivity());
                    ItemTouchHelper.Callback callback = new SwipeHelper(adapter);
                    ItemTouchHelper helper = new ItemTouchHelper(callback);
                    helper.attachToRecyclerView(recyclerView);

                } else {
                    UtilMethods.Error(getActivity(), " No result found ");
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
            }
        });
    }


}