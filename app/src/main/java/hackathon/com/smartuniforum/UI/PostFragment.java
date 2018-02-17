package hackathon.com.smartuniforum.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import hackathon.com.smartuniforum.Adapter.PostAdapter;
import hackathon.com.smartuniforum.Models.PostModel;
import hackathon.com.smartuniforum.R;

public class PostFragment extends Fragment {

    View rootView;
    ListView listView;
    ArrayList<PostModel> postModels;
    PostModel postModel;
    PostAdapter postAdapter;

    public PostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_post, container, false);
        initui();
        return rootView;
    }

    void initui(){

        listView = rootView.findViewById(R.id.lvPosts);
        postModels = new ArrayList();
        postModel = new PostModel();
        postModel = new PostModel();
        postModel.setPostQuestion("Question 1");
        postModel.setPostDate("1/1/2018");
        postModel.setPostLabel("Label 1");
        postModels.add(postModel);
        postModel = new PostModel();
        postModel.setPostQuestion("Question 2");
        postModel.setPostDate("2/1/2018");
        postModel.setPostLabel("Label 2");
        postModels.add(postModel);
        postModel = new PostModel();
        postModel.setPostQuestion("Question 3");
        postModel.setPostDate("3/1/2018");
        postModel.setPostLabel("Label 3");
        postModels.add(postModel);
        postModel = new PostModel();
        postModel.setPostQuestion("Question 4");
        postModel.setPostDate("4/1/2018");
        postModel.setPostLabel("Label 4");
        postModels.add(postModel);
        postModel = new PostModel();
        postModel.setPostQuestion("Question 5");
        postModel.setPostDate("5/1/2018");
        postModel.setPostLabel("Label 5");
        postModel.setPostLabel("Label 5");
        postModels.add(postModel);
        postAdapter = new PostAdapter(postModels, getActivity());
        listView.setAdapter(postAdapter);
    }
}
