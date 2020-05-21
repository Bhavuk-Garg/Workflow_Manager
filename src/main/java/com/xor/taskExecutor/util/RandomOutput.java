package com.xor.taskExecutor.util;

import java.util.Random;

public class RandomOutput {
    public static String getOutput(){
        Random random=new Random();
        int randomValue=random.nextInt(4);  //random value between [0-3]
        switch (randomValue)
        {
            case 0:
                return "A";
            case  1:
                return "B";
            case 2:
                return "C";
//            case 3:
//                return "D";
            default:
                return "E";
        }
    }
}
