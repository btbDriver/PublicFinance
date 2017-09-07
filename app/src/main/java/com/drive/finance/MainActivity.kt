package com.drive.finance

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.drive.finance.base.BaseActivity
import com.drive.finance.ui.createLoginFragment
import com.drive.finance.ui.drawer.*
import com.drive.finance.ui.tab.createTabHostFragment

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

        mListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // 个人信息
            if (position == 0) {
                loadRootFragment(R.id.drawerFragmentContainer, createUserInfoFragment())
            }
            // 团队管理
            else if (position == 1) {
                loadRootFragment(R.id.drawerFragmentContainer, createTeamFragment())
            }
            // 财务中心
            else if (position == 2) {
                loadRootFragment(R.id.drawerFragmentContainer, createFinanceCenterFragment())
            }
            // 大众财富
            else if (position == 3) {
                loadRootFragment(R.id.drawerFragmentContainer, createPublicFinanceFragment())
            }
            // 公司资讯
            else if (position == 4) {
                loadRootFragment(R.id.drawerFragmentContainer, createConsultFragment())
            }
            // 联系我们
            else if (position == 5) {
                loadRootFragment(R.id.drawerFragmentContainer, createContactFragment())
            }
            // 安全退出
            else if (position == 6) {
                loadRootFragment(R.id.drawerFragmentContainer, createLoginFragment())
            }
        }
    }
}
