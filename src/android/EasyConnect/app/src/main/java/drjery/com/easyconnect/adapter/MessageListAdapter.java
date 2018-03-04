package drjery.com.easyconnect.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import drjery.com.easyconnect.pojo.Message;
import drjery.com.easyconnect.R;
import drjery.com.easyconnect.util.ConstUtil;

/**
 * Created by DRJ on 2017/7/3.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private List<Message> msgList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftLayout;

        LinearLayout rightLayout;

        TextView leftMsg;

        TextView rightMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            leftLayout = (LinearLayout) itemView.findViewById(R.id.left_msg_layout);
            rightLayout = (LinearLayout) itemView.findViewById(R.id.right_msg_layout);
            leftMsg = (TextView) itemView.findViewById(R.id.left_msg_text);
            rightMsg = (TextView) itemView.findViewById(R.id.right_msg_text);
        }
    }


    public MessageListAdapter(List<Message> msgs){
        this.msgList = msgs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.msg_recy_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = msgList.get(position);
        if (message.getSender() == ConstUtil.TYPE_RECEIVE)
        {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(message.getMessage());
        }
        else if(message.getSender() == ConstUtil.TYPE_SEND)
        {
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(message.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
