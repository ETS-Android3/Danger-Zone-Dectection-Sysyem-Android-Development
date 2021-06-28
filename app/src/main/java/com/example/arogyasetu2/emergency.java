package com.example.arogyasetu2;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
//

public class emergency extends AppCompatActivity {


    Button addinfo;
    private PdfRenderer mPdfRenderer;
    private PdfRenderer.Page mPdfPage;
    private ImageView mImageView;
    ImageButton iml,imr;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emengercy);

        addinfo=findViewById(R.id.button10);

        iml=findViewById(R.id.imageButton3);
        imr=findViewById(R.id.imageButton4);

        addinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intoreg=new Intent(emergency.this,info.class);
                startActivity(intoreg);
            }
        });

        mImageView = findViewById(R.id.imageView8);
        try {
            openPdfWithAndroidSDK(mImageView, i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        iml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(i==0) {
                        i = 9;
                    }
                    else{
                        i--;
                    }
                    openPdfWithAndroidSDK(mImageView, i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        imr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(i<9){
                    i++;
                    }
                    else{
                        i=0;
                    }
                    openPdfWithAndroidSDK(mImageView, i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPdfPage != null) {
            mPdfPage.close();
        }
        if (mPdfRenderer != null) {
            mPdfRenderer.close();
        }
    }

    /**
     * Render a given page in the PDF document into an ImageView.
     *
     * @param imageView  used to display the PDF
     * @param pageNumber page of the PDF to view (index starting at 0)
     */
    void openPdfWithAndroidSDK(ImageView imageView, int pageNumber) throws IOException {
        // Copy sample.pdf from raw resource folder into local cache, so PdfRenderer can handle it
        File fileCopy = new File(getCacheDir(), "src\\main\\res\\raw\\additionnalinfo.pdf");
        copyToLocalCache(fileCopy, R.raw.additionnalinfo);

        // We will get a page from the PDF file by calling PdfRenderer.openPage
        ParcelFileDescriptor fileDescriptor =
                ParcelFileDescriptor.open(fileCopy,
                        ParcelFileDescriptor.MODE_READ_ONLY);
        mPdfRenderer = new PdfRenderer(fileDescriptor);
        mPdfPage = mPdfRenderer.openPage(pageNumber);

        // Create a new bitmap and render the page contents on to it
        Bitmap bitmap = Bitmap.createBitmap(mPdfPage.getWidth(),
                mPdfPage.getHeight(),
                Bitmap.Config.ARGB_8888);
        mPdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        // Set the bitmap in the ImageView so we can view it
        imageView.setImageBitmap(bitmap);
    }

    /**
     * Copies the resource PDF file locally so that {@link PdfRenderer} can handle the file
     *
     * @param outputFile  location of copied file
     * @param pdfResource pdf resource file
     */
    void copyToLocalCache(File outputFile, @RawRes int pdfResource) throws IOException {
        if (!outputFile.exists()) {
            InputStream input = getResources().openRawResource(pdfResource);
            FileOutputStream output;
            output = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int size;
            // Just copy the entire contents of the file
            while ((size = input.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }
            input.close();
            output.close();
        }
    }
}