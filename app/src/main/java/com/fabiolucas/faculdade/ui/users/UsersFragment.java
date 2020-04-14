package com.fabiolucas.faculdade.ui.users;

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
import com.fabiolucas.faculdade.data.model.Users;
import com.fabiolucas.faculdade.retrofit.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UsersFragment extends Fragment {

    private UsersViewModel usersFragmentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        usersFragmentViewModel =
                ViewModelProviders.of(this).get(UsersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_users, container, false);
        final TextView textView = root.findViewById(R.id.text_users);
        usersFragmentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

        Call<List<Users>> get = request.getUsers();

        get.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {

                List<Users> users = response.body();

                List<HashMap<String, String>> hashMaps = new ArrayList<>();

                HashMap<String, String> map = new HashMap<>();


                for (int c = 0; c < users.size(); c++) {

                    map.put("name", users.get(c).getName());
                    map.put("username", users.get(c).getUsername());
                    map.put("email", users.get(c).getEmail());
                    map.put("street", users.get(c).getAddress().getStreet());
                    map.put("suite", users.get(c).getAddress().getSuite());
                    map.put("city", users.get(c).getAddress().getCity());
                    map.put("zipcode", users.get(c).getAddress().getZipcode());
                    map.put("lat", users.get(c).getAddress().getGeo().getLat());
                    map.put("lng", users.get(c).getAddress().getGeo().getLng());
                    map.put("id", String.valueOf(users.get(c).getId()));

                }

                hashMaps.add(map);

                String[] de = {"name", "username", "email", "street", "suite", "city", "lat", "lng", "id"};
                int[] para = {R.id.txtUsersName, R.id.txtUsersUserName, R.id.txtUsersEmail, R.id.txtUsersStreet, R.id.txtUsersSuite, R.id.txtUsersCity, R.id.txtUsersLat, R.id.txtUsersLong, R.id.txtUsersId};

                ListView listarDadosJSON = view.findViewById(R.id.listaDadosUsers);

                //SIMPLE ADAPTER
                SimpleAdapter adapter =
                        new SimpleAdapter(getContext(), hashMaps,
                                R.layout.users, de, para);


                listarDadosJSON.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Toast.makeText(getContext(), "Deu ruim" + t.getMessage() + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
