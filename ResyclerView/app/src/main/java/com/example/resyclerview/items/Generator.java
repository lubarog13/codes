package com.example.resyclerview.items;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Generator {
    private final static String[] images = {
        "https://i.pinimg.com/564x/96/72/42/967242178d80d6398c0c134944a7eefa.jpg",
            "https://i.pinimg.com/564x/37/d6/5e/37d65e6b8658661ef89b87af010ed04d.jpg",
        "https://i.pinimg.com/564x/1a/b7/bc/1ab7bcb3a0d1a94eca056b1680751513.jpg",
        "https://i.pinimg.com/564x/61/53/2e/61532e4a5062488c6e74ede116c2687e.jpg",
        "https://i.pinimg.com/564x/40/d1/f2/40d1f28d2b8466dd25f2737d8b8a0c6a.jpg"
    };

    public static List<RowType> imageItemGenerator(int count){
        List<RowType> list = new ArrayList<>(count);
        Random random = new Random();
//        String image = images[random.nextInt(4)];
//        Log.d("image", image);
        for(int i=0; i<count; i++){
            list.add(new ImageItem(UUID.randomUUID().toString(), images[random.nextInt(5)]));
        }
        return list;
    }

    public static List<RowType> descriptionItemGenerator(int count){
        List<RowType> list = new ArrayList<>(count);
        Random random = new Random();

        for(int i=0; i<count; i++){
            list.add(new DescriptionItem(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        }
        return list;
    }
}
