package com.suenara.exampleapp.presentation.exception;

import android.content.Context;

import com.suenara.exampleapp.data.exception.NetworkRequestException;
import com.suenara.exampleapp.presentation.R;

public class ErrorMessageFactory {

    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.exception_message_generic);

        if (exception instanceof NetworkRequestException) {
            message = context.getString(R.string.exception_message_no_connection);
        }

        return message;
    }
}
