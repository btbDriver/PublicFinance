package finance.drive.com.publicfinance

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    //声明相关变量
    var toolbar: Toolbar? = null
    var mDrawerLayout: DrawerLayout? = null
    var mDrawerToggle: ActionBarDrawerToggle? = null
    var lvLeftMenu: ListView? = null
    val lvs = arrayOf("个人信息", "团队管理", "财务中心", "大众财富", "公司咨询", "系统公告", "理财政策", "公司简介", "联系客服", "安全退出")
    var arrayAdapter: ArrayAdapter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews() //获取控件
        toolbar!!.setTitle("Toolbar")//设置Toolbar标题
        toolbar!!.setTitleTextColor(Color.parseColor("#ffffff")) //设置标题颜色
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true) //设置返回键可用
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = object : ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {}
        mDrawerToggle!!.syncState()
        mDrawerLayout!!.addDrawerListener(mDrawerToggle!!)
        //设置菜单列表
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lvs)
        lvLeftMenu!!.setAdapter(arrayAdapter)
    }

    private fun findViews() {
        toolbar = findViewById(R.id.tl_custom) as Toolbar
        mDrawerLayout = findViewById(R.id.dl_left) as DrawerLayout
        lvLeftMenu = findViewById(R.id.lv_left_menu) as ListView
    }
}
