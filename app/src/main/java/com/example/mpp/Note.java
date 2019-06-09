package com.example.mpp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class  Note{
    private SQLiteDatabase myBag;
    private String noteName;
    private ArrayList<String> description;

    public Note(String noteName)
    {
        this.noteName = noteName;
        this.myBag = SQLiteDatabase.openOrCreateDatabase("myBag",null);
        this.getDescription();
    }
    public Note(String noteName,ArrayList<String> description)
    {
        this.noteName = noteName;
        this.myBag = SQLiteDatabase.openOrCreateDatabase("myBag",null);
        String descriptionStr ="";
        int numberOfDescription = description.size();
        for(int i = 0 ; i < numberOfDescription;i++)
        {
            descriptionStr += description.get(i) + " TEXT";
            if(i == 0 )
                descriptionStr += " PRIMARY KEY";
            if(i != numberOfDescription - 1)
                descriptionStr +=",";
        }

        myBag.rawQuery("CREATE IF NOT EXISTS " + noteName +"("+ descriptionStr +")",null);

    }

    public static boolean exists(String noteName)
    {
        SQLiteDatabase temp = SQLiteDatabase.openOrCreateDatabase("myBag",null);
        Cursor tmp = temp.rawQuery("SELECT name FROM sqlite_master WHERE type = \'table\' AND name = \'" + noteName  + "\'",null);

        return tmp.getCount() > 0 ? true:false;

    }
    private ArrayList<String> getDescription()
    {
        ArrayList<String> result= new ArrayList();

        Cursor temp =  myBag.rawQuery("SELECT sql FROM sqlite_master WHERE name=\'" + noteName + "\'",null);
        for(int i = 0 ; !temp.isAfterLast();i++)
        {
            result.add(temp.getString(i));
        }

     return result;
    }
    public Cursor open()
    {
         return this.findMemo(null,null);
    }

    public int writeMemo(ArrayList<String> contexts)
    {
        String descriptionStr = "";
        String contextStr = "";
        int numberOfContext = description.size();

        descriptionStr+="(";
        for(int  i = 0 ; i < numberOfContext;i++)
        {
            descriptionStr +=description.get(i);
            if(i != numberOfContext - 1)
                descriptionStr +=",";
        }
        descriptionStr+=")";
        contextStr+="(";
        for(int  i = 0 ; i < numberOfContext;i++)
        {
            contextStr +=description.get(i);
            if(i != numberOfContext - 1)
                contextStr +=",";
        }
        contextStr+=")";
        try{
            myBag.execSQL("INSERT INTO "+noteName+descriptionStr+ "VALUES" +contextStr);
        }
        catch(Exception e)
        {
            return 0;
        }
        return 1;
    }

    public ArrayList<String>getHowToRead()
    {
        return description;
    }

    public boolean discardNote()
    {
        try {
            myBag.execSQL("DROP TABLE IF EXISTS \'" + noteName + "\'");
        }
        catch(Exception e)
        {
            return false;
        }

        return true;
    }

    public Cursor findMemo(ArrayList<String> field,ArrayList<String> options)
    {
        String fieldStr ="";
        String optionStr ="";
        if(field == null)
        {
            fieldStr = "*";
        }
        else
        {
            for(int  i = 0 ; i < field.size();i++)
            {
                fieldStr +=field.get(i) ;
                if(i!=field.size() -1)
                    fieldStr +=",";
            }
        }
        if(options == null)
        {
            return myBag.rawQuery("SELECT " + fieldStr +"FROM" + noteName,null);
        }
        else
        {
            for(int i = 0 ; i < options.size();i++)
            {
                optionStr +=options.get(i);
            }

            return myBag.rawQuery("SELECT " + fieldStr +"FROM" + noteName + "WHERE" + optionStr,null);
        }

    }

    public int retreiveNote(ArrayList<String> newDescription)
    {
        try {
            for(int i = 0 ; i < newDescription.size();i++)
            {
                if(!description.contains(newDescription.get(i)))
                    myBag.execSQL("ALTER TABLE" + noteName +"ADD COLUMN" + newDescription.get(i) + "TEXT");
            }
        }
        catch(Exception e)
        {
            return 0;
        }
        this.description = newDescription;
        return 1;
    }

    public boolean deleteMemo(String createdTime)
    {
        try{
            myBag.execSQL("DELETE " + noteName + " WHERE " + description.get(0) + " = " + createdTime);
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }

    public int retreiveMemo(String createdTime,ArrayList<String> newContents)
    {
        String setStr = null;
        String descriptionTmp;
        String contentsTmp;

        int numberOfDescription = description.size();

        for(int i = 0 ; i < numberOfDescription ;i++)
        {
            descriptionTmp = description.get(i);

            if(newContents.get(i) == null)
                contentsTmp = "-";
            else
                contentsTmp = newContents.get(i);
            setStr += descriptionTmp + "= \'" +contentsTmp +"\'";
            if(i != numberOfDescription - 1)
                setStr +=",";
        }

        try {
            myBag.execSQL("UPDATE " +noteName+" SET " + setStr +" WHERE " + description.get(0) + " = " + createdTime);
        }
        catch (Exception e)
        {
            return 0;
        }
        return 1;
    }

    public class Memo{
        ArrayList<String> descriptions;
        ArrayList<String> attributes;
        ArrayList<String> contents;
    }
}
