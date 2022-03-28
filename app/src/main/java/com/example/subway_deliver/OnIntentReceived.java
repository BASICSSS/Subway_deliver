package com.example.subway_deliver;

import android.content.Intent;

public interface OnIntentReceived {
    void onIntent(Intent i, int resultCode);
}