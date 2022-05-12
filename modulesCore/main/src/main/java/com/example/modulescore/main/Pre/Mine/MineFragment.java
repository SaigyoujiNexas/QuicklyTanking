package com.example.modulescore.main.Pre.Mine;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.example.modulescore.main.Data.PreDataActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.modulescore.main.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.example.modulescore.main.Data.StatisticsActivity;

public class MineFragment extends Fragment {
    LinearLayout linearLayout;
    List<View> viewList = new ArrayList<>();
    final String TAG = "MineFragmentTAG";
    public ImageView userimg_fragmentmine;
    public ImageView backimg_fragmentmine;
    String[] titles = {"跑步记录","数据统计","我的购物车","我的收藏","关于我们"};
    int[] imgResource = {R.drawable.runningimg,R.drawable.statisticsimg,R.drawable.shoppingimg,R.drawable.collectionimg,R.drawable.aboutusimg};
    public Uri picUri;
    public Uri tempPicUri;
    public Uri backgroundUri;
    public Uri tempBackgroundUri;
    private ActivityResultLauncher imagePickLauncher,backgroundPickLauncher;
    private ActivityResultLauncher openCameraLauncher,openBackgroundCameraLauncher;
    private ActivityResultLauncher cropLauncher,cropBackgroundLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(o) -> {
            Uri uri;
            if (o.getData() != null)    uri = o.getData().getData();//获取选中图片Uri
            else    uri = Uri.EMPTY;
            ///raw//storage/emulated/0/Pictures/1651760808654.jpg 原图片uri
            // /data/user/0/com.example.modulescore.main/cache/1651760808654.jpg 新建的file保存图片
            var file = new File(getContext().getCacheDir(), new File(uri.getPath()).getName());
            Log.d(TAG,uri.getPath()+",,"+file.getPath());
            if(file.exists())           file.delete();
            picUri = Uri.fromFile(file);            //准备保存裁剪后图片Uri
            Log.d(TAG,picUri+",0,"+tempPicUri);
            cropImg(uri);
        });
        backgroundPickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(o)->{
            Uri uri;
            if (o.getData() != null)    uri = o.getData().getData();//获取选中图片Uri
            else    uri = Uri.EMPTY;
            ///raw//storage/emulated/0/Pictures/1651760808654.jpg 原图片uri uri
            // /data/user/0/com.example.modulescore.main/cache/1651760808654.jpg 新建的file保存图片 file
            var file = new File(getContext().getCacheDir(), new File(uri.getPath()).getName());
            Log.d(TAG,uri.getPath()+",,"+file.getPath());
            if(file.exists())           file.delete();
            backgroundUri = Uri.fromFile(file);            //准备保存裁剪后图片Uri
            Log.d(TAG,backgroundUri+",0,"+tempBackgroundUri);
            cropBackGroundImg(uri);
        });

        cropLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Glide.with(getContext()).load(picUri).into(userimg_fragmentmine);
            }
        });
        cropBackgroundLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(o)->{
            Glide.with(getContext()).load(backgroundUri).into(backimg_fragmentmine);
        });
        openCameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
               Log.d(TAG,picUri+",1,"+tempPicUri);
               //原图片Uri; = picUri2，tempUri是保存照相（获取）选中图片Uri
               File file = new File(getContext().getCacheDir(), new File(tempPicUri.getPath()).getName());
               if(file.exists())           file.delete();
               picUri = Uri.fromFile(file);            //准备保存裁剪后图片Uri
               cropImg(tempPicUri);
            }
        });

        openBackgroundCameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(o) ->{
            Log.d(TAG,backgroundUri+",1,"+tempBackgroundUri);
            //原图片Uri; = picUri2，tempUri是保存照相（获取）选中图片Uri
            File file = new File(getContext().getCacheDir(), new File(tempBackgroundUri.getPath()).getName());
            if(file.exists())           file.delete();
            backgroundUri = Uri.fromFile(file);            //准备保存裁剪后图片Uri
            cropBackGroundImg(tempBackgroundUri);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        userimg_fragmentmine = view.findViewById(R.id.userimg_fragmentmine);
        backimg_fragmentmine = view.findViewById(R.id.back_minefragment);
        linearLayout = view.findViewById(R.id.linearlayout_minefragment);
        for(int i = 0;i<5;i++){
            LinearAddView(i);
        }
        viewList.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onclick");
                Intent intent = new Intent(getActivity(), PreDataActivity.class);
                startActivity(intent);
            }
        });
        viewList.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onclick");
                Intent intent = new Intent(getActivity(),StatisticsActivity.class);
                startActivity(intent);
            }
        });
        userimg_fragmentmine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDialog();
            }
        });
        backimg_fragmentmine.setOnClickListener((o)->{
            initBackDialog();
        });
        return view;
    }
    private void initBackDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//创建对话框
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.userimg_dialog, null);//获取自定义布局
        TextView tv_camera_dialog = layout.findViewById(R.id.tv_camera_dialog);
        TextView tv_photoalbum_dialog = layout.findViewById(R.id.tv_photoalbum_dialog);
        TextView tv_cancel = layout.findViewById(R.id.tv_cancel);
        builder.setView(layout);//设置对话框的布局
        AlertDialog alertDialog = builder.create();//生成最终的对话框
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        tv_camera_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identifyTakePhotoImage();
                try {
                    openCamera();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                alertDialog.cancel();
            }
        });
        tv_photoalbum_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBackFromAlbm();
                alertDialog.cancel();
            }
        });
        alertDialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭
        alertDialog.show();//显示对话框
    }
    private void initDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//创建对话框
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.userimg_dialog, null);//获取自定义布局
        TextView tv_camera_dialog = layout.findViewById(R.id.tv_camera_dialog);
        TextView tv_photoalbum_dialog = layout.findViewById(R.id.tv_photoalbum_dialog);
        TextView tv_cancel = layout.findViewById(R.id.tv_cancel);
        builder.setView(layout);//设置对话框的布局
        AlertDialog backAlertDialog = builder.create();//生成最终的对话框
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backAlertDialog.cancel();
            }
        });
        tv_camera_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identifyTakePhotoImage();
                try {
                    openCamera_Back();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                backAlertDialog.cancel();
            }
        });
        tv_photoalbum_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPicFromAlbm();
                backAlertDialog.cancel();
            }
        });
        backAlertDialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭
        backAlertDialog.show();//显示对话框
    }
    private void LinearAddView(int i){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.mine_item,linearLayout,false);
        ConstraintLayout constraintLayout = view.findViewById(R.id.constraint_mine_item);
        ImageView img_item = view.findViewById(R.id.img_mine_item);
        TextView text_item = view.findViewById(R.id.text_mine_item);
        text_item.setText(titles[i]);
        img_item.setImageResource(imgResource[i]);
        linearLayout.addView(view);
        Log.d("LinearAddView","FINISH");
        viewList.add(view);
    }
    public void identifyTakePhotoImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                        1);
            }
        }
    }

    //调用相机（指定相机拍摄照片保存地址，相片清晰度高）
    private void openCamera() throws IOException {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        SimpleDateFormat timeStampFormat2 = new SimpleDateFormat(
                "yyyy");
        String filename = timeStampFormat.format(new Date());
        String filename2 = timeStampFormat2.format(new Date());
        File tempFile = new File(Environment.getExternalStorageDirectory(),
                filename + ".jpg");
        File tempFile2 = new File(Environment.getExternalStorageDirectory(),
                filename2 + ".jpg");
        //兼容android7.0 使用共享文件的形式
        ContentValues contentValues = new ContentValues(1);
        //contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
        ContentValues contentValues2 = new ContentValues(1);
        contentValues2.put(MediaStore.Images.Media.DATA, tempFile2.getAbsolutePath());
        tempPicUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        //picUri2 = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues2);

        if(tempFile != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Uri uri = FileProvider.getUriForFile(getContext(), "com.example.modulescore.main.fileprovider", outputImageFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,tempPicUri);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            openCameraLauncher.launch(intent);
        }
    }


    //调用相机（指定相机拍摄照片保存地址，相片清晰度高）
    private void openCamera_Back() throws IOException {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        SimpleDateFormat timeStampFormat2 = new SimpleDateFormat(
                "yyyy");
        String filename = timeStampFormat.format(new Date());
        String filename2 = timeStampFormat2.format(new Date());
        File tempFile = new File(Environment.getExternalStorageDirectory(),
                filename + ".jpg");
        File tempFile2 = new File(Environment.getExternalStorageDirectory(),
                filename2 + ".jpg");
        //兼容android7.0 使用共享文件的形式
        ContentValues contentValues = new ContentValues(1);
        //contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
        ContentValues contentValues2 = new ContentValues(1);
        contentValues2.put(MediaStore.Images.Media.DATA, tempFile2.getAbsolutePath());
        tempBackgroundUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);


        if(tempFile != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,tempBackgroundUri);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            openBackgroundCameraLauncher.launch(intent);
        }
    }


    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm() {
        var intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        imagePickLauncher.launch(intent);
    }

    /**
     * 从相册获取图片
     */
    private void getBackFromAlbm() {
        var intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        backgroundPickLauncher.launch(intent);
    }

    public void cropImg(Uri uri) {
        var intent = UCrop.of(uri, picUri)
                .withAspectRatio(1, 1)
                .getIntent(getContext());
        cropLauncher.launch(intent);
    }
    public void cropBackGroundImg(Uri uri) {
        var intent = UCrop.of(uri, backgroundUri)
                .withAspectRatio(1, 1)
                .getIntent(getContext());
        cropBackgroundLauncher.launch(intent);
    }

}