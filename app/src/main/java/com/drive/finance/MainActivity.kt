package com.drive.finance

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import com.drive.finance.base.BaseActivity
import com.drive.finance.ui.drawer.*
import com.drive.finance.ui.tab.createBonusListFragment
import com.drive.finance.ui.tab.createTabHostFragment
import com.hwangjr.rxbus.annotation.Subscribe
import org.jetbrains.anko.onClick

class MainActivity : BaseActivity() {

    val mDrawerLayout by lazy {
        findViewById(R.id.drawerLayout) as DrawerLayout
    }
    val toolbar by lazy{
        findViewById(R.id.toolbar) as Toolbar
    }
    val drawerHomeLayout by lazy {
        findViewById(R.id.drawerHomeLayout) as RelativeLayout
    }
    val drawerUserLayout by lazy {
        findViewById(R.id.drawerUserLayout) as RelativeLayout
    }
    val drawerTeamLayout by lazy {
        findViewById(R.id.drawerTeamLayout) as RelativeLayout
    }
    val drawerCenterLayout by lazy {
        findViewById(R.id.drawerCenterLayout) as RelativeLayout
    }
    val drawerPublicLayout by lazy {
        findViewById(R.id.drawerPublicLayout) as RelativeLayout
    }
    val drawerConsultLayout by lazy {
        findViewById(R.id.drawerConsultLayout) as RelativeLayout
    }
    val drawerContactLayout by lazy {
        findViewById(R.id.drawerContactLayout) as RelativeLayout
    }
    lateinit var menuTitles: Array<String>
    lateinit var arrayAdapter: ArrayAdapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close)
        mDrawerLayout.setDrawerListener(toggle)
        toggle.syncState()

        //设置菜单列表
        menuTitles = resources.getStringArray(R.array.menuList)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menuTitles)

        loadRootFragment(R.id.contentContainerLayout, createTabHostFragment())

        drawerHomeLayout.onClick {
            loadRootFragment(R.id.drawerFragmentContainer, createUserInfoFragment())
        }

        drawerUserLayout.onClick {
            loadRootFragment(R.id.drawerFragmentContainer, createUserInfoFragment())
        }

        drawerTeamLayout.onClick {
            loadRootFragment(R.id.drawerFragmentContainer, createTeamFragment())
        }

        drawerCenterLayout.onClick {
            loadRootFragment(R.id.drawerFragmentContainer, createTeamFragment())
        }

        drawerPublicLayout.onClick {
            loadRootFragment(R.id.drawerFragmentContainer, createPublicFinanceFragment())
        }

        drawerConsultLayout.onClick {
            loadRootFragment(R.id.drawerFragmentContainer, createConsultFragment())
        }

        drawerContactLayout.onClick {
            loadRootFragment(R.id.drawerFragmentContainer, createContactFragment())
        }
    }

    @Subscribe
    fun onCreateBounsListFragmentEvent(event: CreateBounsListFragmentEvent) {
        loadRootFragment(R.id.drawerFragmentContainer, createBonusListFragment())
    }
}
