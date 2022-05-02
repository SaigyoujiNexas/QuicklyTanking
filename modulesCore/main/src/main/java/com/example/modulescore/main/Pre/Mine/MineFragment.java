package com.example.modulescore.main.Pre.Mine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

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

import com.example.modulescore.main.Pre.Data.PreDataActivity;
import com.example.modulescore.main.R;
import com.example.modulescore.main.Trace.TraceActivity;
import com.example.modulespublic.common.base.RunningRecord;
import com.example.modulespublic.common.utils.TimeManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MineFragment extends Fragment {
    LinearLayout linearLayout;
    List<View> viewList = new ArrayList<>();
    final String TAG = "MineFragmentTAG";
    ImageView userimg_fragmentmine;
    private File outputImage;
    String[] titles = {"跑步记录","数据统计","我的购物车","我的收藏","关于我们"};
    private static final int REQUEST_CODE_ALBUM = 100;//打开相册
    private static final int REQUEST_CODE_CAMERA = 101;//打开相机
    private static final int REQUEST_CROP_CODE = 102;//裁剪后保存
    private Uri picUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                openCamera();
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
    private void openCamera(){
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        //创建File对象
        outputImage = new File(getContext().getExternalCacheDir(), "takePhoto" + filename + ".jpg");

        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String packageName = getActivity().getApplicationContext().getPackageName();
            picUri = FileProvider.getUriForFile(getActivity(), new StringBuilder(packageName).append(".fileProvider").toString(), outputImage);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            picUri = Uri.fromFile(outputImage);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        startActivityForResult(intent, REQUEST_CROP_CODE);
    }
    //拍照 返回
    public String recognition() {
        //拍照返回
        String imagePath = outputImage.getAbsolutePath();
        return imagePath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ALBUM){
            Log.d(TAG,"0001");
            if (data != null) {
//                // 照片的原始资源地址
//                Uri uri = data.getData();
//                String path = uri.getPath();
                ContentResolver cr = getActivity().getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(picUri));
                    /* 将Bitmap设定到ImageView */
                    userimg_fragmentmine.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Log.e("Exception", e.getMessage(), e);
                }
            }
        }else if(requestCode == REQUEST_CODE_CAMERA){
            Log.d(TAG,"0002");
        } else if(requestCode ==REQUEST_CROP_CODE){
            cropImg(picUri);//对照片进行裁剪保存
            try {
                Log.d(TAG,"1");
                Bitmap image = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(picUri));
                saveImageToGallery(image);
                /* 将Bitmap设定到ImageView */
                Log.d(TAG,"2");
                userimg_fragmentmine.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //保存图片
    public File saveImageToGallery(Bitmap bitmap) {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有授权的话调用ActivityCompat.requestPermissions（）方法向客户申请授权
            //第一个参数是Activity实例 第二个是String数组 将需要申诉的权限名放入即可 第三个是请求码 只要是唯一值即可
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "android");
        if (!appDir.exists()) {
            // 目录不存在 则创建
            appDir.mkdirs();
        }
        //下面的CompressFormat.PNG/CompressFormat.JPEG， 这里对应.png/.jpeg
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos); // 保存bitmap至本地
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.d("Personal_TAG", "保存图片异常" + e.toString());
            e.printStackTrace();
        } finally {
            if (bitmap!=null&&!bitmap.isRecycled()) {
                //当存储大图片时，为避免出现OOM ，及时回收Bitmap
                //bitmap.recycle();
                // 通知系统回收
                System.gc();
            }
            //返回保存的图片路径
            return file;
        }
    }
//    public Uri getImageContentUri(File imageFile) {
//        //Log.d(TAG,"0000000000");
//        String filePath = imageFile.getAbsolutePath();
//        Cursor cursor = getActivity().getContentResolver().query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                new String[]{MediaStore.Images.Media._ID},
//                MediaStore.Images.Media.DATA + "=? ",
//                new String[]{filePath}, null);
//        //Log.d(TAG,"00000001");
//        if (cursor != null && cursor.moveToFirst()) {
//            @SuppressLint("Range") int id = cursor.getInt(cursor
//                    .getColumnIndex(MediaStore.MediaColumns._ID));
//            Uri baseUri = Uri.parse("content://media/external/images/media");
//            //Log.d(TAG,"00000002");
//            return Uri.withAppendedPath(baseUri, "" + id);
//        } else {
//            if (imageFile.exists()) {
//                ContentValues values = new ContentValues();
//                values.put(MediaStore.Images.Media.DATA, filePath);
//                //Log.d(TAG,"00000003");
//                return getActivity().getContentResolver().insert(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//            } else {
//                return null;
//            }
//        }
//    }

    public void cropImg(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        //实现对图片的裁剪，必须要设置图片的属性和大小
        intent.putExtra("crop", "true");  //滑动选中图片区域
        intent.putExtra("aspectX", 1);  //裁剪框比例1:1
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 700);  //输出图片大小
        intent.putExtra("outputY", 700);
//        intent.putExtra("return-data", true);  //有返回值

        Log.d(TAG,"0");
        Uri cropImgUri;
        cropImgUri = Uri.parse("file://" + outputImage.getAbsolutePath());
        Log.d(TAG,"000");
        //将裁剪好的图输出到所建文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImgUri);
        Log.d(TAG,"0000");
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        Log.d(TAG,"000000");
        //注意：此处应设置return-data为false，如果设置为true，是直接返回bitmap格式的数据，耗费内存。
//        //设置为false，然后，设置裁剪完之后保存的路径，即：intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, REQUEST_CROP_CODE);
    }
    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

}