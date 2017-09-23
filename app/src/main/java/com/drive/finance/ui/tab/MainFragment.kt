package com.drive.finance.ui.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.drive.finance.*
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.network.model.HomeModel
import com.hwangjr.rxbus.RxBus
import com.jude.rollviewpager.RollPagerView
import com.jude.rollviewpager.adapter.LoopPagerAdapter
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class MainFragment : BaseFragment() {

    val rollPagerView by lazy {
        view?.findViewById(R.id.rollPagerView) as RollPagerView
    }
    val inviteLayout by lazy {
        view?.findViewById(R.id.inviteLayout) as LinearLayout
    }
    val bonusListLayout by lazy {
        view?.findViewById(R.id.bonusListLayout) as LinearLayout
    }
    val financeListLayout by lazy {
        view?.findViewById(R.id.financeListLayout) as LinearLayout
    }
    val pickLayout by lazy {
        view?.findViewById(R.id.pickLayout) as LinearLayout
    }
    val userInfoLayout by lazy {
        view?.findViewById(R.id.userInfoLayout) as LinearLayout
    }
    val suggestLayout by lazy {
        view?.findViewById(R.id.suggestLayout) as LinearLayout
    }
    val userNumText by lazy {
        view?.findViewById(R.id.userNumText) as TextView
    }
    val userStatusText by lazy {
        view?.findViewById(R.id.userStatusText) as TextView
    }
    val inviteCount by lazy {
        view?.findViewById(R.id.inviteCount) as TextView
    }
    val moneyText by lazy {
        view?.findViewById(R.id.moneyText) as TextView
    }
    val gpText by lazy {
        view?.findViewById(R.id.gpText) as TextView
    }
    val kreditText by lazy {
        view?.findViewById(R.id.kreditText) as TextView
    }
    val teamCount by lazy {
        view?.findViewById(R.id.teamCount) as TextView
    }
    val allyjText by lazy {
        view?.findViewById(R.id.allyjText) as TextView
    }
    val rebuy by lazy {
        view?.findViewById(R.id.rebuy) as TextView
    }

    val apiClient by lazy {
        APIClient()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rollPagerView.setAdapter(TestLoopAdapter(rollPagerView))

        bonusListLayout.onClick {
            RxBus.get().post(CreateBonusListFragmentEvent(""))
        }

        financeListLayout.onClick {
            if (parentFragment is TabHostFragment) {
                (parentFragment as TabHostFragment).showFinanceListFragmentEvent()
            }
        }

        pickLayout.onClick {
            RxBus.get().post(CreatePickFragmentEvent("1"))
        }

        userInfoLayout.onClick {
            RxBus.get().post(CreateUserInfoFragmentEvent(""))
        }

        inviteLayout.onClick {
            RxBus.get().post(CreateInviteFragmentEvent(""))
        }

        suggestLayout.onClick {
            RxBus.get().post(CreateSuggestFragmentEvent(""))
        }

        apiClient.requestHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ homeModel ->
                    updateUI(homeModel)
                }, {})
    }

    fun updateUI(homeModel: HomeModel) {
        userNumText.text = homeModel.username
        if (homeModel.status == "1") {
            userStatusText.text = "已激活"
        } else {
            userStatusText.text = "未激活"
        }
        inviteCount.text = homeModel.renum
        moneyText.text = homeModel.money
        gpText.text = homeModel.gp
        kreditText.text = homeModel.kredit
        teamCount.text = homeModel.teamcount
        allyjText.text = homeModel.allyj
        rebuy.text = homeModel.rebuy
    }
}

internal class TestLoopAdapter(viewPager: RollPagerView) : LoopPagerAdapter(viewPager) {
    private val imgs = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)

    override fun getView(container: ViewGroup, position: Int): View {
        val view = ImageView(container.context)
        view.setImageResource(imgs[position])
        view.scaleType = ImageView.ScaleType.CENTER_CROP
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return view
    }

    override fun getRealCount(): Int {
        return imgs.size
    }
}

fun createMainFragment() : MainFragment {
    return MainFragment()
}
