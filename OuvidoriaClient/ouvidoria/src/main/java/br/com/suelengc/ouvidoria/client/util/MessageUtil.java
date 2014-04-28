package br.com.suelengc.ouvidoria.client.util;

import android.content.Context;
import android.widget.Toast;

public class MessageUtil {

    private final Context context;

    public MessageUtil(Context context) {
        this.context = context;
    }

    public void sendUserMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
