package estrada.leon.rafael.readwatch;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainFileManager extends AppCompatActivity {

    Button btnfilePicker;
    Intent myFileIntent;
    TextView txtUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_file_manager);
        btnfilePicker = findViewById(R.id.btn_filePicker);
        txtUrl = findViewById(R.id.txtUrl);

        btnfilePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myFileIntent=new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");
                startActivityForResult(myFileIntent, 10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    String path = data.getData().getPath();
                    txtUrl.setText(path);
                }
                break;
        }
    }
}
