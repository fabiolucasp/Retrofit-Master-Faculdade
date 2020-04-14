package com.fabiolucas.faculdade.ui.photos;

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
import com.fabiolucas.faculdade.data.model.Photos;
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

public class PhotosFragment extends Fragment {

    private PhotosViewModel photosFragmentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        photosFragmentViewModel =
                ViewModelProviders.of(this).get(PhotosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_photos, container, false);
        final TextView textView = root.findViewById(R.id.text_photos);
        photosFragmentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

        Call<List<Photos>> get = request.getPhotos();

        get.enqueue(new Callback<List<Photos>>() {
            @Override
            public void onResponse(Call<List<Photos>> call, Response<List<Photos>> response) {

                List<Photos> photos = response.body();

                List<HashMap<String, String>> hashMaps = new ArrayList<>();

                HashMap<String, String> map = new HashMap<>();

                for (int c = 0; c < photos.size(); c++) {


                    map.put("albumid", String.valueOf(photos.get(c).getAlbumId()));
                    map.put("id", String.valueOf(photos.get(c).getId()));
                    map.put("title", photos.get(c).getTitle());
                    map.put("url", photos.get(c).getUrl());
                    map.put("thumbnailurl", photos.get(c).getThumbnailUrl());


                }

                hashMaps.add(map);

                String[] de = {"albumid", "id", "title", "url", "thumbnailurl"};
                int[] para = {R.id.txtPhotosAlbumId, R.id.txtPhotosId, R.id.txtPhotosTitle, R.id.txtPhotosUrl, R.id.txtPhotosThumbNailUrl};

                ListView listarDadosJSON = view.findViewById(R.id.listaDadosPhotos);

                //SIMPLE ADAPTER
                SimpleAdapter adapter =
                        new SimpleAdapter(getContext(), hashMaps,
                                R.layout.photos,de,para);


                listarDadosJSON.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<Photos>> call, Throwable t) {
                Toast.makeText(getContext(), "Deu ruim" + t.getMessage() + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
