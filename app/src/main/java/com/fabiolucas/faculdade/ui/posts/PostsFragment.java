package com.fabiolucas.faculdade.ui.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fabiolucas.faculdade.R;
import com.fabiolucas.faculdade.data.model.Albums;
import com.fabiolucas.faculdade.data.model.DadosJSON;
import com.fabiolucas.faculdade.data.model.Posts;
import com.fabiolucas.faculdade.retrofit.Request;
import com.fabiolucas.faculdade.ui.slideshow.SlideshowViewModel;
import com.fabiolucas.faculdade.ui.todos.TodosFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostsFragment extends Fragment {

    private PostsViewModel postsFragmentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        postsFragmentViewModel =
                ViewModelProviders.of(this).get(PostsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_posts, container, false);
        final TextView textView = root.findViewById(R.id.text_posts);
        postsFragmentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Retrofit retrofit = DadosJSON.DadosJSON();

        Request request = retrofit.create(Request.class);

        Call<List<Posts>> get = request.getPosts();

        get.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {

                List<Posts> posts = response.body();

                List<HashMap<String, String>> hashMaps = new ArrayList<>();

                HashMap<String, String> map = new HashMap<>();

                for (int c = 0; c < posts.size(); c++) {

                    map.put("titulo", posts.get(c).getTitle());
                    map.put("userid", String.valueOf(posts.get(c).getUserId()));
                    map.put("id", String.valueOf(posts.get(c).getId()));
                    map.put("body", posts.get(c).getBody());

                }

                hashMaps.add(map);

                String[] de = {"titulo", "userid", "id", "body"};
                int[] para = {R.id.txtPostsTitle, R.id.txtPostsUserId, R.id.txtPostsId, R.id.txtPostsBody};

                ListView listarDadosJSON = view.findViewById(R.id.listaDadosPosts);

                //SIMPLE ADAPTER
                SimpleAdapter adapter =
                        new SimpleAdapter(getContext(), hashMaps,
                                R.layout.posts, de, para);


                listarDadosJSON.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Toast.makeText(getContext(), "Deu ruim" + t.getMessage() + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
