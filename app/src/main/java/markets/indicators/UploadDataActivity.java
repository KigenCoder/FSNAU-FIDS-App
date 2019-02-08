package markets.indicators;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import fsnau.org.R;

public class UploadDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity);

        getSupportFragmentManager()
                .beginTransaction().add(R.id.uploadContainer, new UploadMarketData())
                .addToBackStack(null)
                .commit();
    }
}
