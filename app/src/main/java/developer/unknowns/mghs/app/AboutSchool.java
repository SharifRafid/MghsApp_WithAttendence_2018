package developer.unknowns.mghs.app;

import android.graphics.Typeface;
import android.icu.text.TimeZoneFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutSchool extends AppCompatActivity {

    private TextView titleText, bodyText, titleTextEnglish, bodyTextEnglish;
    private ImageView translate;
    private Boolean english = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_school);

        titleText = findViewById(R.id.textViewTitle);
        bodyText = findViewById(R.id.bodyText);
        titleTextEnglish = findViewById(R.id.textViewTitleEnglish);
        bodyTextEnglish = findViewById(R.id.bodyTextEnglish);

        translate = findViewById(R.id.imageView);

        if(english){
            bodyTextEnglish.setText(R.string.bodyHistorySchoolAboutEnglish);
            titleTextEnglish.setText(R.string.titleAboutEnglish);
            bodyText.setVisibility(View.GONE);
            titleText.setVisibility(View.GONE);
            titleTextEnglish.setVisibility(View.VISIBLE);
            bodyTextEnglish.setVisibility(View.VISIBLE);
        }else{
            bodyText.setText(R.string.bodyHistorySchoolAboutBangla);
            titleText.setText(R.string.titleAboutBangla);
            bodyText.setVisibility(View.VISIBLE);
            titleText.setVisibility(View.VISIBLE);
            titleTextEnglish.setVisibility(View.GONE);
            bodyTextEnglish.setVisibility(View.GONE);
        }

        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(english){
                    bodyText.setText(R.string.bodyHistorySchoolAboutBangla);
                    titleText.setText(R.string.titleAboutBangla);
                    bodyText.setVisibility(View.VISIBLE);
                    titleText.setVisibility(View.VISIBLE);
                    titleTextEnglish.setVisibility(View.GONE);
                    bodyTextEnglish.setVisibility(View.GONE);
                    english = false;
                }else{
                    bodyTextEnglish.setText(R.string.bodyHistorySchoolAboutEnglish);
                    titleTextEnglish.setText(R.string.titleAboutEnglish);
                    bodyText.setVisibility(View.GONE);
                    titleText.setVisibility(View.GONE);
                    titleTextEnglish.setVisibility(View.VISIBLE);
                    bodyTextEnglish.setVisibility(View.VISIBLE);
                    english = true;
                }
            }
        });


    }
}
