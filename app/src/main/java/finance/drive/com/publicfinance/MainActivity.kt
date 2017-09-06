package finance.drive.com.publicfinance

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.widget.ArrayAdapter
import android.widget.ListView
import finance.drive.com.publicfinance.base.BaseActivity

class MainActivity : BaseActivity() {

    val toolbar by lazy {
        findViewById(R.id.toolBar) as Toolbar
    }
    val mDrawerLayout by lazy {
        findViewById(R.id.drawerLayout) as DrawerLayout
    }
    val mListView by lazy {
        findViewById(R.id.listView) as ListView
    }
    lateinit var mDrawerToggle: ActionBarDrawerToggle
    lateinit var menuTitles: Array<String>
    lateinit var arrayAdapter: ArrayAdapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        toolbar.title = "大众理财"
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true) //设置返回键可用
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = object : ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {}
        mDrawerToggle.syncState()
        mDrawerLayout.addDrawerListener(mDrawerToggle)
        //设置菜单列表
        menuTitles = resources.getStringArray(R.array.menuList)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menuTitles)
        mListView.adapter = arrayAdapter

        loadRootFragment(R.id.contentContainerLayout, createTabHostFragment())
    }
}
