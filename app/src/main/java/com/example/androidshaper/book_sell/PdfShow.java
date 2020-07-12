package com.example.androidshaper.book_sell;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;
import java.util.List;

public class PdfShow extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    PDFView pdfview;
    ProgressBar progressBar;
    Toolbar toolbarPdfView;
    Bundle bundleA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_show);
        //
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        this.bundleA=bundle;
        //
        pdfview=findViewById(R.id.pdfview);
        progressBar=findViewById(R.id.progress);
        toolbarPdfView=findViewById(R.id.toolbarPdfView);
        setSupportActionBar(toolbarPdfView);
        getSupportActionBar().setTitle(bundleA.getString("name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        progressBar.setVisibility(View.VISIBLE);

        UsePermission();


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.pdfshow_menue,menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home)
        {
            finish();
        }
        else if(item.getItemId()==R.id.refresh)
        {


           finish();
        }
        return true;
    }

    private void UsePermission() {
        String[] perms={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        if(EasyPermissions.hasPermissions(this,perms))
        {

            PdfViewShow();
        }
        else{
            EasyPermissions.requestPermissions(this,"Please Allow Permission Camera and Storage",1,perms);
            progressBar.setVisibility(View.INVISIBLE);

        }

    }


    private void PdfViewShow() {
        FileLoader.with(this)
                .load(bundleA.getString("url"))
                .fromDirectory("PDFFiles",FileLoader.DIR_EXTERNAL_PUBLIC)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        File file=response.getBody();

//                        if (request.getRequestListener()!=null)
//                        {
//                            file.delete();
//                            //Toast.makeText(getApplicationContext(),"requst null",Toast.LENGTH_LONG).show();
//
//                        }
//                        if(file.exists())
//                        {
//                            file.delete();
//                            file=response.getBody();
//
//                            Toast.makeText(getApplicationContext(),"File exist",Toast.LENGTH_LONG).show();
//                        }


                             pdfview.fromUri(Uri.fromFile(file))
                                     .pages(0,1,2,3,4,5,6,7,8)
                                     .password(null)
                                     .defaultPage(0)
                                     .enableSwipe(true)
                                     .enableDoubletap(true)
                                     .swipeHorizontal(false)
                                     .onDraw(new OnDrawListener() {
                                         @Override
                                         public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                         }
                                     })
                                     .onDrawAll(new OnDrawListener() {
                                         @Override
                                         public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                         }
                                     })
                                     .onPageError(new OnPageErrorListener() {
                                         @Override
                                         public void onPageError(int page, Throwable t) {
                                             Toast.makeText(getApplicationContext(),"Error loading"+page,Toast.LENGTH_LONG).show();
                                         }
                                     })
                                     .onPageChange(new OnPageChangeListener() {
                                         @Override
                                         public void onPageChanged(int page, int pageCount) {

                                         }
                                     })
                                     .onTap(new OnTapListener() {
                                         @Override
                                         public boolean onTap(MotionEvent e) {
                                             return true;
                                         }
                                     })
                                     .onRender(new OnRenderListener() {
                                         @Override
                                         public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                             pdfview.fitToWidth();
                                         }
                                     })
                                     .enableAnnotationRendering(true)
                                     .invalidPageColor(Color.WHITE)
                                     .load();







                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {

                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Error loading"+t,Toast.LENGTH_LONG).show();

                    }
                });
    }

    @Override
    protected void onRestart() {
        UsePermission();
        super.onRestart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        PdfViewShow();

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this,"If You Cant't Permission Granted Them You Cant't See Book Sample.",Toast.LENGTH_LONG).show();
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms))
        {

            new AppSettingsDialog.Builder(this).build().show();;
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }


}