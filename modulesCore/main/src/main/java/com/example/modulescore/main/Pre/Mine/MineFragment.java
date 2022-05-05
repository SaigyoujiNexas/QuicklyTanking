package com.example.modulescore.main.Pre.Mine;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.common.utils.ToastUtil;
import com.example.modulescore.main.Pre.Data.PreDataActivity;
import com.example.modulescore.main.R;
import com.example.modulescore.main.Trace.TraceActivity;
import com.example.modulespublic.common.base.RunningRecord;
import com.example.modulespublic.common.utils.TimeManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MineFragment extends Fragment {
    public static File outputImageFile;
    LinearLayout linearLayout;
    List<View> viewList = new ArrayList<>();
    final String TAG = "MineFragmentTAG";
    public static ImageView userimg_fragmentmine;
    String[] titles = {"跑步记录","数据统计","我的购物车","我的收藏","关于我们"};
    int[] imgResource = {R.drawable.runningimg,R.drawable.statisticsimg,R.drawable.shoppingimg,R.drawable.collectionimg,R.drawable.aboutusimg};
    public  static final int REQUEST_CODE_ALBUM = 100;//打开相册
    public static final int REQUEST_CODE_CAMERA = 101;//打开相机
    public static final int REQUEST_CROP_CODE = 102;//裁剪后保存
    public Uri picUri;
    public Uri tempPicUri;
    private ActivityResultLauncher imagePickLauncher;
    private ActivityResultLauncher openCameraLauncher;
    private ActivityResultLauncher cropLauncher;

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
        cropLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Glide.with(getContext()).load(picUri).into(userimg_fragmentmine);
            }
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        userimg_fragmentmine = view.findViewById(R.id.userimg_fragmentmine);
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
        userimg_fragmentmine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDialog();
            }
        });
        return view;
    }
    private void initDialog(){
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
                getPicFromAlbm();
                alertDialog.cancel();
            }
        });
        alertDialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭
        alertDialog.show();//显示对话框
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




    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm() {
        var intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        imagePickLauncher.launch(intent);
    }


    public void cropImg(Uri uri) {
        var intent = UCrop.of(uri, picUri)
                .withAspectRatio(1, 1)
                .getIntent(getContext());
        cropLauncher.launch(intent);
    }

    /**
     * @author xixili
     * created at 2016/2/27 14:32
     * 获取剪切之后的图片
     */
    public static Bitmap getPic(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");//转换为Bitmap类型

            if(bitmap!=null){
                Log.d("123", "getpic");
                return bitmap;
            }
        }
        return null;
    }
    /**
     * 小米
     *
     * @param intent
     * @return
     */
    public Uri getPictureUri(android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = getActivity().getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }
}
//    //保存图片
//    public File saveImageToGallery(Bitmap bitmap) {
//        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            //没有授权的话调用ActivityCompat.requestPermissions（）方法向客户申请授权
//            //第一个参数是Activity实例 第二个是String数组 将需要申诉的权限名放入即可 第三个是请求码 只要是唯一值即可
//            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }
//
//        File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "android");
//        if (!appDir.exists()) {
//            // 目录不存在 则创建
//            appDir.mkdirs();
//        }
//        //下面的CompressFormat.PNG/CompressFormat.JPEG， 这里对应.png/.jpeg
//        String fileName = System.currentTimeMillis() + ".png";
//        File file = new File(appDir, fileName);
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos); // 保存bitmap至本地
//            fos.flush();
//            fos.close();
//        } catch (Exception e) {
//            Log.d(TAG, "保存图片异常" + e.toString());
//            e.printStackTrace();
//        } finally {
//            if (bitmap!=null&&!bitmap.isRecycled()) {
//                //当存储大图片时，为避免出现OOM ，及时回收Bitmap
//                //bitmap.recycle();
//                // 通知系统回收
//                System.gc();
//            }
//            //返回保存的图片路径
//            return file;
//        }
//    }