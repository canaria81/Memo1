package com.dodo81.memo1.adapter;

//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;

import com.dodo81.memo1.EditActivity;
import com.dodo81.memo1.R;
import com.dodo81.memo1.data.DatabaseHandler;
import com.dodo81.memo1.model.Memo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.net.BindException;
import java.util.List;

import java.util.ArrayList;
public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {
    Context context;
    ArrayList<Memo> memoList;

    public MemoAdapter(Context context, ArrayList<Memo> memoList) {
        this.context = context;
        this.memoList = memoList;
    }

    @NonNull
    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // xml 파일을 연결하는 작업
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memo_row, parent, false);
        return new MemoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoAdapter.ViewHolder holder, int position) {
        // 뷰에 데이터 셋팅
        Memo memo = memoList.get(position);

        holder.txtTitle.setText( memo.getTitle() );
        holder.txtContent.setText( memo.getContent() );
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtContent;
        ImageView imgDelete;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cardView = itemView.findViewById(R.id.cardView);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // x 버튼 누르면 메모 삭제하는 코드 작성해야 한다.
                    // 1. 알러트 다이얼로그를 띄운다.
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("메모 삭제");
                    alert.setMessage("정말 삭제하시겠습니까?");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 2. YES 버튼을 누르면, 디비에서 삭제해야 한다.

                            // 2-1. 데이터를 가져오기 위해서 몇번째 행을 눌렀는지 알아야한다.
                            int index = getAdapterPosition();
                            Memo memo = memoList.get(index);

                            // 2-2. 디비에서 삭제.
                            DatabaseHandler db = new DatabaseHandler(context);
                            db.deleteMemo(memo.id);

                            // 3. 삭제한 행은, 화면에서도 사라져야 한다.
                            memoList.remove(index);
                            notifyDataSetChanged();
                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert.show();
                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 메모 선택하면 수정하는 액티비티로 가도록 코드 작성해야한다.
                    Intent intent = new Intent(context, EditActivity.class);

                    int index = getAdapterPosition();

                    Memo memo = memoList.get(index);
                    // 객체를 putExtra 로 넘겨줄 때는
                    // 해당 클래스에 implements Serializable 해줘야한다.

                    intent.putExtra("memo", memo);

                    // 수정하는 액티비티를 메인 액티비티 위에 띄워라
                    context.startActivity(intent);

                }
            });
        }
    }
}