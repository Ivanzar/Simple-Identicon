package ivanzar.Identicon;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import ivanzar.Identicon.identicon.HashGeneratorInterface;
import ivanzar.Identicon.identicon.IdenticonGenerator;
import ivanzar.Identicon.identicon.MessageDigestHashGenerator;

import android.support.design.widget.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    Bitmap bitmap;
    String text = "";

    public void generate(View v)
    {
        TextView text = (TextView)findViewById(R.id.editText);
        ImageView img  =  (ImageView)findViewById(R.id.imageView);

        HashGeneratorInterface gen = new MessageDigestHashGenerator("MD5");

        Bitmap bitmap = IdenticonGenerator.generate(text.getText().toString(), gen);

        img.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false));

        this.bitmap = bitmap;
        this.text = text.getText().toString();


    }

    public void save(View v)
    {

        if(bitmap == null)
        {
            Snackbar.make(v, "Сначала сгенерируйте картинку", Snackbar.LENGTH_LONG).show();
            return;
        }

        File file = new File("/sdcard/Pictures/Identicon_"+text+".png");
        try {
            FileOutputStream fo = new FileOutputStream(file);
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fo);
            fo.flush();
            fo.close();
            Snackbar.make(v, file.getAbsolutePath(), Snackbar.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
