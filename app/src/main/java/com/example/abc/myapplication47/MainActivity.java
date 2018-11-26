package com.example.abc.myapplication47;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.example.abc.myapplication47.greendao.CollectionDao;
import com.example.abc.myapplication47.greendao.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.tv_showData)
    TextView tvShowData;
    private UserDao userDao;
    private CollectionDao collectionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        userDao = MyApplication.getDaoSession().getUserDao();
        collectionDao=MyApplication.getDaoSession().getCollectionDao();



    }

    @OnClick({R.id.btn_add, R.id.btn_delete, R.id.btn_update, R.id.btn_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                add();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_update:
                update();
                break;
            case R.id.btn_query:
                 query();
                break;
        }
    }


    public void add ()
    {
        User user1=new User();
        user1.setUserName("marry");
        user1.setIcon("ncnsi/csv/scs");
        userDao.insert(user1);//插入数据基础方法，如果数据库已经存在该对象再次插入就会抛异常
        userDao.insertOrReplace(user1);//如果存在该对象就替换，不存在就插入
        userDao.insertInTx();

        User user2=new User();
        user2.setIcon("vscs/bsb/crv");
        user2.setUserName("tom");
        userDao.insert(user2);

        Collection collection=new Collection();
        collection.setCollectionId(user1.getId());//关联user的id,指明该collection指向user1
        collection.setImageUrl("cscgzzxsveggacszgecag");
        collection.setImageWidth(1920);
        collection.setImageHeight(1080);
        collectionDao.insert(collection);

        Collection collection2=new Collection();
        collection2.setCollectionId(user1.getId());//关联user的id,指明该collection指向user1
        collection2.setImageUrl("vcscgzcscawgavacwa");
        collection2.setImageWidth(1920);
        collection2.setImageHeight(1080);
        collectionDao.insert(collection2);

    }

    public void query()
    {
//        List<User> list=userDao.loadAll();//全表查询
//        StringBuffer stringBuffer=new StringBuffer();
//        for(int i=0;i<list.size();i++)
//        {
//           stringBuffer.append(list.get(i).toString());
//        }
//        tvShowData.setText(stringBuffer);//输出user表数据

//        List<Collection> list1=list.get(0).getList();//查询user的关联表collection的数据
//
//        for(int i=0;i<list1.size();i++)
//        {
//            stringBuffer.append(list1.get(i).toString());
//
//        }
//        tvShowData.setText(stringBuffer);

          List<User> list=userDao.queryBuilder().where(UserDao.Properties.UserName.eq("marry")).list();//条件查询

          StringBuffer stringBuffer=new StringBuffer();
          for(int i=0;i<list.size();i++)
          {
           stringBuffer.append(list.get(i).toString());
          }
          tvShowData.setText(stringBuffer);





    }

    public void delete()
    {
         //userDao.deleteAll();//删除全表

         List<User> list=userDao.queryBuilder().where(UserDao.Properties.UserName.eq("Lisa")).list();

         User user=list.get(0);

         userDao.delete(user);//删除某一个user对象，删除与修改都一样都需要进行查询，除了全部删除或则统一修改


    }

    public void update()
    {

        List<User> list=userDao.queryBuilder().where(UserDao.Properties.UserName.eq("tom")).list();

        User user=list.get(0);

        user.setUserName("Lisa");
        user.setIcon("vdbavaaevdaabavav");
         userDao.update(user);
    }
}
