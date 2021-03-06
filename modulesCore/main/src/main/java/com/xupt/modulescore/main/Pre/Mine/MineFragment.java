package com.xupt.modulescore.main.Pre.Mine;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.xupt.safeAndRun.modulesbase.libbase.util.PropertiesUtil;
import com.xupt.modulescore.main.Data.PreDataActivity;
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
import com.xupt.modulescore.main.Identification.IdentificationActivity;
import com.example.modulescore.main.R;
import com.xupt.modulescore.main.net.GetRequest_Interface;
import com.xupt.modulescore.main.Data.StatisticsActivity;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MineFragment extends Fragment {
    LinearLayout linearLayout;
    List<View> viewList = new ArrayList<>();
    final String TAG = "MineFragmentTAG";
    public ImageView userimg_fragmentmine;
    public ImageView backimg_fragmentmine;
    String[] titles = {"????????????","????????????","????????????","???????????????","????????????","????????????"};
    int[] imgResource = {R.drawable.runningimg,R.drawable.statisticsimg,R.drawable.dishimg,R.drawable.shoppingimg,R.drawable.collectionimg,R.drawable.aboutusimg};
    public Uri picUri;
    public Uri tempPicUri;
    public Uri backgroundUri;
    public Uri tempBackgroundUri;
    private ActivityResultLauncher imagePickLauncher,backgroundPickLauncher;
    private ActivityResultLauncher openCameraLauncher,openBackgroundCameraLauncher;
    private ActivityResultLauncher cropLauncher,cropBackgroundLauncher;
    private File picFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(o) -> {
            Uri uri;
            if (o.getData() != null)    uri = o.getData().getData();//??????????????????Uri
            else    uri = Uri.EMPTY;
            ///raw//storage/emulated/0/Pictures/1651760808654.jpg ?????????uri
            // /data/user/0/com.example.modulescore.main/cache/1651760808654.jpg ?????????file????????????
            picFile = new File(getContext().getCacheDir(), new File(uri.getPath()).getName());
            Log.d(TAG,uri.getPath()+",,"+picFile.getPath());
            if(picFile.exists())           picFile.delete();
            picUri = Uri.fromFile(picFile);            //???????????????????????????Uri
            Log.d(TAG,picUri+",0,"+tempPicUri);
            cropImg(uri);
        });
        backgroundPickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(o)->{
            Uri uri;
            if (o.getData() != null)    uri = o.getData().getData();//??????????????????Uri
            else    uri = Uri.EMPTY;
            ///raw//storage/emulated/0/Pictures/1651760808654.jpg ?????????uri uri
            // /data/user/0/com.example.modulescore.main/cache/1651760808654.jpg ?????????file???????????? file
            picFile = new File(getContext().getCacheDir(), new File(uri.getPath()).getName());
            Log.d(TAG,uri.getPath()+",,"+picFile.getPath());
            if(picFile.exists())           picFile.delete();
            backgroundUri = Uri.fromFile(picFile);            //???????????????????????????Uri
            Log.d(TAG,backgroundUri+",0,"+tempBackgroundUri);
            cropBackGroundImg(uri);
        });

        cropLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Glide.with(getContext()).load(picUri).into(userimg_fragmentmine);
                uploadUserImg();
            }
        });
        cropBackgroundLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(o)->{
            Glide.with(getContext()).load(backgroundUri).into(backimg_fragmentmine);
        });
        openCameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
               Log.d(TAG,picUri+",1,"+tempPicUri);
               //?????????Uri; = picUri2???tempUri???????????????????????????????????????Uri
                picFile = new File(getContext().getCacheDir(), new File(tempPicUri.getPath()).getName());
               if(picFile.exists())           picFile.delete();
               picUri = Uri.fromFile(picFile);            //???????????????????????????Uri
               cropImg(tempPicUri);
            }
        });

        openBackgroundCameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(o) ->{
            Log.d(TAG,backgroundUri+",1,"+tempBackgroundUri);
            //?????????Uri; = picUri2???tempUri???????????????????????????????????????Uri
            picFile = new File(getContext().getCacheDir(), new File(tempBackgroundUri.getPath()).getName());
            if(picFile.exists())           picFile.delete();
            backgroundUri = Uri.fromFile(picFile);            //???????????????????????????Uri
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
                Intent intent = new Intent(getActivity(), PreDataActivity.class);
                startActivity(intent);
            }
        });
        viewList.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StatisticsActivity.class);
                startActivity(intent);
            }
        });
        viewList.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IdentificationActivity.class);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//???????????????
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.userimg_dialog, null);//?????????????????????
        TextView tv_camera_dialog = layout.findViewById(R.id.tv_camera_dialog);
        TextView tv_photoalbum_dialog = layout.findViewById(R.id.tv_photoalbum_dialog);
        TextView tv_cancel = layout.findViewById(R.id.tv_cancel);
        builder.setView(layout);//????????????????????????
        AlertDialog alertDialog = builder.create();//????????????????????????
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
                    openCamera_Back();
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
        alertDialog.setCanceledOnTouchOutside(true);//????????????Dialog????????????????????????
        alertDialog.show();//???????????????
    }
    private void initDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//???????????????
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.userimg_dialog, null);//?????????????????????
        TextView tv_camera_dialog = layout.findViewById(R.id.tv_camera_dialog);
        TextView tv_photoalbum_dialog = layout.findViewById(R.id.tv_photoalbum_dialog);
        TextView tv_cancel = layout.findViewById(R.id.tv_cancel);
        builder.setView(layout);//????????????????????????
        AlertDialog backAlertDialog = builder.create();//????????????????????????
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
        backAlertDialog.setCanceledOnTouchOutside(true);//????????????Dialog????????????????????????
        backAlertDialog.show();//???????????????
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
                //??????WRITE_EXTERNAL_STORAGE??????
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                        1);
            }
        }
    }

    //???????????????????????????????????????????????????????????????????????????
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
        //??????android7.0 ???????????????????????????
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

    //???????????????????????????????????????????????????????????????????????????
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
        //??????android7.0 ???????????????????????????
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
     * ?????????????????????
     */
    private void getPicFromAlbm() {
        var intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        imagePickLauncher.launch(intent);
    }

    /**
     * ?????????????????????
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

    private void uploadUserImg(){
        String baseUrl = PropertiesUtil.props.getProperty("baseUrl");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);


        // ?????? RequestBody?????????????????????RequestBody
        // RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"),picFile);

        // MultipartBody.Part  ??????????????????Key????????????partName??????file
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", picFile.getName(), requestFile);

        // ????????????
        String descriptionString = "hello, ??????????????????";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

        Call<Object> call = request.uploadUserImg( body);//??????call??????
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d(TAG,"success"+response);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d(TAG,"failure"+t);
            }
        });
    }
}