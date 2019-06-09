package com.example.mpp;

import android.database.Cursor;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;


public class User {
    private ArrayList<String> noteList;

    public User()
    {
        noteList = new ArrayList();
       File file = new File("brain");
        if(file.exists() == false && Note.exists("EMPTY") == false)
        {
            try{
                FileWriter fw = new FileWriter(file,true);
                fw.write("EMPTY");
                ArrayList<String> tmp = new ArrayList();
                tmp.add("createdTime");
                tmp.add("title");
                tmp.add("article");
                Note temp = new Note("EMPTY",tmp);
                fw.flush();
                fw.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        try{
            Scanner input = new Scanner(file);
            while(input.hasNext())
            {
                noteList.add(input.next());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public ArrayList<String> getNoteList()
    {
        return noteList;
    }


    public Cursor readNote(String noteName)
    {
        return new Note(noteName).open();
    }
    public Cursor getMemosByTime(String noteName, SimpleDateFormat form, SimpleDateFormat to)//YYYY-MM-DD
    {
        Note temp = new Note(noteName);

        String tmpStr = temp.getHowToRead().get(0);
        ArrayList<String> timeOptionStr = new ArrayList<String>();
        timeOptionStr.add(tmpStr + " BETWEEN \'"+ form + "\' AND \'" + to + "\'");

        return  temp.findMemo(null, timeOptionStr);
    }
    public Cursor getMemosByString(String noteName, String targetStr,ArrayList<String> field)
    {
        Note temp = new Note(noteName);
        ArrayList<String> options = new ArrayList();
        int numberOfField = field.size();
        for(int i = 0 ;i < numberOfField;i++)
        {
            options.add(field.get(i) + " like " + "\'%" + targetStr + "%\'");
            if(i < numberOfField - 1)
                options.add(" OR ");
        }
        return temp.findMemo(field,options);
    }

    public void addNote(String noteName, ArrayList<String> description)
    {
        Note temp = new Note(noteName,description);
        return;
    }

    public int addMemo(String noteName, ArrayList<String> contents)
    {
        Note temp = new Note(noteName);
        return temp.writeMemo(contents);
    }
    public int updateNote(String noteName, ArrayList<String> description)
    {
        Note temp = new Note(noteName);
        return temp.retreiveNote(description);
    }
    public int updateMemo(String noteName, ArrayList<String> contents)
    {
        Note temp = new Note(noteName);
        return temp.retreiveMemo(contents.get(0),contents);
    }
    public boolean deleteNote(String noteName)
    {
        return new Note(noteName).discardNote();
    }
    public boolean deleteMemo(String noteName,String createdTime)
    {
        return new Note(noteName).deleteMemo(createdTime);
    }




}
