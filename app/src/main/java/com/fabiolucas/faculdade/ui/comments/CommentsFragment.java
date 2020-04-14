package com.fabiolucas.faculdade.ui.comments;

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
import com.fabiolucas.faculdade.data.model.Comments;
import com.fabiolucas.faculdade.data.model.DadosJSON;
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

public class CommentsFragment extends Fragment {

    private CommentsViewModel commentsFragmentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        commentsFragmentViewModel =
                ViewModelProviders.of(this).get(CommentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_comments, container, false);
        final TextView textView = root.findViewById(R.id.text_comments);
        commentsFragmentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

        Call<List<Comments>> get = request.getComments();

        get.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {

                List<Comments> comments = response.body();

                List<HashMap<String, String>> hashMaps = new ArrayList<>();

                HashMap<String, String> map = new HashMap<>();

                for (int c = 0; c < comments.size(); c++) {


                    map.put("nome", comments.get(c).getName());
                    map.put("email", comments.get(c).getEmail());
                    map.put("body", comments.get(c).getBody());
                    map.put("id", String.valueOf(comments.get(c).getId()));
                    map.put("postid", String.valueOf(comments.get(c).getPostId()));

                }

                hashMaps.add(map);

                String[] de = {"nome", "email", "body", "id", "postid"};
                int[] para = {R.id.txtCommentsName, R.id.txtCommentsEmail, R.id.txtCommentsBody, R.id.txtCommentsId, R.id.txtCommentsPostId};

                ListView listarDadosJSON = view.findViewById(R.id.listaDadosComments);

                //SIMPLE ADAPTER
                SimpleAdapter adapter =
                        new SimpleAdapter(getContext(), hashMaps,
                                R.layout.comments, de, para);


                listarDadosJSON.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                Toast.makeText(getContext(), "Deu ruim" + t.getMessage() + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
