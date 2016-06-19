package sachin.com.twangotask;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    /**
     * Persist URI image to crop URI if specific permissions are required
     */
    private Uri mCropImageUri;
   String counter = "1";


int thumbs[]={R.id.quick_start_cropped_image,R.id.quick_start_cropped_image2,R.id.quick_start_cropped_image3,R.id.quick_start_cropped_image4,R.id.quick_start_cropped_image5,R.id.quick_start_cropped_image6};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

       // getSupportActionBar().setDisplayShowTitleEnabled(false);


        DatabaseHandler db = new DatabaseHandler(this);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
        List<Contact> contactt = db.getAllContacts();
        for(int i=0;i<contactt.size();i++) {
            int pos =Integer.parseInt(contactt.get(i).getPhoneNumber());
            Uri myUri = Uri.parse(contactt.get(i).getName());
            ImageView imageView= ((ImageView) findViewById(thumbs[pos-1]));
            imageView.setImageURI(null);
            imageView.setImageURI(myUri);
        }


    }




/**
 * Start pick image activity with chooser.
 */
public void onSelectImageClick(View view) {
    counter="1";
    CropImage.startPickImageActivity(this);

}

    public void onSelectImageClick2(View view) {
        counter="2";
        CropImage.startPickImageActivity(this);
    }

    public void onSelectImageClick3(View view) {
        counter="3";
        CropImage.startPickImageActivity(this);
    }

    public void onSelectImageClick4(View view) {
        counter="4";
        CropImage.startPickImageActivity(this);
    }
    public void onSelectImageClick5(View view) {
        counter="5";
        CropImage.startPickImageActivity(this);
    }

    public void onSelectImageClick6(View view) {
        counter="6";
        CropImage.startPickImageActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            boolean requirePermissions = false;
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                requirePermissions = true;
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {



                switch(counter) {
                    case "1":
                    {  ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
                        setupImage(counter, result.getUri().toString());}
                        break;
                    case "2":
                    {  ((ImageView) findViewById(R.id.quick_start_cropped_image2)).setImageURI(result.getUri());
                        setupImage(counter, result.getUri().toString());}
                        break;
                    case "3":
                    {  ((ImageView) findViewById(R.id.quick_start_cropped_image3)).setImageURI(result.getUri());
                        setupImage(counter, result.getUri().toString());}
                        break;
                    case "4":
                       {  ((ImageView) findViewById(R.id.quick_start_cropped_image4)).setImageURI(result.getUri());
                        setupImage(counter, result.getUri().toString());}
                        break;
                    case "5":
                    {  ((ImageView) findViewById(R.id.quick_start_cropped_image5)).setImageURI(result.getUri());
                        setupImage(counter, result.getUri().toString());}
                        break;
                    case "6":
                       {  ((ImageView) findViewById(R.id.quick_start_cropped_image6)).setImageURI(result.getUri());
                        setupImage(counter, result.getUri().toString());}
                        break;
                    default:
                        Toast.makeText(this,  "Please Select an Images Again !", Toast.LENGTH_LONG).show();
                }





            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }


    private void setupImage(String counter, String picUri){
        DatabaseHandler db = new DatabaseHandler(this);




         int count = db.getContact(Integer.parseInt(counter));
            Toast.makeText(this, "The size is  "+ count, Toast.LENGTH_LONG).show();
           // Contact contact =  db.getContact(Integer.parseInt(counter));
        if(count==0)
        db.addContact(new Contact(picUri.toString(), counter));
        else
            db.updateContact(new Contact(picUri.toString(), counter));






    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }
}

