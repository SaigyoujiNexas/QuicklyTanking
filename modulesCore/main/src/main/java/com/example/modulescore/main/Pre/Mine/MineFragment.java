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
import java.io.IOException;
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
    int[] imgResource = {R.drawable.runningimg,R.drawable.statisticsimg,R.drawable.shoppingimg,R.drawable.collectionimg,R.drawable.aboutusimg};
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
    private void openCamera(){
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("HH_mm_ss");
        //创建File对象
        outputImage = new File(getActivity().getExternalCacheDir(), "takePhoto" + System.currentTimeMillis() + ".jpg");
        //outputImage = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");

        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            picUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".fileprovider", outputImage);
        } else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            picUri = Uri.fromFile(outputImage);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ALBUM) {
            Log.d(TAG,"0001");
//            if (data != null) {
//                ContentResolver cr = getActivity().getContentResolver();
//                try {
//                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(picUri));
//                    /* 将Bitmap设定到ImageView */
//                    userimg_fragmentmine.setImageBitmap(bitmap);
//                } catch (FileNotFoundException e) {
//                    Log.e("Exception", e.getMessage(), e);
//                }
//            }
            Uri uri = data.getData();
            cropImg(uri);
        }else if(requestCode == REQUEST_CODE_CAMERA && resultCode== RESULT_OK){
            Log.d(TAG,"0002");
            Uri contentUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".fileprovider", outputImage);
            cropImg(contentUri);
        } else if(requestCode ==REQUEST_CROP_CODE){
            //try {
//                Log.d(TAG,"1");
//                getActivity().getContentResolver().openInputStream(picUri);
//                Bitmap image = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(picUri));
//                //saveImageToGallery(image);
//                Log.d(TAG,"2");
//
                //图片剪裁返回
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Log.d("xxxxxx", "eee");
                    //在这里获得了剪裁后的Bitmap对象，可以用于上传
                    Bitmap image = bundle.getParcelable("data");
                    userimg_fragmentmine.setImageBitmap(image);
                    //File file = saveImageToGallery(map);
                    //uploadFanganFile(file);
                    //deleteSuccess(this, file.getName());
                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
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
            Log.d(TAG, "保存图片异常" + e.toString());
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


    public void cropImg(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //Android 7.0需要临时添加读取Url的权限， 添加此属性是为了解决：调用裁剪框时候提示：图片无法加载或者加载图片失败或者无法加载此图片
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        //实现对图片的裁剪，必须要设置图片的属性和大小
        intent.putExtra("crop", "true");//发送裁剪信号，去掉也能进行裁剪
        intent.putExtra("scale", true);// 设置缩放
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        intent.putExtra("aspectX", 1);  //裁剪框比例1:1
        intent.putExtra("aspectY", 1);
//        intent.putExtra("return-data", true);  //有返回值

        //上述两个属性控制裁剪框的缩放比例。
        //当用户用手拉伸裁剪框时候，裁剪框会按照上述比例缩放。
        intent.putExtra("outputX", 300);//属性控制裁剪完毕，保存的图片的大小格式。
        intent.putExtra("outputY", 300);//你按照1:1的比例来裁剪的，如果最后成像是800*400，那么按照2:1的样式保存，
        intent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());//输出裁剪文件的格式
        intent.putExtra("return-data", true);//是否返回裁剪后图片的Bitmap
//        Log.d(TAG,"0");
//        Uri cropImgUri;
//        cropImgUri = Uri.parse("file://" + outputImage.getAbsolutePath());
//        Log.d(TAG,"000");
        //将裁剪好的图输出到所建文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//设置输出路径
        Log.d(TAG,"000000");
        //注意：此处应设置return-data为false，如果设置为true，是直接返回bitmap格式的数据，耗费内存。
//        //设置为false，然后，设置裁剪完之后保存的路径，即：intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
        //intent.putExtra("return-data", false);
        startActivityForResult(intent, REQUEST_CROP_CODE);// 启动裁剪程序

    }
    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_ALBUM);
    }
}//    public Uri getImageContentUri(File imageFile) {
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