package lt.vitalis.notes;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private String message;
    private String tag;

    public BaseActivity(String message, String tag) {
        this.message = message;
        this.tag = tag;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        print("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        print("onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        print("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        print("onDestry");
    }

    @Override
    protected void onPause() {
        super.onPause();
        print("onPause");
    }
    void  print(String lifecycle) {

        Log.i(tag,
                "**********************" + "\n" +
                message + "\n*" +
                        lifecycle + '\n' +
                "**********************");
    }
}
