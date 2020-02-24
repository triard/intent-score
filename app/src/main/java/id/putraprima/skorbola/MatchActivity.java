package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MatchActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();
    private ImageView homeLogo, awayLogo;
    private TextView homeTeam, awayTeam, scoreHome, scoreAway;
    private int homeScoreVal, awayScoreVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        //2.Tombol add score menambahkan satu angka dari angka 0, setiap kali di tekan
        //3.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang ke ResultActivity, jika seri di kirim text "Draw"

        homeLogo = findViewById(R.id.home_logo);
        awayLogo = findViewById(R.id.away_logo);

        homeTeam = findViewById(R.id.txt_home);
        awayTeam = findViewById(R.id.txt_away);

        Bundle bundle = getIntent().getExtras();
        Uri homeLogoUri = Uri.parse(bundle.getString("homeUri"));
        Uri awayLogoUri = Uri.parse(bundle.getString("awayUri"));
        if (bundle != null){
            homeTeam.setText(bundle.getString("homeTeam"));
            awayTeam.setText(bundle.getString("awayTeam"));
            try {
                Bitmap homeBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), homeLogoUri);
                Bitmap awayBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), awayLogoUri);
                homeLogo.setImageBitmap(homeBitmap);
                awayLogo.setImageBitmap(awayBitmap);
            }catch (IOException e){
                Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
            }
        }

    }

    public void handleHomeScoreIncrement(View view) {
        scoreHome = findViewById(R.id.score_home);
        homeScoreVal = Integer.valueOf(scoreHome.getText().toString());
        homeScoreVal += 1;
        scoreHome.setText(String.valueOf(homeScoreVal));
    }

    public void handleAwayScoreIncrement(View view) {
        scoreAway = findViewById(R.id.score_away);
        awayScoreVal = Integer.valueOf(scoreAway.getText().toString());
        awayScoreVal += 1;
        scoreAway.setText(String.valueOf(awayScoreVal));
    }

    public void handleResult(View view) {
        Intent i = new Intent(MatchActivity.this, ResultActivity.class);
        if (homeScoreVal > awayScoreVal){
            i.putExtra("result", "The winner is " + homeTeam.getText().toString());
        }else if(homeScoreVal < awayScoreVal){
            i.putExtra("result", "The winner is " + awayTeam.getText().toString());
        }else{
            i.putExtra("result","The result is Draw");
        }
        startActivity(i);
    }
}
