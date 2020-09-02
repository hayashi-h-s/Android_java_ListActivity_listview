package com.haya.java_listactivity_listview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// 参考サイト https://akira-watson.com/android/listactivity.html

public class MainActivity extends ListActivity {

  // itemのアイコンと名前を保持するクラス
  class CellData {
    String imageComment;
    int imageDrawableId;

    // データを呼び出すクラス
    CellData(String imageComment, int imageDrawableId) {
      this.imageComment = imageComment;
      this.imageDrawableId = imageDrawableId;
    }
  }

  // Android が持っているシステムアイコン
  private Integer[] imageDrawables = {
      android.R.drawable.ic_menu_call,
      android.R.drawable.ic_menu_close_clear_cancel,
      android.R.drawable.ic_menu_compass,
      android.R.drawable.ic_menu_crop,
      android.R.drawable.ic_menu_delete,
      android.R.drawable.ic_menu_directions,
      android.R.drawable.ic_menu_gallery,
      android.R.drawable.ic_menu_edit,
      android.R.drawable.ic_menu_help,
      android.R.drawable.ic_btn_speak_now,
  };

  // textView の文言
  private String[] imageComments = {
      "call", "cancel", "compass", "crop", "delete",
      "directions", "gallery","edit","help","speak"
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // ListViewオブジェクトを既に含んだListActivityを使っているので、新たにListViewのインスタンスを生成して、
    // アクティビティにセットする必要がありません。
    List<CellData> list = new ArrayList<>();

    for (int i = 0; i < imageDrawables.length ; i++){
      CellData data = new CellData(imageComments[i], imageDrawables[i]);
      list.add(data);
    }

    // etAdapter() ではなく setListAdapter() を使います
    setListAdapter(new ListViewAdapter(this, R.layout.list, list));
  }

  // ViewHolder
  class ViewHolder {
    TextView textView;
    ImageView imageView;
  }

  // ArrayAdapterを継承したカスタムのアダプタークラス(ListViewAdapter)
  class ListViewAdapter extends ArrayAdapter<CellData> {

    //LayoutInflaterは、指定したxmlのレイアウト(View)リソースを利用できる仕組み
    // https://qiita.com/Bth0061/items/c4f66477979d064913e4
    private LayoutInflater inflater;
    private int itemLayout;
    CellData data;

    // ArrayAdapterクラスの引数
    // ArrayAdapter(@NonNull Context context, int resource)
    //
    ListViewAdapter(Context context, int itemLayout, List<CellData> list) {
      super(context, 0, list);
      this.inflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      this.itemLayout = itemLayout;
    }

    // getView で どの position で itemLayout を
    @Override
    public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
      ViewHolder holder;
      if (convertView == null) {
        convertView = inflater.inflate(itemLayout, parent, false);
        holder = new ViewHolder();
        holder.textView = convertView.findViewById(R.id.textView);
        holder.imageView = convertView.findViewById(R.id.imageView);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }

      data = getItem(position);
      if(data != null){
        holder.textView.setText(data.imageComment);
        holder.imageView.setImageResource(data.imageDrawableId);
      }
      return convertView;
    }
  }
}