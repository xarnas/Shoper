package com.arnas.shoper;


import android.app.Notification;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by arnaspetrauskas on 25/05/2017.
 */

public class FileReadWrite {

    public void FileWrite() {
       String Message ="TextMessage";
       String fileName ="MainMenu";
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(Message.getBytes());
            fileOutputStream.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String FileRead(){
        StringBuilder stringBuilder = new StringBuilder();
        String Message;
        try{

            FileInputStream fileInputStream = new FileInputStream("MainMenu");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((Message=bufferedReader.readLine())!=null){
                stringBuilder.append(Message+"\n");
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
            }
            return stringBuilder.toString();
        }
    }

