package com.hch.hooney.filecontrolproject.FilePack;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.view.View;

import com.hch.hooney.filecontrolproject.Views.PageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MyPdfControl {
    public final static String dir_name =
            MyFIleControl.dir_name+ "/exports";

    public static PdfDocument exportPdf(ArrayList<PageView> list){
        PdfDocument pdfDocument = new PdfDocument();

        for(int index = 0 ; index < list.size(); index++){
            PageView view = list.get(index);

            Bitmap bitmap = convertVtoB(view);
            //Page 정보 가로, 세로, 총 페이지
            PdfDocument.PageInfo info = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), (index+1)).create();

            //Page 생성
            PdfDocument.Page page = pdfDocument.startPage(info);

            //흰 바탕 깔기!
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#FFFFFF"));
            canvas.drawPaint(paint);

            //Bitmap 그리기
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
            paint.setColor(Color.BLUE);
            canvas.drawBitmap(bitmap, 0, 0, null);

            //pdf 종료
            pdfDocument.finishPage(page);
        }

        return pdfDocument;
    }

    public static Bitmap convertVtoB(View view){
        Bitmap bit = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bit);
        view.draw(c);
        return bit;
    }

    public static PdfDocument convertBtoP(Bitmap bitmap){
        PdfDocument pdfDocument = new PdfDocument();

        //Page 정보 가로, 세로, 총 페이지
        PdfDocument.PageInfo info = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();

        //Page 생성
        PdfDocument.Page page_1 = pdfDocument.startPage(info);

        //흰 바탕 깔기!
        Canvas canvas = page_1.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#FFFFFF"));
        canvas.drawPaint(paint);

        //Bitmap 그리기
        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);

        //pdf 종료
        pdfDocument.finishPage(page_1);
        return pdfDocument;
    }

    public static boolean savePdf(PdfDocument pdfDocument){
        if(pdfDocument != null){
            boolean flag = false;
            FileOutputStream fOut = null;
            try{
                File dir = new File(MyPdfControl.dir_name);
                if(!dir.exists())
                    dir.mkdirs();
                File file = new File(dir, MyFIleControl.makeFileName("pdf"));
                fOut = new FileOutputStream(file);
                pdfDocument.writeTo(fOut);
                fOut.flush();
                flag = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                pdfDocument.close();
                try {
                    if (fOut != null) {
                        fOut.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return flag;
            }
        }else{
            return false;
        }
    }
}
