package com.dodo81.memo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dodo81.memo1.adapter.MemoAdapter;
import com.dodo81.memo1.data.DatabaseHandler;
import com.dodo81.memo1.model.Memo;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    EditText editSearch;
    ImageView imgSearch;
    ImageView imgDelete;
    RecyclerView recyclerView;
    MemoAdapter adapter;
    ArrayList<Memo> memoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        editSearch = findViewById(R.id.editSearch);
        imgSearch = findViewById(R.id.imgSearch);
        imgDelete = findViewById(R.id.imgDelete);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 메모 추가 액티비티 실행!
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 에디트 텍스트에서 검색어 가져온다.
                String keyword = editSearch.getText().toString().trim();

                // 2. 검색어로 DB에 쿼리한다.=> 메모리스트 가져옴
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                memoList = db.searchMemo(keyword);

                // 3. 검색어에 매칭한 메모 결과들을 화면에 표시한다.
                adapter = new MemoAdapter(MainActivity.this, memoList);
                recyclerView.setAdapter(adapter);
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 에디트 텍스트의 문자열을 지운다.
                editSearch.setText("");
                // 앱 실행시 저장된 데이터를 화면에 보여준다.
                // DB에 저장된 데이터를 가져온다.
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                memoList = db.getAllMemos();

                adapter = new MemoAdapter(MainActivity.this, memoList);
                recyclerView.setAdapter(adapter);
            }
        });
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 키워드 검색 에디트 텍스트에 글자를 쓸 때마다,
                // 자동으로 해당 검색어를 가져와서 디비에서 쿼리해서
                // 검색 결과를 화면에 표시해 주는 기능 개발. (돋보기 버튼을 누를 필요 없이 화면에 바로 노출)

                String keyword = editSearch.getText().toString().trim();
                Log.i("MyMemoApp", keyword);

                if (keyword.length() < 2) {
                    return;
                }

                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                memoList = db.searchMemo(keyword);
                adapter = new MemoAdapter(MainActivity.this, memoList);
                recyclerView.setAdapter(adapter);

            }
        });

        editSearch.addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                  // 유저가 입력한 검색 키워드를 뽑아
             string keyword = editSearch.getText().toString().trim();
                // 2글자 이상 입력했을 때만 검색
                if (keyword.length() <2) {
                    return;
                }
                // 디미에서 가져온 후 화면에 보여줌
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                memodList = db.searchMemo(keyword);

                adapter =new MemoAdapter(MainActivity.this, memoList);
                  recyclerView.setAdapter(adapter);
            }
        }));
    }
