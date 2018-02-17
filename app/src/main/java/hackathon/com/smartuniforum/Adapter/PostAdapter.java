package hackathon.com.smartuniforum.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import hackathon.com.smartuniforum.Models.PostModel;
import hackathon.com.smartuniforum.R;

/**
 * Created by aditya on 17/2/18.
 */

public class PostAdapter extends BaseAdapter{

    List<PostModel> postModels;
    Context context;
    LayoutInflater layoutInflater;

    public PostAdapter(List<PostModel> postModels, Context context) {
        this.postModels = postModels;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return postModels.size();
    }

    @Override
    public Object getItem(int i) {
        return postModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.post_card_layout, viewGroup, false);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
            viewHolder.tvPostQuestion = view.findViewById(R.id.tvPostQuestion);
            viewHolder.tvPostPubDate= view.findViewById(R.id.tvPostPubDate);
            viewHolder.tvPostLabel = view.findViewById(R.id.tvPostLabel);
        }else{

            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvPostQuestion.setText(postModels.get(i).getPostQuestion());
        viewHolder.tvPostPubDate.setText(postModels.get(i).getPostDate());
        viewHolder.tvPostLabel.setText(postModels.get(i).getPostLabel());
        return view;
    }

    private class ViewHolder{
        TextView tvPostQuestion, tvPostPubDate, tvPostLabel;
    }
}
