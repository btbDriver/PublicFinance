package com.drive.finance

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import com.drive.finance.base.BaseActivity
import com.drive.finance.ui.tab.createInviteFragment
import com.drive.finance.ui.login.createLoginFragment
import com.drive.finance.ui.tab.createPickFragment
import com.drive.finance.ui.tab.createSuggestFragment
import com.drive.finance.ui.drawer.*
import com.drive.finance.ui.drawer.consult.createConsultFragment
import com.drive.finance.ui.drawer.contact.createContactFragment
import com.drive.finance.ui.drawer.public.createPublicFinanceFragment
import com.drive.finance.ui.drawer.team.createTeamFragment
import com.drive.finance.ui.drawer.user.createUserInfoFragment
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

        /**
         * 登录
         */
        drawerHomeLayout.onClick {
            mDrawerLayout.closeDrawers()
            loadRootFragment(R.id.drawerFragmentContainer, createLoginFragment(), true, true)
        }

        /**
         * 个人信息
         */
        drawerUserLayout.onClick {
            mDrawerLayout.closeDrawers()
            loadRootFragment(R.id.drawerFragmentContainer, createUserInfoFragment(), true, true)
        }

        /**
         * 团队管理
         */
        drawerTeamLayout.onClick {
            mDrawerLayout.closeDrawers()
            loadRootFragment(R.id.drawerFragmentContainer, createTeamFragment(), true, true)
        }

        /**
         * 财务中心
         */
        drawerCenterLayout.onClick {
            mDrawerLayout.closeDrawers()
            loadRootFragment(R.id.drawerFragmentContainer, createFinanceCenterFragment(), true, true)
        }

        /**
         * 大众财富
         */
        drawerPublicLayout.onClick {
            mDrawerLayout.closeDrawers()
            loadRootFragment(R.id.drawerFragmentContainer, createPublicFinanceFragment(), true, true)
        }

        /**
         * 公司咨询
         */
        drawerConsultLayout.onClick {
            mDrawerLayout.closeDrawers()
            loadRootFragment(R.id.drawerFragmentContainer, createConsultFragment(), true, true)
        }

        /**
         * 联系我们
         */
        drawerContactLayout.onClick {
            mDrawerLayout.closeDrawers()
            loadRootFragment(R.id.drawerFragmentContainer, createContactFragment(), true, true)
        }

        // 判断当前是否需要登录

    }


    /**
     * 奖金列表
     */
    @Subscribe
    fun onCreateBonusListFragmentEvent(event: CreateBonusListFragmentEvent) {
        loadRootFragment(R.id.drawerFragmentContainer, createBonusListFragment(), true, true)
    }

    /**
     * 提取收益
     */
    @Subscribe
    fun onCreatePickFragmentEvent(event: CreatePickFragmentEvent) {
        loadRootFragment(R.id.drawerFragmentContainer, createPickFragment(), true, true)
    }

    /**
     * 个人信息
     */
    @Subscribe
    fun onCreateUserInfoFragmentEvent(event: CreateUserInfoFragmentEvent) {
        loadRootFragment(R.id.drawerFragmentContainer, createUserInfoFragment(), true, true)
    }

    /**
     * 邀请链接
     */
    @Subscribe
    fun onCreateInviteFragmentEvent(event: CreateInviteFragmentEvent) {
        loadRootFragment(R.id.drawerFragmentContainer, createInviteFragment(), true, true)
    }

    /**
     * 我要留言
     */
    @Subscribe
    fun onCreateSuggestFragmentEvent(event: CreateSuggestFragmentEvent) {
        loadRootFragment(R.id.drawerFragmentContainer, createSuggestFragment(), true, true)
    }
}
