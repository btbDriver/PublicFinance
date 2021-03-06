package com.drive.finance.ui.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.drive.finance.R
import com.drive.finance.ShowMineEvent
import com.drive.finance.base.BaseFragment
import com.hwangjr.rxbus.annotation.Subscribe
import org.jetbrains.anko.*


class TabHostFragment : BaseFragment() {

    val mainLayout by lazy {
        view?.findViewById(R.id.mainLayout) as LinearLayout
    }
    val mainImage by lazy {
        view?.findViewById(R.id.mainImage) as ImageView
    }
    val mainText by lazy {
        view?.findViewById(R.id.mainText) as TextView
    }

    val financeLayout by lazy {
        view?.findViewById(R.id.financeLayout) as LinearLayout
    }
    val financeImage by lazy {
        view?.findViewById(R.id.financeImage) as ImageView
    }
    val financeText by lazy {
        view?.findViewById(R.id.financeText) as TextView
    }

    val mineLayout by lazy {
        view?.findViewById(R.id.mineLayout) as LinearLayout
    }
    val mineImage by lazy {
        view?.findViewById(R.id.mineImage) as ImageView
    }
    val mineText by lazy {
        view?.findViewById(R.id.mineText) as TextView
    }

    val cardLayout by lazy {
        view?.findViewById(R.id.cardLayout) as LinearLayout
    }
    val cardImage by lazy {
        view?.findViewById(R.id.cardImage) as ImageView
    }
    val cardText by lazy {
        view?.findViewById(R.id.cardText) as TextView
    }

    lateinit var mainFragment: MainFragment
    lateinit var financeFragment: FinanceFragment
    lateinit var mineFragment: MineFragment
    lateinit var cardFragment: CardFragment

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragment = createMainFragment()
        financeFragment = createFinanceFragment(false)
        mineFragment = createMineFragment(false)
        cardFragment = createCardFragment(false)
        loadMultipleRootFragment(R.id.fragmentTabContent, 0, mainFragment, financeFragment, mineFragment, cardFragment)
        showPosition(0)
        initView()
    }

    var lastBackTime: Long = 0

    override fun onBackPressedSupport(): Boolean {
        if (lastBackTime == 0.toLong() || System.currentTimeMillis() - lastBackTime >= 3000) {
            Toast.makeText(activity, "在按一次退出程序", Toast.LENGTH_SHORT).show()
            lastBackTime = System.currentTimeMillis()
        } else {
            activity.finish()
        }
        return true
    }

    private fun initView() {
        mainLayout.onClick {
            showHideFragment(mainFragment)
            showPosition(0)
            mainFragment.refresh()
        }

        financeLayout.onClick {
            showHideFragment(financeFragment)
            showPosition(1)
            financeFragment.refresh()
        }

        mineLayout.onClick {
            showHideFragment(mineFragment)
            showPosition(2)
            mineFragment.refresh()
        }

        cardLayout.onClick {
            showHideFragment(cardFragment)
            showPosition(3)
            cardFragment.refresh()
        }
    }

    private fun showPosition(position: Int) {
        if (position == 0) {
            mainImage.imageResource = R.drawable.home_select
            mainText.textColor = resources.getColor(R.color.f1)
            financeImage.imageResource = R.drawable.finance_normal
            financeText.textColor = resources.getColor(R.color.f3)
            mineImage.imageResource = R.drawable.mine_normal
            mineText.textColor = resources.getColor(R.color.f3)
            cardImage.imageResource = R.drawable.card_normal
            cardText.textColor = resources.getColor(R.color.f3)
        } else if (position == 1) {
            mainImage.imageResource = R.drawable.home_normal
            mainText.textColor = resources.getColor(R.color.f3)
            financeImage.imageResource = R.drawable.finance_select
            financeText.textColor = resources.getColor(R.color.f1)
            mineImage.imageResource = R.drawable.mine_normal
            mineText.textColor = resources.getColor(R.color.f3)
            cardImage.imageResource = R.drawable.card_normal
            cardText.textColor = resources.getColor(R.color.f3)
        } else if (position == 2) {
            mainImage.imageResource = R.drawable.home_normal
            mainText.textColor = resources.getColor(R.color.f3)
            financeImage.imageResource = R.drawable.finance_normal
            financeText.textColor = resources.getColor(R.color.f3)
            mineImage.imageResource = R.drawable.mine_select
            mineText.textColor = resources.getColor(R.color.f1)
            cardImage.imageResource = R.drawable.card_normal
            cardText.textColor = resources.getColor(R.color.f3)
        } else {
            mainImage.imageResource = R.drawable.home_normal
            mainText.textColor = resources.getColor(R.color.f3)
            financeImage.imageResource = R.drawable.finance_normal
            financeText.textColor = resources.getColor(R.color.f3)
            mineImage.imageResource = R.drawable.mine_normal
            mineText.textColor = resources.getColor(R.color.f3)
            cardImage.imageResource = R.drawable.card_select
            cardText.textColor = resources.getColor(R.color.f1)
        }
    }

    fun showFinanceListFragmentEvent() {
        showHideFragment(financeFragment)
        showPosition(1)
        financeFragment.refresh()
    }

    @Subscribe
    fun onShowMineEvent(event: ShowMineEvent) {
        showHideFragment(mineFragment)
        showPosition(2)
        mainFragment.refresh()
    }
}

fun createTabHostFragment() : TabHostFragment {
    return TabHostFragment()
}
