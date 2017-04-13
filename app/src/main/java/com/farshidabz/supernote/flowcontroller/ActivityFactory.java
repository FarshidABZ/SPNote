package com.farshidabz.supernote.flowcontroller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.farshidabz.supernote.view.ui.mainpage.MainPageActivity;
import com.farshidabz.supernote.view.ui.note.TextNoteActivity;

/**
 * Created by FarshidAbz.
 * Since 4/13/2017.
 */

public class ActivityFactory {
    public static void startActivity(Context context, String activityName, Bundle bundle) {
        context.startActivity(getActivityIntent(context, activityName).putExtras(bundle));
    }

    public static void startActivity(Context context, String activityName) {
        context.startActivity(getActivityIntent(context, activityName));
    }

    private static Intent getActivityIntent(Context context, String activityName) {
        switch (activityName) {
            case "MainPageActivity":
                return new Intent(context, MainPageActivity.class);
            case "TextNoteActivity":
                return new Intent(context, TextNoteActivity.class);
            default:
                return new Intent(context, MainPageActivity.class);
        }
    }
}
