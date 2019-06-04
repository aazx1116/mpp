package com.example.mpp;

import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Map;

public class Note implements mongoDBIO{

    String name;
    ArrayList<String>  description;
    long createTime;
    long updateTime;

    public Note(String name, ArrayList<String> descripton)
    {
        this.name = name;
        this.description = descripton;
    }

    public Memo getMemo(String title)
    {
        ArrayList<String> tmp = new ArrayList<String>();

        Memo temp = new Memo(title, tmp,tmp);
        return temp;
    }

    @Override
    public ArrayList<String> read(String noteName) {
        return null;
    }
    @Override
    public void write(String noteName, ArrayList<String> context)
    {
        MongoCollection<Document> localCollection = mobileClient.getDatabase(noteName).getCollection(noteName);

    }
    @Override
    public void update(String name, ArrayList<String> context)
    {

    }
    @Override
    public void delete(String name)
    {

    }

    class Memo implements mongoDBIO{
        String title;
        Map<String, String> contents;
        long createTime;

        public Memo(String title, Map<String, String> contents)
        {
            this.title = title;
            this.contents = contents;
        }

        public Memo(String title, ArrayList<String> description, ArrayList<String> contents)
        {
            int tmp = description.size();
            this.title =title;
            for(int i = 0 ; i < tmp; i++)
            {
                this.contents.put(description.get(i),contents.get(i));
            }
        }

        public long getCreateTime()
        {
            return this.createTime;
        }

        @Override
        public ArrayList<String> read(String name) {
            return null;
        }
        @Override
        public void write(String name, ArrayList<String> context)
        {

        }
        @Override
        public void update(String name, ArrayList<String> context)
        {

        }
        @Override
        public void delete(String name)
        {

        }
    }
}
