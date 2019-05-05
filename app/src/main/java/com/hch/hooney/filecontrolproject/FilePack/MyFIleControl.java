package com.hch.hooney.filecontrolproject.FilePack;

import android.os.Environment;
import android.widget.Toast;

import com.hch.hooney.filecontrolproject.Do.MainData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyFIleControl {
    public final static String dir_name =
            Environment.getExternalStorageDirectory().getAbsolutePath()+ "/FileConvertApp";

    public static boolean makeTempCsv(String file){
        File saveFile = new File(dir_name);
        if(!saveFile.exists()){
            saveFile.mkdir();
        }

        ArrayList<MainData> list = tempList();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile+"/"+file, false));
            for(MainData item : list){
                writer.append(item.toString());
                writer.newLine();
            }
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static ArrayList<MainData> tempList(){
        ArrayList<MainData> temp = new ArrayList<>();
        for(int i = 0 ; i < 100 ; i++){
            MainData item = new MainData();
            item.setM_name("홍길동"+(i+1));
            item.setM_postNumber("" + (1234+i));
            item.setM_address1("서울 중랑구 신내로 "+(101+i));
            item.setM_address2("1동 "+ (101+i) +"호(신내동,동성아파트)");
            temp.add(item);
        }
        return temp;
    }

    public static String makeFileName(String type){
        Date current = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_hhmmss", Locale.KOREA);
        return format.format(current).toString() +"."+type;
    }

    public static ArrayList<MainData> importCsv(String file){
        File loadFile = new File(dir_name+"/"+file);
        if(!loadFile.exists()){
            return null;
        }

        try {
            ArrayList<MainData> list = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(loadFile));
            String line = null;

            while ((line = reader.readLine())!= null){
                String[] split = line.split(",");
                MainData item = new MainData();
                item.setM_name(split[0]);
                item.setM_postNumber(split[1]);
                item.setM_address1(split[2]);
                item.setM_address2(split[3]);
                list.add(item);
            }
            return list;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
