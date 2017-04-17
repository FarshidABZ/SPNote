package com.farshidabz.spnote.flowcontroller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.farshidabz.spnote.view.ui.mainpage.MainPageActivity;
import com.farshidabz.spnote.view.ui.note.NoteActivity;

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
            case "NoteActivity":
                return new Intent(context, NoteActivity.class);
            default:
                return new Intent(context, MainPageActivity.class);
        }
    }
}
