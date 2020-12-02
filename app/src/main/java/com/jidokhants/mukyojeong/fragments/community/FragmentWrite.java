package com.jidokhants.mukyojeong.fragments.community;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jidokhants.mukyojeong.R;
import com.jidokhants.mukyojeong.activities.MainActivity;
import com.jidokhants.mukyojeong.model.Comment;
import com.jidokhants.mukyojeong.model.Food;
import com.jidokhants.mukyojeong.model.Ingredient;
import com.jidokhants.mukyojeong.model.Post;
import com.jidokhants.mukyojeong.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FragmentWrite extends Fragment implements View.OnClickListener {
    EditText title, ingredientText;

    ImageView mainImg;
    EditText content;
    Button submitButton;

    ArrayList<Ingredient> ingredients;

    FragmentIngredientDialog fragmentIngredientDialog;

    FirebaseUser currentUser;
    DatabaseReference mDatabase;

    Uri imageFilePath;

    public static FragmentWrite newInstance() {
        FragmentWrite fragment = new FragmentWrite();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write, container, false);

        title = view.findViewById(R.id.commu_write_title);
        ingredientText = view.findViewById(R.id.commu_write_ing_list_edit);
        mainImg = view.findViewById(R.id.commu_write_img);
        content = view.findViewById(R.id.commu_write_content);
        submitButton = view.findViewById(R.id.commu_write_submit_btn);

        ingredientText.setInputType(0);
        mainImg.setOnClickListener(this);
        ingredientText.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStop() {
        ((MainActivity) getActivity()).getSupportActionBar().show();
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commu_write_ing_list_edit:
                openIngredientDialog();
                break;
            case R.id.commu_write_img:
                // 이미지 추가 버튼 눌렸을 때
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                } else {
                    startGallery();
                }
                break;
            case R.id.commu_write_submit_btn:
                submitPost();

                break;
        }


    }

    public void submitPost() {
        final String title = this.title.getText().toString();
        final String content = this.content.getText().toString();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final User writer = new User(currentUser.getEmail());

        Date currentTime = Calendar.getInstance().getTime();
        final String date = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(currentTime);

        final ArrayList<Ingredient> ingredients = this.ingredients;
        final ArrayList<Comment> comments = new ArrayList<>();

        final String dBGroup = "레시피";
        final String commercial = "품목대표";
        final String name = title;
        final String from = "전국(대표)";
        final String subCategory = "레시피";
        double servingSize = 0;
        final String unit = "g";
        double totalGram = 0;
        double totalML = 0;
        double calorie = 0;
        double moisture = 0;
        double protein = 0;
        double fat = 0;
        double carbohydrate = 0;
        double sugars = 0;
        double fiber = 0;
        double calcium = 0;
        double fe = 0;
        double magnesium = 0;
        double phosphorus = 0;
        double potassium = 0;
        double salt = 0;
        double zinc = 0;
        double copper = 0;
        double manganese = 0;
        double selenium = 0;
        double iodine = 0;
        double chlorine = 0;
        double vitaminA = 0;
        double vitaminARE = 0;
        double retinol = 0;
        double betaCarotene = 0;
        double vitaminD = 0;
        double vitaminK = 0;
        double panto = 0;
        double vitaminB6 = 0;
        double biotin = 0;
        double vitaminC = 0;
        double omega3FattyAcids = 0;
        double omega6FattyAcids = 0;

        for (Ingredient item : ingredients) {
            Food foodItem = item.getFood();
            double ratio = item.getRatio();

            servingSize += foodItem.getServingSize() * ratio;
            totalGram += foodItem.getTotalGram() * ratio;
            totalML += foodItem.getTotalML() * ratio;
            calorie += foodItem.getCalorie() * ratio;
            moisture += foodItem.getMoisture() * ratio;
            protein += foodItem.getProtein() * ratio;
            fat += foodItem.getFat() * ratio;
            carbohydrate += foodItem.getCarbohydrate() * ratio;
            sugars += foodItem.getSugars() * ratio;
            fiber += foodItem.getFiber() * ratio;
            calcium += foodItem.getCalcium() * ratio;
            fe += foodItem.getFe() * ratio;
            magnesium += foodItem.getMagnesium() * ratio;
            phosphorus += foodItem.getPhosphorus() * ratio;
            potassium += foodItem.getPotassium() * ratio;
            salt += foodItem.getSalt() * ratio;
            zinc += foodItem.getZinc() * ratio;
            copper += foodItem.getCopper() * ratio;
            manganese += foodItem.getManganese() * ratio;
            selenium += foodItem.getSelenium() * ratio;
            iodine += foodItem.getIodine() * ratio;
            chlorine += foodItem.getChlorine() * ratio;
            vitaminA += foodItem.getVitaminA() * ratio;
            vitaminARE += foodItem.getVitaminARE() * ratio;
            retinol += foodItem.getRetinol() * ratio;
            betaCarotene += foodItem.getBetaCarotene() * ratio;
            vitaminD += foodItem.getVitaminD() * ratio;
            vitaminK += foodItem.getVitaminK() * ratio;
            panto += foodItem.getPanto() * ratio;
            vitaminB6 += foodItem.getVitaminB6() * ratio;
            biotin += foodItem.getBiotin() * ratio;
            vitaminC += foodItem.getVitaminC() * ratio;
            omega3FattyAcids += foodItem.getOmega3FattyAcids() * ratio;
            omega6FattyAcids += foodItem.getOmega6FattyAcids() * ratio;
        }
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = simpleDateFormat.format(now);
        String imageFileName = "MUK_" + timeStamp + ".png";

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("images").child("posts").child(imageFileName);
        final double finalServingSize = servingSize;
        final double finalTotalGram = totalGram;
        final double finalTotalML = totalML;
        final double finalCalorie = calorie;
        final double finalMoisture = moisture;
        final double finalProtein = protein;
        final double finalFat = fat;
        final double finalCarbohydrate = carbohydrate;
        final double finalSugars = sugars;
        final double finalFiber = fiber;
        final double finalCalcium = calcium;
        final double finalFe = fe;
        final double finalVitaminD = vitaminD;
        final double finalSalt = salt;
        final double finalVitaminK = vitaminK;
        final double finalZinc = zinc;
        final double finalCopper = copper;
        final double finalManganese = manganese;
        final double finalSelenium = selenium;
        final double finalIodine = iodine;
        final double finalChlorine = chlorine;
        final double finalPanto = panto;
        final double finalVitaminB = vitaminB6;
        final double finalBiotin = biotin;
        final double finalVitaminC = vitaminC;
        final double finalOmega3FattyAcids = omega3FattyAcids;
        final double finalOmega6FattyAcids = omega6FattyAcids;

        final double finalVitaminA = vitaminA;
        final double finalVitaminARE = vitaminARE;
        final double finalMagnesium = magnesium;
        final double finalRetinol = retinol;
        final double finalBetaCarotene = betaCarotene;
        final double finalPhosphorus = phosphorus;
        final double finalPotassium = potassium;
        storageReference.putFile(imageFilePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {
                        final String imgPath = uri.toString();
                        mDatabase.child("foods").child("index").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int index = Integer.valueOf(snapshot.getValue().toString());
                                Log.d("FB index", index + "");
                                int id = index + 1;

                                Food food = new Food(id, dBGroup, commercial, name, from, subCategory, finalServingSize, unit, finalTotalGram, finalTotalML, finalCalorie, finalMoisture, finalProtein, finalFat, finalCarbohydrate, finalSugars, finalFiber, finalCalcium, finalFe, finalMagnesium, finalPhosphorus, finalPotassium, finalSalt, finalZinc, finalCopper, finalManganese, finalSelenium, finalIodine, finalChlorine, finalVitaminA, finalVitaminARE, finalRetinol, finalBetaCarotene, finalVitaminD, finalVitaminK, finalPanto, finalVitaminB, finalBiotin, finalVitaminC, finalOmega3FattyAcids, finalOmega6FattyAcids);
                                mDatabase.child("foods").push().setValue(food);
                                Map<String, Object> map = new HashMap<>();
                                map.put("foods/index", id);
                                mDatabase.updateChildren(map);
                                Post post = new Post(title, writer, date, content, imgPath, ingredients, food, comments, null);
                                mDatabase.child("posts").push().setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getActivity(), "글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                        FragmentCommunity fragmentCommunity = new FragmentCommunity();
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.frameLayout, fragmentCommunity).commit();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
        });

    }

    public void openIngredientDialog() {
        FragmentManager fragmentManager = ((MainActivity) getActivity()).getSupportFragmentManager();
        Log.d("DIALOG OPEN", "CALLED, size: " + ingredients.size());
        if (ingredients.size() > 0) {
            Log.d("제발2", ingredients.get(0).getFood().getName());
        }
        fragmentIngredientDialog = FragmentIngredientDialog.newInstance(ingredients);
        fragmentIngredientDialog.setTargetFragment(this, 7777);
        fragmentIngredientDialog.show(fragmentManager, null);
        getFragmentManager().executePendingTransactions();
        fragmentIngredientDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                setIngredients(fragmentIngredientDialog.getIngredientFromDialog());
            }
        });


    }

    private void startGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, 1000);
        }
    }

    public void updateIngredientEdittext() {
        String temp = "";
        Log.d("DIALOG OPEN 2", "CALLED, size: " + ingredients.size());
        if (ingredients != null && ingredients.size() != 0) {
            temp += ingredients.get(0).getFood().getName();
            Log.d("UPDATEING", temp);
            if (ingredients.size() > 1) {
                for (int i = 1; i < ingredients.size(); i++) {
                    temp += ", " + ingredients.get(i).getFood().getName();
                }
            }
        }
        ingredientText.setText(temp);

    }

    public ArrayList<Ingredient> getIngredientsFromEdit() {
        return ingredients;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == MainActivity.RESULT_OK) {
            if (requestCode == 1000) {
                try {
                    imageFilePath = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageFilePath);
                    mainImg.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 7777) {
                setIngredients(data.<Ingredient>getParcelableArrayListExtra("ingredients"));
            }
        }
    }

    public void setIngredients(ArrayList<Ingredient> temp) {
        this.ingredients = temp;
        Log.d("DIALOG SET", "CALLED, size: " + this.ingredients.size());
        updateIngredientEdittext();
    }

}
