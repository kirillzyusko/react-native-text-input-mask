package com.RNTextInputMask;

import android.app.Activity;
import android.widget.EditText;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Callback;

import com.redmadrobot.inputmask.MaskedTextChangedListener;
import com.redmadrobot.inputmask.PolyMaskTextChangedListener;

import com.redmadrobot.inputmask.model.CaretString;
import com.redmadrobot.inputmask.helper.Mask;
import android.support.annotation.NonNull;

public class RNTextInputMaskModule extends ReactContextBaseJavaModule {
    ReactApplicationContext reactContext;

    public RNTextInputMaskModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNTextInputMask";
    }

    @ReactMethod
    public void mask(final String maskString,
                     final String inputValue,
                     final Callback onResult) {
      final Mask mask = new Mask(maskString);
      final String input = inputValue;
      final Mask.Result result = mask.apply(
          new CaretString(
              input,
              input.length()
          ),
          true
      );
      final String output = result.getFormattedText().getString();
      onResult.invoke(output);
    }

    @ReactMethod
    public void setMask(final int view, final String mask) {
      final Activity currentActivity = this.reactContext.getCurrentActivity();

      currentActivity.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          EditText editText = (EditText)currentActivity.findViewById(view);

          final MaskedTextChangedListener listener = new MaskedTextChangedListener(
            mask,
            true,
            editText,
            null,
            new MaskedTextChangedListener.ValueListener() {
                @Override
                public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue) {
                }
            }
          );

          editText.addTextChangedListener(listener);
        }
      });
    }
}