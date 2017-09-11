package com.drive.finance.ui.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
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
        return inflater?.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragment = createMainFragment()
        financeFragment = createFinanceFragment()
        mineFragment = createMineFragment()
        cardFragment = createCardFragment()
        loadMultipleRootFragment(R.id.fragmentTabContent, 0, mainFragment, financeFragment, mineFragment, cardFragment)
        showPosition(0)
        initView()
    }

    private fun initView() {
        mainLayout.onClick {
            showHideFragment(mainFragment)
            showPosition(0)
        }

        financeLayout.onClick {
            showHideFragment(financeFragment)
            showPosition(1)
        }

        mineLayout.onClick {
            showHideFragment(mineFragment)
            showPosition(2)
        }

        cardLayout.onClick {
            showHideFragment(cardFragment)
            showPosition(3)
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
}

fun createTabHostFragment() : TabHostFragment {
    return TabHostFragment()
}
