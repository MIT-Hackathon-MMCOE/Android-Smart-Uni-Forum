package hackathon.com.smartuniforum.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hackathon.com.smartuniforum.Models.FeedModel;
import hackathon.com.smartuniforum.R;

/**
 * Created by aditya on 16/2/18.
 */

public class FeedAdapter extends BaseAdapter{
    List<FeedModel> feedModels;
    Context context;
    LayoutInflater layoutInflater;

    public FeedAdapter(List<FeedModel> feedModels, Context context) {
        this.feedModels = feedModels;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return feedModels.size();
    }

    @Override
    public Object getItem(int i) {
        return feedModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.feed_card_layout, viewGroup,false);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
            viewHolder.tvFeedQuestion= view.findViewById(R.id.tvFeedQuestion);
            viewHolder.tvFeedPubDate = view.findViewById(R.id.tvFeedPubDate);
            viewHolder.tvFeedTag = view.findViewById(R.id.tvFeedTag);
            viewHolder.tvFeedUser= view.findViewById(R.id.tvFeedLabel);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvFeedQuestion.setText(feedModels.get(i).getQuestion());
        viewHolder.tvFeedPubDate.setText(feedModels.get(i).getCreated());
        /*for(int j = i; j < feedModels.get(j).getTags().size(); j++){
            viewHolder.tvFeedTag.append(feedModels.get(j).getTags().get(j) + ", ");
        }*/
        viewHolder.tvFeedUser.setText(feedModels.get(i).getLabels());
        return view;
    }

    private class ViewHolder{
        TextView tvFeedQuestion, tvFeedPubDate,tvFeedTag,tvFeedUser;
    }
}
