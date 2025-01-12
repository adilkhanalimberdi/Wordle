package com.example.wordle.words;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FiveLetterWord {
    private static ArrayList <String> data = new ArrayList<>(List.of(
            "PEACE",
            "BEACH",
            "KNIFE",
            "BLOOD",
            "SHEEP",
            "HORSE",
            "HOUSE",
            "KITTY",
            "QUEUE",
            "SOCKS",
            "SANTA",
            "QUICK",
            "PIZZA",
            "JUICE",
            "APPLE",
            "OCEAN",
            "ENEMY",
            "GREAT",
            "CRUSH",
            "RELAX",
            "DRINK",
            "WORLD",
            "CRAZY",
            "FIXED",
            "EQUIP",
            "FLASH"
    ));
    private static int pointer = data.size();

    public static String randomWord() {
        if (pointer == data.size()) {
            Collections.shuffle(data);
            pointer = 0;
        }
        pointer++;
        return data.get(pointer - 1);
    }

}
