package com.example.administrator.myapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nanit on 2016-05-23.
 */
public class KpuDishesActivity extends ListActivity {
    // constants
    private interface DishesKey {
        String Name = "Name";
        String Price = "Price";
        String Rating = "Rating";
    }
    // var
    private ArrayList< HashMap<String, String> > mHashMapArrayList;
    // methods
    private void setmHashMapArrayListFoodCourt(){
        mHashMapArrayList = new ArrayList<HashMap<String, String>>();
        FoodCort foodCort = new FoodCort();
        for(int i = 0 ; i<foodCort.getCount(); i++) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put(DishesKey.Name, foodCort.getFood(i, 0));
            hashMap.put(DishesKey.Price, foodCort.getFood(i, 1));
            hashMap.put(DishesKey.Rating, "" + (float)(Math.random()* 5) );
            mHashMapArrayList.add(hashMap);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmHashMapArrayListFoodCourt();
        setListAdapter(new BaseAdapter() {
            @Override
            public int getCount() { // 전체크기
                return mHashMapArrayList.size();
            }
            @Override
            public Object getItem(int position) { // 몰라
                return null;
            }
            @Override
            public long getItemId(int position) { // 몰라
                return 0;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) { // 겟겟
                View rowView = getLayoutInflater().inflate(R.layout.listitem_dishes, null, true);
                ((TextView) rowView.findViewById(R.id.tvName)).setText(mHashMapArrayList.get(position).get(DishesKey.Name));
                ((TextView) rowView.findViewById(R.id.tvPrice)).setText(mHashMapArrayList.get(position).get(DishesKey.Price));
                ((RatingBar) rowView.findViewById(R.id.rb1)).setRating(Float.parseFloat(mHashMapArrayList.get(position).get(DishesKey.Rating)));
                return rowView;
            }
        }); // setListAdapter()
    } // onCreate()
    class FoodCort {
        private String[][] food = {
                {"등심돈까스", "4000"},
                {"비엔나에그덮밥", "3500"},
                {"치킨까스", "4000"},
                {"닭불고기덮밥", "3500"},
                {"부타동", "4000"},
                {"김치찌개돈까스", "4500"},
                {"파채돈까스", "4500"},
                {"폭찹와인덮밥", "4000"},
                {"참치회덮밥", "3500"},
                {"고구마치즈돈까스", "4500"},
                {"치즈돈까스", "4500"},
                {"쇠고기덮밥", "3000"},
                {"쇠고기돌솥밥", "4000"},
                {"날치알돌솥밥", "4000"},
                {"치킨마요덮밥", "4000"},
                {"치킨타꼬야끼덮밥", "4000"},
                {"해물야끼우동", "4500"},
                {"양푼비빔밥", "3500"},
                {"쇠고기양푼비빔밥", "4000"},
                {"돈까스양푼비빔밥", "4000"},
                {"모듬컵밥", "3000"},
                {"새우튀김우동", "4000"},
                {"유부우동", "3000"},
                {"김말이우동", "4000"},
                {"학식ㅈㄴ맛없어", "1000"},
                //
                {"양념감자오믈렛", "3800"},
                {"한스오믈렛", "3000"},
                {"치킨볼오믈렛", "4000"},
                {"소세지오믈렛", "3800"},
                {"불고기오믈렛", "4000"},
                {"불닭오믈렛", "4000"},
                {"해물돈도리숙주볶음밥", "3000"},
                {"스팸에그볶음밥", "3500"},
                {"베이컨야채볶음밥", "3500"},
                {"돈도리볶음밥", "3000"},
                {"돈도리김치볶음밥", "3500"},
                {"불멸자볶음밥", "3500"},
                {"쇠고기갈릭볶음밥", "4000"},
                {"소금구이덮밥", "3500"},
                {"해물소금구이덮밥", "3500"},
                {"매콤돼지불고기덮밥", "3500"},
                {"불닭라이스", "3500"},
                {"스팸구이정식", "3500"},
                {"새우튀김비빔밥", "3500"},
                {"불닭치즈도리아", "4000"},
                {"콘치즈베이컨도리아", "4000"},
                {"매운참치비빔밥", "3000"},
                {"얼큰수제비", "4000"},
                //
                {"간짜장", "3000"},
                {"짜장면", "3000"},
                {"쟁반짜장", "3500"},
                {"짬뽕밥", "3500"},
                {"짬뽕", "3000"},
                {"짬짜면", "3500"},
                {"삼선짬뽕", "4000"},
                {"삼선짬뽕밥", "3500"},
                {"매콤해물잡채밥", "5500"},
                {"매콤대패삼겹덮밥", "4000"},
                {"돈육김치덮밥", "3000"},
                {"볶음밥자장면", "3500"},
                {"매콤돼지갈비덮밥", "3500"},
                {"탕수육세트", "5000"},
                {"깐쇼치킨세트", "5000"},
                {"사천볶음밥", "3500"},
                {"간장치킨덮밥", "3500"},
                {"유위쭈밥", "3500"},
                {"깐쇼치킨덮밥", "3500"},
                {"탕볶밥", "5000"},
                {"탕짬면", "4500"},
                {"사천탕수육", "15000"},
                //
                {"돌솥비빔밥", "4000"},
                {"돌솥닭갈비", "3500"},
                {"돌솥제육덮밥", "4000"},
                {"철판참치김치덮밥", "3500"},
                {"함박덮밥", "3800"},
                {"철판떡갈비", "3800"},
                {"제육덮밥", "3500"},
                {"소세지덮밥", "3500"},
                {"참치비빔밥", "3500"},
                {"비빔밥", "3000"},
                {"순두부찌개", "3500"},
                {"육개장", "4000"},
                {"참치김치찌개", "3500"},
                {"떡만두국", "3500"},
                {"물냉면", "3500"},
                {"열무냉면", "3500"},
                {"쫄면", "3000"},
                {"비빔냉면", "3000"},
                {"야채김밥", "1500"},
                {"치즈김밥", "2000"},
                {"참치김밥", "2000"},
                {"해물라면", "2300"},
                {"햄김치라면", "3000"},
                {"라면", "2000"},
                {"떡라면", "2500"},
                {"만두라면", "2500"},
                {"치즈라면", "2500"},
                {"떡볶이", "2000"},
                {"라볶이", "2500"},
                {"치즈떡볶이", "2500"},
                {"치즈라볶이", "3000"}
        };
        public String getFood(int index, int element){
            return food[index][element];
        }
        public int getCount(){
            return food.length;
        }
    }
}
