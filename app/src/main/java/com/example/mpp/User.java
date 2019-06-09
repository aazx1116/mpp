package com.example.mpp;

import android.database.Cursor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;


public class User {
    private ArrayList<String> noteList;

    public User()
    {

       File file = new File("brain");

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

    public ArrayList<String> getHowToUse(String noteName)
    {
        return Note.getHowToUse(noteName);
    }

    public ArrayList<String> getNoteList()
    {
        return noteList;
    }


    public Cursor readNote(String noteName)
    {
        return Note.open(noteName);
    }

    public ArrayList<String> getMemo(String noteName, String createdTime)
    {
        ArrayList<String> temp = new ArrayList();
        temp.add("createdTime = "+createdTime);
        Cursor tmp = Note.findMemo(noteName,null,temp);
        for(int i  = 0 ;!tmp.isAfterLast();i++)
        {
            temp.add(tmp.getString(i));
        }
        tmp.close();
        return temp;
    }

    public Cursor getMemosByTime(String noteName, SimpleDateFormat form, SimpleDateFormat to)//YYYY-MM-DD
    {

        String tmpStr = Note.getHowToUse(noteName).get(0);
        ArrayList<String> timeOptionStr = new ArrayList<String>();
        timeOptionStr.add(tmpStr + " BETWEEN \'"+ form + "\' AND \'" + to + "\'");

        return  Note.findMemo(noteName,null, timeOptionStr);
    }
    public Cursor getMemosByString(String noteName, String targetStr,ArrayList<String> field)
    {

        ArrayList<String> options = new ArrayList();
        int numberOfField = field.size();
        for(int i = 0 ;i < numberOfField;i++)
        {
            options.add(field.get(i) + " like " + "\'%" + targetStr + "%\'");
            if(i < numberOfField - 1)
                options.add(" OR ");
        }
        return Note.findMemo(noteName,field,options);
    }

    public int addNote(String noteName, ArrayList<String> description)
    {
        return Note.createNote(noteName,description);
    }

    public int addMemo(String noteName, ArrayList<String> contents)
    {

        return Note.writeMemo(noteName,contents);
    }
    public int updateNote(String noteName, ArrayList<String> description)
    {
        return Note.modifyNote(noteName,description);
    }
    public int updateMemo(String noteName, ArrayList<String> contents)
    {
        return Note.modifyMemo(noteName,contents.get(0),contents);
    }
    public boolean deleteNote(String noteName)
    {
        return Note.discardNote(noteName);
    }
    public boolean deleteMemo(String noteName,String createdTime)
    {
        return Note.deleteMemo(noteName,createdTime);
    }




}
