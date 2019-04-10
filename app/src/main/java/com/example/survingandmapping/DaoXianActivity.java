package com.example.survingandmapping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.survingandmapping.db.DaoXianQiShi;

//控制起始数据的输入
public class DaoXianActivity extends AppCompatActivity {
    private EditText du;
    private EditText fen;
    private EditText miao;
    private EditText du2;
    private EditText fen2;
    private EditText miao2;
    private EditText Ax;
    private EditText Ay;
    private EditText Bx;
    private EditText By;
//region
    private Button begin;
	//endregion
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao_xian);
//region
        du=(EditText)findViewById(R.id.du);
        fen=(EditText)findViewById(R.id.fen);
        miao=(EditText)findViewById(R.id.miao);
        du2=(EditText)findViewById(R.id.du2);
        fen2=(EditText)findViewById(R.id.fen2);
        miao2=(EditText)findViewById(R.id.miao2);
//endregion
        Ax=(EditText)findViewById(R.id.ax);
        Ay=(EditText)findViewById(R.id.ay);
        Bx=(EditText)findViewById(R.id.bx);
        By=(EditText)findViewById(R.id.by);
        begin=(Button)findViewById(R.id.begin);

        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()==1){
                    Toast.makeText(DaoXianActivity.this,"输入不完整，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }else if(checkInput()==2){
                    Toast.makeText(DaoXianActivity.this,"输入含有非数，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                //将计算平差的起算数据放入PingchaUtil工具类中
                PingchaUtil.baFangwei=new PingchaUtil.Jiao(Integer.parseInt(du.getText().toString())
                        ,Integer.parseInt(fen.getText().toString()),Integer.parseInt(miao.getText().toString()));
                PingchaUtil.cdFangwei =new PingchaUtil.Jiao(Integer.parseInt(du2.getText().toString())
                        ,Integer.parseInt(fen2.getText().toString())
                        ,Integer.parseInt(miao2.getText().toString()));
                PingchaUtil.begin=new PingchaUtil.ZuoBiao(Double.parseDouble(Ax.getText().toString())
                        ,Double.parseDouble(Ay.getText().toString()));
                PingchaUtil.end=new PingchaUtil.ZuoBiao(Double.parseDouble(Bx.getText().toString()),
                        Double.parseDouble(By.getText().toString()));

                //将起始数据保存如数据库
                DaoXianQiShi daoXianQiShi=new DaoXianQiShi();
                float qishi=Float.parseFloat(du.getText().toString())+Float.parseFloat(fen.getText().toString())/60
                        +Float.parseFloat(miao.getText().toString())/3600;
                float jieshu=Float.parseFloat(du2.getText().toString())+Float.parseFloat(fen2.getText().toString())/60
                        +Float.parseFloat(miao2.getText().toString());
                float qx=Float.parseFloat(Ax.getText().toString());
                float qy=Float.parseFloat(Ay.getText().toString());
                float jx=Float.parseFloat(Bx.getText().toString());
                float jy=Float.parseFloat(By.getText().toString());
                daoXianQiShi.setQishibianFWJ(qishi);
                daoXianQiShi.setJieshubianFWJ(jieshu);
                daoXianQiShi.setQishidianZBX(qx);
                daoXianQiShi.setQishidianZBX(qy);
                daoXianQiShi.setJieshudianZBX(jx);
                daoXianQiShi.setJieshudianZBY(jy);
                daoXianQiShi.save();
                Log.d("sqpolar","起始已保存如数据库");

                Intent intent=new Intent(DaoXianActivity.this,DaoXianBegin.class);
                startActivity(intent);
            }
        });
    }
    int checkInput(){
        if(du.getText().toString().equals("")
                ||fen.getText().toString().equals("")
                ||miao.getText().toString().equals("")
                ||du2.getText().toString().equals("")
                ||fen2.getText().toString().equals("")
                ||miao2.getText().toString().equals("")
                ||Ax.getText().toString().equals("")
                ||Ay.getText().toString().equals("")
                ||Bx.getText().toString().equals("")
                ||By.getText().toString().equals("")){
            return 1;
        }
        if (check(du.getText().toString())==false
                ||check(fen.getText().toString())==false
                ||check(miao.getText().toString())==false
                ||check(du2.getText().toString())==false
                ||check(fen2.getText().toString())==false
                ||check(miao2.getText().toString())==false
                ||check(Ax.getText().toString())==false
                ||check(Ax.getText().toString())==false
                ||check(Bx.getText().toString())==false
                ||check(By.getText().toString())==false){
            return 2;
        }
        return 0;

    }
    boolean check(String str){
        try {
            Double.valueOf(str);
            return true;
        } catch (NumberFormatException e) {


            return false;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.backup:
                Toast.makeText(this,"上传",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"删除",Toast.LENGTH_SHORT).show();
                break;
            case R.id.dx:
                Toast.makeText(this,"你正处于导线测量界面",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sz:

                Intent intent=new Intent(DaoXianActivity.this,ShuizhunActivity.class);
                startActivity(intent);
                break;
            default:break;
        }
        return true;
    }
}
