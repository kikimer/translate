package com.freeCodder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.util.function.Consumer;

public class ChangeSelectedObserver implements Runnable {

    public ChangeSelectedObserver(TranslateClient translateClient, Consumer<String> translateListener) {
        this.translateClient = translateClient;
        this.translateListener = translateListener;
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemSelection();
        String textToTranslate = null;
        while (true) {
            try {
                textToTranslate = (String) cb.getData(DataFlavor.stringFlavor);
            } catch (Exception e) {
                textToTranslate = null;
                e.printStackTrace();
            }
            if (textToTranslate != null && !textToTranslate.equals(oldValue)) {
                oldValue = textToTranslate;
                textToTranslate = textToTranslate.replaceAll(System.getProperty("line.separator"), " ");
                if (textToTranslate.length() != 0)
                    translateListener.accept(translateClient.translate(textToTranslate));
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }


    private String oldValue;
    private TranslateClient translateClient;
    private Consumer<String> translateListener;
}
