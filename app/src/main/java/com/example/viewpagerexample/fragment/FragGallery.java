package com.example.viewpagerexample.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.viewpagerexample.BuildConfig;
import com.example.viewpagerexample.Image_show;
import com.example.viewpagerexample.R;
import com.example.viewpagerexample.Room.AppDataBase_gallery;
import com.example.viewpagerexample.adapters.ImageAdapter;
import com.example.viewpagerexample.adapters.RecyclerViewDecoration;
import com.example.viewpagerexample.gallery_camera;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class FragGallery extends Fragment {

    private View view;
    private Button add;
    private Button remove;
    private FloatingActionButton cam;

    private ImageView img1;
    private ImageView img2;
    private Integer cnt=0;
    String cnt_s;
    String image_num = "count";
    static private String SHARE_NAME = "SHARE_PREF";
    static SharedPreferences sharePref = null;
    static SharedPreferences.Editor editor = null;

    private static final String TAG = "MultiImageActivity";
    ArrayList<Uri> uriList = new ArrayList<>();     // ???????????? uri??? ?????? ArrayList ??????

    RecyclerView recyclerView;  // ???????????? ????????? ??????????????????
    ImageAdapter adapter;  // ????????????????????? ???????????? ?????????
    GridLayoutManager gridLayoutManager;

    AppDataBase_gallery db;

    File file;

    private SwipeRefreshLayout swipe;

    private String mCurrentPhotoPath;



    //?????? ????????????
    public static FragGallery newInstance() {
        FragGallery fragGallery = new FragGallery();
        return fragGallery;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_gallery, container, false);


        add = (Button) view.findViewById(R.id.getGallery);
        remove = (Button)view.findViewById(R.id.remove);
        recyclerView = view.findViewById(R.id.recyclerView);
        cam = view.findViewById(R.id.getCamera);

        sharePref = getActivity().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        editor = sharePref.edit();
        gridLayoutManager = new GridLayoutManager(getContext(), 4);
        db = AppDataBase_gallery.getInstance(getContext());

        swipe = view.findViewById(R.id.swipelayout);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Map<String, ?> totalValue = sharePref.getAll();
                cnt = sharePref.getInt("Count",0 );
                Log.d("count", "??????:"+cnt);
                Toast.makeText(getContext(), "??????:"+cnt, Toast.LENGTH_SHORT).show();

                if(cnt==0){
                    uriList.clear();
                }

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(gridLayoutManager);

                swipe.setRefreshing(false);
            }
        });


        Map<String, ?> totalValue = sharePref.getAll();
        cnt = sharePref.getInt("Count",0 );
        Log.d("count", "??????:"+cnt);
        Toast.makeText(getContext(), "??????:"+cnt, Toast.LENGTH_SHORT).show();


        try {
            for(int i=0;i<cnt;i++){
                Log.d("look", "for ??????");
                String imgpath = getActivity().getCacheDir() + "/" + i;   // ?????? ???????????? ???????????? ?????? ????????? ??????
                Log.d("look", "impath ??????"+imgpath);
                Bitmap bm = BitmapFactory.decodeFile(imgpath);
                Log.d("look", "???????????????"+bm);

                ExifInterface ei = new ExifInterface(imgpath);
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

//                            //?????????????????? ?????? ????????? ??????????????? ??????
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 8; //8?????? 1????????? ????????? ?????? ??????
                            Bitmap bitmap = BitmapFactory.decodeFile(imgpath, options);

                Bitmap rotatedBitmap = null;
                switch (orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateImage(bm, 90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateImage(bm, 180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateImage(bm, 270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        if(bitmap.getHeight()==bitmap.getWidth()){
                            rotatedBitmap = rotateImage(bm, 90);
                            break;

                        }
                        rotatedBitmap = bm;
                }



                Uri uri_set = getImageUri(getContext(), rotatedBitmap);
                Log.d("look", "uri ??????");

                uriList.add(uri_set);
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
        }

        adapter = new ImageAdapter(uriList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(getContext(), 3,3));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cnt >= 20) {
                    Toast.makeText(getContext(), "???????????? ?????? ????????????.", Toast.LENGTH_LONG).show();

                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
                }
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeData();
                db.userDao().deleteAll();
            }
        });

        file = new File(getActivity().getCacheDir(), cnt.toString());


        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), gallery_camera.class);
                startActivity(intent);
            }
        });




        return view;
    }



    public void capture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(),BuildConfig.APPLICATION_ID+".fileprovider",file));
        //Log.d("look", "????????????"+file);
        startActivityForResult(intent, 101);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // ?????????
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("look", "???????????? ?????? ???");

        if (requestCode == 100) {
            if (resultCode == getActivity().RESULT_OK) {
                //ClipData ?????? Uri??? ????????????
                Uri uri = data.getData();
                ClipData clipData = data.getClipData();

                //????????? URI ??? ???????????? ??????????????? ???????????? ????????????.
                if (clipData != null) {

                    if(clipData.getItemCount() > 20) {   // ????????? ???????????? 20??? ????????? ??????
                        Toast.makeText(getContext(), "????????? 20????????? ?????? ???????????????.", Toast.LENGTH_LONG).show();
                    }

                    else if(clipData.getItemCount()+cnt>20){
                        Toast.makeText(getContext(), "?????? "+(20-cnt)+"?????? ?????? ???????????????.", Toast.LENGTH_LONG).show();

                    }

                    else {
                        ContentResolver resolver = getActivity().getContentResolver();
                        for (int i = 0; i < clipData.getItemCount(); i++) {

                            Uri uri_re = clipData.getItemAt(i).getUri();

                            try {
                                uriList.add(uri_re);  //uri??? list??? ?????????.
                                InputStream instream = resolver.openInputStream(uri_re);
                                Bitmap imgBitmap = BitmapFactory.decodeStream(instream);



                                instream.close();   // ????????? ????????????
                                saveBitmapToJpeg(imgBitmap);    // ?????? ???????????? ??????
                            } catch (Exception e) {
                                Toast.makeText(getContext(), "?????? ???????????? ??????", Toast.LENGTH_SHORT).show();
                            }

                        }





                    }
                } else if (uri != null) {
                    ContentResolver resolver = getActivity().getContentResolver();

                    uriList.add(uri);

                    try {
                        InputStream instream = resolver.openInputStream(uri);
                        Bitmap imgBitmap = BitmapFactory.decodeStream(instream);

                        instream.close();   // ????????? ????????????
                        saveBitmapToJpeg(imgBitmap);    // ?????? ???????????? ??????

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "?????? ???????????? ??????", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        }


        updateData(cnt);
        adapter = new ImageAdapter(uriList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(getContext(), 3,3));
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public void saveBitmapToJpeg (Bitmap bitmap){   // ????????? ????????? ?????? ???????????? ??????
        File tempFile = new File(getActivity().getCacheDir(), cnt.toString());
        Log.d("look", "????????????"+tempFile);

        // ?????? ????????? ?????? ??????
        try {
            tempFile.createNewFile();   // ???????????? ??? ????????? ????????????
            FileOutputStream out = new FileOutputStream(tempFile);  // ????????? ??? ??? ?????? ???????????? ????????????




            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   // compress ????????? ????????? ???????????? ???????????? ????????????
            out.close();    // ????????? ????????????
            cnt++;
        } catch (Exception e) {
            Toast.makeText(getContext(), "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
        }
    }



    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        Log.d("look", "compress ??????");
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        Log.d("look", "insert ??????"+path);
        Uri i = Uri.parse(path);
        Log.d("look", "parse ??????");
        return i;
    }


    public void updateData(int cnt){
        editor.putInt("Count", cnt);
        editor.apply();
    }

    public void removeData(){
        editor.putInt("Count", 0);
        editor.apply();
    }



}
