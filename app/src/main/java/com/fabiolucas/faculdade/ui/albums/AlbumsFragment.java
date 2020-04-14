package com.fabiolucas.faculdade.ui.albums;

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
import com.fabiolucas.faculdade.retrofit.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AlbumsFragment extends Fragment {

    private AlbumsViewModel albumsFragmentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        albumsFragmentViewModel =
                ViewModelProviders.of(this).get(AlbumsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_albums, container, false);
        final TextView textView = root.findViewById(R.id.text_albums);
        albumsFragmentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

        Call<List<Albums>> get = request.getAlbums();

        get.enqueue(new Callback<List<Albums>>() {
            @Override
            public void onResponse(Call<List<Albums>> call, Response<List<Albums>> response) {

                List<Albums> albums = response.body();

                List<HashMap<String, String>> hashMaps = new ArrayList<>();

                HashMap<String, String> map = new HashMap<>();

                List<String> texto = new ArrayList<>();

                for (int c = 0; c < albums.size(); c++) {



                    map.put("titulo", albums.get(c).getTitle());
                    map.put("userid", String.valueOf(albums.get(c).getUserId()));
                    map.put("id", String.valueOf(albums.get(c).getId()));

                    texto.add(map.get(albums));
                }

                hashMaps.add(map);

                String[] de = {"titulo", "userid", "id"};
                int[] para = {R.id.txtAlbumsTitulo, R.id.txtAlbumsUserId, R.id.txtAlbumsId};

                ListView listarDadosJSON = view.findViewById(R.id.listaDadosAlbum);

                //SIMPLE ADAPTER
                SimpleAdapter adapter =
                        new SimpleAdapter(getContext(), hashMaps,
                                R.layout.albums,de,para);


                listarDadosJSON.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<Albums>> call, Throwable t) {
                Toast.makeText(getContext(), "Deu ruim" + t.getMessage() + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
