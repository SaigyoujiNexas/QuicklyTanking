package com.example.modulescore.main.Identification;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.common.utils.ToastUtil;
import com.example.modulescore.main.R;
import com.example.modulespublic.common.constant.Constant;
import com.example.modulespublic.common.constant.SPUtils;
import com.example.modulespublic.common.net.BaseResponse;
import com.example.modulespublic.common.net.DiscernResultResponse;
import com.example.modulespublic.common.net.GetRequest_Interface;
import com.example.modulespublic.common.net.GetTokenItem;
import com.example.modulespublic.common.utils.Base64Util;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IdentificationActivity extends AppCompatActivity {

    private String baseUrl="https://aip.baidubce.com/";
    private final String TAG = "IdentificationActivityTAG";
    String secretKey = "cNRIehDO0xxo89p0fZxxxb6O79hXwcv8";
    String apiKey = "uv7Y1XiG5broeDMU70AINwAh";
    String grant_type = "client_credentials";
    String access_token = "24.89f9c3eb78a22cb5bb6ffb21f80290fd.2592000.1655390639.282335-26232617";
    Retrofit retrofit;
    RecyclerView recycler_identificationActivity;
    ImageView img_identificationActivity;
    DiscernResultResponse discernResultResponse;
    HandlerIdentification handlerIdentification;
    private ActivityResultLauncher imagePickLauncher;
    private ActivityResultLauncher openCameraLauncher;
    private ActivityResultLauncher cropLauncher;
    public Uri picUri;
    public Uri tempPicUri;
    public String imageParam;
    File picFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);
        getAccessToken();
        //requestIdentificationResult();
        recycler_identificationActivity = findViewById(R.id.recycler_identificationActivity);
        img_identificationActivity = findViewById(R.id.img_identificationActivity);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(o) -> {
            Uri uri;
            if (o.getData() != null)    uri = o.getData().getData();//获取选中图片,Uri.intent.getData()
            else    uri = Uri.EMPTY;
            ///raw//storage/emulated/0/Pictures/1651760808654.jpg 原图片uri
            // /data/user/0/com.example.modulescore.main/cache/1651760808654.jpg 新建的file保存图片

            picFile = new File(getCacheDir(), new File(uri.getPath()).getName());
            Log.d(TAG,uri.getPath()+",,"+picFile.getPath());
            if(picFile.exists())           picFile.delete();
            picUri = Uri.fromFile(picFile);            //准备保存裁剪后图片Uri
            Log.d(TAG,picUri+",0,"+tempPicUri);
            cropImg(uri);
        });
        cropLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Glide.with(IdentificationActivity.this).load(picUri).into(img_identificationActivity);

                String filePath = picFile.getPath();
                byte[] imgData = new byte[0];
                try {
                    imgData = FileUtil.readFileByBytes(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //String imgStr = Base64Util.encode(imgData);
                //try {
                    //String imgParam = URLEncoder.encode(imgStr, "UTF-8");
                    //imageParam = "image=" + imgParam + "&top_num=" + 5;
                imageParam = Base64Util.encode(imgData);
                requestIdentificationResult();
               // } catch (UnsupportedEncodingException e) {
                //    e.printStackTrace();
               // }
            }
        });
        openCameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Log.d(TAG,picUri+",1,"+tempPicUri);
                //tempPicUri是拍摄图片的uri，用此uri生产picFile来保存裁剪后的图片
                picFile = new File(getCacheDir(), new File(tempPicUri.getPath()).getName());
                if(picFile.exists())           picFile.delete();
                picUri = Uri.fromFile(picFile);            //准备保存裁剪后图片Uri
                cropImg(tempPicUri);
            }
        });
        initDialog();
    }

    public void cropImg(Uri uri) {
        var intent = UCrop.of(uri, picUri)
                .withAspectRatio(1, 1)
                .getIntent(this);
        cropLauncher.launch(intent);
    }

   public void requestBaiduToken(){
       retrofit = new Retrofit.Builder()
               .baseUrl(baseUrl)
               .addConverterFactory(GsonConverterFactory.create())
               .build();
       GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
       Call<GetTokenItem> call = request.getBaiduToken(grant_type,apiKey,secretKey);//获得call对象
       call.enqueue(new Callback<GetTokenItem>() {
           @Override
           public void onResponse(Call<GetTokenItem> call, Response<GetTokenItem> response) {
               Log.d(TAG,"token:"+response.body().getAccess_token()+"detail:"+response);
               //鉴权Token
               access_token = response.body().getAccess_token();
               //过期时间 秒
               long expiresIn = response.body().getExpires_in();
               //当前时间 秒
               long currentTimeMillis = System.currentTimeMillis() / 1000;
               //放入缓存
               SPUtils.putString(Constant.TOKEN, access_token, IdentificationActivity.this);
               SPUtils.putLong(Constant.GET_TOKEN_TIME, currentTimeMillis, IdentificationActivity.this);
               SPUtils.putLong(Constant.TOKEN_VALID_PERIOD, expiresIn, IdentificationActivity.this);
               Log.d(TAG+"tokentoken:",",expiresIn:"+expiresIn+",currentTimeMillis"+currentTimeMillis);
           }

           @Override
           public void onFailure(Call<GetTokenItem> call, Throwable t) {
                Log.w(TAG,t.toString());
           }
       });
   }

    public void requestIdentificationResult(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<DiscernResultResponse> call = request.getDiscernResultResponse(access_token,imageParam,(float) 0.95);//获得call对象
        call.enqueue(new Callback<DiscernResultResponse>() {
            @Override
            public void onResponse(Call<DiscernResultResponse> call, Response<DiscernResultResponse> response) {
                discernResultResponse = response.body();
                Log.d(TAG, response.errorBody()+","+response+","+discernResultResponse.getLog_id() + ","+discernResultResponse.getResult_num());
                if (discernResultResponse.getResult() == null) {
                    Log.w(TAG, "RESULT == NULL");
                    ToastUtil.Companion.showToast("未识别出相应结果");
                } else {

                    Message message = new Message();
                    message.what = HandlerIdentification.finishIdentification;
                    handlerIdentification = new HandlerIdentification(Looper.getMainLooper(),IdentificationActivity.this);
                    handlerIdentification.sendMessage(message);
                    Log.d(TAG, discernResultResponse.getResult().size() + ","+discernResultResponse.getResult().get(0).getName());
                }
            }
            @Override
            public void onFailure(Call<DiscernResultResponse> call, Throwable t) {
                Log.w(TAG,t.toString());
            }
        });
    }

    public void refreshItemIdentification(){
        AdapterIdentification adapterIdentification = new AdapterIdentification(discernResultResponse.getResult());
        recycler_identificationActivity.setAdapter(adapterIdentification);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_identificationActivity.setLayoutManager(linearLayoutManager);
        adapterIdentification.notifyDataSetChanged();
    }

    /**
     * Token是否过期
     *
     * @return
     */
    private boolean isTokenExpired() {
        //获取Token的时间
        long getTokenTime = SPUtils.getLong(Constant.GET_TOKEN_TIME, 0, this);
        //获取Token的有效时间
        long effectiveTime = SPUtils.getLong(Constant.TOKEN_VALID_PERIOD, 0, this);
        //获取当前系统时间
        long currentTime = System.currentTimeMillis() / 1000;

        return (currentTime - getTokenTime) >= effectiveTime;
        //取出缓存中的获取Token的时间，然后获取Token的有效时长，再获取当前系统时间，然后通过当前系统时间减去获得Token的时间，
        // 得到的值再与Token有效期做比较，如果大于等于有效期则说明Token过期，返回true，否则返回false。
    }

    /**
     * 获取鉴权Token
     */
    private String getAccessToken() {
        String token = SPUtils.getString(Constant.TOKEN, null, this);
        Log.d(TAG,"SPtoken:"+token);
        if (token == null) {
            //访问API获取接口
            requestBaiduToken();
        } else {
            //则判断Token是否过期
            if (isTokenExpired()) {
                //过期
                requestBaiduToken();
            } else {
                access_token = token;
            }
        }
        return access_token;
    }

    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm() {
        var intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        imagePickLauncher.launch(intent);
    }

    //调用相机（指定相机拍摄照片保存地址，相片清晰度高）
    private void openCamera() throws IOException {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        File tempFile = new File(Environment.getExternalStorageDirectory(),
                filename + ".jpg");//先建保存拍摄图片的file

        //兼容android7.0 使用共享文件的形式
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
        tempPicUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        //再利用拍摄图片file生成uri

        if(tempFile != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Uri uri = FileProvider.getUriForFile(getContext(), "com.example.modulescore.main.fileprovider", outputImageFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,tempPicUri);//最后将uri放入intent中回调
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            openCameraLauncher.launch(intent);
        }
    }

    private void initDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);//创建对话框
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
                finish();
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

    public void identifyTakePhotoImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        1);
            }
        }
    }
}