package hackathon.com.smartuniforum.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hackathon.com.smartuniforum.API.API;
import hackathon.com.smartuniforum.Adapter.FeedAdapter;
import hackathon.com.smartuniforum.Config.ProgressDialogConfig;
import hackathon.com.smartuniforum.Config.RetrofitConfig;
import hackathon.com.smartuniforum.Models.FeedModel;
import hackathon.com.smartuniforum.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FeedFragment extends Fragment {

    String TAG = getClass().getSimpleName();
    View rootView;
    ListView listView;
    FeedModel feedModel;
    ArrayList<FeedModel> feedModels;
    FeedAdapter feedAdapter;
    ProgressDialogConfig progressDialogConfig;
    RetrofitConfig retrofitConfig;
    public FeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        initui();
        getFeed();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                Log.d(TAG + "@@@@@@@@@", feedModel.getUrl());
                intent.putExtra("url", feedModels.get(i).getUrl());
                startActivity(intent);
            }
        });
        return rootView;
    }

    void initui(){

        progressDialogConfig = new ProgressDialogConfig();
        feedModels = new ArrayList<>();
        retrofitConfig = new RetrofitConfig();
        listView = rootView.findViewById(R.id.lvFeeds);
    }

    void getFeed(){

        final ProgressDialog progressDialog = progressDialogConfig.config(getActivity(), getString(R.string.getting_feed));
        progressDialog.show();

        final SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyyy");
        Retrofit retrofit = retrofitConfig.config();

        API api = retrofit.create(API.class);

        Call<List<FeedModel>> call = api.getQuestions();

        call.enqueue(new Callback<List<FeedModel>>() {
            @Override
            public void onResponse(Call<List<FeedModel>> call, Response<List<FeedModel>> response) {
                progressDialog.dismiss();
                try {
                    for (int i = 0; i < response.body().size(); i++) {
                        feedModel = new FeedModel();
                        feedModel.setUrl(response.body().get(i).getUrl()
                                .replace("api-auth/", "")
                                .replaceAll("/[0-9]/", "/detail/" + response.body().get(i).getSlug() + "/"));
                        feedModel.setSlug(response.body().get(i).getSlug());
                        feedModel.setQuestion(response.body().get(i).getQuestion().substring(0,50) + "...");
                        feedModel.setTags(response.body().get(i).getTags());                        String labelSub = response.body().get(i).getLabels()
                                .replace("_"," ");
                        String label = labelSub.substring(0,1).toUpperCase() + labelSub.substring(1);
                        feedModel.setLabels(label);
                        feedModel.setClosed(response.body().get(i).getClosed());
                        feedModel.setCreated(response.body().get(i).getCreated().substring(0, 9));
                        feedModel.setAttachment(response.body().get(i).getAttachment());
                        feedModels.add(feedModel);
                    }
                    feedAdapter = new FeedAdapter(feedModels, getActivity());
                    listView.setAdapter(feedAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                    ArrayList arrayList = new ArrayList();
                    arrayList.add("Nothing to show");
                    ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1);
                    listView.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<FeedModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
            }
        });
    }
}
