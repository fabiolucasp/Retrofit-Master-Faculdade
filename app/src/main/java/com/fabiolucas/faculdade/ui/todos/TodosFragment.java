package com.fabiolucas.faculdade.ui.todos;

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
import com.fabiolucas.faculdade.data.model.Todos;
import com.fabiolucas.faculdade.retrofit.Request;
import com.fabiolucas.faculdade.ui.slideshow.SlideshowViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TodosFragment extends Fragment {

    private TodosViewModel todosFragmentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        todosFragmentViewModel =
                ViewModelProviders.of(this).get(TodosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_todos, container, false);
        final TextView textView = root.findViewById(R.id.text_todos);
        todosFragmentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

        Call<List<Todos>> get = request.getTodos();

        get.enqueue(new Callback<List<Todos>>() {
            @Override
            public void onResponse(Call<List<Todos>> call, Response<List<Todos>> response) {

                List<Todos> todos = response.body();

                List<HashMap<String, String>> hashMaps = new ArrayList<>();

                HashMap<String, String> map = new HashMap<>();

                for (int c = 0; c < todos.size(); c++) {

                    map.put("titulo", todos.get(c).getTitle());
                    map.put("userid", String.valueOf(todos.get(c).getUserId()));
                    map.put("id", String.valueOf(todos.get(c).getId()));
                    map.put("completed", todos.get(c).getTitle());

                }

                hashMaps.add(map);

                String[] de = {"titulo", "userid", "id", "completed"};
                int[] para = {R.id.txtTodosTitle, R.id.txtTodosUserId, R.id.txtTodosId, R.id.txtTodosCompleted};

                ListView listarDadosJSON = view.findViewById(R.id.listaDadosTodos);

                //SIMPLE ADAPTER
                SimpleAdapter adapter =
                        new SimpleAdapter(getContext(), hashMaps,
                                R.layout.todos, de, para);


                listarDadosJSON.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<Todos>> call, Throwable t) {
                Toast.makeText(getContext(), "Deu ruim" + t.getMessage() + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
