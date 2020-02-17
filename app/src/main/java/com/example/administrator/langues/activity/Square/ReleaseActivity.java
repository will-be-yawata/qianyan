package com.example.administrator.langues.activity.Square;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.langues.R;
import com.scrat.app.selectorlibrary.ImageSelector;

import java.util.ArrayList;
import java.util.List;

public class ReleaseActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton add_pho_btn;
    private ImageButton release_return;

    private static final int REQUEST_CODE_SELECT_IMG = 1;
    private static final int MAX_SELECT_COUNT = 9;


    private int defaultMaxCount = 5;
    private ArrayList<String> results;
    /**显示图片的GridView*/
    private GridView gridview;
    /**文件夹下所有图片的bitmap*/
    private List<Bitmap> listpath;
    /**文件夹下图片的真实路径*/
    private String scanpath;
    /**显示图片的适配器*/
    private Releaseadapter adapter;

    private int mScreenHeight;
    private int mScreenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        init();
        getWindowWH();
        add_pho_btn.setOnClickListener(this);
        release_return.setOnClickListener(this);
    }

    private void init() {
        listpath = new ArrayList<>();
        add_pho_btn=findViewById(R.id.add_pho_btn);
        release_return=findViewById(R.id.release_return);

        gridview=findViewById(R.id.release_gridview);
    }
    public void getWindowWH(){
        Display display = getWindowManager().getDefaultDisplay();
        mScreenHeight= display.getHeight();
        mScreenWidth = display.getWidth();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.release_return:
                finish();
                break;
            case R.id.add_pho_btn:
               if(listpath.size() >= 9){
                   Toast.makeText(ReleaseActivity.this, "图片数9张已满", Toast.LENGTH_SHORT).show();
               }else{
                   ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT);
               }

                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMG) {
           /* // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                Log.i("name11","----"+String.valueOf(uri));
                add_pho.setImageURI(uri);*/
            showContent(data);
            }
        }


    private void showContent(Intent data) {
        ArrayList<String> paths = ImageSelector.getImagePaths(data);
        if (paths.isEmpty()) {
            //mContentTv.setText(R.string.image_selector_select_none);

            return;
        }

        results = ImageSelector.getImagePaths(data);

        for(int i=0;i<results.size();i++){
            scanpath = results.get(i);
            Bitmap bitmap = BitmapFactory.decodeFile(scanpath);
            //Log.i("bitmap","bitmap="+results.get(i));
            if(bitmap!=null) {
                listpath.add(bitmap);
                // Log.i("listpath","listpath="+listpath);
            }
        }
        adapter = new Releaseadapter(listpath, this);


            gridview.setAdapter(adapter);
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    dialog(position);//删除所选图片
                }
            });

        if (results == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < results.size(); i++) {
            sb.append(i+1).append('：').append(results.get(i)).append("\n");
        }
    }
    public void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReleaseActivity.this);
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                listpath.remove(position);
                adapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    class VIewHolder {
        ImageView iv;
    }
    public class Releaseadapter extends BaseAdapter {
        private List<Bitmap> mlist;
        private Context mcontext;
        private LayoutInflater minflater;

        public Releaseadapter(List<Bitmap> list, Context context) {
            super();
            this.mlist = list;
            this.mcontext = context;
            this.minflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            VIewHolder vh;
            if (convertView == null) {
                vh = new VIewHolder();
                convertView = minflater.inflate(R.layout.square_find_photo, null);
                vh.iv = (ImageView) convertView.findViewById(R.id.square_photo);
                convertView.setTag(vh);
            } else {
                vh = (VIewHolder) convertView.getTag();
            }

            ViewGroup.LayoutParams para = vh.iv.getLayoutParams();
            para.width = (mScreenWidth-20)/3;//一屏显示3列
            para.height=para.width;
            vh.iv.setLayoutParams(para);

            vh.iv.setImageBitmap(mlist.get(position));

            return convertView;
        }
    }

}
