package estrada.leon.rafael.readwatch;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainFileManager extends AppCompatActivity {

    Button btnfilePicker;
    TextView messageText;
    ProgressDialog dialog = null;
    Intent data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_file_manager);
        btnfilePicker = findViewById(R.id.btn_filePicker);
        messageText = findViewById(R.id.txtUrl);

        btnfilePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker().withActivity(MainFileManager.this).withRequestCode(10).start();
            }
        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                return;
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            dialog = ProgressDialog.show(MainFileManager.this, "Subiendo archivo", "Por favor espere.", true);
            this.data = data;
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    subirArchivo();
                }
            });
            hilo.start();
        }
    }

    private void subirArchivo(){
        File f  = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
        String content_type  = getMimeType(f.getPath());

        String file_path = f.getAbsolutePath();
        OkHttpClient client = new OkHttpClient();
        RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

        RequestBody request_body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("type",content_type)
                .addFormDataPart("uploaded_file",file_path.substring(file_path.lastIndexOf("/")+1), file_body)
                .build();
        Request request = new Request.Builder().url(getString(R.string.ip_server_archivos_php) + "guardarArchivos.php").post(request_body).build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                Toast.makeText(MainFileManager.this, "Error de peticion", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainFileManager.this, "Se subio correctamente el archivo", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(MainFileManager.this, "Error de peticion", Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();

    }

    private String getMimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
}

