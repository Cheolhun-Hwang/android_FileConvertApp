package com.hch.hooney.filecontrolproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hch.hooney.filecontrolproject.Application.MyApp;
import com.hch.hooney.filecontrolproject.Databases.DatabaseHandler;
import com.hch.hooney.filecontrolproject.Do.MainData;
import com.hch.hooney.filecontrolproject.FilePack.MyFIleControl;
import com.hch.hooney.filecontrolproject.FilePack.MyPdfControl;
import com.hch.hooney.filecontrolproject.Views.PageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int SIG_PERMISSION = 201;

    private Button search, import_csv, save_db, export_pdf;
    private EditText search_edit;
    private LinearLayout contents_view;

    private ArrayList<PageView> page_list;
    private ArrayList<MainData> main_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        loadData();
    }

    private void loadData() {
        DatabaseHandler.getInstance(getApplicationContext());
        main_list = DatabaseHandler.selectAll();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!isPermissions()){
            commitPermissions();
        }else{
            showPageViews();
        }
    }

    @Override
    protected void onStop() {
        DatabaseHandler.instance.helper.close();

        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_menu_make_temp_csv:
                if(MyFIleControl.makeTempCsv("temp.csv")){
                    Toast.makeText(this, "생성 완료", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "생성 실패", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isPermissions(){
        for(String permission : MyApp.permissions){
            int check = ContextCompat.checkSelfPermission(getApplicationContext(), permission);
            if(check == PackageManager.PERMISSION_DENIED){
                return false;
            }
        }
        return true;
    }

    private void commitPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(MyApp.permissions, SIG_PERMISSION);
        }
    }

    private void init() {
        page_list = new ArrayList<>();

        search = findViewById(R.id.main_search_btn);
        search_edit = findViewById(R.id.main_search_edit);
        contents_view = findViewById(R.id.main_contents_view);
        import_csv = findViewById(R.id.main_import_csv);
        export_pdf = findViewById(R.id.main_export_pdf);
        save_db = findViewById(R.id.main_save_db);

        import_csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissions()){
                    main_list = MyFIleControl.importCsv("temp.csv");
                    if(main_list != null){
                        Toast.makeText(getApplicationContext(), "import row "+main_list.size()+" 완료", Toast.LENGTH_SHORT).show();
                        showPageViews();
                    }else{
                        Toast.makeText(getApplicationContext(), "파일이 존재하지 않거나, 읽는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    commitPermissions();
                }
            }
        });

        save_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler.insertAll(main_list);
                Toast.makeText(MainActivity.this, "저장 완료", Toast.LENGTH_SHORT).show();
            }
        });

        export_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissions()){
                    if(MyPdfControl.savePdf(MyPdfControl.exportPdf(page_list))) {
                        Toast.makeText(MainActivity.this, "PDF 저장 완료", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "PDF 저장 실패", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    commitPermissions();
                }
            }
        });
    }

    private void showPageViews(){
        contents_view.removeAllViews();
        ArrayList<MainData> pageList = new ArrayList<>();
        int page_num = 1;
        for(int index = 0 ; index<main_list.size() ; index++){
            MainData item = main_list.get(index);
            pageList.add(item);

            if(((index+1)%10) == 0){
                PageView pageView = new PageView(getApplicationContext());
                pageView.addItem(pageList, page_num++);
                page_list.add(pageView);
                contents_view.addView(pageView);
                pageList.clear();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case SIG_PERMISSION:
                if(!isPermissions()){
                    noticeRequireUser();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private void noticeRequireUser() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("권한 설정")
                .setMessage("모든 권한이 필요합니다.")
                .setPositiveButton("권한 설정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        commitPermissions();
                    }
                }).show();
    }
}
