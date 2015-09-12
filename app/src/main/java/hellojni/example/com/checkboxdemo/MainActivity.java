package hellojni.example.com.checkboxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    //数据源
    private ArrayList<Data> datas;
    //记录checkbox在listview中被选中的位置
    private Set<Integer> tags = new HashSet<>();
    //适配器
    private MyAdater myAdater = new MyAdater();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list_view);
        datas = getDatas();
        listView.setAdapter(myAdater);


    }


    //产生数据源
    private ArrayList<Data> getDatas() {
        ArrayList<Data> datas = new ArrayList<>();
        Data data = null;
        int j = 0;
        for (int i = 0; i < 50; i++) {

            data = new Data();

            if (i % 5 == 0) {
                data.setIsLable(true);
                data.setLable("label" + j++);
            } else {
                data.setIsLable(false);
                data.setTitle("title" + i % 5);
            }

            datas.add(data);

        }

        return datas;
    }


    class MyAdater extends BaseAdapter implements View.OnClickListener {

        //上-次所选的checkbox所属label的位置
        private int preChoiceCheckBox = -1;


        @Override
        public void onClick(View v) {


            int position = (int) v.getTag();
            Data data = datas.get(position);
            CheckBox checkBox = (CheckBox) v;

            Data d = null;

            if (checkBox.isChecked()) {
                if (data.isLable()) {

                    if (preChoiceCheckBox != position) {
                        tags.clear();
                    }

                    preChoiceCheckBox = position;

                    tags.add(position);
                    for (int i = position + 1; i < datas.size(); i++) {
                        d = datas.get(i);
                        if (d.isLable()) {
                            break;
                        } else {
                            tags.add(i);
                        }
                    }

                } else {

                    int j = 0;
                    for (int i = 0; i < datas.size(); i++) {
                        d = datas.get(i);

                        if (i == position) {
                            break;
                        }

                        if (d.isLable()) {
                            j = i;
                        }
                    }


                    if (j != preChoiceCheckBox) {
                        tags.clear();
                        preChoiceCheckBox = j;
                    }

                    tags.add(position);

                }

            } else {
                if (data.isLable()) {
                    tags.remove(position);
                    for (int i = position + 1; i < datas.size(); i++) {
                        d = datas.get(i);
                        if (d.isLable()) {
                            break;
                        } else {
                            tags.remove(i);
                        }
                    }
                } else {
                    tags.remove(position);
                }
            }


            for (int i = preChoiceCheckBox + 1; i < datas.size(); i++) {


                d = datas.get(i);
                if (d.isLable()) {
                    break;
                }

                if (!tags.contains(i)) {
                    tags.remove(preChoiceCheckBox);
                    break;
                }

                tags.add(preChoiceCheckBox);
            }


            myAdater.notifyDataSetChanged();

        }

        class ViewItemHolder {
            CheckBox item;
        }

        class ViewLabelHolder {
            CheckBox label;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewItemHolder viewItemHolder = null;
            ViewLabelHolder viewLabelHolder = null;

            if (getItemViewType(position) == 1) {

                if (convertView == null) {

                    convertView = getLayoutInflater().inflate(R.layout.item, parent, false);
                    viewItemHolder = new ViewItemHolder();
                    viewItemHolder.item = (CheckBox) convertView.findViewById(R.id.item);
                    viewItemHolder.item.setOnClickListener(this);
                    convertView.setTag(viewItemHolder);
                }

                viewItemHolder = (ViewItemHolder) convertView.getTag();
                //记录被点击的checkbox的位置
                viewItemHolder.item.setTag(position);
                viewItemHolder.item.setText(datas.get(position).getTitle());
                if (tags.contains(position)) {
                    viewItemHolder.item.setChecked(true);
                } else {
                    viewItemHolder.item.setChecked(false);
                }

            } else {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.label, parent, false);
                    viewLabelHolder = new ViewLabelHolder();
                    viewLabelHolder.label = (CheckBox) convertView.findViewById(R.id.label);
                    viewLabelHolder.label.setOnClickListener(this);
                    convertView.setTag(viewLabelHolder);
                }

                viewLabelHolder = (ViewLabelHolder) convertView.getTag();
                //记录被点击的checkbox的位置
                viewLabelHolder.label.setTag(position);
                viewLabelHolder.label.setText(datas.get(position).getLable());
                if (tags.contains(position)) {
                    viewLabelHolder.label.setChecked(true);
                } else {
                    viewLabelHolder.label.setChecked(false);
                }

            }
            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            return datas.get(position).isLable() ? 0 : 1;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }
    }

}
