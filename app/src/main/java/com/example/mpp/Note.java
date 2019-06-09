package com.example.mpp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class  Note{
    private static SQLiteDatabase myBag;
    //myBag.rawQuery("CREATE IF NOT EXISTS " + noteName +"("+ descriptionStr +")",null);
    static {
        myBag = SQLiteDatabase.openOrCreateDatabase("myBag",null);
        myBag.execSQL("CREATE TABLE IF NOT EXISTS EMPTY " +
                "(createdTime TEXT primary Key," +
                "title TEXT," +
                "article TEXT)");
        //tmp.add("createdTime");
        //tmp.add("title");
        //mp.add("article");
        //
        //
    }

    public static int createNote(String noteName,ArrayList<String> description)
    {
        String attributeStr ="(";
        int numberOfDescription = description.size();


        for(int i = 0 ; i < numberOfDescription ;i++)
        {
            attributeStr += description.get(i) + " TEXT ";
            if(i == 0 )
            {
                attributeStr +="PRIMARY KEY,";
            }
            if(i < numberOfDescription -1)
            {
                attributeStr +=",";
            }
        }
        attributeStr +=")";
        try {
            myBag.execSQL("CREATE TABLE IF NOT EXISTS " + noteName + attributeStr +";");
        }
        catch(Exception e)
        {
            return 0;
        }
        return 1;
    }

    public static boolean exists(String noteName)
    {
        SQLiteDatabase temp = SQLiteDatabase.openOrCreateDatabase("myBag",null);
        Cursor tmp = temp.rawQuery("SELECT name FROM sqlite_master WHERE type = \'table\' AND name = \'" + noteName  + "\'",null);

        return tmp.getCount() > 0 ? true:false;

    }
    private static ArrayList<String> getDescription(String noteName)
    {
        ArrayList<String> result= new ArrayList();

        Cursor temp =  myBag.rawQuery("SELECT sql FROM sqlite_master WHERE name=\'" + noteName + "\'",null);
        for(int i = 0 ; !temp.isAfterLast();i++)
        {
            result.add(temp.getString(i));
        }

     return result;
    }
    public static ArrayList<String> getHowToUse(String noteName)
    {
        return getDescription(noteName);
    }

    public static Cursor open(String noteName)
    {
         return findMemo(noteName,null,null);
    }

    public static int writeMemo(String noteName,ArrayList<String> contexts)
    {
        ArrayList<String> description = getDescription(noteName);
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
            contextStr +=contexts.get(i);
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


    public static boolean discardNote(String noteName)
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

    public static Cursor findMemo(String noteName,ArrayList<String> field,ArrayList<String> options)
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

    public static int modifyNote(String noteName,ArrayList<String> newDescription)
    {
        ArrayList<String> description = getDescription(noteName);
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
        description = newDescription;
        return 1;
    }

    public static boolean deleteMemo(String noteName,String createdTime)
    {
        ArrayList<String> description = getDescription(noteName);
        try{
            myBag.execSQL("DELETE " + noteName + " WHERE " + description.get(0) + " = " + createdTime);
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }

    public static int modifyMemo(String noteName,String createdTime,ArrayList<String> newContents)
    {
        ArrayList<String> description = getDescription(noteName);
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
