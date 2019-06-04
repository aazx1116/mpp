package com.example.mpp;

import com.mongodb.client.MongoClient;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.services.mongodb.local.LocalMongoDbService;

import java.util.ArrayList;

public interface mongoDBIO {
    StitchAppClient client = Stitch.initializeDefaultAppClient("com.example.mpp");
    MongoClient mobileClient = client.getServiceClient(LocalMongoDbService.clientFactory);


    public ArrayList<String> read(String name);
    public void write(String name, ArrayList<String> context);
    public void update(String name, ArrayList<String> context);
    public void delete(String name);

}
